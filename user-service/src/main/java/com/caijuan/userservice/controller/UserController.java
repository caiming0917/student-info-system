package com.caijuan.userservice.controller;


import com.caijuan.commons.common.BaseResponse;
import com.caijuan.commons.common.ErrorCode;
import com.caijuan.commons.common.ResultUtils;
import com.caijuan.commons.exception.BusinessException;
import com.caijuan.userservice.controller.vo.UserRegisterRequest;
import com.caijuan.userservice.entity.User;
import com.caijuan.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cai juan
 * @since 2023-02-25
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(result);
    }

    @GetMapping("/list")
    public BaseResponse<List<User>> getUsers() {
        return ResultUtils.success(userService.list());
    }

    @GetMapping("/{id}")
    public BaseResponse<User> getUser(@PathVariable("id") long id) {
        return ResultUtils.success(userService.getById(id));
    }

    @PutMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id) {
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    @PutMapping("/logout")
    public BaseResponse<Boolean> logout(@RequestBody long id){
        boolean b = userService.logout(id);
        return ResultUtils.success(b);
    }

    @PutMapping("/login")
    public BaseResponse<String> login(HttpServletRequest request, HttpServletResponse response){
        String account = request.getHeader("account");
        String password = request.getHeader("password");
        String rememberMe = request.getHeader("rememberMe");
        String token = userService.login(account, password, Boolean.parseBoolean(rememberMe));
        response.setHeader("token", "token");
        return ResultUtils.success("success");
    }
}
