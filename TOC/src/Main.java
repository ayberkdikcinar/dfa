import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static ArrayList<Connection> c; // Transactions
	private static String S;
	private static String[] E, Q, A;

	private static void convert() {
		
		handleFile("file.txt");
		simplification();
		
		for (int i = 0; i < Q.length; i++) {
			if (isConnContain(Q[i])) {
				c.add(transaction(Q[i]));
			}
		}
		System.out.println(display());
		System.out.println("Regular Expression = " + c.get(c.size()-1).getConnection());
	}

	private static void simplification() {
		String start, conn, end, start2, conn2, end2;
		Connection connection = new Connection();
		for (int i = 0; i < c.size(); i++) {
			start = c.get(i).getStart();
			conn = c.get(i).getConnection();
			end = c.get(i).getEnd();

			for (int j = 0; j < c.size(); j++) {
				start2 = c.get(j).getStart();
				conn2 = c.get(j).getConnection();
				end2 = c.get(j).getEnd();
				if (start.equalsIgnoreCase(end2) && start2.equalsIgnoreCase(end)
						&& (!start.equalsIgnoreCase(start2) && !end.equalsIgnoreCase(end2))) {
					connection.setStart(start);
					connection.setEnd(start);
					connection.setConnection(conn);
					for (int k = 0; k < c.size(); k++) {
						if (c.get(k).getStart().equalsIgnoreCase(end) && c.get(k).getEnd().equalsIgnoreCase(end)) {
							connection
									.setConnection(connection.getConnection() + "(" + c.get(k).getConnection() + ")*");
							break;
						}
					}
					connection.setConnection(connection.getConnection() + conn2);
					c.remove(j);
					break;
				}

			}
		}
		c.add(connection);
	}
	
	private static Connection transaction(String state) {
		boolean flag = true;
		String start, conn, end, start2, conn2, end2;
		Connection connection = new Connection();
		
		while (isConnContain(state) && flag) {

			for (int i = 0; i < c.size(); i++) {
				for (int j = 0; j < c.size(); j++) {
					if (c.get(i).getStart().equalsIgnoreCase(c.get(j).getStart())
							&& c.get(i).getEnd().equalsIgnoreCase(c.get(j).getEnd()) && j != i) {
						c.get(i).setConnection("(" + c.get(i).getConnection() + "+" + c.get(j).getConnection() + ")");
						System.out.println(c.get(i).print() + "{ADDED} \n\n*** TRANSACTION DONE! ***\n\n");
						c.remove(j);
						break;
					}
				}
			}
			for (int i = 0; i < c.size(); i++) {
				flag = true;

				start = c.get(i).getStart();
				conn = c.get(i).getConnection();
				end = c.get(i).getEnd();

				if (start.equalsIgnoreCase(state) || end.equalsIgnoreCase(state)) {

					if (start.equalsIgnoreCase(end)) {
						connection.setConnection(connection.getConnection() + "(" + conn + ")*");

					} else if (start.equalsIgnoreCase(state)) {
						if (connection.getEnd().equalsIgnoreCase("")) {
							connection.setEnd(end);
						}
						if (connection.getEnd().equalsIgnoreCase(end) && !conn.equalsIgnoreCase("epsilon")) {
							connection.setConnection(connection.getConnection() + conn);
						} else if (conn.equalsIgnoreCase("epsilon")) {
							c.get(i).setStart(connection.getStart());
							flag = true;
						} else {
							Connection data = new Connection(connection.getStart(), c.get(i).getConnection(),
									c.get(i).getEnd());
							c.remove(i);
							c.add(data);
							System.out.println(data.print() + "{ADDED} \n\n*** TRANSACTION DONE! ***\n\n");
							flag = false;
						}

					} else if (end.equalsIgnoreCase(state)) {

						if (connection.getStart().equalsIgnoreCase("")) {
							connection.setStart(start);
						}
						if (connection.getEnd().equalsIgnoreCase(end)
								|| connection.getStart().equalsIgnoreCase(start) && !conn.equalsIgnoreCase("epsilon")) {
							connection.setConnection(conn + connection.getConnection());
						}

					}
					if (flag) {

						System.out.println(display());
						System.out.println(c.get(i).print() + "{REMOVED}");
						System.out.println("Connection: " + connection.print());
						c.remove(i);

						break;
					}
				}
			}

		}
		// c.add(connection);
		System.out.println(connection.print() + "{ADDED} \n\n*** TRANSACTION DONE! ***\n\n");

		return connection;
	}

	// it is checked whether the state is included in any connections.
	private static boolean isConnContain(String state) {
		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).getStart().equalsIgnoreCase(state) || c.get(i).getEnd().equalsIgnoreCase(state)) {
				return true;
			}
		}
		return false;
	}

	private static int minConnState() {
		int result = 0, count = Integer.MAX_VALUE, temp = Integer.MAX_VALUE;

		for (int i = 0; i < Q.length; i++) {
			for (int j = 0; j < c.size(); j++) {
				if (c.get(j).getStart().equalsIgnoreCase(Q[i]) && c.get(j).getEnd().equalsIgnoreCase(Q[i])) {
					temp += 2;
				} else if (c.get(j).getStart().equalsIgnoreCase(Q[i]) || c.get(j).getEnd().equalsIgnoreCase(Q[i])) {
					temp++;
				}
			}
			if (temp < count) {
				result = i;
				count = temp;
				temp = Integer.MAX_VALUE;
			}
		}
		return result;
	}

	private static String display() {

		/*
		 * String str = "S = " + S + "\nA = "; for (int i = 0; i < A.length; i++) { str
		 * += A[i] + " "; } str += "\nE = "; for (int i = 0; i < E.length; i++) { str +=
		 * E[i] + " "; } str += "\nQ = "; for (int i = 0; i < Q.length; i++) { str +=
		 * Q[i] + " "; }
		 */
		String str = "\n\nTransactions; \n\n";
		for (int i = 0; i < c.size(); i++) {
			str += c.get(i).print() + "\n";
		}
		return str + "\n-----------------\n";
	}

	private static void handleFile(String filename) {
		try {
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
			Connection temp = null;
			c = new ArrayList<>();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine(); /// each line of the .txt file.
				String splitByEqualMark[] = data.split("=");
				String splitByComma[] = splitByEqualMark[1].split(",");
				if (splitByEqualMark[0].equals("S")) {
					S = splitByEqualMark[1];
					temp = new Connection("Start", "Epsilon", S);
					c.add(temp);
				} else if (splitByEqualMark[0].equals("A")) {
					A = splitByComma;
				} else if (splitByEqualMark[0].equals("E"))
					E = splitByComma;
				else if (splitByEqualMark[0].equals("Q"))
					Q = splitByComma;
				else {
					splitByComma = splitByEqualMark[0].split(",");
					String start = splitByComma[0];
					String chr = splitByComma[1];
					String end = splitByEqualMark[1];
					temp = new Connection(start, chr, end);
					for (int i = 0; i < c.size(); i++) {
						if (c.get(i).getStart().equalsIgnoreCase(start) && c.get(i).getEnd().equalsIgnoreCase(end)) {
							temp = new Connection(start, c.get(i).getConnection() + "+" + chr, end);
							c.remove(i);
						}
					}

					c.add(temp);
				}
			}
			for (int i = 0; i < A.length; i++) { //
				temp = new Connection(A[i], "Epsilon", "Final");
				c.add(temp);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		convert();
	}

}
