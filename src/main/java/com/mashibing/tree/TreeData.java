package com.mashibing.tree;

import lombok.Data;

import java.util.List;

@Data
public class TreeData {

    private Integer id;

    private Integer parentId;

    private String value;

    private List<TreeData> trees;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<TreeData> getTrees() {
        return trees;
    }

    public void setTrees(List<TreeData> trees) {
        this.trees = trees;
    }

    public TreeData(Integer id, Integer parentId, String value) {
        this.id = id;
        this.parentId = parentId;
        this.value = value;
    }
}
