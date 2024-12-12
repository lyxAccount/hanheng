package com.example.interfacedemo.entity;

import com.example.interfacedemo.util.TreeUtils;

import java.util.ArrayList;
import java.util.List;

public class TestTree {
    public static void main(String[] args) {
        List<Dept> list = new ArrayList<>();
        list.add(Dept.builder().id("1").pid("0").name("1").build());
        list.add(Dept.builder().id("2").pid("1").name("1-1").build());
        list.add(Dept.builder().id("3").pid("1").name("1-2").build());
        list.add(Dept.builder().id("6").pid("2").name("1-1-1").build());
        list.add(Dept.builder().id("7").pid("6").name("1-1-2").build());
        list.add(Dept.builder().id("4").pid("0").name("2").build());
        list.add(Dept.builder().id("5").pid("4").name("2-1").build());

        //这个工具类的转换有bug，三级及以下转不了
//        List<Dept> depts = TreeUtil.listToTree(list, Dept::setChildren, Dept::getId, Dept::getPid, (l) -> l.getPid().equals("0"));
        //可以转三级 及以下
        List<Dept> depts1 = TreeUtils.listToTree(list, Dept::setChildren, Dept::getId, Dept::getPid, (l) -> l.getPid().equals("0"));
//        System.out.println(depts.size());

        List<Dept> testDepts = new ArrayList<Dept>() {{
            add(Dept.builder().id("1").pid("0").children(new ArrayList<Dept>() {{
                add(Dept.builder().id("11").pid("1").children(new ArrayList<Dept>() {{
                    add(Dept.builder().id("111").pid("11").build());
                    add(Dept.builder().id("112").pid("11").build());
                }}).build());
            }}).build());
        }};
        List<Dept> result = new ArrayList<>();
        //子集合为null，也就是求的最后一级的子类的集合
//        TreeUtil.treeToListDeep(testDepts, result, Dept::getChildren, (l) -> l.getChildren() == null);
        System.out.println(result);

        List<Dept> result2 = new ArrayList<>();
        //父级id为0，也就是把一级及子部门组装为集合（可通过debug模式发现result和result2的区别）
//        TreeUtil.treeToListDeep(testDepts, result2, Dept::getChildren, (l) -> l.getPid().equals("0"));
        System.out.println(result2);
    }
}
