package control_package;

import java.util.ArrayList;

// necessary node class for network storing
public class Node {
	// node id beginning from 1
	public int id;
	public String name;
	// edge id beginning from 1
	public ArrayList<Integer> nodeEdges;

	public Node(int id) {
		this.id = id;
		this.nodeEdges = new ArrayList<Integer>();
	}
}