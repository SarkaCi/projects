package com.example.projectaccount.auditLog;

import com.example.projectaccount.auditAction.AuditAction;
import com.example.projectaccount.account.Account;
import com.example.projectaccount.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    public Account account;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "audit_action_id", nullable = false)
    public AuditAction action;

    @Column(name = "message", length = 500, nullable = false)
    public String message;

    @Column(name = "timestamp")
    public LocalDateTime timestamp;

    @Column(name = "create_by", length = 50, nullable = false)
    public String createBy;

    @Column(name = "create_when", nullable = false)
    public LocalDateTime createWhen;

    @Column(name = "update_by", length = 50)
    public String updateBy;

    @Column(name = "update_when")
    public LocalDateTime updateWhen;


}