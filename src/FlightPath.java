import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;


public class FlightPath extends JPanel{
	static JComboBox<String> source;
	static JComboBox<String> destination;
	static JButton go;
	static JFrame frame;
	static JTextArea output;
	static JComboBox<String> choice;
	static Network map;
	static CopyOnWriteArrayList<Node> tentativeNodes;
	static CopyOnWriteArrayList<Node> traversedNodes ;
	static ArrayList<Node> path;
	static Node start;
	static Node end;
	static int drawPath = 0;
	static int heuristic = 0;

	FlightPath() {
		source = new JComboBox<>();
		source.addItem("A");
		source.addItem("B");
		source.addItem("C");
		source.addItem("D");
		source.addItem("E");
		source.addItem("F");
		source.addItem("G");
		source.addItem("H");
		source.addItem("I");
		source.addItem("J");
		destination = new JComboBox<>();
		destination.addItem("A");
		destination.addItem("B");
		destination.addItem("C");
		destination.addItem("D");
		destination.addItem("E");
		destination.addItem("F");
		destination.addItem("G");
		destination.addItem("H");
		destination.addItem("I");
		destination.addItem("J");
		go = new JButton("Go");
		go.addActionListener(new buttonClick());;
		frame = new JFrame("Map");
		output = new JTextArea("Output will be displayed here", 5, 50);	
		choice = new JComboBox<>();
		choice.addItem("Distance");
		choice.addItem("Price");
		init();
	}

	public void init() {
		map = new Network();
		tentativeNodes = new CopyOnWriteArrayList<>();
		traversedNodes = new CopyOnWriteArrayList<>();
	}
	
	protected void paintComponent(Graphics g)
	{
		for(Node n : map.list) {
			g.setColor(Color.RED);
			g.drawString(n.name, n.xlocation-10, n.ylocation-15);
			g.setColor(Color.BLACK);
			for(int i = 0; i < map.list.size(); i++) {
				if(map.distances[n.id][i] != 0) {
					g.drawLine(n.xlocation, n.ylocation, map.list.get(i).xlocation, map.list.get(i).ylocation);
					//g.drawString(Double.toString(map.prices[n.id][i]), ((n.xlocation + map.list.get(i).xlocation)/2) - 5, ((n.ylocation + map.list.get(i).ylocation)/2) - 10);
				}
			}
		}
		if(drawPath == 1) {
			for(int i = path.size()-1;i >= 0;i--) { 
				if(i != 0) {
					g.setColor(Color.GREEN);
					g.drawLine(path.get(i).xlocation, path.get(i).ylocation, path.get(i-1).xlocation, path.get(i-1).ylocation);
					g.setColor(Color.BLACK);
					if(heuristic == 1) {
						g.drawString(Double.toString(map.distances[path.get(i).id][path.get(i-1).id]), ((path.get(i).xlocation + path.get(i-1).xlocation)/2) - 5, ((path.get(i).ylocation + path.get(i-1).ylocation)/2) - 10);
					}
					else if(heuristic == 2) {
						g.drawString(Double.toString(map.prices[path.get(i).id][path.get(i-1).id]), ((path.get(i).xlocation + path.get(i-1).xlocation)/2) - 5, ((path.get(i).ylocation + path.get(i-1).ylocation)/2) - 10);
					}
				}
			}
		}
	}

	class buttonClick implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			start = map.list.get(map.locations.get((String) source.getSelectedItem()));
			end = map.list.get(map.locations.get((String) destination.getSelectedItem()));
			String h = (String) choice.getSelectedItem();
			switch(h) {
			case "Distance" : heuristic = 1;break;
			case "Price" : heuristic = 2;break;
			default : heuristic = 1; break;
			}
			if(start.id == end.id) {
				printPath(start);
			}
			else {
				Node solution = AStar(start,end,heuristic);
				printPath(start,solution,heuristic);
				drawPath = 1;
			}

