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
 *
 * </p>
 *
 * @author liangqq
 * @since 2021-06-18
 */
@TableName("sys_scheduled")
@Data
//@ApiModel(value = "SysScheduled对象", description = "")
public class SysScheduledEntity implements Serializable, Cloneable {
//    @ApiModelProperty(value = "")
    @TableId("id")
    private String id;


//    @ApiModelProperty(value = "任务名")
    @TableField("job_name")
    private String jobName;


//    @ApiModelProperty(value = "请求地址")
    @TableField("url")
    private String url;


//    @ApiModelProperty(value = "请求方式")
    @TableField("method")
    private String method;


//    @ApiModelProperty(value = "参数json")
    @TableField("param_json")
    private String paramJson;


//    @ApiModelProperty(value = "任务执行时调用哪个类的方法 包名+类名")
    @TableField("bean_class")
    private String beanClass;


//    @ApiModelProperty(value = "cron表达式")
    @TableField("cron_expression")
    private String cronExpression;


//    @ApiModelProperty(value = "任务描述")
    @TableField("description")
    private String description;


//    @ApiModelProperty(value = "任务状态(Y/N)")
    @TableField("job_status")
    private String jobStatus;


//    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;


//    @ApiModelProperty(value = "创建人")
    @TableField("create_people")
    private String createPeople;


//    @ApiModelProperty(value = "更新人")
    @TableField("update_people")
    private String updatePeople;


//    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;


//    @ApiModelProperty(value = "是否删除(Y/N)")
    @TableField("is_deleted")
    private String isDeleted;



    @TableField(exist = false)
    private Date nextTime;



    @Override
    public Object clone() {
        SysScheduledEntity enty = null;
        try {
            enty = (SysScheduledEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return enty;
    }
}