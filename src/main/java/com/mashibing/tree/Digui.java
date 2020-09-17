package com.mashibing.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Digui {

    public static void main(String[] args) {
        List<TreeData> trees=new ArrayList<>();
        trees.add(new TreeData(1,0,"父一"));
        trees.add(new TreeData(2,0,"父二"));
        trees.add(new TreeData(3,0,"父三"));
        trees.add(new TreeData(4,0,"父四"));
        trees.add(new TreeData(5,1,"子一"));
        trees.add(new TreeData(6,1,"子二"));
        trees.add(new TreeData(7,2,"子三"));
        trees.add(new TreeData(8,2,"子四"));
        trees.add(new TreeData(9,3,"子五"));
        trees.add(new TreeData(10,6,"子子一"));
        trees.add(new TreeData(11,5,"子子二"));
        trees.add(new TreeData(12,7,"子子三"));
        trees.add(new TreeData(13,7,"子子四"));
        trees.add(new TreeData(14,8,"子子五"));
        List<TreeData> collect = trees.stream().filter(treeData -> {
            return treeData.getParentId() == 0;
        }).map(
                //遍历每个父类
                treeData -> {
                    treeData.setTrees(getChildrens(treeData, trees));
                    return treeData;
                }
        ).collect(Collectors.toList());
        System.out.println(collect);


    }

    private static List<TreeData> getChildrens(TreeData root, List<TreeData> trees){
        //遍历集合
        List<TreeData> collect = trees.stream().filter((tree) -> {
            //过滤当前父亲节点下的数据
            return root.getId() == tree.getParentId();
        }).map(treeData -> {
            //递归调用设置其子类
            treeData.setTrees(getChildrens(treeData, trees));
             //返回当前对象
            return  treeData;
        }).collect(Collectors.toList());

        //返回
        return  collect;
    }
}
