package naive;

import java.util.*;

public class TreeNode {
	int no = -1;
	ArrayList<TreeNode> node;
	TreeNode father;

	public TreeNode(int i, TreeNode father) {
		no = i;
		node = new ArrayList<TreeNode>();
		this.father = father;
	}
}
