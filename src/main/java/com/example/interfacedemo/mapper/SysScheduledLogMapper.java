package com.example.interfacedemo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.interfacedemo.entity.SysScheduledLogEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 任务日志表 Mapper 接口
 * </p>
 *
 * @author liangqq
 * @since 2021-06-18
 */
public interface  SysScheduledLogMapper extends BaseMapper<SysScheduledLogEntity> {

    @Select("SELECT a.`status`,a.schedule_id,a.exectue_time,a.remark,a.id,b.job_name schedule_name FROM `sys_scheduled_log` a\n" +
            "left join sys_scheduled b on a.schedule_id=b.id " +
            "${ew.customSqlSegment}")
    IPage<SysScheduledLogEntity> ListByScheduleId(Page<SysScheduledLogEntity> page, @Param(Constants.WRAPPER) QueryWrapper<SysScheduledLogEntity> query);
}