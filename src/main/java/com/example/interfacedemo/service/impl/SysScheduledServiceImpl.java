package com.example.interfacedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.interfacedemo.entity.SysScheduledEntity;
import com.example.interfacedemo.entity.SysScheduledLogEntity;
import com.example.interfacedemo.job.manage.QuartzManager;
import com.example.interfacedemo.mapper.SysScheduledLogMapper;
import com.example.interfacedemo.mapper.SysScheduledMapper;
import com.example.interfacedemo.service.SysScheduledService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class SysScheduledServiceImpl implements SysScheduledService {

    @Autowired(required = false)
    private SysScheduledMapper sysScheduledMapper;

    @Autowired(required = false)
    private SysScheduledLogMapper sysScheduledLogMapper;


    @Override
    public SysScheduledEntity saveJob(HttpServletRequest request, SysScheduledEntity entity) {
        //判断是新增还是修改

        QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
        query.eq("is_deleted", "N");
        query.eq("job_name", entity.getJobName());
        if(ObjectUtils.isEmpty(entity.getId())){
            //新增
            SysScheduledEntity utSysScheduledCheck = sysScheduledMapper.selectOne(query);
            if (utSysScheduledCheck != null) {
                return null;
            }

            if (entity.getJobStatus() == null || "".equals(entity.getJobStatus())) {
                entity.setJobStatus("Y");
            }
            //添加数据库记录
            entity.setId(UUID.randomUUID().toString());
            sysScheduledMapper.insert(entity);

        }else{
            //编辑
            query.ne("id", entity.getId());
            SysScheduledEntity utSysScheduledCheck = sysScheduledMapper.selectOne(query);
            if (utSysScheduledCheck != null) {
                return null;
            }
            //编辑修改的时候先移除对应的job
            QuartzManager.removeJob(entity);

            sysScheduledMapper.updateById(entity);
        }

        //任务状态为启动才启动
        if(entity.getJobStatus().equals("Y")){
            QuartzManager.addHttpJob(entity);
        }

        return null;
    }

    @Override
    public Object restoreAllJob(HttpServletRequest request) {
        QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
        query.eq("job_status", "Y");
        query.eq("is_deleted", "N");
        List<SysScheduledEntity> jobs = sysScheduledMapper.selectList(query);
        jobs.forEach((entity) -> {
            QuartzManager.addHttpJob(entity);
        });
        QuartzManager.startJobs();
        return "success";
    }

    @Override
    public Object changeScheduledCron(HttpServletRequest request, String id, String description, String cron) {
        QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
        query.eq("id", id);
        query.eq("is_deleted", "N");
        SysScheduledEntity utSysScheduledCheck = sysScheduledMapper.selectOne(query);

        if (utSysScheduledCheck == null) {
            return null;
        }
        QuartzManager.modifyJobTime(utSysScheduledCheck, cron);
        SysScheduledEntity utSysScheduledEntity = new SysScheduledEntity();
        utSysScheduledEntity.setId(id);
        utSysScheduledEntity.setDescription(description);
        utSysScheduledEntity.setCronExpression(cron);
        sysScheduledMapper.updateById(utSysScheduledEntity);
        return "success";
    }

    @Override
    public Object removeJobs(HttpServletRequest request, String idStr) {
        String[] ids=idStr.split(",");
        QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
        query.in("id", ids);
        query.eq("is_deleted", "N");
        List<SysScheduledEntity> jobs = sysScheduledMapper.selectList(query);
        if (jobs.size() > 0) {
            jobs.forEach(el -> {
                //移除任务
                QuartzManager.removeJob(el);

            });
        }
        SysScheduledEntity utSysScheduledEntity = new SysScheduledEntity();
        utSysScheduledEntity.setIsDeleted("Y");
        sysScheduledMapper.update(utSysScheduledEntity, query);
        return "success";
    }


    /**
     * 暂停指定job
     *
     * @param request
     * @param idStr
     * @return
     */
    @Override
    public Object pauseJob(HttpServletRequest request, String idStr) {
        String[] ids=idStr.split(",");
        QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
        query.in("id", ids);
        query.eq("is_deleted", "N");
        List<SysScheduledEntity> jobs = sysScheduledMapper.selectList(query);
        if (jobs.size() > 0) {
            jobs.forEach(el -> {
                try {
                    QuartzManager.pauseJob(el);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            });
        }

        SysScheduledEntity utSysScheduledEntity = new SysScheduledEntity();
        utSysScheduledEntity.setJobStatus("N");
        sysScheduledMapper.update(utSysScheduledEntity, query);

        return "success";
    }

    /**
     * 恢复指定job
     *
     * @param request
     * @param idStr
     * @return
     */
    @Override
    public Object resumeJob(HttpServletRequest request, String idStr) {
        String[] ids=idStr.split(",");
        QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
        query.eq("is_deleted", "N");
        query.in("id", ids);
        List<SysScheduledEntity> jobs = sysScheduledMapper.selectList(query);
        if (jobs.size() > 0) {
            jobs.forEach(el -> {
                try {
                    QuartzManager.resumeJob(el);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            });
        }

        SysScheduledEntity utSysScheduledEntity = new SysScheduledEntity();
        utSysScheduledEntity.setJobStatus("Y");
        sysScheduledMapper.update(utSysScheduledEntity, query);
        return "success";
    }






    @Override
    public Object getSchedulerStatus(HttpServletRequest request) {
        boolean isStart=false;
        try {
            isStart= QuartzManager.getSchedulerStatus();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @Override
    public Object startScheduler(HttpServletRequest request) {
        QuartzManager.startJobs();
        return "success";
    }

    @Override
    public Object shutdownScheduler(HttpServletRequest request) {
        QuartzManager.shutdownJobs();
        return "success";
    }





    @SuppressWarnings("unchecked")
    @Override
    public IPage<SysScheduledEntity> getList(HttpServletRequest request, String orderByColumn, String orderByType, Integer pageIndex, Integer size
            , String jobName, String description, String cronExpression, String jobStatus) {
//        if (!CheckUtil.checkPageIndexForInt(pageIndex)) {
//            return null;
//        }
//        if (!CheckUtil.checkPageIndexForInt(size)) {
//            return null;
//        }
        Page<SysScheduledEntity> page = new Page<SysScheduledEntity>(pageIndex, size);

        /**查询条件配置  不使用条件时直接将参数为null*/
        QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
        query.eq("is_deleted", "N");
        if(!ObjectUtils.isEmpty(jobName)){
            query.like("job_name", jobName);
        }
        if(!ObjectUtils.isEmpty(cronExpression)){
            query.like("cronExpression", cronExpression);
        }
        if(!ObjectUtils.isEmpty(jobStatus)){
            query.eq("job_status", jobStatus);
        }
        if (ObjectUtils.isEmpty(orderByColumn)) {
        } else {
            //排序字段不为空则判断升序降序为不为空
            if (ObjectUtils.isEmpty(orderByType)) {
                query.orderByAsc(orderByColumn);
            } else {
                //排序类型不为空
                if ("asc".equals(orderByType)) {
                    query.orderByAsc(orderByColumn);
                } else {
                    query.orderByDesc(orderByColumn);
                }
            }
        }
        //简单分页查询
        IPage<SysScheduledEntity> pageData = sysScheduledMapper.selectPage(page, query);
        return pageData;
    }

    @Override
    public IPage<SysScheduledLogEntity> ListLogByJobId(HttpServletRequest request, String orderByColumn, String orderByType, Integer pageIndex, Integer size, String jobId) {
//        if (!CheckUtil.checkPageIndexForInt(pageIndex)) {
//            return new ResultModel<IPage<SysScheduledLogEntity>>(false, "页码不能为空，且页码不能小于0！", null, ResultModel.ERROR_PARAM_INVALID);
//        }
//        if (!CheckUtil.checkPageIndexForInt(size)) {
//            return new ResultModel<IPage<SysScheduledLogEntity>>(false, "每页记录数不能为空,且不能小于0！", null, ResultModel.ERROR_PARAM_INVALID);
//        }
        Page<SysScheduledLogEntity> page = new Page<SysScheduledLogEntity>(pageIndex, size);

        /**查询条件配置  不使用条件时直接将参数为null*/
        QueryWrapper<SysScheduledLogEntity> query = new QueryWrapper<>();
        query.eq("schedule_id", jobId);

        if (ObjectUtils.isEmpty(orderByColumn)) {
            query.orderByDesc("a.exectue_time");
        } else {
            //排序字段不为空则判断升序降序为不为空
            if (ObjectUtils.isEmpty(orderByType)) {
                query.orderByAsc(orderByColumn);
            } else {
                //排序类型不为空
                if ("asc".equals(orderByType)) {
                    query.orderByAsc(orderByColumn);
                } else {
                    query.orderByDesc(orderByColumn);
                }
            }
        }
        //简单分页查询
        IPage<SysScheduledLogEntity> pageData = sysScheduledLogMapper.ListByScheduleId(page, query);
        return pageData;
    }







    @Override
    public List<SysScheduledEntity> getAllList(HttpServletRequest request) {
        QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
        query.eq("is_deleted", "N");
        List<SysScheduledEntity> dataList = sysScheduledMapper.selectList(query);
        return dataList;
    }

    @Override
    public SysScheduledEntity getById(HttpServletRequest request, String id) {
//        if (CheckUtil.isNullOrEmpty(id)) {
//            return new ResultModel<SysScheduledEntity>(false, "id不能为空！", null, ResultModel.ERROR_PARAM_INVALID);
//        }
        QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
        query.eq("id", id);
        query.eq("is_deleted", "N");
        SysScheduledEntity utSysScheduledCheck = sysScheduledMapper.selectOne(query);
        if (utSysScheduledCheck == null) {
            return null;
        }
        return utSysScheduledCheck;
    }

    @Override
    public Object saveData(HttpServletRequest request, SysScheduledEntity utSysScheduledEntity) {
        if (!ObjectUtils.isEmpty(utSysScheduledEntity.getId())) {//编辑
            QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
            query.eq("id", utSysScheduledEntity.getId());
            query.eq("is_deleted", "N");
            SysScheduledEntity utSysScheduledCheck = sysScheduledMapper.selectOne(query);
            if (utSysScheduledCheck == null) {
                return null;
            } else {
                sysScheduledMapper.updateById(utSysScheduledEntity);
            }
        } else {
            //新增
            utSysScheduledEntity.setId(UUID.randomUUID().toString());
            sysScheduledMapper.insert(utSysScheduledEntity);
        }
        return "success";
    }

    @Override
    public Object deleteById(HttpServletRequest request, String id) {
//        if (CheckUtil.isNullOrEmpty(id)) {
//            return new ResultModel<Object>(false, "id不能为空！", null, ResultModel.ERROR_PARAM_INVALID);
//        }
        QueryWrapper<SysScheduledEntity> query = new QueryWrapper<>();
        query.eq("id", id);
        query.eq("is_deleted", "N");
        SysScheduledEntity utSysScheduledCheck = sysScheduledMapper.selectOne(query);
        if (utSysScheduledCheck == null) {
            return null;
        }
        sysScheduledMapper.deleteById(id);
        return "success";
    }
}