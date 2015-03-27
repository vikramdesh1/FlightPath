import java.util.ArrayList;


public class Node {
	String name;
	int xlocation, ylocation, id;
	Node previousNode;
	double g, f, h;
	ArrayList<Node> neighbors;
	boolean visited;

	Node(String n, int i, int x, int y) {
		this.name = n;
		this.id = i;
		this.xlocation = x;
		this.ylocation = y;
		this.g = this.h = this.f = 0;
		neighbors = new ArrayList<>();
		this.visited = false;
	}
}
