package com.example.projectaccount.user;

import com.example.projectaccount.account.Account;
import com.example.projectaccount.userAddress.UserAddress;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer id;

    @Column(name = "user_name", length = 50, nullable = false)
    public String userName;

    @Column(name = "first_name", length = 50, nullable = false)
    public String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    public String lastName;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    public String email;

    @Column(name = "password", length = 50, nullable = false)
    public String password;

    public boolean enabled;

    @Column(name = "create_by", length = 50, nullable = false)
    public String createBy;

    @Column(name = "create_when", nullable = false)
    public LocalDateTime createWhen;

    @Column(name = "update_by", length = 50)
    public String updateBy;

    @Column(name = "update_when")
    public LocalDateTime updateWhen;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    public Account account;

    @ManyToOne
    @JoinColumn(name = "user_address_id", nullable = false)
    public UserAddress userAddress;

}
