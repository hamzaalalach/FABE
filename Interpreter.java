import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Interpreter {
	public void start(String filePath) {
		Stack<Integer> pile = new Stack<>();
		ArrayList<String> vic = new ArrayList<String>();
		int CO = 0;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
				String[] lineParts = line.split(" ");
				if (lineParts.length == 2) {
					pile.push(Integer.parseInt(lineParts[1]));
				} else if (lineParts.length == 1) {
					pile.push(0);
				}
				vic.add(lineParts[0]);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(!vic.get(CO).equals("END")) {
		    String Instr_Courante = vic.get(CO);
            switch(Instr_Courante) {
            case "ADD":
                Integer operd1 = Integer.hashCode((Integer) pile.pop());
                Integer operd2 = Integer.hashCode((Integer) pile.pop());
                int v = operd1 + operd2;
                pile.push(v);
                break;
            case "SUB":
                Integer operd11 = Integer.hashCode((Integer) pile.pop());
                Integer operd21 = Integer.hashCode((Integer) pile.pop());
                int v1 = operd11 - operd21;
                pile.push(v1);
                break;
            case "MUL":
                Integer operd12 = Integer.hashCode((Integer) pile.pop());
                Integer operd22 = Integer.hashCode((Integer) pile.pop());
                int v2 = operd12 * operd22;
                pile.push(v2);
                break;
            case "DIV":
                Integer operd13 = Integer.hashCode((Integer) pile.pop());
                Integer operd23 = Integer.hashCode((Integer) pile.pop());
                int v3 = operd13 / operd23;
                pile.push(v3);
                break;
            case "SUP":
                int n;
                Integer n1 = Integer.hashCode((Integer) pile.pop());
                Integer n2 = Integer.hashCode((Integer) pile.pop());
                if (n2 > n1){
                    n = n2;
                }
                else n = n1;
                pile.push(n);
                break;
            case "INF":
                int m;
                Integer m1 = Integer.hashCode((Integer) pile.pop());
                Integer m2 = Integer.hashCode((Integer) pile.pop());
                if (m1 < m2){
                    m = m1;
                }
                else m = m2;
                pile.push(m);
                break;
            case "SUPE":
                int L;
                Integer n11 = Integer.hashCode((Integer) pile.pop());
                Integer n21 = Integer.hashCode((Integer) pile.pop());
                if (n11 >= n21){
                    L= n11;
                }
                else L = n21;
                pile.push(L);
                break;
            case "INFE":
                int L1;
                Integer n12 = Integer.hashCode((Integer) pile.pop());
                Integer n22 = Integer.hashCode((Integer) pile.pop());
                if (n22 <= n12){
                    L1 = n22;
                }
                else L1 = n12;
                pile.push(L1);
                break;
            case "EQUAL":
                int x1 = Integer.hashCode((Integer) pile.pop());
                int x2 = Integer.hashCode((Integer) pile.pop());
                if(x1 == x2) {
                    pile.push(1);
                } else {
                    pile.push(0);
                }
                break;
            case "LOAD":
                pile.push(pile.get(pile.get(CO)));
                break;
            case "LOADC":
            	pile.push(pile.get(CO));
            	break;
            case "STORE":
            	pile.set(pile.get(CO), pile.pop());
            	break;
            case "JUMP":
            	CO = pile.get(CO);
            	CO--;
            	break;
            case "JZERO":
            	int val = pile.pop();
            	if (val == 0) {
            		CO = pile.get(CO);
            		CO--;
            	}
            	break;
            case "READ":
            	Scanner sc = new Scanner(System.in);
            	pile.set(pile.get(CO), sc.nextInt());
            	break;
            case "WRITEC":
            	System.out.println(pile.get(CO));
            	break;
            case "WRITE":
            	System.out.println(pile.get(pile.get(CO)));
            	break;
            }
            CO++;
		}

		System.out.println(pile.peek());
	}
}