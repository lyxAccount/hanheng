package com.example.interfacedemo.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 任务日志表
 * </p>
 *
 * @author liangqq
 * @since 2021-06-18
 */
@TableName("sys_scheduled_log")
@Data
public class SysScheduledLogEntity implements Serializable, Cloneable {

    @TableId("id")
    private String id;


//    @ApiModelProperty(value = "任务id")
    @TableField("schedule_id")
    private String scheduleId;


//    @ApiModelProperty(value = "执行时间")
    @TableField("exectue_time")
    private Date exectueTime;


//    @ApiModelProperty(value = "Y成功，N失败")
    @TableField("status")
    private String status;


//    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;


//    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

//    @ApiModelProperty(value = "任务名称")
    @TableField(exist = false)
    private String scheduleName;




    @Override
    public Object clone() {
        SysScheduledLogEntity enty = null;
        try {
            enty = (SysScheduledLogEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return enty;
    }
}