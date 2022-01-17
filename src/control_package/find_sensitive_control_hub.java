package control_package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.print.attribute.standard.PrinterInfo;

import control_package.node_classification;


public class find_sensitive_control_hub {
	public int[] nodeNum;
	public int[] edgeNum;
	public Node[] nodeSrc;
	public Node[] nodeDes;
	public Edge[] edge;
	public ArrayList<String> name;
	public TreeSet<Integer> init_control_hub;
	public TreeSet<Integer> sensitive_control_hub;
	public int[] markedSrc;
	public int[] markedDes;
	public int[] distSrc;
	public int[] distDes;
	public ArrayList<Integer> unMatchedNode;
	public TreeSet<Integer> Control_hub;
	public TreeSet<Integer> Head;
	public TreeSet<Integer> Tail;
	
	
	public find_sensitive_control_hub(String inputFile) throws IOException {
		
		/*
		 * note: 
		 * This method will calculate sensitive control hubs of input network.
		 * 
		 * 
		 * args:
		 * inputFile: Path of the network file to be calculated.
		 * 
		 * 
		 * returns: 
		 * the sensitive control hubs of input network will calculate 
		 * and save it as a txt in the path "outputFile".
		 * 
		 */
		
		String outputFile = "./result/sensitive_control_hub.txt";
		String tempFile = "./net/temp.net";
		
		// init node type
		node_classification node_type = new node_classification(inputFile);
		this.init_control_hub = node_type.Control_hub;
		this.sensitive_control_hub=new TreeSet<Integer>();
		
		
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

		int index_of_del_edge = 1;
		
		while(index_of_del_edge <= edgeNum[0]) {
			int del_row = nodeNum[0] + index_of_del_edge + 2;
			
			BufferedReader reader2 = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			int i = 1;
			String data2 = reader2.readLine();
			while (i <= nodeNum[0] + edgeNum[0] + 2 && data2!=null) {
				if(i!=del_row) {
					writer.write(data2);
					if(i != nodeNum[0] + edgeNum[0] + 2) {
						writer.write("\n");					
					}

				}
				data2 = reader2.readLine();
				i++;
			}
			reader2.close();
			writer.close();
			
			
			node_classification temp_control_hub = new node_classification(tempFile);
			for(int temp_hub:init_control_hub) {
				if(!temp_control_hub.Control_hub.contains(temp_hub)) {
					sensitive_control_hub.add(temp_hub);
				}
				
			}
			
			System.out.println("del_edge: "+index_of_del_edge);
			System.out.println("sensitive_control_hub: "+sensitive_control_hub);
			
			
			index_of_del_edge++;

		}
		
		BufferedWriter result_writer = new BufferedWriter(new FileWriter(outputFile));
		result_writer.write("sensitive control hub id:\n");
		for(int temp_sensitive:sensitive_control_hub) {
			result_writer.write(temp_sensitive+"\n");
		}
		result_writer.close();
		System.out.println("result saved");
		
	}

	
}