			//System.out.println(calcDistance(start, end));
			frame.repaint();
			init();
		}
	}

	public static Node AStar(Node start, Node end, int heuristic) {
		tentativeNodes.add(start);
		while(!tentativeNodes.isEmpty()) {
			Node x = findLowestFValue();
			tentativeNodes.remove(x);
			for(Node n : x.neighbors) { 
				n.previousNode = x;
			}
			for(Node n : x.neighbors) { 
				if(n.id == end.id) {
					return n;
				}
				if(heuristic == 1) {
					n.g = x.g + map.distances[n.id][x.id];
					n.h = calcDistance(n, end);
					n.f = n.g + n.h;
				}
				else if(heuristic == 2) {
					n.g = x.g + map.prices[n.id][x.id];
					n.h = calcPrice(n, end);
					n.f = n.g + n.h;
				}
				int flag = 1;
				for(Node n1 : tentativeNodes) {
					if(n1.id == n.id && n1.f < n.f) { 
						flag = 0;
					}
				}
				for(Node n1 : traversedNodes) {
					if(n1.id == n.id && n1.f < n.f) { 
						flag = 0;
					}
				}
				if(flag == 1) {
					tentativeNodes.add(n);	
				}
			}
			traversedNodes.add(x);
		}
		return null;
	}



	public static Node findLowestFValue() {
		Node temp = tentativeNodes.get(0);
		for(Node n : tentativeNodes) {
			if(n.f < temp.f) {
				temp = n;
			}
			else if(n.f == temp.f) {
				if(n.id < temp.id) {
					temp = n;
				}
			}
		}
		return temp;
	}

	public static void printPath(Node s, Node e, int h) {
		int count = 0;
		output.setText("");
		double distance = 0;
		double price = 0;
		System.out.println("The starting node is : " + s.name);
		output.append("The starting node is : " + s.name + "\n");
		System.out.println("The destination node is : " + e.name);
		output.append("The destination node is : " + e.name + "\n");
		System.out.print("The optimal path is : ");
		output.append("The optimal path is : ");
		path = new ArrayList<>();
		path.add(e);
		Node temp = e.previousNode;
		while(temp.id != s.id) {
			path.add(temp);
			temp = temp.previousNode;
			count++;
			if(count > 100) {
				System.out.println("Infinite loop detected! Quitting...");
				System.exit(1);
			}
		}
		path.add(s);
		for(int i = path.size()-1;i >= 0;i--) { 
			if(i != 0) {
				System.out.print(path.get(i).name + " -> ");
				output.append(path.get(i).name + " -> ");
				distance = distance + map.distances[path.get(i).id][path.get(i-1).id];
				price = price + map.prices[path.get(i).id][path.get(i-1).id];
			}
			else {
				System.out.print(path.get(i).name);
				output.append(path.get(i).name + "\n");
			}
		}
		if(h == 1) { 
			System.out.println("\nThe total distance is : " + distance + " miles");
			output.append("The total distance is : " + distance + " miles");
		}
		else if(h == 2) { 
			System.out.println("\nThe total price is : $" + price);
			output.append("The total price is : $" + price);
		}
	}

	public static void printPath(Node n) {
		System.out.println("You are already at your destination : " + n.name + "!");
		output.setText("");
		output.append("You are already at your destination " + n.name + "!\n");
	}

	public static double calcDistance(Node a, Node b) {
		return Math.sqrt(Math.pow((b.xlocation-a.xlocation), 2) + Math.pow((b.ylocation-a.ylocation), 2));
	}

	public static double calcPrice(Node a, Node b) {
		return (0.2*calcDistance(a, b));
	}

	public static void main(String args[]) {
		FlightPath path = new FlightPath();
		path.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
		top.add(new JLabel("Source"));
		top.add(source);
		top.add(new JLabel("Destination"));
		top.add(destination);
		top.add(new JLabel("Heuristic"));
		top.add(choice);
		top.add(go);
		frame.setLayout(new BorderLayout(10,10));
		frame.setSize(1000,1000);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(top,BorderLayout.NORTH);
		frame.add(output,BorderLayout.AFTER_LAST_LINE);
		frame.add(path,BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
