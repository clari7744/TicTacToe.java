import java.util.ArrayList;

public class Player {
	private TicTacToe game;
	private String name;
	private String symbol;
	private String color;
	private int num;
	private boolean ai;
	private boolean aiIsGood;

	public Player(TicTacToe game, String name, String symbol, int num) {
		this.game = game;
		this.name = name;
		this.symbol = symbol;
		this.num = num;
		this.color = num == 1 ? Colors.P : Colors.Y;
	}

	public Player(TicTacToe game, String name, int num) {
		this.game = game;
		this.name = name;
		this.num = num;
		this.symbol = num == 1 ? "X" : "O";
		this.color = num == 1 ? Colors.P : Colors.Y;
	}

	public Player(TicTacToe game, int num) {
		this.game = game;
		this.num = num;
		this.symbol = num == 1 ? "X" : "O";
		this.name = this.symbol;
		this.color = num == 1 ? Colors.P : Colors.Y;
	}

	public boolean isValidName(String newName) {
		if (newName.length() < 1) {
			game.utils.error("Name cannot be empty");
			game.utils.quest("Enter name for Player " + num + ": ");
			return false;
		}
		if (num == 2 && newName.equalsIgnoreCase(game.getPlayer(1).getRawName())) {
			game.utils.error("Name cannot match Player 1's.");
			game.utils.quest("Enter name for Player " + num + ": ");
			return false;
		}
		return true;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return color + name + Colors.RE;
	}

	public String getRawName() {
		return name;
	}

	public boolean isValidSymbol(String newSymbol) {
		if (newSymbol.length() < 1) {
			game.utils.error("Symbol cannot be empty");
			game.utils.quest("Enter symbol for Player " + num + ": ");
			return false;
		}
		if (num == 2 && newSymbol.equalsIgnoreCase(game.getPlayer(1).getRawSymbol())) {
			game.utils.error("Symbol cannot match Player 1's.");
			game.utils.quest("Enter symbol for Player " + num + ": ");
			return false;
		}
		return true;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return color + symbol + Colors.RE;
	}

	public String getRawSymbol() {
		return name;
	}

	public int getNum() {
		return num;
	}

	public boolean toggleAiMode() {
		ai = ai ? false : true;
		return ai;
	}

	public boolean toggleAiMode(boolean good) {
		ai = ai ? false : true;
		aiIsGood = good;
		return ai;
	}

	public boolean isAi() {
		return ai;
	}

	public String getAiMove(Board board) {
		try {
			Thread.sleep(500);
		} catch (Exception e) {
		}
		ArrayList<String> moves = board.getValidMoves();
		return !aiIsGood ? moves.get((int) (Math.random() * (moves.size()))) : getGoodMove(board);
	}

	public String getGoodMove(Board board) {
		String ret = "";
		Player[][] b = board.getInner();
		// ArrayList<String> mvs = board.getValidMoves();
		ArrayList<String> bestMoves = new ArrayList<String>();
		for (int r = 0; r < b.length; r++) {
			Player[] row = b[r];
			if (row[0].equals(row[1]) && row[0].equals(this)) {
				bestMoves.add(0, "c" + r);
			}
		}
		return ret;
	}

	public String toString() {
		return String.format("%s[%s%s] Player %d%s: %s", Colors.G, getSymbol(), Colors.G, getNum(), ai ? " [AI]" : "",
				getName());
	}
}
