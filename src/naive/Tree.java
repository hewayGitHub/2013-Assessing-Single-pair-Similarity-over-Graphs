package naive;

public class Tree {
	TreeNode rootNode;
	int depth;
	

	public Tree(int root, int depth) {
		this.depth = depth;
		rootNode = new TreeNode(root, null);
		expandNode(rootNode, 0);

	}

	// dep: the depth of treenode
	void expandNode(TreeNode treenode, int dep) {
		if (treenode.no >= 0 && treenode.node.size() == 0) {
			int size = SimQuery.transition.length;
			int no = treenode.no;
			for (int i = 0; i < size; i++)
				if (SimQuery.transition[no][i] == 1) {
					TreeNode newNode = new TreeNode(i, treenode);
					treenode.node.add(newNode);
					if (dep < depth-1)
						expandNode(newNode, dep + 1);
				}
		} else {
			System.out.println(treenode.no + " initialize error!");
			return;
		}

	}

	public static void main(String[] args) {

	}

}
