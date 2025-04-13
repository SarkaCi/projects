package com.example.projectaccount.user;

import com.example.projectaccount.account.Account;
import com.example.projectaccount.userAddress.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper
public interface UserDaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createBy", source = "sessionUserId")
    @Mapping(target = "createWhen", source = "now")
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "updateWhen", ignore = true)
    @Mapping(target = "userAddress", source = "userAddress")
    User toEntity(UserDto userDto, Account account, UserAddress userAddress, String sessionUserId, LocalDateTime now);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
 /*   @Mapping(target = "userAddress", ignore = true)*/
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "createWhen", ignore = true)
    @Mapping(target = "updateBy", source = "sessionUserId")
    @Mapping(target = "updateWhen", source = "now")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "userAddress", source = "userAddress")
    void toUpdateEntity(UserDto userDto, String password, @MappingTarget User user, String sessionUserId, LocalDateTime now, UserAddress userAddress);
}
