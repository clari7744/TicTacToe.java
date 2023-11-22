
//TODO: Finish colors
import java.util.ArrayList;
import java.util.Arrays;

public class TicTacToe {
	private ArrayList<Player> players;
	private Board board;
	private int turn;
	public Utils utils;

	public TicTacToe(Player[] players, Utils utils) {
		this.players = new ArrayList<Player>(Arrays.asList(players));
		this.board = new Board(this, utils);
		this.turn = (int) (Math.random() * 2 + 1);
		this.utils = utils;
	}

	public TicTacToe(Utils utils) {
		this.players = new ArrayList<Player>();
		this.board = new Board(this, utils);
		this.turn = (int) (Math.random() * 2 + 1);
		this.utils = utils;
	}

	public void run() {
		/**
		 * Runs the game.
		 * Starts by clearing the board, then loops Board.move until either the board is
		 * full or a player wins.
		 */
		clearBoard();
		while (!board.isDone()) {
			board.move(players.get(turn - 1));
			turn = turn % 2 + 1;
		}
	}

	public void clearBoard() {
		/**
		 * Clears the board for a new game.
		 */
		board.clear();
		turn = (int) (Math.random() * 2 + 1);
	}

	public boolean parseSpecials(String input) {
		/**
		 * Parses the given input and executes the command if it's special,
		 * otherwise returns false and the move continues.
		 * 
		 * @param input The input given by the player.
		 * @return Whether or not the input was a special command.
		 */
		if (input.equals("help") || input.equals("h")) {
			System.out.println("Commands:");
			System.out.println("help/h - Displays this help message.");
			System.out.println("restart/r - Restarts the game.");
			System.out.println("show - Displays the players and board.");
			System.out.println("edit - Allows you to edit name, symbol, or AI mode of a player.");
			System.out.println("exit/quit/q - Quits the game.");
		}
		if (input.equals("show")) {
			System.out.println(stringPlayers());
			return true;
		}
		if (input.equals("edit") || input.equals("e")) {
			edit();
			return true;
		}
		if (input.equals("restart") || input.equals("r")) {
			System.out.println(Colors.RBG + Colors.W
					+ "Are you sure you want to clear the board and restart the game? (y/n)" + Colors.RE);
			if (utils.ln().toLowerCase().equals("y"))
				clearBoard();
			return true;
		}
		utils.quit(input);
		return false;
	}

	public void edit() {
		/**
		 * Opens interactive interface for editing players' names or symbols.
		 **/
		System.out.println("Players: \n" + stringPlayers());
		utils.quest(String.format("Which player would you like to edit? (%s%s/%s%s)", utils.opt("name"), Colors.B,
				utils.opt("number"), Colors.B));
		String pl = utils.ln();
		Player u = getPlayer(pl);
		while (u == null)
			utils.error("Please enter a valid player identifier.");
		utils.quest(String.format("What would you like to edit for %s? (%s/%s/%s)", u.getName(), utils.opt("name"),
				utils.opt("symbol"), utils.opt("aimode")));
		String resp = utils.ln().toLowerCase();
		while (!(resp.equals("name") || resp.equals("symbol") || resp.equals("aimode") || resp.equals("cancel"))) {
			utils.error("Invalid option. Valid options are name, symbol, or aimode");
			resp = utils.ln().toLowerCase();
		}
		if (resp.equals("cancel"))
			return;
		if (resp.equals("aimode")) {
			System.out.println((u.toggleAiMode() ? "en" : "dis") + "abled AI mode for " + u.getName());
			return;
		}
		utils.quest(String.format("Enter new %s for %s:", resp, u.getName()));
		String r = utils.ln();
		if (resp.equals("name")) {
			while (!u.isValidName(r))
				r = utils.ln();
			u.setName(r);
		} else if (resp.equals("symbol")) {
			while (!u.isValidSymbol(r))
				r = utils.ln();
			u.setSymbol(r);
		}
	}

	public Board getBoard() {
		/**
		 * @return The board object.
		 */
		return board;
	}

	public ArrayList<Player> getPlayers() {
		/**
		 * @return The array of players.
		 */
		return players;
	}

	public Player getPlayer(int num) {
		/**
		 * @param num The player number.
		 * @return The player with the given number.
		 */
		return players.get(num - 1);
	}

	public Player getPlayer(String name) {
		/**
		 * @param name The player's name.
		 * @return The player with the given name.
		 */
		for (Player p : players)
			if (p.getRawName().toLowerCase().equals(name) || String.valueOf(p.getNum()).equals(name))
				return p;
		return null;
	}

	public Player addPlayer(int n) {
		Player p = new Player(this, n);
		players.add(n - 1, p);
		utils.quest("Enter name for Player " + n + ": ");
		String r = utils.ln();
		while (!p.isValidName(r)) {
			r = utils.ln();
		}
		p.setName(r);
		utils.quest("Would you like a custom symbol? (y/n)");
		if (utils.ln().equalsIgnoreCase("y")) {
			utils.quest("Enter symbol for " + r + ":");
			String s = utils.ln();
			while (!p.isValidSymbol(s)) {
				s = utils.ln();
			}
			p.setSymbol(s);
		}
		utils.quest("Should this player be an AI? (y/n)");
		String ai = utils.ln();
		if (ai.equalsIgnoreCase("y")) {
			p.toggleAiMode();
			System.out.println("Set " + p.getName() + " to AI mode.");
		} else if (ai.equalsIgnoreCase("ygood")) {
			p.toggleAiMode(true);
			System.out.println("Enabled good AI mode for " + p.getName());
		}
		return p;
	}

	public String stringPlayers() {
		/**
		 * Prints the players' names and symbols.
		 */
		ArrayList<String> pl = new ArrayList<String>();
		for (Player p : players)
			pl.add(p.toString());
		return String.join("\n", pl);
	}

	public String toString() {
		return String.format("%s\n\n%s", stringPlayers(), board);
	}

	public static void main(String[] args) {
		Utils utils = new Utils();
		TicTacToe ttt = new TicTacToe(utils);
		ttt.addPlayer(1);
		ttt.addPlayer(2);
		System.out.println("Type `help` for help at any time.");
		System.out.println();
		String input = "";
		while (!input.equals("n")) {
			ttt.run();
			utils.quest("Thanks for playing!\nPlay again? (y/n)");
			input = utils.ln().toLowerCase();
		}
	}
}