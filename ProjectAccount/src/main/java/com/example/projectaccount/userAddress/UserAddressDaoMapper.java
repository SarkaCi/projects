package com.example.projectaccount.userAddress;

import com.example.projectaccount.user.User;
import com.example.projectaccount.user.UserAddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper
public interface UserAddressDaoMapper {

    @Mapping(target = "createBy", source = "sessionUserId")
    @Mapping(target = "createWhen", source = "now")
    UserAddress toEntity(UserAddressDto userAddressDto, String sessionUserId, LocalDateTime now);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "createWhen", ignore = true)
    @Mapping(target = "updateBy", source = "sessionUserId")
    @Mapping(target = "updateWhen", source = "now")
    void toUpdateEntity(UserAddressDto userAddressDto, @MappingTarget UserAddress userAddress, String sessionUserId, LocalDateTime now);


}
