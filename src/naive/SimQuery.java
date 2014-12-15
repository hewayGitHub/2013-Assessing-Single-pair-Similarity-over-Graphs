package naive;

import icdm.ScaleFreeGraph;

import java.io.File;
import java.util.Random;

import share.Matrix;

public class SimQuery {

	double C = 0.5;
	static int K = 6;
	static double sim = 0;

	public static int[][] transition;

	public static void main(String[] args) throws Exception {
		//		transition = new int[][] { { 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
		//				{ 1, 0, 0, 1, 1 }, { 1, 1, 1, 0, 0 }, { 0, 0, 0, 1, 0 } };
		//		transition = Matrix.getTranspose(transition);

		int nodeNum = 4000;
		int repeat = 100;
		transition = ScaleFreeGraph
				.getAdjacent(
						new File(
								"C:\\Program Files\\Barabasi Graph Generator\\Barabasi\\Edges.txt"),
						nodeNum);
		System.gc();

		long t1 = System.currentTimeMillis();
		for (int count = 0; count < repeat; count++) {

			Random random = new Random();
			int a = random.nextInt(nodeNum);
			int b;
			do {
				b = random.nextInt(nodeNum);
				if (a != b)
					break;
			} while (true);
			
			System.out.println(":: "+a+" "+b);
			Tree treeA = new Tree(a, K);
			Tree treeB = new Tree(b, K);

			// broadth first search
			SimQuery query = new SimQuery();
			query.BFS(treeA.rootNode, treeB.rootNode);
			//System.out.println(sim);
			sim = 0;
			System.out.println(count+": "+a+" "+b);
		}
		System.out.println(("NLS average time cost: "+(System.currentTimeMillis()-t1)/100));
		
	}

	double BFS(TreeNode nodeA, TreeNode nodeB) {
		double d = 0;
		for (int i = 0; i < nodeA.node.size(); i++)
			for (int j = 0; j < nodeB.node.size(); j++) {
				TreeNode childA = nodeA.node.get(i);
				TreeNode childB = nodeB.node.get(j);
				if (childA.no == childB.no) {//last meet

					TreeNode fatherA = childA.father;
					TreeNode fatherB = childB.father;
					double proA = 1.;
					double proB = 1.;
					boolean flag = true;
					for (; fatherA != null && fatherB != null;) {
						if (fatherA.no != fatherB.no) {

							proA *= C * (1. / fatherA.node.size());
							proB *= (1. / fatherB.node.size());
							fatherA = fatherA.father;
							fatherB = fatherB.father;
						} else {
							flag = false;
							break;
						}
					}
					if (flag){
						double tmp=proA * proB;
						d += tmp;
						if(tmp<0.001)
							continue;
					}
				}

				//System.out.println(childA.no + " " + childB.no + " " + d);

				//BFS
				if (childA.node.size() > 0 && childB.node.size() > 0)
					BFS(childA, childB);
			}
		sim += d;
		return d;
	}
}
