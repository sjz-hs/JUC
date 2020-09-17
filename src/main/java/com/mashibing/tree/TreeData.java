package com.mashibing.tree;

import lombok.Data;

import java.util.List;

@Data
public class TreeData {

    private Integer id;

    private Integer parentId;

    private String value;

    private List<TreeData> trees;

    public TreeData(Integer id, Integer parentId, String value) {
        this.id = id;
        this.parentId = parentId;
        this.value = value;
    }
}
