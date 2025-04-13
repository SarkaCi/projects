package com.example.projectaccount.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto implements Serializable {
    public  Integer id;
    public  String userName;
    public  String firstName;
    public  String lastName;
    public  String email;
    public  String password;
    public  boolean enabled;
    public  String createBy;
    public  LocalDateTime createWhen;
    public  String updateBy;
    public  LocalDateTime updateWhen;
    public  UserAddressDto userAddress;
}