package com.caijuan.studentservice.mapper;

import com.caijuan.studentservice.entity.Student;
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
public interface StudentMapper extends BaseMapper<Student> {

}
