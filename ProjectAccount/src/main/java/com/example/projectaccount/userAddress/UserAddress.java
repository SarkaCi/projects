package com.example.projectaccount.userAddress;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_address")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer id;

    @Column(name = "street", length = 50, nullable = false)
    public String street;

    @Column(name = "building_number", length = 50, nullable = false)
    public Integer buildingNumber;

    @Column(name = "city", length = 50, nullable = false)
    public String city;

    @Column(name = "post_number", length = 50, nullable = false)
    public Integer postNumber;

    @Column(name = "country", length = 50, nullable = false)
    public String country;

    @Column(name = "create_by", length = 50, nullable = false)
    public String createBy;

    @Column(name = "create_when", nullable = false)
    public LocalDateTime createWhen;

    @Column(name = "update_by", length = 50)
    public String updateBy;

    @Column(name = "update_when")
    public LocalDateTime updateWhen;

}

