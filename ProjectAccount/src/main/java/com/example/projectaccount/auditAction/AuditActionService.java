package com.example.projectaccount.auditAction;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditActionService {

    private final AuditActionRepository auditActionRepository;

    public AuditAction getAuditAction(AuditActionEnum auditActionEnum) {
        Optional<AuditAction> auditAction = auditActionRepository.getByCode(auditActionEnum.getAction());

        return auditAction.orElseThrow(() -> new IllegalStateException("Cannot found auditAction for code: " + auditActionEnum.getAction()));
    }

}
