package com.example.projectaccount.user;


import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Integer> {
    public Long countById(Integer id);

    List<User> findByAccountId(Integer accountId);
}