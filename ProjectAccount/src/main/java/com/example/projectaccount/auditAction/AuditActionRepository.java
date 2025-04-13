package com.example.projectaccount.auditAction;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuditActionRepository extends CrudRepository<AuditAction, Integer> {

    Optional<AuditAction> findById(Integer id);

    Optional<AuditAction> getByCode(String code);
}