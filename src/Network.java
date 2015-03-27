import java.util.ArrayList;
import java.util.HashMap;


public class Network {
	ArrayList<Node> list = new ArrayList<>();
	HashMap<String, Integer> locations = new HashMap<>();
	int prices[][];
	double distances[][];

	double calcDistance(Node a, Node b) {
		return Math.sqrt(Math.pow((b.xlocation-a.xlocation), 2) + Math.pow((b.ylocation-a.ylocation), 2));
	}

	Network() {
		list.add(new Node("A", 0, 50, 370));
		list.add(new Node("B", 1, 150, 150));
		list.add(new Node("C", 2, 200, 590));
		list.add(new Node("D", 3, 400, 220));
		list.add(new Node("E", 4, 420, 750));
		list.add(new Node("F", 5, 650, 480));
		list.add(new Node("G", 6, 680, 90));
		list.add(new Node("H", 7, 810, 350));
		list.add(new Node("I", 8, 850, 730));
		list.add(new Node("J", 9, 900, 550));
		locations.put("A", 0);
		locations.put("B", 1);
		locations.put("C", 2);
		locations.put("D", 3);
		locations.put("E", 4);
		locations.put("F", 5);
		locations.put("G", 6);
		locations.put("H", 7);
		locations.put("I", 8);
		locations.put("J", 9);
		prices = new int[list.size()][list.size()];
		distances = new double[list.size()][list.size()];
		for(int i = 0; i < list.size(); i++) {
			for(int j = 0; j < list.size(); j++) {
				prices[i][j] = 0;
				distances[i][j] = 0;
			}
		}
		prices[0][1] = 100;
		prices[0][2] = 150;
		prices[0][3] = 250;
		prices[0][5] = 400;
		prices[1][0] = 100;
		prices[1][3] = 100;
		prices[2][0] = 150;
		prices[2][3] = 200;
		prices[2][4] = 200;
		prices[3][0] = 250;
		prices[3][1] = 100;
		prices[3][2] = 200;
		prices[3][5] = 100;
		prices[3][7] = 250;
		prices[3][6] = 150;
		prices[4][2] = 200;
		prices[4][5] = 150;
		prices[4][9] = 150;
		prices[5][0] = 400;
		prices[5][3] = 100;
		prices[5][6] = 100;
		prices[5][6] = 150;
		prices[5][7] = 100;
		prices[5][8] = 150;
		prices[5][9] = 200;
		prices[6][3] = 150;
		prices[6][5] = 100;
		prices[6][7] = 100;
		prices[7][6] = 100;
		prices[7][3] = 200;
		prices[7][5] = 100;
		prices[7][8] = 250;
		prices[8][9] = 150;
		prices[8][2] = 350;
		prices[8][5] = 150;
		prices[8][7] = 250;
		prices[9][4] = 150;
		prices[9][5] = 200;
		prices[9][8] = 150;

		distances[0][1] = calcDistance(list.get(0), list.get(1));
		distances[0][2] = calcDistance(list.get(0), list.get(2));
		distances[0][3] = calcDistance(list.get(0), list.get(3));
		distances[0][5] = calcDistance(list.get(0), list.get(5));
		distances[1][0] = calcDistance(list.get(1), list.get(0));
		distances[1][3] = calcDistance(list.get(1), list.get(3));
		distances[2][0] = calcDistance(list.get(2), list.get(0));
		distances[2][3] = calcDistance(list.get(2), list.get(3));
		distances[2][4] = calcDistance(list.get(2), list.get(4));
		distances[3][0] = calcDistance(list.get(3), list.get(0));
		distances[3][1] = calcDistance(list.get(3), list.get(1));
		distances[3][2] = calcDistance(list.get(3), list.get(2));
		distances[3][5] = calcDistance(list.get(3), list.get(5));
		distances[3][7] = calcDistance(list.get(3), list.get(7));
		distances[3][6] = calcDistance(list.get(3), list.get(6));
		distances[4][2] = calcDistance(list.get(4), list.get(2));
		distances[4][5] = calcDistance(list.get(4), list.get(5));
		distances[4][9] = calcDistance(list.get(4), list.get(9));
		distances[5][0] = calcDistance(list.get(5), list.get(0));
		distances[5][3] = calcDistance(list.get(5), list.get(3));
		distances[5][6] = calcDistance(list.get(5), list.get(6));
		distances[5][6] = calcDistance(list.get(5), list.get(6));
		distances[5][7] = calcDistance(list.get(5), list.get(7));
		distances[5][8] = calcDistance(list.get(5), list.get(8));
		distances[5][9] = calcDistance(list.get(5), list.get(9));
		distances[6][3] = calcDistance(list.get(6), list.get(3));
		distances[6][5] = calcDistance(list.get(6), list.get(5));
		distances[6][7] = calcDistance(list.get(6), list.get(7));
		distances[7][6] = calcDistance(list.get(7), list.get(6));
		distances[7][3] = calcDistance(list.get(7), list.get(3));
		distances[7][5] = calcDistance(list.get(7), list.get(5));
		distances[7][8] = calcDistance(list.get(7), list.get(8));
		distances[8][9] = calcDistance(list.get(8), list.get(9));
		distances[8][2] = calcDistance(list.get(8), list.get(2));
		distances[8][5] = calcDistance(list.get(8), list.get(5));
		distances[8][7] = calcDistance(list.get(8), list.get(7));
		distances[9][4] = calcDistance(list.get(9), list.get(4));
		distances[9][5] = calcDistance(list.get(9), list.get(5));
		distances[9][8] = calcDistance(list.get(9), list.get(8));

		for(Node n : list) {
			for(int i = 0; i < list.size(); i++) {
				if(distances[n.id][i] != 0) {
					n.neighbors.add(list.get(i));
				}
			}
		}
	}
}
