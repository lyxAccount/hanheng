package com.example.interfacedemo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.interfacedemo.entity.SysScheduledEntity;
import com.example.interfacedemo.entity.SysScheduledLogEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liangqq
 * @since 2020-12-15
 */
public interface SysScheduledService {


   Object restoreAllJob(HttpServletRequest request);

    Object changeScheduledCron(HttpServletRequest request, String id, String description, String cron);

   SysScheduledEntity saveJob(HttpServletRequest request, SysScheduledEntity entity);

    Object pauseJob(HttpServletRequest request, String ids);

   Object resumeJob(HttpServletRequest request, String ids);

   Object removeJobs(HttpServletRequest request, String ids);




   Object getSchedulerStatus(HttpServletRequest request);

  Object startScheduler(HttpServletRequest request);

    Object shutdownScheduler(HttpServletRequest request);

    IPage<SysScheduledEntity> getList(HttpServletRequest request, String orderByColumn, String orderByType, Integer pageIndex, Integer size, String jobName, String description, String cronExpression, String jobStatus);

  IPage<SysScheduledLogEntity> ListLogByJobId(HttpServletRequest request, String orderByColumn, String orderByType, Integer pageIndex, Integer size, String jobId);











    List<SysScheduledEntity> getAllList(HttpServletRequest request);

    SysScheduledEntity getById(HttpServletRequest request, String id);

   Object saveData(HttpServletRequest request, SysScheduledEntity utSysScheduledEntity);

    Object deleteById(HttpServletRequest request, String id);

}