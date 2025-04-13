package com.example.projectaccount.account;

import com.example.projectaccount.auditLog.AuditLog;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer id;

    @Column(name = "account_type", length = 50, nullable = false)
    public String accountType;

    @Column(name = "account_status", length = 50, nullable = false)
    public String accountStatus;

    @Column(name = "date_created")
    public LocalDateTime dateCreated;

    @Column(name = "create_by", length = 50, nullable = false)
    public String createBy;

    @Column(name = "create_when", nullable = false)
    public LocalDateTime createWhen;

    @Column(name = "update_by", length = 50)
    public String updateBy;

    @Column(name = "update_when")
    public LocalDateTime updateWhen;
}