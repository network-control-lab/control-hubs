package control_package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;



public class node_classification {
	
	//head¡¢tail¡¢control_hub
	public int[] nodeNum;
	public int[] edgeNum;
	public Node[] nodeSrc;
	public Node[] nodeDes;
	public Edge[] edge;
	public int[] markedSrc;
	public int[] markedDes;
	public int[] distSrc;
	public int[] distDes;
	public ArrayList<Integer> unMatchedNode;
	public TreeSet<Integer> Control_hub;
	public TreeSet<Integer> Head;
	public TreeSet<Integer> Tail;
	public ArrayList<String> name;
	


	public node_classification(String inputFile) throws IOException {
		
		/*
		 * note: 
		 * This method classifies nodes into head, tail 
		 * and control center according to their position in 
		 * the control path.
		 * 
		 * During the calculation, the file named "nodetpye.txt" 
		 * will be rewritten. Before that, please save the result
		 * file of the same name calculated by the "node_classification" function.
		 * 
		 * args:
		 * inputFile: Path of the network file to be calculated.
		 * 
		 * 
		 * returns: 
		 * Network node classification results will be 
		 * saved as a txt in the path "outputFile"
		 * 
		 */
		
		String outputFile = "./result/nodeType.txt";
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		
		list.add("BEGIN");
		String data = reader.readLine();
		while (null != data) {
			list.add(data);
			data = reader.readLine();
		}
		reader.close();
		nodeNum = new int[1];
		edgeNum = new int[1];
		nodeNum[0] = new Integer(list.get(1).replaceAll("\t", " ").replaceAll(" +", " ").split(" ")[1]);
		edgeNum[0] = list.size() - nodeNum[0] - 3;
		nodeSrc = new Node[nodeNum[0] + 1];
		nodeDes = new Node[nodeNum[0] + 1];
		edge = new Edge[edgeNum[0] + 1];
		for (int i = 1; i <= edgeNum[0]; i++) {
			String array = list.get(i + nodeNum[0] + 2).trim().replaceAll("\t", " ").replaceAll(" +", " ");
			if (array.indexOf(" ", array.indexOf(" ") + 1) == -1) {
				edge[i] = new Edge(new Integer(array.split(" ")[0]), new Integer(array.split(" ")[1]));
			} else {
				array = array.substring(0, array.indexOf(" ", array.indexOf(" ") + 1));
				edge[i] = new Edge(new Integer(array.split(" ")[0]), new Integer(array.split(" ")[1]));
			}
		}
		for (int i = 1; i <= nodeNum[0]; i++) {
			nodeSrc[i] = new Node(i);
			nodeDes[i] = new Node(i);
			nodeSrc[i].nodeEdges.add(0);
			nodeDes[i].nodeEdges.add(0);
		}
		for (int i = 1; i <= edgeNum[0]; i++) {
			nodeSrc[edge[i].src].nodeEdges.add(i);
			nodeDes[edge[i].des].nodeEdges.add(i);
		}
		// ==================== ReadNetwork ====================
		// ==================== ReadName =======================
		name = new ArrayList<String>();
		name.add("NULL");
		for (int i = 2; i < 2 + nodeNum[0]; i++) {
			name.add(null);
		}
		// ==================== ReadName =======================
		System.out.println("================");
		System.out.println("Network Details");
		System.out.println("================");
		System.out.println("Node Number:" + nodeNum[0]);
		System.out.println("Edge Number:" + edgeNum[0]);
		System.out.println("================");
		System.out.println();
		
		
		this.markedSrc = new int[nodeNum[0] + 1];
		this.markedDes = new int[nodeNum[0] + 1];
		this.distSrc = new int[nodeNum[0] + 1];
		this.distDes = new int[nodeNum[0] + 1];
		for (int i = 1; i <= nodeNum[0]; i++) {
			markedSrc[i] = 0;
			markedDes[i] = 0;
			distSrc[i] = 0;
			distDes[i] = 0;
		}
		this.unMatchedNode = new ArrayList<Integer>();
		this.Control_hub = new TreeSet<Integer>();
		this.Head=new TreeSet<Integer>();
		this.Tail=new TreeSet<Integer>();
		
		this.judge(inputFile, outputFile);

	}

	public void initialize() {
		for (int i = 1; i <= nodeNum[0]; i++) {
			markedSrc[i] = 0;
			markedDes[i] = 0;
			distSrc[i] = 0;
			distDes[i] = 0;
		}
		this.unMatchedNode.clear();
	}

