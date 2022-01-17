package control_package;

import java.beans.IntrospectionException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;


public class randomHK{
	
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
	public TreeSet<Integer> APD;
	public String outputFile;
	public int countNum=0;
	public ArrayList<Integer> NodeList;
//	public ArrayList<Integer> srcList;
	public ArrayList<Integer> matchedEdge;
	public ArrayList<String> name;
	public int map1[];
	public int map2[];
	public int map3[];
	public int map4[];
	public Node[] nodeSrc1;
	public Node[] nodeDes1;
	public Edge[] edge1;
	public TreeSet<Integer> matchedEdgeList;
	public int num;
	public TreeSet<Integer> driverNode;
	public TreeSet<Integer> tailNode;
	
	public randomHK(Node[] nodeSrc, Node[] nodeDes, Edge[] edge, int[] nodeNum, int[] edgeNum, TreeSet<Integer> APD,String outputFile,ArrayList<String> name,TreeSet<Integer> matchedEdgeList,TreeSet<Integer> driverNode,TreeSet<Integer> tailNode) {
		this.nodeSrc = nodeSrc;
		this.nodeDes = nodeDes;
		this.edge = edge;
		this.nodeNum = nodeNum;
		this.edgeNum = edgeNum;
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
		
		this.APD = APD;
		this.outputFile=outputFile;

		this.NodeList=new ArrayList<Integer>();
//		this.srcList=new ArrayList<Integer>();
		this.matchedEdge=new ArrayList<Integer>();
		this.name=name;
		
		this.map1=new int[nodeNum[0] + 1];
		this.map2=new int[nodeNum[0] + 1];
		this.map3=new int[edgeNum[0] + 1];
		this.map4=new int[edgeNum[0] + 1];
		this.num=num;
		this.driverNode=driverNode;
		this.matchedEdgeList=matchedEdgeList;
		this.tailNode=tailNode;
		
	
	}

	
	
	
	public void initialize() {
		for (int i = 1; i <= nodeNum[0]; i++) {
			markedSrc[i] = 0;
			markedDes[i] = 0;
			distSrc[i] = 0;
			distDes[i] = 0;
			map1[i]=0;
			map2[i]=0;
		}
		
		for(int i = 1; i <= edgeNum[0]; i++) {
			map3[i]=0;
			map4[i]=0;
		}

		this.unMatchedNode.clear();
		
		
		nodeSrc1=new Node[nodeNum[0] + 1];
		nodeDes1=new Node[nodeNum[0] + 1];
		edge1=new Edge[edgeNum[0] + 1];
				
		for(int i=nodeNum[0];i>0;) {		
            Random random = new Random();
            int a=random.nextInt(nodeNum[0]-1+1)+1; 
            if(map2[a]==0) {    	
                map1[i]=a;
                map2[a]=i;	
                i--;
            }else {
            	continue;
            }
		}	
		
		for(int i=edgeNum[0];i>0;) {		
            Random random = new Random();
            int a=random.nextInt(edgeNum[0]-1+1)+1; 
            if(map4[a]==0) {    	
                map3[i]=a;
                map4[a]=i;	
                i--;
            }else {
            	continue;
            }
		}	
		
		for(int i=1;i<=edgeNum[0];i++) {
			int src=map1[edge[i].src];
			int des=map1[edge[i].des];			
			Edge temp=new Edge(src, des);
			edge1[map3[i]]=temp;
			//edge1[i]=temp;
		}
		
		for(int i = 1; i <= nodeNum[0]; i++) {
			
			//NodeSrc
			Node tempSrc=new Node(map1[i]);
			tempSrc.name=nodeSrc[i].name;
			TreeSet<Integer> tempId1=new TreeSet<Integer>();
			tempId1.clear();
			for(Integer edgeId:nodeSrc[i].nodeEdges) {
				tempId1.add(map3[edgeId]);
			}
			tempId1.comparator();
			tempSrc.nodeEdges.addAll(tempId1);
			nodeSrc1[map1[i]]=tempSrc;
			
			
			//NodeDes
			Node tempDes=new Node(map1[i]);
			tempDes.name=nodeDes[i].name;
			TreeSet<Integer> tempId2=new TreeSet<Integer>();
			tempId2.clear();
			for(Integer edgeId:nodeDes[i].nodeEdges) {
				tempId2.add(map3[edgeId]);
			}
			tempId2.comparator();
			tempDes.nodeEdges.addAll(tempId2);
			nodeDes1[map1[i]]=tempDes;		
		}
		


		
		
		
	}
	
