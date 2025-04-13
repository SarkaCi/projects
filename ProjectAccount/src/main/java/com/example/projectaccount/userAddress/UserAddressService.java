package com.example.projectaccount.userAddress;

import com.example.projectaccount.SessionService;
import com.example.projectaccount.auditLog.AuditLogService;
import com.example.projectaccount.user.UserAddressDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;

    private final UserAddressDaoMapper userAddressDaoMapper;

    private final SessionService sessionService;

    public UserAddress save(UserAddressDto userAddressDto) {

        UserAddress persistedUserAddress;

        if (userAddressDto.getId() == null) {
            UserAddress userAddress = userAddressDaoMapper.toEntity(userAddressDto, sessionService.getSessionUserId(), LocalDateTime.now());
            persistedUserAddress = userAddressRepository.save(userAddress);
        } else {
            UserAddress userAddress = userAddressRepository.findById(userAddressDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("UserAddress not found with id: " + userAddressDto.getId()));

            userAddressDaoMapper.toUpdateEntity(userAddressDto, userAddress, sessionService.getSessionUserId(), LocalDateTime.now());
            persistedUserAddress = userAddressRepository.save(userAddress);
        }
        return persistedUserAddress;
    }
}


