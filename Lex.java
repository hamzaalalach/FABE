import java.util.ArrayList;


public class Lex {
	enum Cat {MUL, PARENTHESEO, PARENTHESEF, EGAL, PLUS, INT, VAR, MOINS, AFFECTATION, IDENTIF, NEANT, INF, DO, DIV, SUP, DIFF, IF, ELSE, WHILE, THEN};
	static ArrayList<Cat> result = new ArrayList<>();
	static ArrayList<String> valeurs = new ArrayList<>();
	static String sequence;
	static int index = 0;
	
	Lex(String sequence) {
		Lex.sequence = sequence;
	}
	
	public static char lireCar() {
		index++;
		if (index >= sequence.length()) {
			return '$';
		}
		while (Character.isWhitespace(sequence.charAt(index))) {
			if (Character.isWhitespace(sequence.charAt(++index))) {
				index++;
			} else {
				index--;
				return '#';
			}
		}
		return sequence.charAt(index);
	}

	public static char delire() {
		index--;
		return sequence.charAt(index);
	}
	
	public static ArrayList<String> start() {
		Character c = sequence.charAt(0);
		while (!c.equals('$')) {
			if (c == '(') {
				valeurs.add(c + "");
				result.add(Cat.PARENTHESEO);
			} else if (c == ')') {
				valeurs.add(c + "");
				result.add(Cat.PARENTHESEF);
			} else if (c == '=') {
				c = lireCar();
				if (c == '=') {
					valeurs.add("a");
					result.add(Cat.AFFECTATION);
				} else {
					c = delire();
					valeurs.add(c + "");
					result.add(Cat.EGAL);
				}
			} else if (c == '/') {
				result.add(Cat.DIV);
				valeurs.add("/");
			} else if (c == '+') {
				result.add(Cat.PLUS);
				valeurs.add("+");
			} else if (c == '<') {
				c = lireCar();
				if (c == '>') {
					valeurs.add("<" + c);
				    result.add(Cat.DIFF);
				} else {
					c = delire();
					valeurs.add(c + "");
					result.add(Cat.INF);
				}
			 } else if (c == '>') {
				valeurs.add(c + "");
				result.add(Cat.SUP);
			} else if (Character.isDigit(c)) {
				String digit = "";
				digit += c;
				c = lireCar();
				while (Character.isDigit(c)) {
					digit += c;
					c = lireCar();
				}
				c = delire();
				result.add(Cat.INT);
				valeurs.add("n");
			} else if (Character.isLetter(c)) {
				String motR = "";
				motR += c;
				c = lireCar();
				while (!c.equals('#')) {
					if (Character.isLetter(c) || Character.isDigit(c)) {
						motR += c;
						c = lireCar();
					}
				}
				c = delire();
				String motRLower = motR.toLowerCase();
				if (motRLower.equals("if")) {
					result.add(Cat.IF);
					valeurs.add("i");
				} else if (motRLower.equals("else")) {
					result.add(Cat.ELSE);
					valeurs.add("l");
				} else if (motRLower.equals("while")) {
					result.add(Cat.WHILE);
					valeurs.add("w");
				} else if (motRLower.equals("do")) {
					result.add(Cat.DO);
					valeurs.add("d");
				} else if (motRLower.equals("then")) {
					result.add(Cat.THEN);
					valeurs.add("t");
				} else if (motRLower.equals("var")) {
					result.add(Cat.VAR);
					valeurs.add("v");
				} else {
					valeurs.add("c");
					result.add(Cat.IDENTIF);
				}
			} else {
				if (!c.equals('#')) {
					result.add(Cat.NEANT);
					valeurs.add("ERR");
				}
			}
			c = lireCar();
		}
		
		return valeurs;
	}
}