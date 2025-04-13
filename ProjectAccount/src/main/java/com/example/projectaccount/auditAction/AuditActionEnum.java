package com.example.projectaccount.auditAction;

import lombok.Getter;

@Getter
public enum AuditActionEnum {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    private final String action;

    AuditActionEnum(String action) {
        this.action = action;
    }

}
