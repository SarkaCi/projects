CREATE TABLE account
(
    id             INT AUTO_INCREMENT NOT NULL,
    account_type   VARCHAR(50)        NOT NULL,
    account_status VARCHAR(50)        NOT NULL,
    date_created   datetime           NULL,
    create_by      VARCHAR(50)        NOT NULL,
    create_when    datetime           NOT NULL,
    update_by      VARCHAR(50)        NULL,
    update_when    datetime           NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE user_address
(
    id              INT AUTO_INCREMENT NOT NULL,
    street          VARCHAR(50)        NOT NULL,
    building_number INT                NOT NULL,
    city            VARCHAR(50)        NOT NULL,
    post_number     INT                NOT NULL,
    country         VARCHAR(50)        NOT NULL,
    create_by       VARCHAR(50)        NOT NULL,
    create_when     datetime           NOT NULL,
    update_by       VARCHAR(50)        NULL,
    update_when     datetime           NULL,
    CONSTRAINT pk_useraddress PRIMARY KEY (id)
);

CREATE TABLE user
(
    id              INT AUTO_INCREMENT NOT NULL,
    user_address_id INT                NOT NULL,
    account_id      INT                NOT NULL,
    user_name       VARCHAR(50)        NOT NULL,
    first_name      VARCHAR(50)        NOT NULL,
    last_name       VARCHAR(50)        NOT NULL,
    email           VARCHAR(50)        NOT NULL,
    password        VARCHAR(50)        NOT NULL,
    enabled         BIT(1)             NOT NULL,
    create_by       VARCHAR(50)        NOT NULL,
    create_when     datetime           NOT NULL,
    update_by       VARCHAR(50)        NULL,
    update_when     datetime           NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_ID_account FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_ID_user_address FOREIGN KEY (user_address_id) REFERENCES user_address (id);



CREATE TABLE audit_action
(
    id          INT AUTO_INCREMENT NOT NULL,
    code        VARCHAR(255)       NULL,
    create_by   VARCHAR(50)        NOT NULL,
    create_when datetime           NOT NULL,
    update_by   VARCHAR(50)        NULL,
    update_when datetime           NULL,
    CONSTRAINT pk_action PRIMARY KEY (id)
);

CREATE TABLE audit_log
(
    id              INT AUTO_INCREMENT NOT NULL,
    user_id         INT                NOT NULL,
    account_id      INT                NOT NULL,
    audit_action_id INT                NOT NULL,
    message         VARCHAR(500)       NOT NULL,
    timestamp       datetime           NULL,
    create_by       VARCHAR(50)        NOT NULL,
    create_when     datetime           NOT NULL,
    update_by       VARCHAR(50)        NULL,
    update_when     datetime           NULL,
    CONSTRAINT pk_auditlog PRIMARY KEY (id)
);

ALTER TABLE audit_log
    ADD CONSTRAINT FK_AUDITLOG_ON_ID FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE audit_log
    ADD CONSTRAINT FK_AUDITLOG_ON_IDmJqc0L FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE audit_log
    ADD CONSTRAINT FK_AUDITLOG_ON_IDn6Wg3C FOREIGN KEY (audit_action_id) REFERENCES audit_action (id);