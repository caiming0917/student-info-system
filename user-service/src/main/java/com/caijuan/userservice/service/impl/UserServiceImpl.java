package com.caijuan.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.caijuan.commons.common.ErrorCode;
import com.caijuan.commons.exception.BusinessException;
import com.caijuan.userservice.controller.vo.UserRegisterRequest;
import com.caijuan.userservice.entity.User;
import com.caijuan.userservice.mapper.UserMapper;
import com.caijuan.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final String SALT = "cai_juan";

    private static final String TOKEN_PREFIX = "token:";
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

    @Override
    public boolean logout(long id) {
        redisTemplate.opsForValue().getAndDelete(TOKEN_PREFIX + id);
        return true;
    }

    @Override
    public String login(String account, String password, boolean rememberMe) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", account);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        if (!encryptPassword.equals(password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        final String tokenValue = "token";

        if (rememberMe) {
            // 生成时间长的 token

            redisTemplate.opsForValue().set(TOKEN_PREFIX + user.getId(), tokenValue, 7, TimeUnit.DAYS);
        } else {
            // 生成时间短的 token
            redisTemplate.opsForValue().set(TOKEN_PREFIX + user.getId(), tokenValue, 30, TimeUnit.MINUTES);
        }
        return tokenValue;
    }
}
