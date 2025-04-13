package com.example.projectaccount.user;

import com.example.projectaccount.userAddress.UserAddressDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses={UserAddressDtoMapper.class})
public interface UserDtoMapper {

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

}
