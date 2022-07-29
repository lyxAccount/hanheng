package com.example.interfacedemo.job.manage;


import com.example.interfacedemo.entity.SysScheduledEntity;
import com.example.interfacedemo.job.HttpJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.Date;
import java.util.List;


/**
 * @Author liangqq
 * 定时任务管理类
 * @Date 2020/12/15 13:03
 * @Version 1.0
 */

public class QuartzManager {

    public static Class getclass(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException//className是类名
    {
        Class obj = Class.forName(className); //以String类型的className实例化类
        return obj;
    }


    private static String JOB = "Job";
    private static String JOBGROUP = "JobGroup";

    private static String TRIGGER = "Trigger";
    private static String TRIGGERGROUP = "TriggerGroup";


    public static void addHttpJob(SysScheduledEntity sysScheduledEntity) {
        try {
            if (!scheduler.checkExists(JobKey.jobKey(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOBGROUP))) {
                JobDataMap jsonMap = new JobDataMap();
                jsonMap.put("id", sysScheduledEntity.getId());
                jsonMap.put("url", sysScheduledEntity.getUrl());
                jsonMap.put("method", sysScheduledEntity.getMethod());
                jsonMap.put("cron", sysScheduledEntity.getCronExpression());
                JobDetail jobDetail = JobBuilder.newJob(HttpJob.class)//任务执行类
                        .withIdentity(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOBGROUP).usingJobData(jsonMap).build();// 任务名，任务组
                //构建Trigger实例
                CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(sysScheduledEntity.getUrl() + TRIGGER, sysScheduledEntity.getUrl() + TRIGGERGROUP)
                        //.usingJobData(url+TRIGGER, "这是jobDetail1的trigger")
                        .startNow()//立即生效
                        .withSchedule(CronScheduleBuilder.cronSchedule(sysScheduledEntity.getCronExpression()))
                        .build();
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param sysScheduledEntity
     * @param cronExpression
     */
    public static void modifyJobTime(SysScheduledEntity sysScheduledEntity, String cronExpression) {

        try {

            /**
             * 先停止调度
             */
            scheduler.unscheduleJob(TriggerKey.triggerKey(sysScheduledEntity.getUrl() + TRIGGER, sysScheduledEntity.getUrl() + TRIGGERGROUP));

            /**
             * 重新实例化job
             */
            //Class class1 = getclass(sysScheduledEntity.getBeanClass());
            Class class1 = HttpJob.class;
            if (!scheduler.checkExists(JobKey.jobKey(sysScheduledEntity.getJobName(), sysScheduledEntity.getUrl() + JOBGROUP))) {
                //任务

                JobDataMap jsonMap = new JobDataMap();
                jsonMap.put("id", sysScheduledEntity.getId());
                jsonMap.put("url", sysScheduledEntity.getUrl());
                jsonMap.put("method", sysScheduledEntity.getMethod());
                jsonMap.put("cron", sysScheduledEntity.getCronExpression());
                JobDetail jobDetail = JobBuilder.newJob(HttpJob.class)//任务执行类
                        .withIdentity(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOBGROUP).usingJobData(jsonMap).build();// 任务名，任务组
                //构建Trigger实例
                CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(sysScheduledEntity.getUrl() + TRIGGER, sysScheduledEntity.getUrl() + TRIGGERGROUP)
                        //.usingJobData(url+TRIGGER, "这是jobDetail1的trigger")
                        .startNow()//立即生效
                        .withSchedule(CronScheduleBuilder.cronSchedule(sysScheduledEntity.getCronExpression()))
                        .build();
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    /**
     * 移除一个任务
     */
    public static void removeJob(SysScheduledEntity sysScheduledEntity) {
        try {
            scheduler.pauseJob(JobKey.jobKey(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOB));//停止任务
            scheduler.pauseTrigger(TriggerKey.triggerKey(sysScheduledEntity.getUrl() + TRIGGER, sysScheduledEntity.getUrl() + TRIGGERGROUP));// 停止触发器
            scheduler.unscheduleJob(TriggerKey.triggerKey(sysScheduledEntity.getUrl() + TRIGGER, sysScheduledEntity.getUrl() + TRIGGERGROUP));// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOB));// 删除任务
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 暂停调度指定job任务
     *
     * @throws SchedulerException
     */
    public static void pauseJob(SysScheduledEntity sysScheduledEntity) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOBGROUP));


//        scheduler.pauseJob(JobKey.jobKey(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOBGROUP));//停止任务
//        scheduler.pauseTrigger(TriggerKey.triggerKey(sysScheduledEntity.getUrl() + TRIGGER, sysScheduledEntity.getUrl() + TRIGGERGROUP));// 停止触发器
//        scheduler.unscheduleJob(TriggerKey.triggerKey(sysScheduledEntity.getUrl() + TRIGGER, sysScheduledEntity.getUrl() + TRIGGERGROUP));// 移除触发器
//        scheduler.deleteJob(JobKey.jobKey(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOBGROUP));// 删除任务
    }

    /**
     * 恢复调度指定job的任务
     *
     * @throws SchedulerException
     */
    public static void resumeJob(SysScheduledEntity sysScheduledEntity) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOBGROUP));
        /**
         * 重新实例化job
         */

        try {
            if (!scheduler.checkExists(JobKey.jobKey(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOBGROUP))) {
                JobDataMap jsonMap = new JobDataMap();
                jsonMap.put("id", sysScheduledEntity.getId());
                jsonMap.put("url", sysScheduledEntity.getUrl());
                jsonMap.put("method", sysScheduledEntity.getMethod());
                jsonMap.put("cron", sysScheduledEntity.getCronExpression());
                JobDetail jobDetail = JobBuilder.newJob(HttpJob.class)//任务执行类
                        .withIdentity(sysScheduledEntity.getUrl() + JOB, sysScheduledEntity.getUrl() + JOBGROUP).usingJobData(jsonMap).build();// 任务名，任务组
                //构建Trigger实例
                CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(sysScheduledEntity.getUrl() + TRIGGER, sysScheduledEntity.getUrl() + TRIGGERGROUP)
                        //.usingJobData(url+TRIGGER, "这是jobDetail1的trigger")
                        .startNow()//立即生效
                        .withSchedule(CronScheduleBuilder.cronSchedule(sysScheduledEntity.getCronExpression()))
                        .build();
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 停止调度Job任务
     */
    public static void unscheduleJob(String trigger, String triggerGroup) {
        try {
            scheduler.unscheduleJob(TriggerKey.triggerKey(trigger, triggerGroup));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 重新恢复触发器相关的job任务
     *
     * @param triggerName
     * @param triggerGroupName
     * @return
     * @throws SchedulerException
     */
    public static Date rescheduleJob(String triggerName, String triggerGroupName, String cronExpression)
            throws SchedulerException {
        //构建Trigger实例
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                .usingJobData(triggerName, triggerName)
                .startNow()//立即生效
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        return scheduler.rescheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName), cronTrigger);
    }

    /**
     * 启动所有定时任务
     */
    public static boolean getSchedulerStatus() throws SchedulerException {
        return scheduler.isStarted();
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.standby();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 暂停调度中所有的job任务
     *
     * @throws SchedulerException
     */
    public static void pauseAll() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 恢复调度中所有的job的任务
     *
     * @throws SchedulerException
     */
    public static void resumeAll() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));//停止任务
            scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));// 停止触发器
            scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


//    /**
//     * 移除一个任务
//     *
//     * @param jobName
//     * @param jobGroupName
//     * @param triggerName
//     * @param triggerGroupName
//     */
//    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
//        try {
//            boolean isok= scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));
//
//            Scheduler sched = gSchedulerFactory.getScheduler();
//            sched.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));// 停止触发器
//            sched.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));// 移除触发器
//            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }


    public static Scheduler scheduler = getScheduler();

    /**
     * 创建一个调度对象
     *
     * @return
     * @throws SchedulerException
     */
    public static Scheduler getScheduler() {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return scheduler;
    }

    public static Scheduler getInstanceScheduler() {
        return scheduler;
    }

    /**
     * 启动一个调度对象
     *
     * @throws SchedulerException
     */
    public void start() throws SchedulerException {
        scheduler.start();
    }

    /**
     * 检查调度是否启动
     *
     * @return
     * @throws SchedulerException
     */
    public boolean isStarted() throws SchedulerException {
        return scheduler.isStarted();
    }

    /**
     * 关闭调度信息
     *
     * @throws SchedulerException
     */
    public void shutdown() throws SchedulerException {
        scheduler.shutdown();
    }

    /**
     * 添加调度的job信息
     *
     * @param jobdetail
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date scheduleJob(JobDetail jobdetail, Trigger trigger)
            throws SchedulerException {
        return scheduler.scheduleJob(jobdetail, trigger);
    }

    /**
     * 添加相关的触发器
     *
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date scheduleJob(Trigger trigger) throws SchedulerException {
        return scheduler.scheduleJob(trigger);
    }
//    /**
//     * 添加多个job任务
//     * @param triggersAndJobs
//     * @param replace
//     * @throws SchedulerException
//     */
//    public  void scheduleJobs(Map<JobDetail,Set<CronTrigger>> triggersAndJobs, boolean replace) throws SchedulerException
//    {
//        scheduler.scheduleJobs(triggersAndJobs, replace);
//    }


    /**
     * 停止调度Job任务
     *
     * @param triggerkey
     * @return
     * @throws SchedulerException
     */
    public boolean unscheduleJob(TriggerKey triggerkey)
            throws SchedulerException {
        return scheduler.unscheduleJob(triggerkey);
    }

    /**
     * 停止调度多个触发器相关的job
     *
     * @param triggerKeylist
     * @return
     * @throws SchedulerException
     */
    public boolean unscheduleJobs(List<TriggerKey> triggerKeylist) throws SchedulerException {
        return scheduler.unscheduleJobs(triggerKeylist);
    }

    /**
     * 重新恢复触发器相关的job任务
     *
     * @param triggerkey
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date rescheduleJob(TriggerKey triggerkey, Trigger trigger)
            throws SchedulerException {
        return scheduler.rescheduleJob(triggerkey, trigger);
    }

    /**
     * 添加相关的job任务
     *
     * @param jobdetail
     * @param flag
     * @throws SchedulerException
     */
    public void addJob(JobDetail jobdetail, boolean flag)
            throws SchedulerException {
        scheduler.addJob(jobdetail, flag);
    }

    /**
     * 删除相关的job任务
     *
     * @param jobkey
     * @return
     * @throws SchedulerException
     */
    public boolean deleteJob(JobKey jobkey) throws SchedulerException {
        return scheduler.deleteJob(jobkey);
    }

    /**
     * 删除相关的多个job任务
     *
     * @param jobKeys
     * @return
     * @throws SchedulerException
     */
    public boolean deleteJobs(List<JobKey> jobKeys)
            throws SchedulerException {
        return scheduler.deleteJobs(jobKeys);
    }

    /**
     * @param jobkey
     * @throws SchedulerException
     */
    public void triggerJob(JobKey jobkey) throws SchedulerException {
        scheduler.triggerJob(jobkey);
    }

    /**
     * @param jobkey
     * @param jobdatamap
     * @throws SchedulerException
     */
    public void triggerJob(JobKey jobkey, JobDataMap jobdatamap)
            throws SchedulerException {
        scheduler.triggerJob(jobkey, jobdatamap);
    }

    /**
     * 停止一个job任务
     *
     * @param jobkey
     * @throws SchedulerException
     */
    public void pauseJob(JobKey jobkey) throws SchedulerException {
        scheduler.pauseJob(jobkey);
    }

    /**
     * 停止多个job任务
     *
     * @param groupmatcher
     * @throws SchedulerException
     */
    public void pauseJobs(GroupMatcher<JobKey> groupmatcher)
            throws SchedulerException {
        scheduler.pauseJobs(groupmatcher);
    }

    /**
     * 停止使用相关的触发器
     *
     * @param triggerkey
     * @throws SchedulerException
     */
    public void pauseTrigger(TriggerKey triggerkey)
            throws SchedulerException {
        scheduler.pauseTrigger(triggerkey);
    }

    public void pauseTriggers(GroupMatcher<TriggerKey> groupmatcher)
            throws SchedulerException {
        scheduler.pauseTriggers(groupmatcher);
    }

    /**
     * 恢复相关的job任务
     *
     * @param jobkey
     * @throws SchedulerException
     */
    public void resumeJob(JobKey jobkey) throws SchedulerException {
        scheduler.pauseJob(jobkey);
    }

    public void resumeJobs(GroupMatcher<JobKey> matcher)
            throws SchedulerException {
        scheduler.resumeJobs(matcher);
    }

    public void resumeTrigger(TriggerKey triggerkey)
            throws SchedulerException {
        scheduler.resumeTrigger(triggerkey);
    }

    public void resumeTriggers(GroupMatcher<TriggerKey> groupmatcher)
            throws SchedulerException {
        scheduler.resumeTriggers(groupmatcher);
    }


}
