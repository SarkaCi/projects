package com.example.projectaccount.userAddress;

import com.example.projectaccount.user.User;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserAddressRepository extends CrudRepository<UserAddress, Integer> {

    Optional<UserAddress> findById(Integer id);
}