package com.sam.api.service.demand;

import com.sam.api.common.PaginateRes;
import com.sam.api.db.entity.Demand;
import com.sam.api.db.entity.EquipmentDemand;
import com.sam.api.db.entity.User;
import com.sam.api.db.entity.department.Department;
import com.sam.api.db.entity.department.DepartmentType;
import com.sam.api.db.entity.enums.DemandStatus;
import com.sam.api.db.repository.DemandRepository;
import com.sam.api.db.repository.DepartmentRepository;
import com.sam.api.db.repository.EquipmentDemandRepository;
import com.sam.api.exception.SamForbiddenException;
import com.sam.api.exception.SamNotFoundException;
import com.sam.api.service.demand.dto.*;
import com.sam.api.service.equipment.EquipmentService;
import com.sam.api.service.equipment.dto.EquipmentUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sam.api.service.demand.DemandSpecification.*;

@Service
public class DemandService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EquipmentDemandRepository equipmentDemandRepository;

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private DemandMapper mapper;

    @Transactional
    public void create(User user, DemandCreateRequest req) {
        Demand demand = new Demand();
        demand.setDepartmentId(user.getDepartment().getId());
        demand.setCreatedBy(user.getId());
        demand.setStatus(DemandStatus.PENDING);
        demand.setDescription(req.getDescription());
        demand = demandRepository.save(demand);

        Set<EquipmentDemand> equipmentDemands = new HashSet<>();
        for (DemandCreateRequest.Equipment reqEquipment : req.getEquipments()) {
            EquipmentDemand equipmentDemand = new EquipmentDemand();
            equipmentDemand.setEquipmentId(reqEquipment.getId());
            equipmentDemand.setDemandId(demand.getId());
            equipmentDemand.setQuantity(reqEquipment.getQuantity());
            equipmentDemands.add(equipmentDemand);
        }
        this.equipmentDemandRepository.saveAll(equipmentDemands);
    }

    public PaginateRes<DemandResponse> index(User user, DemandQuery query) {
        Department userDepartment = user.getDepartment();
        Specification<Demand> specification = Specification.where(null);
        // Getting all level 3 department of user department
        Set<Long> level3DepartmentIds = this.getDepartmentLevel3IdsOfDepartment(userDepartment);
        if (userDepartment.getType() == DepartmentType.LEVEL_2) {
            specification = specification.and(statusNot(DemandStatus.PENDING));
        }

        if (userDepartment.getType() == DepartmentType.LEVEL_1) {
            specification = specification.and(statusNotIn(
                    DemandStatus.PENDING,
                    DemandStatus.WAITING_FOR_LEVEL_2,
                    DemandStatus.REJECTED_BY_LEVEL_2
            ));
        }
        specification = specification.and(departmentIdIn(level3DepartmentIds));
        PageRequest page = PageRequest.of(query.getPage(), query.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Demand> demands = this.demandRepository.findAll(specification, page);
        return new PaginateRes<>(demands.map(x -> mapper.mapToRes(x)));
    }

    public DemandDetailResponse findById(User user, Long demandId) {
        // TODO authorization
        Demand demand = this.demandRepository.findById(demandId).orElseThrow(() -> new SamNotFoundException("Demand not found"));
        demand.setDemandApprovals(demand.getDemandApprovals());
        return mapper.mapToResDetail(demand);
    }

    public void submit(User user, Long demandId) {
        // TODO use user to authorization
        Demand demand = this.demandRepository.findById(demandId).orElseThrow(() -> new SamNotFoundException("Demand not found"));
        if (demand.getStatus() != DemandStatus.PENDING) {
            throw new SamForbiddenException("This demand is already submitted to higher department");
        }
        demand.setStatus(DemandStatus.WAITING_FOR_LEVEL_2);
        demandRepository.save(demand);
    }

    @Transactional
    public void update(User user, Long demandId, DemandUpdateRequest request) {
        // TODO user authorization
        Demand demand = this.demandRepository.findById(demandId).orElseThrow(() -> new SamNotFoundException("Demand not found"));
        if (demand.getStatus() != DemandStatus.PENDING) {
            throw new SamForbiddenException("Submitted demand can not be edited");
        }
        demand.setDescription(request.getDescription());
        this.equipmentDemandRepository.deleteByDemandId(demandId);
        Set<EquipmentDemand> equipmentDemands = new HashSet<>();
        for (DemandUpdateRequest.Equipment reqEquipment : request.getEquipments()) {
            EquipmentDemand equipmentDemand = new EquipmentDemand();
            equipmentDemand.setEquipmentId(reqEquipment.getId());
            equipmentDemand.setDemandId(demand.getId());
            equipmentDemand.setQuantity(reqEquipment.getQuantity());
            equipmentDemands.add(equipmentDemand);
        }
        this.equipmentDemandRepository.saveAll(equipmentDemands);
    }

    @Transactional
    public void delete(User user, Long demandId) {
        Demand demand = this.demandRepository.findById(demandId).orElseThrow(() -> new SamNotFoundException("Demand not found"));
        if (demand.getStatus() != DemandStatus.PENDING) {
            throw new SamForbiddenException("Submitted demand can not be deleted");
        }
        this.equipmentDemandRepository.deleteByDemandId(demandId);
        this.demandRepository.deleteById(demandId);
    }

    private Set<Long> getDepartmentLevel3IdsOfDepartment(Department department) {
        Set<Long> level3DepartmentIds = new HashSet<>();
        switch (department.getType()) {
            case LEVEL_1: {
                // getting all level 2
                List<Long> level2Ids = this.departmentRepository.findAllByParentIdIn(Collections.singletonList(department.getId()));
                // getting all level 3
                List<Long> level3Ids = this.departmentRepository.findAllByParentIdIn(level2Ids);
                level3DepartmentIds.addAll(level3Ids);
            }
            break;
            case LEVEL_2: {
                List<Long> level3Ids = this.departmentRepository.findAllByParentIdIn(Collections.singletonList(department.getId()));
                level3DepartmentIds.addAll(level3Ids);
            }
            break;
            case LEVEL_3:
                level3DepartmentIds.add(department.getId());
                break;
        }

        return level3DepartmentIds;
    }

    public DemandStatistic statistic(User user) {
        return this.getStatisticByDepartment(user.getDepartment());
    }

    public DemandStatistic getStatisticByDepartment(Department department) {
        Set<Long> level3DepartmentIds = this.getDepartmentLevel3IdsOfDepartment(department);
        List<DemandRepository.StatusStatistic> statusStatistics = this.demandRepository.countGroupByStatus(level3DepartmentIds);
        DemandStatistic demandStatistic = new DemandStatistic();
        demandStatistic.setData(statusStatistics.stream().collect(Collectors.toMap(
                DemandRepository.StatusStatistic::getStatus,
                DemandRepository.StatusStatistic::getCount)));
        return demandStatistic;
    }

    @Transactional
    public void confirm(User user, Long demandId) {
        // TODO use user to authorization
        Demand demand = this.demandRepository.findById(demandId).orElseThrow(() -> new SamNotFoundException("Demand not found"));
        if (demand.getStatus() != DemandStatus.APPROVED) {
            throw new SamForbiddenException("Cannot confirm due to status");
        }
        demand.setStatus(DemandStatus.DONE);
        demandRepository.save(demand);
        demand.getEquipmentDemands().stream().forEach(equipmentDemand -> {
            EquipmentUpdateReq updateReq = new EquipmentUpdateReq();
            updateReq.setQuantity(equipmentDemand.getQuantity());
            equipmentService.increaseQuantity(equipmentDemand.getEquipmentId(), equipmentDemand.getQuantity());
        });
    }
}
