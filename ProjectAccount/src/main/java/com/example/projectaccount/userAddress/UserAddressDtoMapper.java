package com.example.projectaccount.userAddress;

import com.example.projectaccount.user.UserAddressDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserAddressDtoMapper {

    UserAddressDto toDto(UserAddress userAddress);
}
