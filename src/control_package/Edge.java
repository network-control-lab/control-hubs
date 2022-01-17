package control_package;

//necessary edge class for network storing
public class Edge {
	// source & destination node of one edge
	public int src;
	public int des;

	public Edge(int src, int des) {
		this.src = src;
		this.des = des;
	}
}