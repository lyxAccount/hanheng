package com.example.interfacedemo.service.impl;

import com.example.interfacedemo.service.StockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 两种方案实现分布式锁：
 * 1、通过redis的setifabsent方法，但仍有不足，详见注释
 * 2、通过redisson实现，他会另开一个线程去监控锁的有效时间，每过一定时间重新设置有效时间
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public String deal() {

        String lockKey = "lyx";
        String clientId= UUID.randomUUID().toString();
        RLock lock =redissonClient.getFairLock(lockKey);
        try{
            //这种写法仍有不足，如有效时间过期了但业务仍没处理完
//            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey,clientId,10, TimeUnit.SECONDS);
//            if (!result){
//                return "error";
//            }
            lock.lock(20,TimeUnit.SECONDS);
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock").toString());
            System.out.println("剩余数量为："+stock);
            if(stock>0){
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock",realStock);
            }else {
                System.out.println("库存不足");
            }
        }finally {
            //释放锁
//            if(clientId.equals(redisTemplate.opsForValue().get(lockKey))){
//                redisTemplate.delete(lockKey);
//            }
            lock.unlock();
        }

        return "end";
    }

    /**
     * 添加库存
     * @param num
     * @return
     */
    @Override
    public String add(int num) {
        redisTemplate.opsForValue().set("stock",num);
        return "add success";
    }

    /**
     * 获取库存
     * @return
     */
    @Override
    public String getStock() {
        Object stock = redisTemplate.opsForValue().get("stock");
        return stock.toString();
    }
}
