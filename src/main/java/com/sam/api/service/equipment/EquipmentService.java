package com.sam.api.service.equipment;

import com.sam.api.common.PaginateRes;
import com.sam.api.db.entity.Equipment;
import com.sam.api.db.entity.Equipment_;
import com.sam.api.db.entity.User;
import com.sam.api.db.entity.department.Department_;
import com.sam.api.db.repository.EquipmentRepository;
import com.sam.api.exception.SamException;
import com.sam.api.exception.SamForbiddenException;
import com.sam.api.exception.SamNotFoundException;
import com.sam.api.service.equipment.dto.EquipmentCreateReq;
import com.sam.api.service.equipment.dto.EquipmentQuery;
import com.sam.api.service.equipment.dto.EquipmentRes;
import com.sam.api.service.equipment.dto.EquipmentUpdateReq;
import com.sam.api.utils.CharacterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.sam.api.service.equipment.EquipmentSpecification.nameLike;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentMapper mapper;

    public EquipmentService() {
    }

    public PaginateRes<EquipmentRes> index(User user, EquipmentQuery query) {
        Specification<Equipment> specification = Specification.where(null);
        if (query.getSearch() != null && !query.getSearch().isEmpty()) {
            String removedAccent = CharacterUtils.removeAccent(query.getSearch());
            specification = specification.and(nameLike(removedAccent));
        }
        Page<Equipment> page = this.equipmentRepository.findAll(specification.and((root, query1, criteriaBuilder) -> criteriaBuilder.equal(root.get(Equipment_.DEPARTMENT), user.getDepartment())), query.toPageable());
        return new PaginateRes<>(page.map(x -> mapper.mapEntityToRes(x)));
    }

    public void create(User user, EquipmentCreateReq req) {
        Equipment equipment = new Equipment();
        equipment.setName(req.getName());
        equipment.setPrice(req.getPrice());
        equipment.setQuantity(req.getQuantity());
        equipment.setDescription(req.getDescription());
        equipment.setGrade(req.getGrade());
        equipment.setSubject(req.getSubject());
        equipment.setDepartment(user.getDepartment());
        this.equipmentRepository.save(equipment);
    }

    public void update(Long id, EquipmentUpdateReq req) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new SamNotFoundException("Equipment not found"));
        equipment.setQuantity(req.getQuantity() != null ? req.getQuantity() : equipment.getQuantity());
        equipment.setName(req.getName() != null ? req.getName() : equipment.getName());
        equipment.setPrice(req.getPrice() != null ? req.getPrice() : equipment.getPrice());
        equipment.setDescription(req.getDescription() != null ? req.getDescription() : equipment.getDescription());
        equipment.setGrade(req.getGrade() != null ? req.getGrade() : equipment.getGrade());
        equipment.setSubject(req.getSubject() != null ? req.getSubject() : equipment.getSubject());
        equipmentRepository.save(equipment);
    }

    public void increaseQuantity(Long id, Long bonusCount) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new SamNotFoundException("Equipment not found"));
        equipment.setQuantity(equipment.getQuantity() + bonusCount);
        equipmentRepository.save(equipment);
    }

    public void delete(Long id) {
        equipmentRepository.findById(id).orElseThrow(() -> new SamNotFoundException("Equipment not found"));
        equipmentRepository.deleteById(id);
    }
}