	public void judge(String inputFile,String outputFile) throws IOException {

		// Step1,find all tail nodes
		initialize();
		while (BFS_APU()) {
			for (int src = 1; src <= nodeNum[0]; src++) {
				if (markedSrc[src] == 0) {
					DFS_APU(src);
				}
			}
		}
		Tail.addAll(unMatchedNode);


		// Step2,find all head nodes
		initialize();
		while (BFS_APD()) {
			for (int des = 1; des <= nodeNum[0]; des++) {
				if (markedDes[des] == 0) {
					DFS_APD(des);
				}
			}
		}
		Head.addAll(unMatchedNode);


		// Step3, find all control hub
		for (int i = 1; i <= nodeNum[0]; i++) {
			if (!Head.contains(i) && !Tail.contains(i)) {
				Control_hub.add(i);
			}

		}
		
		
		System.out.println("================");
		System.out.println("Head Number:" + Head.size());
		System.out.println("Control_hub Number:" + Control_hub.size());
		System.out.println("Tail Number:" + Tail.size());
		System.out.println("================");
		System.out.println();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		for (int i = 1; i <= nodeNum[0]; i++) {
			if(Head.contains(i) && !Tail.contains(i)) {
				writer.write(i+": head\r\n");
			}else if (Tail.contains(i) && !Head.contains(i)) {
				writer.write(i+": Tail\r\n");
			}else if (Head.contains(i)) {
				writer.write(i+": Head,Tail\r\n");
			}else if (Control_hub.contains(i)) {
				writer.write(i+": Control_hub\r\n");
			}
				

		}
		
		writer.write("");
		writer.close();
//		System.out.println("File is saved");
	}


	public boolean BFS_APU() {
		boolean flag = false;
		unMatchedNode.clear();
		for (int i = 1; i <= nodeNum[0]; i++) {
			distDes[i] = 0;
			distSrc[i] = 0;
			if (markedSrc[i] == 0) {
				unMatchedNode.add(i);
			}
		}

		for (int src, i = 0; i < unMatchedNode.size(); i++) {
			src = unMatchedNode.get(i);
			for (int edg : nodeSrc[src].nodeEdges) {
				if (edg == 0) {
					continue;
				}
				int des = edge[edg].des;
				if (distDes[des] == 0) {
					distDes[des] = distSrc[src] + 1;
					if (markedDes[des] == 0) {
						flag = true;
					} else {
						distSrc[markedDes[des]] = distDes[des] + 1;
						unMatchedNode.add(markedDes[des]);
					}
				}
			}
		}
		return flag;
	}

	public boolean DFS_APU(int src) {
		for (int edg : nodeSrc[src].nodeEdges) {
			if (edg == 0) {
				continue;
			}
			int des = edge[edg].des;
			if (distDes[des] == distSrc[src] + 1) {
				distDes[des] = 0;
				if ((markedDes[des] == 0) || (DFS_APU(markedDes[des]))) {
					markedDes[des] = src;
					markedSrc[src] = des;
					return true;
				}
			}
		}
		return false;
	}

	public boolean BFS_APD() {
		boolean flag = false;
		unMatchedNode.clear();
		for (int i = 1; i <= nodeNum[0]; i++) {
			distSrc[i] = 0;
			distDes[i] = 0;
			if (markedDes[i] == 0) {
				unMatchedNode.add(i);
			}
		}

		for (int des, i = 0; i < unMatchedNode.size(); i++) {
			des = unMatchedNode.get(i);
			for (int edg : nodeDes[des].nodeEdges) {
				if (edg == 0) {
					continue;
				}
				int src = edge[edg].src;
				if (distSrc[src] == 0) {
					distSrc[src] = distDes[des] + 1;
					if (markedSrc[src] == 0) {
						flag = true;
					} else {
						distDes[markedSrc[src]] = distSrc[src] + 1;
						unMatchedNode.add(markedSrc[src]);
					}
				}
			}
		}
		return flag;
	}

	public boolean DFS_APD(int des) {
		for (int edg : nodeDes[des].nodeEdges) {
			if (edg == 0) {
				continue;
			}
			int src = edge[edg].src;
			if (distSrc[src] == distDes[des] + 1) {
				distSrc[src] = 0;
				if ((markedSrc[src] == 0) || (DFS_APD(markedSrc[src]))) {
					markedSrc[src] = des;
					markedDes[des] = src;
					return true;
				}
			}
		}
		return false;
	}
}
