package com.techlabs.apdcl.view.activity;

import static com.techlabs.apdcl.Utils.Config.isTreeNode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.treelib.TreeNode;
import com.example.treelib.TreeView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.techlabs.apdcl.DataBase.Room.NetworkDBAdaptor;
import com.techlabs.apdcl.Level.MyNodeViewFactory;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ResponseDataUtils;

import java.util.ArrayList;
import java.util.List;

public class TreeActivity extends AppCompatActivity {

    private TreeNode root;
    private TreeNode originalRoot;
    private TreeView treeView;
    private RelativeLayout relativeLayout;
    private ShimmerFrameLayout mShimmerViewContainer;
    private AutoCompleteTextView FiltersAutoCompleteTextView;
    private SearchView FiltersSearchView;
    private ExtendedFloatingActionButton LoadNetworkBtn;
    private boolean doubleBackToExitPressedOnce = false;
    private PrefManager prefManager;
    ArrayList<String> list = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        prefManager = new PrefManager(TreeActivity.this);
        Toolbar toolbar = findViewById(R.id.mToolbar);
        toolbar.setTitle("Network List");
        setSupportActionBar(toolbar);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmer();
        relativeLayout = findViewById(R.id.container);
        FiltersSearchView = findViewById(R.id.autoComplete);
        root = TreeNode.root();

        LoadNetworkBtn = findViewById(R.id.load_network_fabBtn);

        LoadNetworkBtn.setOnClickListener(v -> {
            if (!ResponseDataUtils.NetworkList.isEmpty()) {
                if (ResponseDataUtils.NetworkList.size() < 4){
                    if (prefManager.getType().contains("Edit")) {
                        prefManager.setUserType("Analysis");
                    } else {
                        prefManager.setUserType(prefManager.getType());
                    }
                    Intent intent = new Intent(TreeActivity.this, MapActivity.class);
                    intent.putStringArrayListExtra("NetworkId", ResponseDataUtils.NetworkList);
                    intent.putExtra("Type", "Normal");
                    startActivity(intent);
                }else {
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Please you can select 3 feeder maximum !", Snackbar.LENGTH_LONG);
                    snack.show();
                }
            } else {
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Please select any feeder!", Snackbar.LENGTH_LONG);
                snack.show();
            }
        });

        FiltersSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                filterTree(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTree(newText.trim());
                return true;
            }
        });

        createTree();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (treeView != null) {
            treeView.deselectAll();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (treeView != null) {
            treeView.deselectAll();
        }
    }

    private void createTree() {
        root = TreeNode.root();
        ArrayList<String> group3 = NetworkDBAdaptor.getInstance(TreeActivity.this).getdistinctgroup3();
        for (int i = 0; i < group3.size(); i++) {
            if (group3.get(i) != null) {
                TreeNode group3s = new TreeNode(group3.get(i), 0);
                ArrayList<String> group2 = NetworkDBAdaptor.getInstance(getBaseContext()).getdistinctgroup2(group3.get(i));
                for (int j = 0; j < group2.size(); j++) {
                    TreeNode group2s = new TreeNode(group2.get(j), 1);
                    ArrayList<String> group1 = NetworkDBAdaptor.getInstance(getBaseContext()).getdistinctgroup1(group3.get(i), group2.get(j));
                    for (int k = 0; k < group1.size(); k++) {
                        if (group1.get(k) != null) {
                            TreeNode group1s = new TreeNode(group1.get(k), 2);
                            ArrayList<String> networkIds = NetworkDBAdaptor.getInstance(getBaseContext()).getdistinctNetworkId(group3.get(i), group2.get(j), group1.get(k));
                            for (int m = 0; m < networkIds.size(); m++) {
                                if (networkIds.get(m) != null) {
                                    TreeNode networkIdNode = new TreeNode(networkIds.get(m), 3);
                                    group1s.addChild(networkIdNode);
                                }
                            }
                            if (networkIds.size() == 0) {
                            } else {
                                group2s.addChild(group1s);
                            }
                        }
                    }
                    if (group1.size() == 0) {

                    } else {
                        group3s.addChild(group2s);
                    }
                }
                root.addChild(group3s);
            }
        }
        originalRoot = root;
        if (treeView == null) {
            treeView = new TreeView(root, TreeActivity.this, new MyNodeViewFactory());
            View view = treeView.getView();
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            relativeLayout.addView(view);
        } else {
            treeView.updateTreeView(root);
        }
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    private String getSelectedNodes() {
        String network = "";
        List<TreeNode> selectedNodes = treeView.getSelectedNodes();
        for (int i = 0; i < selectedNodes.size(); i++) {
            list.add(selectedNodes.get(i).getValue().toString());
            if (i == selectedNodes.size() - 1) {
                network = selectedNodes.get(i).getValue().toString();
            }
        }
        return network;
    }

    private void filterTree(String query) {
        if (treeView != null) {
            if (query.isEmpty()) {
                collapseAllNodes(originalRoot);
                treeView.updateTreeView(originalRoot);
                return;
            }
            if (query.length() < 3) {
                return;
            }
            treeView.collapseAll();


            List<TreeNode> matchedNodes = new ArrayList<>();
            findNodesByFeederId(root, query.toLowerCase().replace(" ", ""), matchedNodes);
            TreeNode filteredRoot = TreeNode.root();
            for (TreeNode matchedNode : matchedNodes) {
                addNodeWithParentsAndChildren(filteredRoot, matchedNode);
            }

            treeView.updateTreeView(filteredRoot);

        }
    }

    private void findNodesByFeederId(TreeNode parentNode, String feederId, List<TreeNode> matchedNodes) {
        Object value = parentNode.getValue();
        if (value != null && value.toString().toLowerCase().replace(" ", "").contains(feederId)) {
            matchedNodes.add(parentNode);
        } else {
            for (TreeNode child : parentNode.getChildren()) {
                findNodesByFeederId(child, feederId, matchedNodes);
            }
        }
    }

    private void addNodeWithParentsAndChildren(TreeNode filteredRoot, TreeNode matchedNode) {
        List<TreeNode> path = new ArrayList<>();
        TreeNode currentNode = matchedNode;

        while (currentNode != null && currentNode != root) {
            path.add(0, currentNode);
            currentNode = currentNode.getParent();
        }

        TreeNode currentParent = filteredRoot;
        for (TreeNode node : path) {
            TreeNode existingNode = findOrCreateNode(currentParent, node);
            currentParent = existingNode;
        }

        currentParent.setExpanded(true);
        addChildrenNodes(currentParent, matchedNode);
    }

    private TreeNode findOrCreateNode(TreeNode parent, TreeNode nodeToFind) {
        for (TreeNode child : parent.getChildren()) {
            if (child.getValue().equals(nodeToFind.getValue())) {
                return child;
            }
        }
        TreeNode newNode = new TreeNode(nodeToFind.getValue(), nodeToFind.getLevel());
        newNode.setExpanded(true);
        parent.addChild(newNode);
        return newNode;
    }

    private void addChildrenNodes(TreeNode parentNode, TreeNode originalNode) {
        for (TreeNode child : originalNode.getChildren()) {
            TreeNode newChild = new TreeNode(child.getValue(), child.getLevel());
            parentNode.addChild(newChild);
            addChildrenNodes(newChild, child);
        }
    }

    private void addNodeToFilteredTree(TreeNode filteredRoot, TreeNode nodeToAdd) {
        TreeNode currentParent = filteredRoot;
        TreeNode currentNode = nodeToAdd;
        while (currentNode != null && currentNode != root) {
            TreeNode newNode = new TreeNode(currentNode.getValue(), currentNode.getLevel());
            currentParent.addChild(newNode);
            currentParent = newNode;
            currentNode = currentNode.getParent();
        }
    }

    private void collapseAllNodes(TreeNode node) {
        for (TreeNode child : node.getChildren()) {
            collapseAllNodes(child);
        }
        node.setExpanded(false);
    }

    @Override
    public void onBackPressed() {
        isTreeNode = false;
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Press back again to exit!", Snackbar.LENGTH_LONG);
        snack.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_toll_menu, menu);
        menu.getItem(0).setVisible(prefManager.getType().contains("Edit"));
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                prefManager.setIsUserLogin(false);
                startActivity(new Intent(TreeActivity.this, LoginActivity.class));
                finish();
                break;

            /*case R.id.nav_expand:
                treeView.expandAll();
                break;

            case R.id.nav_collapse_all:
                treeView.collapseAll();
                break;*/

            case R.id.nav_nsc:
                if (prefManager.getType().contains("Edit")) {
                    Intent intent = new Intent(TreeActivity.this, NewConnection.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}