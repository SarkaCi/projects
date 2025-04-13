package com.example.projectaccount.auditAction;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "audit_action")
public class AuditAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "create_by", length = 50, nullable = false)
    private String createBy;

    @Column(name = "create_when", nullable = false)
    private LocalDateTime createWhen;

    @Column(name = "update_by", length = 50)
    private String updateBy;

    @Column(name = "update_when")
    private LocalDateTime updateWhen;


}

