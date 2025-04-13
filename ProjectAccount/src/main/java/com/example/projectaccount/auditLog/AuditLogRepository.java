package com.example.projectaccount.auditLog;

import com.example.projectaccount.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuditLogRepository extends CrudRepository<AuditLog, Integer> {





}
