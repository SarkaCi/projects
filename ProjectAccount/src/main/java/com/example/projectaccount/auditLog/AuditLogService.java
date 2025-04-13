package com.example.projectaccount.auditLog;

import com.example.projectaccount.SessionService;
import com.example.projectaccount.account.Account;
import com.example.projectaccount.auditAction.AuditAction;
import com.example.projectaccount.auditAction.AuditActionEnum;
import com.example.projectaccount.auditAction.AuditActionService;
import com.example.projectaccount.user.User;
import com.example.projectaccount.userAddress.UserAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    private final AuditLogMapper auditLogMapper;

    private final SessionService sessionService;

    private final AuditActionService auditActionService;

    public void saveAccount(Account account) {
        save(account, null, "Save account", AuditActionEnum.UPDATE);
    }

    public void deleteAccount(Account account) {
        save(account, null, "Delete account", AuditActionEnum.DELETE);
    }

    public void saveUser(User user) {
        save(user.account, user, "Save user", AuditActionEnum.UPDATE);
    }

    public void deleteUser(User user) {
        save(user.account, user, "Delete user", AuditActionEnum.DELETE);
    }

    public void saveUserAddress(UserAddress userAddress, User user) {
        save(user.account, user, "Save userAddress id:" + userAddress.id, AuditActionEnum.UPDATE);
    }

    private void save(Account account, User user, String message, AuditActionEnum action) {
        AuditAction auditAction = auditActionService.getAuditAction(action);
        AuditLog auditLog = auditLogMapper.toEntity(account,
                user,
                auditAction,
                message,
                LocalDateTime.now(),
                sessionService.getSessionUserId(),
                LocalDateTime.now());
        auditLogRepository.save(auditLog);
    }


}
