package com.example.interfacedemo.controller.sys;

import com.example.interfacedemo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 分布式锁写法模拟处理可能出现并发情况的库存订单
     * @return
     */
    @PostMapping("/deal")
    public String stockDeal(){
        String result = stockService.deal();
        return result;
    }

    /**
     * 往redis中添加库存
     * @param num
     * @return
     */
    @PostMapping("/add")
    public String addStock(@RequestParam int num){
        String result = stockService.add(num);
        return result;
    }

    /**
     * 获取库存的数量
     * @return
     */
    @GetMapping("/get/stock")
    public String getStock(){
        return stockService.getStock();
    }
}
