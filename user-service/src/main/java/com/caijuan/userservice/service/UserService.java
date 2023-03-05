package com.caijuan.userservice.service;

import com.caijuan.userservice.controller.vo.UserRegisterRequest;
import com.caijuan.userservice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cai juan
 * @since 2023-02-25
 */
public interface UserService extends IService<User> {

    long userRegister(UserRegisterRequest registerRequest);

    boolean logout(long id);

    String login(String account, String password, boolean rememberMe);
}
