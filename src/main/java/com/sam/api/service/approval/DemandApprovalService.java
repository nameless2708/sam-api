package com.sam.api.service.approval;

import com.sam.api.db.entity.Demand;
import com.sam.api.db.entity.DemandApproval;
import com.sam.api.db.entity.User;
import com.sam.api.db.entity.department.DepartmentType;
import com.sam.api.db.entity.enums.DemandStatus;
import com.sam.api.db.repository.DemandApprovalRepository;
import com.sam.api.db.repository.DemandRepository;
import com.sam.api.exception.SamForbiddenException;
import com.sam.api.exception.SamNotFoundException;
import com.sam.api.service.approval.dto.DemandApprovalCreateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemandApprovalService {

    @Autowired
    private DemandApprovalRepository demandApprovalRepository;

    @Autowired
    private DemandRepository demandRepository;

    public void create(User user, Long demandId, DemandApprovalCreateReq req) {
        Demand demand = this.demandRepository.findById(demandId).orElseThrow(() -> new SamNotFoundException("Demand not found"));
        switch (demand.getStatus()) {
            case PENDING:
                throw new SamForbiddenException("Can not approve demand in PENDING status");
            case WAITING_FOR_LEVEL_2:
            case REJECTED_BY_LEVEL_2:
                if (!demand.getDepartment().getParent().equals(user.getDepartment())) {
                    throw new SamForbiddenException("Attempted to approve a demand not in your department");
                }
                if (user.getDepartment().getType() == DepartmentType.LEVEL_2) {
                    DemandApproval demandApproval = new DemandApproval();
                    demandApproval.setDemandId(demandId);
                    demandApproval.setApprovedBy(user.getId());
                    demandApproval.setApproved(req.isApproved());
                    demandApproval.setMessage(req.getMessage());
                    demandApprovalRepository.save(demandApproval);
                    if (req.isApproved()) {
                        demand.setStatus(DemandStatus.WAITING_FOR_LEVEL_1);
                    } else {
                        demand.setStatus(DemandStatus.REJECTED_BY_LEVEL_2);
                    }
                    demandRepository.save(demand);
                    return;
                }
                throw new SamForbiddenException("This demand is waiting for action from department level 2");
            case WAITING_FOR_LEVEL_1:
            case REJECTED_BY_LEVEL_1:
                if (user.getDepartment().getType() == DepartmentType.LEVEL_1) {
                    DemandApproval demandApproval = new DemandApproval();
                    demandApproval.setDemandId(demandId);
                    demandApproval.setApprovedBy(user.getId());
                    demandApproval.setApproved(req.isApproved());
                    demandApprovalRepository.save(demandApproval);
                    if (req.isApproved()) {
                        demand.setStatus(DemandStatus.APPROVED);
                    } else {
                        demand.setStatus(DemandStatus.REJECTED_BY_LEVEL_1);
                    }
                    demandRepository.save(demand);
                    return;
                }
                throw new SamForbiddenException("This demand is waiting for action from department level 1");
            case APPROVED:
                throw new SamForbiddenException("Can not approve demand in APPROVED status");
        }
    }
}
