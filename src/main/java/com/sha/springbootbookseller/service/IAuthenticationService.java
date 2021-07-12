package com.sha.springbootbookseller.service;

import com.sha.springbootbookseller.model.User;

/**
 * @author sa
 * @date 3.07.2021
 * @time 19:40
 */
public interface IAuthenticationService
{
    User signInAndReturnJWT(User signInRequest);
}
