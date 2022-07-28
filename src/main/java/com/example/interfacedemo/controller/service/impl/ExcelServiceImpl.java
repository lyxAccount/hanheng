package com.example.interfacedemo.controller.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.example.interfacedemo.controller.lister.StudentExcelListener;
import com.example.interfacedemo.controller.pojo.Student;
import com.example.interfacedemo.controller.service.ExcelService;
import com.example.interfacedemo.entity.ExcelDto;
import com.example.interfacedemo.entity.User;
import com.example.interfacedemo.entity.dto.RowRangeDto;
import com.example.interfacedemo.util.excel.BizMergeStrategy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public void export(HttpServletResponse response) throws IOException {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User(i, UUID.randomUUID().toString(),"123","ADMIN");
            list.add(user);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String date = sdf.format(new Date());
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("test"+date, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        LongestMatchColumnWidthStyleStrategy longestMatchColumnWidthStyleStrategy =
                new LongestMatchColumnWidthStyleStrategy();
        EasyExcel.write(response.getOutputStream(), User.class)
                .sheet("test")
                .registerWriteHandler(longestMatchColumnWidthStyleStrategy)
                .doWrite(list);

    }

    @Override
    public List<Student> read(MultipartFile file, ExcelService excelService) {
        List<Student> studentList = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, Student.class,new StudentExcelListener(excelService,studentList)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("学生的数量为"+studentList.size());
        return studentList;
    }

    @Override
    public void exportMerge(HttpServletResponse response) {

        List<ExcelDto> list = getData();

        if(!ObjectUtils.isEmpty(list)){
            List<ExcelDto> newList = new ArrayList<>();
            for (ExcelDto applyModel : list) {
//                PropertyUserDto ExcelRespose = new PropertyUserDto();

                if(!ObjectUtils.isEmpty(applyModel.getCreateCompany())){
                    newList.add(applyModel);
                }
            }
            //根据ID对数据进行分组，一定要处理数据，不然会合并不了
            Map<String, List<ExcelDto>> map = newList.stream().collect(Collectors.groupingBy(ExcelDto::getCreateCompany));
            //需要合并数据
            Map<String, List<ExcelDto>> longListMap = new HashMap<>();
            //单个不需要合并数据
            List<ExcelDto> singleList = new ArrayList<>();
            //合并所有数据
            List<ExcelDto> allList = new ArrayList<>();
            for (String aLong : map.keySet()) {
                if(map.get(aLong).size()>1){
                    longListMap.put(aLong,map.get(aLong));
                }else {
                    singleList.addAll(map.get(aLong));
                }
            }
            if(!longListMap.isEmpty()){
                for (String aLong : longListMap.keySet()) {
                    allList.addAll(longListMap.get(aLong));
                }
            }
            if(!ObjectUtils.isEmpty(singleList)){
                allList.addAll(singleList);
            }
            try {
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                String fileName = URLEncoder.encode("文物点列表"+System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
                response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

                Map<String, List<RowRangeDto>> strategyMap = addMerStrategy(allList);
                LongestMatchColumnWidthStyleStrategy longestMatchColumnWidthStyleStrategy =
                        new LongestMatchColumnWidthStyleStrategy();
                EasyExcel.write(response.getOutputStream(), ExcelDto.class)
                        .sheet("文物点")
                        .registerWriteHandler(new BizMergeStrategy(strategyMap)) //这一行就是用来合并单元格的，去除的话就是正常的单条导出
                        .registerWriteHandler(BizMergeStrategy.CellStyleStrategy()) //设置样式
                        .registerWriteHandler(longestMatchColumnWidthStyleStrategy) //设置自适应宽度
                        .doWrite(allList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description: 列表导出--添加合并策略（EasyExcel）
     * @author: panda
     * @date: 2021/4/1
     */
    public static Map<String, List<RowRangeDto>> addMerStrategy(List<ExcelDto> excelDtoList) {
        Map<String, List<RowRangeDto>> strategyMap = new HashMap<>();
        ExcelDto preExcelDto = null;
        for (int i = 0; i < excelDtoList.size(); i++) {
            ExcelDto currDto = excelDtoList.get(i);
            if (preExcelDto != null) {
                //从第二行开始判断是否需要合并，下面的key为需要合并的列
                if (currDto.getCreateCompany().equals(preExcelDto.getCreateCompany())) {
                    fillStrategyMap(strategyMap, "0", i);
                    fillStrategyMap(strategyMap, "2", i);
                }
            }
            preExcelDto = currDto;
        }
        return strategyMap;
    }

    /**
     * @description: 新增或修改合并策略map(EasyExcel)
     * @author: panda
     * @date: 2021/4/1
     */
    private static void fillStrategyMap(Map<String, List<RowRangeDto>> strategyMap, String key, int index) {
        List<RowRangeDto> rowRangeDtoList = strategyMap.get(key) == null ? new ArrayList<>() : strategyMap.get(key);
        boolean flag = false;
        for (RowRangeDto dto : rowRangeDtoList) {
            //分段list中是否有end索引是上一行索引的，如果有，则索引+1
            if (dto.getEnd() == index) {
                dto.setEnd(index + 1);
                flag = true;
            }
        }
        //如果没有，则新增分段
        if (!flag) {
            rowRangeDtoList.add(new RowRangeDto(index, index + 1));
        }
        strategyMap.put(key, rowRangeDtoList);
    }

    public List<ExcelDto> getData(){
        List<ExcelDto> list = new ArrayList<>();
        ExcelDto dto = new ExcelDto();
        dto.setGoodsName("a");
        dto.setCreateCompany("京东");
        dto.setProductCode("01");

        ExcelDto dto2 = new ExcelDto();
        dto2.setGoodsName("a");
        dto2.setCreateCompany("淘宝");
        dto2.setProductCode("02");

        ExcelDto dto3 = new ExcelDto();
        dto3.setGoodsName("c");
        dto3.setCreateCompany("京东");
        dto3.setProductCode("03");

        list.add(dto3);
        list.add(dto);
        list.add(dto2);

        return list;
    }
}
