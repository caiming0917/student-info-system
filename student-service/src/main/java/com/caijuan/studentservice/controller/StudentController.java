package com.caijuan.studentservice.controller;


import com.caijuan.studentservice.entity.Student;
import com.caijuan.studentservice.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cai juan
 * @since 2023-02-25
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @ApiOperation(value = "获取根据ID查询学生信息", tags = {"学生信息"}, notes = "无数据返回404")
    @GetMapping("/getUserInfo")
    public Student getUserInfo(@ApiParam(name = "id", value = "学生id", required = true) Long id) {
        // userService 可忽略，是业务逻辑
        return studentService.getById(id);
    }
}
