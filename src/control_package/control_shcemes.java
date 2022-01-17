package control_package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;


public class control_shcemes{
	
	
	public control_shcemes(String inputFile) throws IOException {
		
		/*
		 * note: 
		 * This method will calculate multiple different control schemes.
		 * 
		 * 
		 * args:
		 * inputFile: Path of the network file to be calculated.
		 * 
		 * 
		 * returns: 
		 * For a control scheme, the function will calculate its 
		 * MDS and maximum matching and save it as a txt in the path "outputFile"
		 * 
		 */
		
		String outputFile="./result/find_shcemes.txt";
		
		// ==================== ReadNetwork ====================
		ArrayList<String> list = new ArrayList<String>();			
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		
		list.add("BEGIN");
		String data = reader.readLine();
		while (null != data) {
			list.add(data);
			data = reader.readLine();
		}
		reader.close();
		int[] nodeNum = new int[1];
		int[] edgeNum = new int[1];
		nodeNum[0] = new Integer(list.get(1).replaceAll("\t", " ").replaceAll(" +", " ").split(" ")[1]);
		edgeNum[0] = list.size() - nodeNum[0] - 3;
		Node[] nodeSrc = new Node[nodeNum[0] + 1];
		Node[] nodeDes = new Node[nodeNum[0] + 1];
		Edge[] edge = new Edge[edgeNum[0] + 1];
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
		ArrayList<String> name = new ArrayList<String>();
		name.add("NULL");
		for (int i = 2; i < 2 + nodeNum[0]; i++) {
			name.add(null);
			 //name.add(list.get(i).replaceAll("\t", " ").replaceAll("\"",
			 //"").replaceAll(" +", " ").split(" ")[1]);
		}
		// ==================== ReadName =======================
		System.out.println("================");
		System.out.println("Network Details");
		System.out.println("================");
		System.out.println("Node Number:" + nodeNum[0]);
		System.out.println("Edge Number:" + edgeNum[0]);
		System.out.println("================");
		System.out.println();
		
		
		TreeSet<Integer> APD = new TreeSet<Integer>();
		TreeSet<Integer> matchedEdgeList= new TreeSet<Integer>();
		TreeSet<Integer> driverNode= new TreeSet<Integer>();
		ArrayList<TreeSet<Integer>> EdgeList=new ArrayList<>();
		TreeSet<Integer> tailNode= new TreeSet<Integer>();
		
		int frequency = 1;
		for(int num=1;num<=frequency;) {
		matchedEdgeList.clear();
		driverNode.clear();
		tailNode.clear();
		randomHK randomHK=new randomHK(nodeSrc, nodeDes, edge, nodeNum, edgeNum, APD,outputFile,name,matchedEdgeList,driverNode,tailNode);
		randomHK.find();
		
		if(withoutRepeat(EdgeList,matchedEdgeList)) {
			
			TreeSet<Integer> matchedEdge= (TreeSet<Integer>) matchedEdgeList.clone();
			EdgeList.add(matchedEdge);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile,true));
			 writer.write("\r\n\r\n");
			 writer.write("maximum matching edgeID for scheme"+num+": ");
			 writer.write("\r\n");
			 for (int i : matchedEdgeList) {

				 writer.write(i + " ");
			 
			 }
			writer.write("\r\n");
			writer.write("minimum driver node set for scheme"+num+": ");
			writer.write("\r\n");
			 for (int i : driverNode) {
				 writer.write(i + " ");	 
			 }
			 
			writer.close();
			if(num == 1) {
				System.out.println("The size of MDS is "+driverNode.size());
				System.out.println("The size of Maximum matching is "+matchedEdgeList.size());
				System.out.println();
			}

			System.out.println("Control scheme save complete");
			num++;
			}
		
			
		}
		 
	}
	
	public boolean withoutRepeat(ArrayList<TreeSet<Integer>> EdgeList,TreeSet<Integer> matchedEdgeList) {
		
		for(int i=0;i<EdgeList.size();i++) {
			
			if(EdgeList.get(i).equals(matchedEdgeList)) {
				
				return false;
			}
		}	
		return true;	

	}
	
	
	
}