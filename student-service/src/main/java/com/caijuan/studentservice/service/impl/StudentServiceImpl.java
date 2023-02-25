package com.caijuan.studentservice.service.impl;

import com.caijuan.studentservice.entity.Student;
import com.caijuan.studentservice.mapper.StudentMapper;
import com.caijuan.studentservice.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cai juan
 * @since 2023-02-25
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
