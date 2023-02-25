package com.caijuan.userservice.mapper;

import com.caijuan.userservice.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cai juan
 * @since 2023-02-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
