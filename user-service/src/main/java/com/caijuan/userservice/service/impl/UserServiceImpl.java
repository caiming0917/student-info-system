package com.caijuan.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.caijuan.commons.common.ErrorCode;
import com.caijuan.commons.exception.BusinessException;
import com.caijuan.userservice.controller.vo.UserRegisterRequest;
import com.caijuan.userservice.entity.User;
import com.caijuan.userservice.mapper.UserMapper;
import com.caijuan.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.caijuan.userservice.controller.vo.UserRegisterRequest.checkUserAccount;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cai juan
 * @since 2023-02-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final String SALT = "cai_juan";
    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(UserRegisterRequest registerRequest) {
        // 1. 校验
        checkUserAccount(registerRequest);
        String account = registerRequest.getUserAccount().trim();
        String password = registerRequest.getUserAccount().trim();
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", registerRequest.getUserAccount());
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setAccount(account);
        user.setPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }
}
