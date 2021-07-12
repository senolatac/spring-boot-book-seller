package com.sha.springbootbookseller.service;

import com.sha.springbootbookseller.model.User;

import java.util.Optional;

/**
 * @author sa
 * @date 3.07.2021
 * @time 18:03
 */
public interface IUserService
{
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    void makeAdmin(String username);
}
