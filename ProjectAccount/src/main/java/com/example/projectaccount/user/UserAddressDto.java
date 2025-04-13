package com.example.projectaccount.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.projectaccount.userAddress.UserAddress}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserAddressDto implements Serializable {
    public Integer id;
    public  String street;
    public  Integer buildingNumber;
    public  String city;
    public  Integer postNumber;
    public  String country;
    public  String createBy;
    public  LocalDateTime createWhen;
    public  String updateBy;
    public  LocalDateTime updateWhen;
}