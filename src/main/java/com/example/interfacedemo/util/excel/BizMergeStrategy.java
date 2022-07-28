package com.example.interfacedemo.util.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.example.interfacedemo.entity.dto.RowRangeDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Map;


public class BizMergeStrategy extends AbstractMergeStrategy {
 
    private Map<String, List<RowRangeDto>> strategyMap;
    private Sheet sheet;
 
    public BizMergeStrategy(Map<String, List<RowRangeDto>> strategyMap) {
        this.strategyMap = strategyMap;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer integer) {
        this.sheet = sheet;
        if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 0) {
            /**
             * 保证每个cell被合并一次，如果不加上面的判断，因为是一个cell一个cell操作的，
             * 例如合并A2:A3,当cell为A2时，合并A2,A3，但是当cell为A3时，又是合并A2,A3，
             * 但此时A2,A3已经是合并的单元格了
             */
            for (Map.Entry<String, List<RowRangeDto>> entry : strategyMap.entrySet()) {
                Integer columnIndex = Integer.valueOf(entry.getKey());
                entry.getValue().forEach(rowRange -> {
                    //添加一个合并请求
                    sheet.addMergedRegionUnsafe(new CellRangeAddress(rowRange.getStart(),
                            rowRange.getEnd(), columnIndex, columnIndex));
                });
            }
        }
    }

    /**
     * @description: 表格样式
     * @author

     * @since 2020/11/20 9:40
     * @Modified By:
     * @return
     */
    public static HorizontalCellStyleStrategy CellStyleStrategy(){
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        //设置头字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)13);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }

}