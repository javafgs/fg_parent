package com.fg.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fg.commonutils.Result;
import com.fg.eduservice.entity.EduTeacher;
import com.fg.eduservice.entity.vo.TeacherQuery;
import com.fg.eduservice.service.EduTeacherService;
import com.fg.servicebase.exception.FgException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author javafg
 * @since 2021-01-12
 */
@RestController
@Api(description = "讲师管理")
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public Result findAll(){
        List<EduTeacher> list=eduTeacherService.list(null);
       /* try {
            int a = 10/0;
        }catch(Exception e) {
            throw new FgException(2000,"分母错误请核对");
        }*/
        return Result.success().data("list",list);
    }
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public Result removeById(
            @ApiParam(name="id",value="讲师id",required = true)
            @PathVariable String id){
        boolean flag=eduTeacherService.removeById(id);
        if(flag){
            return Result.success();
        }else{
            return Result.error();
        }
    }
    @ApiOperation(value = "无条件分页查询讲师列表")
    @GetMapping("/page/{current}/{limit}")
    public Result page(
            @ApiParam(name="current",value="当前页",required = true)
            @PathVariable long current,
            @ApiParam(name="limit",value="显示条数",required = true)
            @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        //调用下面page方法的时候，分页数据会在底层封装到pageTeacher里面
        eduTeacherService.page(pageTeacher,null);
        //总记录数
        long total=pageTeacher.getTotal();
        //数据list集合
        List<EduTeacher> list=pageTeacher.getRecords();
        return Result.success().data("total", total).data("rows", list);
    }
    @ApiOperation(value = "多条件分页查询讲师列表")
    @PostMapping("/pageCondition/{current}/{limit}")
    public Result pageCondition(@ApiParam(name="current",value="当前页",required = true)
                                    @PathVariable long current,
                                @ApiParam(name="limit",value="显示条数",required = true)
                                    @PathVariable long limit,
                                @ApiParam(name="teacherQuery",value="参数对象",required = false)
                                @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        QueryWrapper<EduTeacher>queryWrapper=new QueryWrapper<>();
        String name=teacherQuery.getName();
        Integer level=teacherQuery.getLevel();
        String begin=teacherQuery.getBegin();
        String end=teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(level!=null){
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
        //调用下面page方法的时候，分页数据会在底层封装到pageTeacher里面
        eduTeacherService.page(pageTeacher,queryWrapper);
        //总记录数
        long total=pageTeacher.getTotal();
        //数据list集合
        List<EduTeacher> list=pageTeacher.getRecords();
        return Result.success().data("total", total).data("rows", list);
    }
    @ApiOperation(value="添加讲师")
    @PostMapping("/add")
    public Result add(
            @ApiParam(name = "eduTeacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher){
        boolean flag=eduTeacherService.save(eduTeacher);
        if(flag){
            return Result.success();
        }else{
            return Result.error();
        }
    }
    @ApiOperation(value="获取单条讲师")
    @GetMapping("/{id}")
    public Result getById(
            @ApiParam(name="id",value="讲师id",required = true)
            @PathVariable String id){
        EduTeacher eduTeacher=eduTeacherService.getById(id);
        return Result.success().data("entity",eduTeacher);
    }
    @ApiOperation(value="修改讲师")
    @PostMapping("/update")
    public Result update(
            @ApiParam(name = "eduTeacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher){
        boolean flag=eduTeacherService.updateById(eduTeacher);
        if(flag){
            return Result.success();
        }else{
            return Result.error();
        }
    }
    public Map getPageMap(long total, List list){
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",list);
        return map;
    }

}

