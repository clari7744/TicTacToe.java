import java.util.ArrayList;

public class Board {
	private Player[][] inner;
	private TicTacToe game;
	private Utils utils;
	private static String sep;

	public Board(TicTacToe game, Utils utils) {
		inner = new Player[3][3];
		this.game = game;
		this.utils = utils;
		sep = "―――――――――――――――――\n";
	}

	public void clear() {
		inner = new Player[3][3];
	}

	public boolean isDone() {
		if (isFull()) {
			System.out.println(this);
			System.out.println("Tie game!");
			return true;
		}
		Player c = checkColumns(), r = checkRows(), d = checkDiags();
		Player p = c != null ? c : r != null ? r : d != null ? d : null;
		if (p == null)
			return false;
		System.out.println(this);
		System.out.println(p.getName() + " wins!");
		return true;
	}

	public boolean isFull() {
		for (Player[] r : inner)
			for (Player p : r)
				if (p == null)
					return false;
		return true;
	}

	public Player checkRows() {
		for (Player[] r : inner) {
			if (r[0] == r[1] && r[1] == r[2] && r[0] != null) {
				return r[0];
			}
		}
		return null;
	}

	public Player checkColumns() {
		for (int i = 0; i < inner[0].length; i++)
			if (inner[0][i] == inner[1][i] && inner[1][i] == inner[2][i])
				return inner[0][i];
		return null;
	}

	public Player checkDiags() {
		return inner[0][0] == inner[1][1] && inner[0][0] == inner[2][2]
				|| inner[0][2] == inner[1][1] && inner[1][1] == inner[2][0] ? inner[1][1] : null;
	}

	public Player[][] getInner() {
		return inner;
	}

	public ArrayList<String> getValidMoves() {
		ArrayList<String> validMoves = new ArrayList<String>();
		for (int r = 0; r < inner.length; r++)
			for (int c = 0; c < inner[r].length; c++)
				if (inner[r][c] == null)
					validMoves.add("abc".substring(c, c + 1) + (r + 1));
		return validMoves;
	}

	public int[] parseInput(String input) {
		int[] cr = { -1, -1 };
		try {
			cr[0] = "abc".indexOf(input.substring(1, 2).toLowerCase());
			cr[1] = Integer.parseInt(input.substring(0, 1)) - 1;
		} catch (Exception e) {
			try {
				cr[0] = "abc".indexOf(input.substring(0, 1).toLowerCase());
				cr[1] = Integer.parseInt(input.substring(1, 2)) - 1;
			} catch (Exception e2) {
				return cr;
			}
		}
		return cr;
	}

	public void move(Player player) {
		/**
		 * @param player
		 * @return true if move was successful, false otherwise
		 */
		while (true) {
			System.out.println(this);
			System.out.println(player + Colors.G + ", make your move!");
			String input = "";
			if (player.isAi())
				input = player.getAiMove(this);
			else
				input = utils.ln();
			if (game.parseSpecials(input))
				continue;
			if (input.length() != 2) {
				utils.error("Input length must be exactly 2 (Ex: 1a / a1)");
				continue;
			}
			int[] cr = parseInput(input);
			int c = cr[0], r = cr[1];
			if (c == -1 || r == -1) {
				utils.error("Invalid input. Ex: 1a / a1");
				continue;
			}
			if (r < 0 || r > 2) {
				utils.error("Row must be between 1 and 3");
				continue;
			}
			if (c < 0 || c > 2) {
				utils.error("Column must be A, B, or C");
				continue;
			}
			if (inner[r][c] != null) {
				utils.error("This space is already filled!");
				continue;
			} else {
				inner[r][c] = player;
				return;
			}
		}
	}

	public String niceRow(int rowNum) {
		Player[] row = inner[rowNum];
		String[] nice = new String[4];
		nice[0] = Colors.BOLD + (rowNum + 1) + Colors.RE;
		for (int i = 0; i < row.length; i++)
			nice[i + 1] = row[i] == null ? " " : row[i].getSymbol();
		return niceRow(nice);
	}

	public String niceRow(String[] row) {
		return "| " + String.join(" | ", row) + " |\n";
	}

	public String toString() {
		String[] chrs = { "#", "A", "B", "C" };
		String trow = String.join(String.format("%s | %s", Colors.RE, Colors.BOLD), chrs);
		String str = String.format("%s%s| %s%s%s |\n%s", Colors.RE, sep, Colors.BOLD, trow, Colors.RE, sep);
		for (int i = 0; i < inner.length; i++)
			str += niceRow(i) + sep;
		return str;
	}
}
