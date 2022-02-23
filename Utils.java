import java.util.Scanner;
import java.util.*;

public class Utils {
	private static Scanner scanner;
	private static ArrayList<String> quits;

	public Utils() {
		scanner = new Scanner(System.in);
		String[] _quits = { "exit", "end", "quit", "q" };
		quits = new ArrayList<String>(Arrays.asList(_quits));
	}

	public void quit(String inp) {
		if (quits.contains(inp.toLowerCase())) {
			System.out.println(Colors.R+"Quit game"+Colors.RE);
			scanner.close();
			System.exit(0);
		}
	}

	public String ln() {
		System.out.print(Colors.C+">>> "+Colors.RE);
		return scan();
	}

	public String scan() {
		String scan = scanner.nextLine().strip();
		quit(scan);
		return scan;
	}

	public int num() {
		System.out.print(Colors.C+">>> "+Colors.RE);
		while (true) {
			try {
				return Integer.parseInt(scan());
			} catch (Exception e) {
				System.out.println(Colors.R+"Please enter a valid integer."+Colors.RE);
			}
		}
	}
	public void error(String text){
		System.out.println(Colors.R+text+Colors.RE);
	}
	public void quest(String text) {
		System.out.println(Colors.B+text+Colors.RE);
	}
	public String opt(String text){
		return Colors.P+text+Colors.RE;
	}
}