	public void find() throws IOException {
		initialize();
		while (BFS()) {
			for (int des = 1; des <= nodeNum[0]; des++) {
				if (markedDes[des] == 0) {
					DFS(des);		
				}
			}
		}
		APD.addAll(unMatchedNode);
		APD.comparator();
		int a = 0;
		for (int i = 1; i <= nodeNum[0]; i++) {
			if (markedDes[i] == 0) {
				a++;
				driverNode.add((Integer)map2[i]);
			}
			
			if (markedSrc[i] == 0) {
				a++;
				tailNode.add((Integer)map2[i]);
			}
			
			
			if(markedDes[i]!=0) {
				for(int k=1;k<=edgeNum[0];k++) {
					if((edge1[k].src==markedDes[i])&&(edge1[k].des==i)) {
						if(!matchedEdge.contains(map4[k])) {
							matchedEdge.add(map4[k]);
						}
						
					}
				}
			}
			
			if(markedSrc[i]!=0) {
				for(int k=1;k<=edgeNum[0];k++) {
					if((edge1[k].des==markedSrc[i])&&(edge1[k].src==i)) {
						if(!matchedEdge.contains(map4[k])) {
						matchedEdge.add(map4[k]);
						}
					}
				}
			}
		}

		
		
		
//		for(int i = 1; i <= nodeNum[0]; i++) {
//			
//			if(markedDes[i]!=0) {
//				for(int k=1;k<=edgeNum[0];k++) {
//					if((edge1[k].src==markedDes[i])&&(edge1[k].des==i)) {
//						if(!matchedEdge.contains(k)) {
//							matchedEdge.add(k);
//						}
//						
//					}
//				}
//			}
//			
//			if(markedSrc[i]!=0) {
//				for(int k=1;k<=edgeNum[0];k++) {
//					if((edge1[k].des==markedSrc[i])&&(edge1[k].src==i)) {
//						if(!matchedEdge.contains(k)) {
//						matchedEdge.add(k);
//						}
//					}
//				}
//			}	
//		}

		matchedEdgeList.addAll(matchedEdge);
//		matchedEdgeList.comparator();
		driverNode.comparator();
		tailNode.comparator();
		
//		 for (int i : matchedEdge) {
//				
//			 //writer.write(i + "\t" + name.get(edge[i].src)+ "\t" + name.get(edge[i].des)+"\r\n");
//			 //writer.write(i + "\t" + edge[i].src+ "\t" + edge[i].des+"\r\n");
//			 System.out.println("node of matchedEdge:"+edge[i].src+":"+edge[i].des);
//		 
//		 }

//		 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile,true));
//		 writer.write("\r\n\r\n");
//		 writer.write("edgeID for "+num+": ");
//		 writer.write("\r\n");
//		 for (int i : matchedEdgeList) {
//
//			 writer.write(i + " ");
//		 
//		 }
//		writer.write("\r\n");
//		writer.write("driver node for "+num+": ");
//		writer.write("\r\n");
//		 for (int i : driverNode) {
//			 writer.write(i + " ");	 
//		 }
//		 
//		writer.close();
//		System.out.println(num+" write over, with "+a+" driver node and "+matchedEdge.size()+" matched Edge !!");
//		System.out.println("================");
		
		
		

	}

	public boolean BFS() {
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
			for (int edg : nodeDes1[des].nodeEdges) {
				if (edg == 0) {
					continue;
				}
				int src = edge1[edg].src;				
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

	public boolean DFS(int des) {
		for (int edg : nodeDes1[des].nodeEdges) {
			if (edg == 0) {
				continue;
			}
			int src = edge1[edg].src;
			if (distSrc[src] == distDes[des] + 1) {
				distSrc[src] = 0;
				if ((markedSrc[src] == 0) || (DFS(markedSrc[src]))) {
					markedSrc[src] = des;
					markedDes[des] = src;
					return true;
				}
			}
		}
		return false;
	}
	
	
}