package com.example.treelib.helper.base;

import com.example.treelib.TreeNode;

import java.util.List;

public interface SelectableTreeAction extends BaseTreeAction {
    void selectNode(TreeNode treeNode);

    void deselectNode(TreeNode treeNode);

    void selectAll();

    void deselectAll();

    List<TreeNode> getSelectedNodes();
}

