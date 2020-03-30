import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Syn {
	static String[][] instructions = {
			{"Y", "Z"},
			{"Y", "U"},
			{"Y", "G"},
			{"U", "E"},
			{"U", "O"},
			{"Z", "iAN"},
			{"N", "tUI"},
			{"I", "lU"},
			{"I", "e"},
			{"A", "nBn"},
			{"A", "cBn"},
			{"B", "a"},
			{"B", ">"},
			{"B", "<"},
			{"G", "wAH"},
			{"H", "dU"},
			{"E", "TD"},
			{"D", "+TD"},
			{"D", "-TD"},
			{"D", "e"},
			{"T", "FS"},
			{"S", "*FS"},
			{"S", "/FS"},
			{"S", "e"},
			{"F", "(E)"},
			{"F", "n"},
			{"O", "vcQ"},
			{"Q", "=E"}
	};
	
	static String[] t = {"n", "+", "-", "*", "/", "(", ")", "a", ">", "<", "d", "w", "l", "t", "i", "v", "c", "=", "$"};
	static String[] nt = {"E", "D", "T", "S", "F", "A", "B", "H", "G", "Z", "N", "I", "Y", "O", "Q", "U"};
	static String left;
	static ArrayList<String> premier(String elem) {
		ArrayList<String> pre = new ArrayList<String>();
		if (contains(t, elem)) {
			pre.add(elem);
		} else if (contains(nt, elem)) {
			ArrayList<String> rightParts = retreive(elem);
			for (int c = 0; c < rightParts.size(); c++) {
				ArrayList<String> elems = premier(rightParts.get(c).charAt(0) + "");
				for (int i = 0; i < elems.size(); i++) {
					if (!elems.get(i).equals("e")) {
						pre.add(elems.get(i));
					} else {
						pre.addAll(suivant(elem));
					}
				}
			}
			for (int j = 0; j < rightParts.size(); j++) {
				ArrayList<String> array = retreive(rightParts.get(j));
				if (contains(array, "e")) {
					ArrayList<String> elems1 = premier(rightParts.get(j-1));
					for (int i = 0; i < elems1.size(); i++) {
						if (!elems1.get(i).equals("e")) {
							pre.add(elems1.get(i));
						}
					}
				}
			}
			if (contains(rightParts, "e")) {
				if (!contains(pre, "e")) {
					pre.add("e");
				}
			}
		} else if (elem == "e") {
			return suivant(left);
		}
		return pre;
	}
	
	static ArrayList<String> suivant(String elem) {
		ArrayList<String> sui = new ArrayList<String>();
		sui.add("$");
		Pattern rule1 = Pattern.compile("^(.{0,1})([A-Z]{1,1})(.{1,1})$");
		Pattern rule2 = Pattern.compile("^(.{0,1})([A-Z]{1,1})$");
		
		for (int i = 0; i < instructions.length; i++) {
			Matcher m1 = rule1.matcher(instructions[i][1]);
			if (m1.find()) {
				if (m1.group(2).equals(elem)) {
					ArrayList<String> rightParts = retreive(m1.group(3));
					if (contains(rightParts, "e")) {
						ArrayList<String> suiA = suivant(instructions[i][0]);
						for (int c = 0; c < suiA.size(); c++) {
							if (!suiA.get(c).equals("$")) {
								if (!contains(sui, suiA.get(c))) {
									sui.add(suiA.get(c));
								}
							}
						}
					}
					if (contains(nt, m1.group(2))) {					
						ArrayList<String> elems = premier(m1.group(3));
						for (int c = 0; c < elems.size(); c++) {
							if (!elems.get(c).equals("e")) {
								if (!contains(sui, elems.get(c))) {
									sui.add(elems.get(c));
								}
							}
						}
					}
				}
			}
			Matcher m2 = rule2.matcher(instructions[i][1]);
			if (m2.find()) {
				if (m2.group(2).equals(elem)) {
					ArrayList<String> suiA = suivant(instructions[i][0]);
					for (int c = 0; c < suiA.size(); c++) {
						if (!suiA.get(c).equals("$")) {
							if (!contains(sui, suiA.get(c))) {
								sui.add(suiA.get(c));
							}
						}
					}
				}
			}
		}
		return sui;
	}
	
	static String[][] genAnalyseTable() {
		String[][] analyseTable = new String[nt.length][t.length];
		for (int i = 0; i < instructions.length; i++) {
			String nextInstruction = instructions[i][1].charAt(0) + "";
			ArrayList<String> pre = new ArrayList<String>();
			if (nextInstruction.equals("e")) {
				left = instructions[i][0];
				pre = premier("e");
			} else {
				pre = premier(instructions[i][1].charAt(0) + "");
			}
			if (pre.size() != 0) {
				for (int c = 0; c < pre.size(); c++) {
					if (!pre.get(c).equals("e")) {
						analyseTable[find(nt, instructions[i][0])][find(t, pre.get(c))] = instructions[i][1];
					} else {
						ArrayList<String> sui = suivant(instructions[i][0]);
						if (sui.size() != 0) {
							for (int d = 0; d < sui.size(); d++) {
								analyseTable[find(nt, instructions[i][0])][find(t, sui.get(d))] = instructions[i][1];
							}
						}
					}
				}
			}
		}
		return analyseTable;
	}
	
	static int find(String[] a, String target) {
		for (int c = 0; c < a.length; c++) {
			if (a[c].equals(target)) {
				return c;
			}
		}
		return -1;
	}
	
	static Boolean contains(String[] array, String s) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	static Boolean contains(ArrayList<String> array, String s) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).equals(s)) {
				return true;
			}
		}	
		return false;
	}
	
	static ArrayList<String> retreive(String leftPart) {
		ArrayList<String> values = new ArrayList<String>();
		for (int i = 0; i < instructions.length; i++) {
			if (instructions[i][0].equals(leftPart)) {
				values.add(instructions[i][1]);
			}
		}
		return values;
	}
	
	static void showM(String[][] M) {
		for (int i = 0; i < t.length; i++) {
			System.out.print("         " + t[i]);
		}
		System.out.println("");
		for (int j = 0; j < nt.length; j++) {
			System.out.print(nt[j]);
			for (int c = 0; c < t.length; c++) {
				if (M[j][c] == null) {
					System.out.print("      " + M[j][c]);
				} else {
					System.out.print("        " + M[j][c]);
				}
			}
			System.out.println("");
		}
	}
	
	static String sequence;
	static Stack<String> pile = new Stack<>();
	
	public static void start() {
		Boolean error = false;
		Boolean accepted = false;
		int ps = 0;
		String[][] M = genAnalyseTable();
		
		do {
			String X = pile.peek();
			if (contains(nt, X)) {
				if (M[find(nt, X)][find(t, sequence.charAt(ps) + "")] != null) {
					pile.pop();
					String holder = M[find(nt, X)][find(t, sequence.charAt(ps) + "")];
					if (!holder.equals("e")) {
						for (int i = holder.length() - 1; i >= 0; i--) {
							pile.push(holder.charAt(i) + "");
						}	
					}
				} else {
					error = true;
				}
			} else {
				if (X == "$") {
					if ("$".equals(sequence.charAt(ps) + "")) {
						accepted = true;
					} else {
						error = true;
					}
				}  else {
					if (X.equals(sequence.charAt(ps) + "")) {
						pile.pop();
						ps++;
					} else {
						error = true;
					}
				}
			}
		} while (!error && !accepted);
		if (error) {
			System.out.println("Error");
		}
		if (accepted) {
			System.out.println("Accepted");
		}
	}
	
	public static void main(String[] args) {
		Lex lex = new Lex("if a > 5 then var a = 0 else var a = 4 + 5#");
		sequence = String.join("", lex.start()) + "$";
		pile.push("$");
		pile.push("Y");
		start();
		showM(genAnalyseTable());
	}
}