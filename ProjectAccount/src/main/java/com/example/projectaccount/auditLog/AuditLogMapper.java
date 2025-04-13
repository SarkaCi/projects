package com.example.projectaccount.auditLog;

import com.example.projectaccount.account.Account;
import com.example.projectaccount.auditAction.AuditAction;
import com.example.projectaccount.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper
public interface AuditLogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", source = "account")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "action", source = "action")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "timestamp", source = "timestamp")
    @Mapping(target = "createBy", source = "createBy")
    @Mapping(target = "createWhen", source = "createWhen")
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "updateWhen", ignore = true)
    AuditLog toEntity(Account account,
                      User user,
                      AuditAction action,
                      String message,
                      LocalDateTime timestamp,
                      String createBy,
                      LocalDateTime createWhen);
}