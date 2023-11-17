package com.example.interfacedemo.controller.asponseword;



import com.aspose.words.*;
import com.example.interfacedemo.controller.pojo.Student;
import com.example.interfacedemo.entity.Teacher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyouxiang 2023/11/16
 */
@RestController
@RequestMapping("/word")
public class Word {

    @GetMapping("/create")
    public String createWord() throws Exception{
//        if (!getLicense()) {
//            return "缺少lic文件";
//        }
        OutputStream outputStream = null;
        String templatePath = "F:/template/不可移动文物体检评估报告模板.docx";
        Document doc = null;
        doc = new Document(templatePath);

        MailMerge merge = doc.getMailMerge();
        String[] fillname = new String[]{
                "Po_level", "PO_unitName"
        };
        String level = "国宝";
        String name = "天一阁";
        String[] fillValue = new String[]{
                level,name
        };

        List list = new ArrayList();
        List stuList = new ArrayList();

        Teacher teacher = new Teacher();
        teacher.setName("李老师");
        teacher.setAge(28);
        Student s1 = new Student();
        s1.setName("王同学");
        Student s2 = new Student();
        s2.setName("张同学");

        stuList.add(s1);
        stuList.add(s2);
        teacher.setList(stuList);

        list.add(teacher);


        DocumentBuilder builder = new DocumentBuilder(doc);
        //动态生成表格（包含合并单元格）
        builder.moveToBookmark("SU_zhidu");
        writeTable(builder,list);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        merge.execute(fillname, fillValue);
        //更新目录
        doc.updateFields();
        for (Field field : (Iterable<Field>) doc.getRange().getFields()) {
            if (field.getType() == (FieldType.FIELD_PAGE_REF)) {
                FieldPageRef pageRef = (FieldPageRef) field;
                if (pageRef.getBookmarkName() != "" || pageRef.getBookmarkName().startsWith("_Toc")) {
                    Paragraph tocItem = (Paragraph) field.getStart().getAncestor(NodeType.PARAGRAPH);
//                    tocItem.getParagraphFormat().setLineSpacing(24);
                    for (Run run : tocItem.getRuns()) {
                        run.getFont().setName("宋体 (正文)");
                        run.getFont().setItalic(false);
                        run.getFont().setSize(10);
                    }
                }
            }
        }

        for (Field field : (Iterable<Field>) doc.getRange().getFields()) {
            if (field.getType() == (FieldType.FIELD_TOC)) {
                field.isLocked(true);
            }
        }
        doc.save(bos, SaveFormat.DOCX);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

        // 输出位置
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        String reportName = "测试" + "安全体检评估报告" + localTime + ".docx";
        outputStream = new FileOutputStream(new File("D:/report/" + reportName));

        int bytesRead = 0;
        while ((bytesRead = bis.read()) != -1) {
            outputStream.write(bytesRead);
        }
        outputStream.close();

        return "0";
    }


    public void writeTable(DocumentBuilder builder,List<Teacher> list){
        if(!ObjectUtils.isEmpty(list)){
            builder.getFont().setName("宋体");
            builder.getFont().setSize(12.0);
            builder.getFont().setColor(Color.BLACK);
            builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.BODY_TEXT);//加粗
            builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);//上下居中
            builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);//左右居中
            builder.getParagraphFormat().setLineSpacing(18);//字间距
            builder.getParagraphFormat().setSpaceAfter(0);//段后距离

            builder.startTable();
            builder.insertCell();
            builder.getCellFormat().setWidth(80);
            builder.write("老师名称");

            builder.insertCell();
            builder.getCellFormat().setWidth(80);
            builder.write("老师年龄");

            builder.insertCell();
            builder.getCellFormat().setWidth(80);
            builder.write("学生姓名");
            builder.endRow();

            //CellMerge.FIRST指的是从这个单元格算起，是否水平或垂直合并，CellMerge.NONE是不合并，PREVIOUS是合并
            for (int i = 0;i<list.size();i++){
                builder.insertCell();
                builder.getCellFormat().setVerticalMerge(CellMerge.FIRST);
                builder.write(list.get(i).getName());
                builder.insertCell();
                builder.getCellFormat().setVerticalMerge(CellMerge.FIRST);
                builder.write(list.get(i).getAge().toString());

                List<Student> students = list.get(i).getList();
                if(!ObjectUtils.isEmpty(students)){
                    for (int j = 0;j<students.size();j++){
                        if(j==0){
                            builder.insertCell();
                            builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
                            builder.write(students.get(j).getName());
                            builder.endRow();
                        }else {
                            builder.insertCell();
                            builder.getCellFormat().clearFormatting();
                            builder.getCellFormat().setVerticalMerge(CellMerge.PREVIOUS);

                            builder.insertCell();
                            builder.getCellFormat().clearFormatting();
                            builder.getCellFormat().setVerticalMerge(CellMerge.PREVIOUS);

                            builder.insertCell();
                            builder.getCellFormat().clearFormatting();
                            builder.getCellFormat().setVerticalMerge(CellMerge.NONE);
                            builder.write(students.get(j).getName());
                            builder.endRow();
                        }
                    }
                }
            }
            builder.endTable();
            builder.writeln();
        }
    }

    public boolean getLicense() {
        boolean result = false;
        try {
            File file = ResourceUtils.getFile("D:/lic/license.xml");
            // license.xml找个路径放即可。
            boolean exists = file.exists();
            InputStream is = new FileInputStream(file);
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setStyleFirst(DocumentBuilder builder) throws Exception {
        builder.getCellFormat().setWidth(30);
        builder.getFont().setName("宋体");
        builder.getFont().setSize(12.0);
        builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
    }
}
