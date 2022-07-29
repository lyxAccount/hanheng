package com.example.interfacedemo.job;

import cn.hutool.core.util.EnumUtil;
import com.alibaba.fastjson.JSONObject;

import com.example.interfacedemo.entity.SysScheduledLogEntity;
import com.example.interfacedemo.mapper.SysScheduledLogMapper;
import com.example.interfacedemo.util.HutoolUtil;
import com.example.interfacedemo.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @Author liangqq
 * @Date 2020/12/15 13:19
 * @Version 1.0
 */
@Data
@Component
@Slf4j
public class HttpJob implements Job {


    @Autowired
    private SysScheduledLogMapper sysScheduledLogMapper;


    private static SysScheduledLogMapper sysScheduledLogMapper2;

    @PostConstruct
    public void init() {
        sysScheduledLogMapper2 = sysScheduledLogMapper;
    }




    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("-------Hello World! ------进入HttpJob,请求地址"+ jobExecutionContext.getJobDetail().getJobDataMap().getString("url")+",请求方法"+jobExecutionContext.getJobDetail().getJobDataMap().getString("method"));


        Map params=jobExecutionContext.getJobDetail().getJobDataMap();
        //配置请求头
        Map<String, List<String>> headerMap=new HashMap<>();
        List<String> AuthorizationList=new ArrayList<>();
        //todo 后期要考虑是否需要请求头 因为定时任务是没有操作人是谁的
        AuthorizationList.add("");
        headerMap.put("Authorization",AuthorizationList);
        String method=((JobDataMap) params).getString("method");
        String result=null;
        log.info("1");
        if(method.equals("get")){
            result= HutoolUtil.getByMap(((JobDataMap) params).getString("url"),params,headerMap,300000);
        }else if(method.equals("post")){
            log.info("巡查计划");
            result= HutoolUtil.postByMap(((JobDataMap) params).getString("url"),params,headerMap,300000);
            log.info("巡查计划记录"+result);
        }
        System.out.println("result="+result);
        if(!ObjectUtils.isEmpty(result)){
            Map map= JsonUtil.StringToMap(result);

            String code= (String) map.get("code");
            log.info("巡查计划记录3"+code);
            log.info("巡查计划记录4"+ ((JobDataMap) params).getString("id"));
            SysScheduledLogEntity log=new SysScheduledLogEntity();
            log.setId(UUID.randomUUID().toString());
            log.setCreateTime(new Date());
            log.setExectueTime(new Date());
            log.setScheduleId(((JobDataMap) params).getString("id"));
            if(!ObjectUtils.isEmpty(code)&& "200".equals(code)){
                JSONObject data= (JSONObject)map.get("data");
                String dataStr=JsonUtil.BeantoString(data);
                System.out.println(dataStr);
                log.setRemark(dataStr);
            }else{
                log.setRemark((String) map.get("msg"));
            }
            sysScheduledLogMapper2.insert(log);
        }
    }



    public static String getValue(String columnTypeName, Object value) {
        String result = "";
        if (value == null) {
            return result;
        }
        if (columnTypeName.equals("varchar")) {
            result = value.toString();
        } else if (columnTypeName.equals("float")) {
            result = value.toString();
        } else if (columnTypeName.equals("datetime")) {
            result = value.toString();
        } else if (columnTypeName.equals("int")) {
            result = value.toString();
        } else {
            result = value.toString();
        }
        return result;
    }
}
