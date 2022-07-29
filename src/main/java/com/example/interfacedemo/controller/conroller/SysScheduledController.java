package com.example.interfacedemo.controller.conroller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.interfacedemo.entity.SysScheduledEntity;
import com.example.interfacedemo.entity.SysScheduledLogEntity;
import com.example.interfacedemo.service.SysScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


//@Api(tags = "定时任务")
@RestController
@RequestMapping("/sysScheduled")
public class SysScheduledController {


    @Autowired(required = false)
    private SysScheduledService sysScheduledService;



    @PostMapping("/getSchedulerStatus")
//    @ApiOperation(value = "获取调度是否启用", position = 9)
    public Object getSchedulerStatus(HttpServletRequest request) {
        return sysScheduledService.getSchedulerStatus(request);
    }


    @PostMapping("/saveJob")
//    @ApiOperation(value = "新增job", position = 1)
    public SysScheduledEntity saveJob(HttpServletRequest request, @RequestBody SysScheduledEntity entity) {
        return sysScheduledService.saveJob(request, entity);
    }


    @PostMapping("/restoreAllJob")
//    @ApiOperation(value = "恢复数据库中所有正常执行的job", position = 1)
    public Object restoreAllJob(HttpServletRequest request) {
        return sysScheduledService.restoreAllJob(request);
    }


    @PostMapping("/changeScheduledCron")
//    @ApiOperation(value = "修改定时任务的时间表达式", position = 3)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "任务id", required = true, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "cron", value = "cron表达式", required = true, paramType = "query", dataType = "String")
//    })
    public Object changeScheduledCron(HttpServletRequest request, String id, String description, String cron) {
        return sysScheduledService.changeScheduledCron(request, id,description,  cron);
    }

    @PostMapping("/removeJobs")
//    @ApiOperation(value = "删除/移除任务", position = 4)
    public Object removeJobs(HttpServletRequest request, String ids) {
        return sysScheduledService.removeJobs(request, ids);
    }

    @PostMapping("/pauseJob")
//    @ApiOperation(value = "暂停调度中指定job任务", position = 5)
    public Object pauseJob(HttpServletRequest request, String ids) {
        return sysScheduledService.pauseJob(request, ids);
    }


    @PostMapping("/resumeJob")
//    @ApiOperation(value = "恢复调度中指定job的任务", position = 6)
    public Object resumeJob(HttpServletRequest request, String ids) {
        return sysScheduledService.resumeJob(request, ids);
    }



    @PostMapping("/startScheduler")
//    @ApiOperation(value = "启动调度", position = 9)
    public Object startScheduler(HttpServletRequest request) {
        return sysScheduledService.startScheduler(request);
    }

    @PostMapping("/shutdownScheduler")
//    @ApiOperation(value = "关闭调度", position = 10)
    public Object shutdownScheduler(HttpServletRequest request) {
        return sysScheduledService.shutdownScheduler(request);
    }




    @PostMapping("/getDataListByPage")
//    @ApiOperation(value = "获取列表,分页【√】", notes = "2020-12-15 创建：<br/>2020-12-15 完成：", position = 1)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageIndex", value = "页码", required = true, example = "1", paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "size", value = "每页数量", required = true, example = "10", paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "orderByColumn", value = "排序字段", required = false, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "orderByType", value = "排序类型（desc/asc）", required = false, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "jobName", value = "任务名称", required = false, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "description", value = "描述", required = false, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "cronExpression", value = "时间表达式", required = false, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "jobStatus", value = "任务状态", required = false, paramType = "query", dataType = "String")
//    })
    public IPage<SysScheduledEntity> getDataListByPage(HttpServletRequest request, @RequestParam(required = false)String orderByColumn, @RequestParam(required = false)String orderByType, Integer pageIndex, Integer size
    , @RequestParam(required = false)String jobName, @RequestParam(required = false)String description, @RequestParam(required = false)String cronExpression, @RequestParam(required = false)String jobStatus) {
        return sysScheduledService.getList(request, orderByColumn, orderByType, pageIndex, size, jobName, description, cronExpression, jobStatus);
    }




    @PostMapping("/ListLogByJobId")
//    @ApiOperation(value = "获取执行记录", notes = "2020-12-15 创建：<br/>2020-12-15 完成：", position = 1)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageIndex", value = "页码", required = true, example = "1", paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "size", value = "每页数量", required = true, example = "10", paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "jobId", value = "jobId", required = true, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "orderByColumn", value = "排序字段", required = false, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "orderByType", value = "排序类型（desc/asc）", required = false, paramType = "query", dataType = "String"),
//    })
    public IPage<SysScheduledLogEntity> ListLogByJobId(HttpServletRequest request, @RequestParam(required = false)String orderByColumn, @RequestParam(required = false)String orderByType, Integer pageIndex, Integer size
            , String jobId) {
        return sysScheduledService.ListLogByJobId(request, orderByColumn, orderByType, pageIndex, size, jobId);
    }

}
