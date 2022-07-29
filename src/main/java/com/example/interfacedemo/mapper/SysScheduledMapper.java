package com.example.interfacedemo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.interfacedemo.entity.SysScheduledEntity;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liangqq
 * @since 2021-06-18
 */
//@CacheNamespace(implementation = MybatisRedisCache.class)//二级缓存开启（采用redis作为缓存存储）
public interface  SysScheduledMapper extends BaseMapper<SysScheduledEntity> {



}