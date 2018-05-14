import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

@SuppressWarnings("unchecked")
public class SudukoCmd {
	private Label[] NoLabels = new Label[9];
	private Button[][] btn = new Button[9][9];
	public static String GridFile;
	public static String CommandFile;
	private int cx, cy;
	private static int EmptyCells;
	private static int[][] Values = new int[9][9];
	private static HashMap Possibilities[][] = new HashMap[9][9];
	private static int GroupAvailability[][] = new int[3][3];
	private String Numbers = "123456789";
	private static String Blocks[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
	private static int Availability[][] = new int[9][9];

	public static void main(String[] args) {
		GridFile = args[0];
		CommandFile = args[1];
		ReadBoard(args[0]);
		ReadCommand(args[1]);

	}

	public static void LowerAvailability(int x, int y) {
		int Startx = (int) Math.floor((x) / 3);
		int Starty = (int) Math.floor((y) / 3);
		GroupAvailability[Startx][Starty]--;
	}

	public static int relative(int x, int y) {
		int grid_index = (y / 3) * 3 + (x / 3);
		return grid_index;
	}

	public static void ReadCommand(String command) {
		File file = new File(command);

		String Command = "";
		String Block = "";
		int order = 0;

		int x = 0;
		int y = 0;
		int rx = 0;
		int ry = 0;
		String n = "";
		try {

			Scanner sc = new Scanner(file);

			Command = sc.next();
			Block = sc.next();
			y = Integer.parseInt(sc.next());
			x = Integer.parseInt(sc.next());
			if (sc.hasNextLine()) {
				n = sc.next();

			} else {

			}

			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		rx = x;
		ry = y;
		for (int a = 0; a < 9; a++) {
			if (Block.equals(Blocks[a])) {
				order = a;

				break;
			}
		}

		if (order == 1) {
			rx = x + 3;
		}
		if (order == 2) {
			rx = x + 6;
		}
		if (order == 3) {
			ry = y + 3;
		}
		if (order == 4) {
			ry = y + 3;
			rx = x + 3;
		}
		if (order == 5) {
			ry = y + 3;
			rx = x + 6;
		}
		if (order == 6) {
			ry = y + 6;
		}
		if (order == 7) {
			ry = y + 6;
			rx = x + 3;
		}
		if (order == 8) {
			ry = y + 6;
			rx = x + 6;
		}
		CommandFile = CommandFile.substring(0, 3);
		// System.out.println(order);
		if (Command.equals("1a"))
			Rule1a(rx, ry);
		if (Command.equals("1b"))
			Rule1b(rx, ry);
		if (Command.equals("2a"))
			Rule2a(rx, ry, Integer.parseInt(n));
		if (Command.equals("2b"))
			Rule2b(rx, ry, Integer.parseInt(n), x, y);
		if (Command.equals("2c"))
			Rule2c(rx, ry, x, y);
		if (Command.equals("3"))
			Rule3(rx, ry, x, y);
		if (Command.equals("4a"))
			Rule4a(rx, ry, Integer.parseInt(n));
		if (Command.equals("4b"))
			Rule4b(rx, ry, Integer.parseInt(n));
		// if(Command.equals("5"))Rule5();
	}

	public static void ReadBoard(String board) {
		// File file= new File(board);

		File file = new File(board);

		String Board = "";

		try {

			Scanner sc = new Scanner(file);
			int c = 0;

			Board = sc.next();

			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		GridFile = board;
		String puzzle = Board;
		int sub = 0;
		int number;
		CreateHash();
		char[] ca = puzzle.toCharArray();
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				number = (int) ca[sub] - '0';
				if ((int) ca[sub] - '0' != 0) {
					Add(x, y, number, "Original");
				}

				if ((int) ca[sub] != 0) {
					LowerAvailability(x, y);
				}

				sub++;

			}
		}
	}

	public static void PrintOutput(List Outputs) {
		FileWriter fw;

		try {
			fw = new FileWriter(new File(CommandFile + ".outs"));
			for (int p = 0; p < Outputs.getItemCount(); p++) {

				fw.write(Outputs.getItem(p));
				fw.write(System.lineSeparator());
			}

			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int p = 0; p < Outputs.getItemCount(); p++) {

			System.out.println(Outputs.getItem(p));
		}
	}

	public static void CreateHash() {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				Possibilities[x][y] = new HashMap<>();
				for (int n = 0; n < 10; n++) {
					Possibilities[x][y].put(n, "Valid");
					Availability[x][y] = 9;

				}
			}
		}
	}

	public static HashMap[][] CreateHash(HashMap[][] Hash) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				Hash[x][y] = new HashMap<>();
				for (int n = 1; n < 10; n++) {
					Hash[x][y].put(n, "Valid");
					Availability[x][y] = 9;

				}
			}
		}
		return Hash;
	}

	public static void Add(int x, int y, int n, String r) {
		Values[x][y] = n;
		// Update Possibilities of the Rows
		for (int i = 0; i < 9; i++) {

			if (i != x) {

				Possibilities[i][y].put(n, "Invalid");
				Availability[i][y]--;
			} else {
				Possibilities[i][y].put(n, r);
			}

		}
		// Update Possibilities of the Columns
		for (int j = 0; j < 9; j++) {
			if (j != y) {
				Possibilities[x][j].put(n, "Invalid");
				Availability[x][j]--;
			}

		}
		// Update Possibilities of the Groups
		int Startx = (int) Math.floor((x) / 3) * 3;
		int Starty = (int) Math.floor((y) / 3) * 3;
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				if (i + Startx != x && j + Starty != y) {
					Possibilities[i + Startx][j + Starty].put(n, "Invalid");
					Availability[i + Startx][j + Starty]--;
					// NoLabels[Values[i+Startx][j+Starty]-1].setBackground(new
					// Background(new BackgroundFill(Color.GREY,
					// CornerRadii.EMPTY, Insets.EMPTY)));
				}
			}
		}
		for (int p = 1; p < 10; p++) {
			if (p != n) {
				Possibilities[x][y].put(p, "Invalid");
			}

		}

		EmptyCells--;
	}

	public static String CreateOutput(int x, int y, int n, String s) {
		String Output = "";
		String Block = CurrentBlock(x, y);
		int rx = x;
		int ry = y;
		if (x >= 3) {
			rx = x - 3;
		}
		if (x >= 6) {
			rx = x - 6;
		}
		if (y >= 3) {
			ry = y - 3;
		}
		if (y >= 6) {
			ry = y - 6;
		}
		// System.out.println(rx);
		return (Block + ry + rx + s + n);

	}

	// Rules
	public static void Rule1b(int Cx, int Cy) {
		// Rule1a(Cx,Cy);
		List Outputs = new List();
		int rx = 0, ry = 0;
		boolean single = true;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (GroupAvailability[x][y] == 1) {

					int Startx = x * 3;
					int Starty = y * 3;
					for (int j = 0; j < 3; j++) {
						for (int i = 0; i < 3; i++) {
							if (Values[Startx + i][Starty + j] == 0 && single == true) {
								rx = Startx + i;
								ry = Starty + j;
							}
						}
					}
					for (int n = 1; n < 10; n++) {
						if (Possibilities[rx][ry].get(n) == "Valid") {
							Add(rx, ry, n, "Rule1");
							String Block = CurrentBlock(x, y);
							
							Outputs.add(CreateOutput(x, y, n, "+"));
							
							break;
						}
					}
				}
			}
		}
		PrintOutput(Outputs);
	}

	// Returns the CurrentBlock
	public static String CurrentBlock(int x, int y) {
		int grid_index = (y / 3) * 3 + (x / 3);
		return Blocks[grid_index];

	}

	public static void Rule1a(int x, int y) {
		List Outputs = new List();
		if (Availability[x][y] == 1) {

			for (int n = 1; n < 10; n++) {
				if (Possibilities[x][y].get(n) == "Valid") {
					Add(x, y, n, "Rule1");
					String Block = CurrentBlock(x, y);
					

				
						Outputs.add(CreateOutput(x, y, n, "+"));
						
						
					break;
				}
			}
		}
		PrintOutput(Outputs);

	}

	public static void Rule1a() {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				if (Availability[x][y] == 1) {
					for (int n = 1; n < 10; n++) {
						if (Possibilities[x][y].get(n) == "Valid") {
							Add(x, y, n, "Rule1");
						}
					}
				}
			}
		}

	}

	public static void Rule2a() {
		// Column Check
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				for (int n = 1; n < 10; n++) {
					if (Possibilities[x][y * 3].get(n).equals("Valid")
							|| Possibilities[x][y * 3 + 1].get(n).equals("Valid")
							|| Possibilities[x][y * 3 + 2].get(n).equals("Valid") && Values[x][y * 3] == 0
									&& Values[x][y * 3 + 1] == 0 && Values[x][y * 3 + 2] == 0) {
						int Startx = (int) Math.floor((x) / 3) * 3;
						int Starty = (int) Math.floor((y * 3) / 3) * 3;
						int Check = 0;
						for (int i = 0; i < 3; i++) {
							if (Possibilities[i + Startx][y * 3].get(n).equals("Valid")
									|| Possibilities[i + Startx][y * 3 + 1].get(n).equals("Valid")
									|| Possibilities[i + Startx][y * 3 + 2].get(n).equals("Valid")) {
								if (Values[i + Startx][y * 3] == 0 || Values[i + Startx][y * 3 + 1] == 0
										|| Values[i + Startx][y * 3 + 2] == 0) {
									Check++;
								}

							}

						}
						if (Check == 1) {
							for (int j = 0; j < 9; j++) {
								if (j != Starty && j != Starty + 1 && j != Starty + 2) {
									Possibilities[x][j].put(n, "Invalid");

								}
							}
						}
					}
				}
			}
		}
		// Row Check
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 9; y++) {
				for (int n = 1; n < 10; n++) {
					if (Possibilities[x * 3][y].get(n).equals("Valid")
							|| Possibilities[x * 3 + 1][y].get(n).equals("Valid")
							|| Possibilities[x * 3 + 2][y].get(n).equals("Valid") && Values[x * 3][y] == 0
									&& Values[x * 3 + 1][y] == 0 && Values[x * 3 + 2][y] == 0) {
						int Startx = (int) Math.floor((x * 3) / 3) * 3;
						int Starty = (int) Math.floor((y) / 3) * 3;
						int Check = 0;
						for (int j = 0; j < 3; j++) {
							if (Possibilities[x * 3][j + Starty].get(n).equals("Valid")
									|| Possibilities[x * 3 + 1][j + Starty].get(n).equals("Valid")
									|| Possibilities[x * 3 + 2][j + Starty].get(n).equals("Valid")) {
								if (Values[x * 3][j + Starty] == 0 || Values[x * 3 + 1][j + Starty] == 0
										|| Values[x * 3 + 2][j + Starty] == 0) {
									Check++;
								}
							}

						}
						if (Check == 1) {
							for (int i = 0; i < 9; i++) {
								if (i != Startx && i != Startx + 1 && i != Startx + 2) {
									Possibilities[i][y].put(n, "Invalid");
								}
							}
						}
					}
				}
			}
		}

	}

	public static void Rule2a(int x, int y, int n) {
		// Column Check
		int Startx = (int) Math.floor((x) / 3) * 3;
		int Starty = (int) Math.floor((y) / 3) * 3;
		// System.out.println(Startx);
		List Outputs = new List();
		if (Possibilities[x][Starty].get(n).equals("Valid") || Possibilities[x][Starty + 1].get(n).equals("Valid")
				|| Possibilities[x][Starty + 2].get(n).equals("Valid")
				|| Values[x][Starty] == 0 && Values[x][Starty + 1] == 0 && Values[x][Starty + 2] == 0) {

			int Check = 0;
			for (int i = 0; i < 3; i++) {
				if (Possibilities[i + Startx][Starty].get(n).equals("Valid")
						|| Possibilities[i + Startx][Starty + 1].get(n).equals("Valid")
						|| Possibilities[i + Startx][Starty + 2].get(n).equals("Valid")) {
					if (Values[i + Startx][Starty] == 0 || Values[i + Startx][Starty + 1] == 0
							|| Values[i + Startx][Starty + 2] == 0) {
						Check++;
					}

				}

			}
			if (Check == 1) {
				for (int j = 0; j < 9; j++) {
					if (j != Starty && j != Starty + 1 && j != Starty + 2) {
						if (Possibilities[x][j].get(n).equals("Valid")) {
							Possibilities[x][j].put(n, "Rule2");
							Outputs.add(CreateOutput(x, j, n, "-"));
						}

					}
				}
			}
		}
		// RowCheck
		if (Possibilities[Startx][y].get(n).equals("Valid") || Possibilities[Startx + 1][y].get(n).equals("Valid")
				|| Possibilities[Startx + 2][y].get(n).equals("Valid")
				|| Values[Startx][y] == 0 && Values[Startx + 1][y] == 0 && Values[Startx + 2][y] == 0) {

			int Check = 0;
			for (int j = 0; j < 3; j++) {
				if (Possibilities[Startx][j + Starty].get(n).equals("Valid")
						|| Possibilities[Startx + 1][j + Starty].get(n).equals("Valid")
						|| Possibilities[Startx + 2][j + Starty].get(n).equals("Valid")) {
					if (Values[Startx][j + Starty] == 0 || Values[Startx + 1][j + Starty] == 0
							|| Values[Startx + 2][j + Starty] == 0) {
						Check++;
					}
				}

			}
			if (Check == 1) {
				for (int i = 0; i < 9; i++) {
					if (i != Startx && i != Startx + 1 && i != Startx + 2) {
						if (Possibilities[i][y].get(n).equals("Valid")) {
							Possibilities[i][y].put(n, "Rule2");
							Outputs.add(CreateOutput(i, y, n, "-"));
						}
					}
				}
			}
		}
		PrintOutput(Outputs);

	}

	public static void Rule2b(int x, int y, int n, int rx, int ry) {
		List Outputs = new List();
		// Row Check
		int yoffset1 = 0;
		int yoffset2 = 0;
		if (ry == 0) {
			yoffset1 = 1;
			yoffset2 = 2;

		}
		if (ry == 1) {
			yoffset1 = 0;
			yoffset2 = 2;
		}
		if (ry == 2) {
			yoffset1 = 0;
			yoffset2 = 1;
		}
		for (int i = 0; i < 3; i++) {
			if (Possibilities[i * 3][y + yoffset1].get(n).equals("Valid")
					|| Possibilities[i * 3 + 1][y + yoffset1].get(n).equals("Valid")
					|| Possibilities[i * 3 + 2][y + yoffset1].get(n).equals("Valid") && Values[i * 3][y + yoffset1] == 0
							&& Values[i * 3 + 1][y + yoffset1] == 0 && Values[i * 3 + 2][y + yoffset1] == 0
							&& i * 3 != x && i * 3 + 1 != x && i * 3 + 2 != x) {

				int Startx = (int) Math.floor((i * 3) / 3) * 3;
				int Starty = (int) Math.floor((y + yoffset1) / 3) * 3;
				int Check = 0;
				for (int j = 0; j < 3; j++) {
					if (Possibilities[i * 3][j + Starty].get(n).equals("Valid")
							|| Possibilities[i * 3 + 1][j + Starty].get(n).equals("Valid")
							|| Possibilities[i * 3 + 2][j + Starty].get(n).equals("Valid")) {
						if (Values[i * 3][j + Starty] == 0 && Values[i * 3 + 1][j + Starty] == 0
								&& Values[i * 3 + 2][j + Starty] == 0) {

							Check++;
						}

					}
				}
				if (Check == 1) {
					for (int ii = 0; ii < 9; ii++) {
						if (ii != Startx && ii != Startx + 1 && ii != Startx + 2) {
							if (Possibilities[ii][y + yoffset1].get(n).equals("Valid") && ii != i * 3 && ii != i * 3 + 1
									&& ii != i * 3 + 2) {
								Possibilities[ii][y + yoffset1].put(n, "Rule2");
								Outputs.add(CreateOutput(ii, y + yoffset1, n, "-"));
							}

						}
					}
				}
			}
		}

		for (int i = 0; i < 3; i++) {
			if (Possibilities[i * 3][y + yoffset2].get(n).equals("Valid")
					|| Possibilities[i * 3 + 1][y + yoffset2].get(n).equals("Valid")
					|| Possibilities[i * 3 + 2][y + yoffset2].get(n).equals("Valid") && Values[i * 3][y + yoffset2] == 0
							&& Values[i * 3 + 1][y + yoffset2] == 0 && Values[i * 3 + 2][y + yoffset2] == 0
							&& i * 3 != x && i * 3 + 1 != x && i * 3 + 2 != x) {

				int Startx = (int) Math.floor((i * 3) / 3) * 3;
				int Starty = (int) Math.floor((y + yoffset2) / 3) * 3;
				int Check = 0;
				for (int j = 0; j < 3; j++) {
					if (Possibilities[i * 3][j + Starty].get(n).equals("Valid")
							|| Possibilities[i * 3 + 1][j + Starty].get(n).equals("Valid")
							|| Possibilities[i * 3 + 2][j + Starty].get(n).equals("Valid")) {
						if (Values[i * 3][j + Starty] == 0 && Values[i * 3 + 1][j + Starty] == 0
								&& Values[i * 3 + 2][j + Starty] == 0) {
							// System.out.println("Check " + (i*3)+"
							// "+(j+Starty));
							Check++;
						}

					}
				}
				if (Check == 1) {
					for (int ii = 0; ii < 9; ii++) {
						if (ii != Startx && ii != Startx + 1 && ii != Startx + 2) // Hierdie
																					// is
																					// reg
																					// dat
																					// dit
																					// uitgecomment
																					// moet
																					// word
																					// in
																					// test
																					// case
																					// 4
						{

							if (Possibilities[ii][y + yoffset2].get(n).equals("Valid")) {
								// System.out.println("Invalid added " + ii +
								// (y+2) );
								Possibilities[ii][y + yoffset2].put(n, "Rule2");
								Outputs.add(CreateOutput(ii, y + yoffset2, n, "-"));
							}

						}
					}
				}
			}
		}
		// Column Check
		int xoffset1 = 0;
		int xoffset2 = 0;
		if (rx == 0) {
			xoffset1 = 1;
			xoffset2 = 2;
		}

		if (rx == 1) {
			xoffset1 = 0;
			xoffset2 = 2;
		}
		if (rx == 2) {
			xoffset1 = 1;
			xoffset2 = 0;
		}
		for (int j = 0; j < 3; j++) {
			if (Possibilities[x + xoffset1][j * 3].get(n).equals("Valid")
					|| Possibilities[x + xoffset1][j * 3 + 1].get(n).equals("Valid")
					|| Possibilities[x + xoffset1][j * 3 + 2].get(n).equals("Valid") && Values[x + xoffset1][j * 3] == 0
							&& Values[x + xoffset1][j * 3 + 1] == 0 && Values[x + xoffset1][j * 3 + 2] == 0
							&& j * 3 != y && j * 3 + 1 != y && j * 3 + 2 != y) {

				int Startx = (int) Math.floor((x + xoffset1) / 3) * 3;
				int Starty = (int) Math.floor((j * 3) / 3) * 3;
				int Check = 0;
				for (int i = 0; i < 3; i++) {
					if (Possibilities[i + Startx][j * 3].get(n).equals("Valid")
							|| Possibilities[i + Startx][j * 3 + 1].get(n).equals("Valid")
							|| Possibilities[i + Startx][j * 3 + 2].get(n).equals("Valid")) {
						if (Values[i + Startx][j * 3] == 0 && Values[i + Startx][j * 3 + 1] == 0
								&& Values[i + Startx][j * 3 + 2] == 0) {

							Check++;
						}

					}
				}
				if (Check == 1) {
					for (int jj = 0; jj < 9; jj++) {
						if (jj != Starty && jj != Starty + 1 && jj != Starty + 2) {
							if (Possibilities[x + xoffset1][jj].get(n).equals("Valid")) {
								Possibilities[x + xoffset1][jj].put(n, "Rule2");
								Outputs.add(CreateOutput(x + xoffset1, jj, n, "-"));
							}

						}
					}
				}
			}
		}
		for (int j = 0; j < 3; j++) {
			if (Possibilities[x + xoffset2][j * 3].get(n).equals("Valid")
					|| Possibilities[x + xoffset2][j * 3 + 1].get(n).equals("Valid")
					|| Possibilities[x + xoffset2][j * 3 + 2].get(n).equals("Valid") && Values[x + xoffset2][j * 3] == 0
							&& Values[x + xoffset2][j * 3 + 1] == 0 && Values[x + xoffset2][j * 3 + 2] == 0
							&& j * 3 != y && j * 3 + 1 != y && j * 3 + 2 != y) {
				// System.out.println("rx test");
				int Startx = (int) Math.floor((x + xoffset2) / 3) * 3;
				int Starty = (int) Math.floor((j * 3) / 3) * 3;
				int Check = 0;
				for (int i = 0; i < 3; i++) {
					if (Possibilities[i + Startx][j * 3].get(n).equals("Valid")
							|| Possibilities[i + Startx][j * 3 + 1].get(n).equals("Valid")
							|| Possibilities[i + Startx][j * 3 + 2].get(n).equals("Valid")) {
						if (Values[i + Startx][j * 3] == 0 && Values[i + Startx][j * 3 + 1] == 0
								&& Values[i + Startx][j * 3 + 2] == 0) {

							Check++;
						}

					}
				}
				if (Check == 1) {
					for (int jj = 0; jj < 9; jj++) {
						if (jj != Starty && jj != Starty + 1 && jj != Starty + 2) {
							// &&jj!=j*3&&jj!=j*3+1&&jj!=j*3+2
							if (Possibilities[x + xoffset2][jj].get(n).equals("Valid")) {
								Possibilities[x + xoffset2][jj].put(n, "Rule2");
								Outputs.add(CreateOutput(x + xoffset2, jj, n, "-"));
							}

						}
					}
				}
			}
		}

		// Self Defined Triple Equal Possibility Check. If a row or column in a
		// subgrid have the exact same Possibilities in all three cells.
		// remove their possibilities from the rest of the subgrid
		int Startx = (int) Math.floor((x) / 3) * 3;
		int Starty = (int) Math.floor((y) / 3) * 3;

		int check = 0;

		int no[] = new int[9];
		for (int p = 1; p < 10; p++) {
			if (Possibilities[x][y].get(p).equals("Valid")) {
				no[check] = p;
				check++;
			}

		}
		if (check == 3) {
			// Row triple check
			int equal = 0;
			for (int i = 0; i < 3; i++) {
				if (Values[Startx + i][y] == 0 && Possibilities[Startx + i][y].get(no[0]).equals("Valid")
						&& Possibilities[Startx + i][y].get(no[1]).equals("Valid")
						&& Possibilities[Startx + i][y].get(no[2]).equals("Valid")) {
					equal++;
				}
			}
			if (equal == 3) {
				for (int i = 0; i < 3; i++) {

					for (int j = 0; j < 3; j++) {
						if (Starty + j != y) {
							Possibilities[Startx + i][Starty + j].put(no[0], "Rule2");
							Possibilities[Startx + i][Starty + j].put(no[1], "Rule2");
							Possibilities[Startx + i][Starty + j].put(no[2], "Rule2");
							Outputs.add(CreateOutput(Startx + i, Starty + j, n, "-"));
						}
					}
				}
			}
			equal = 0;
			// Column Triple Check
			for (int j = 0; j < 3; j++) {
				if (Values[x][Starty + j] == 0 && Possibilities[x][Starty + j].get(no[0]).equals("Valid")
						&& Possibilities[x][Starty + j].get(no[1]).equals("Valid")
						&& Possibilities[x][Starty + j].get(no[2]).equals("Valid")) {
					equal++;
				}
			}
			if (equal == 3) {
				for (int j = 0; j < 3; j++) {

					for (int i = 0; i < 3; i++) {
						if (Startx + i != x) {
							Possibilities[Startx + i][Starty + j].put(no[0], "Rule2");
							Possibilities[Startx + i][Starty + j].put(no[1], "Rule2");
							Possibilities[Startx + i][Starty + j].put(no[2], "Rule2");
							Outputs.add(CreateOutput(Startx + i, Starty + j, n, "-"));
						}
					}
				}
			}
		}

		PrintOutput(Outputs);

	}

	public static void Rule2c(int x, int y, int rx, int ry) {

		HashMap tempPossibilities[][] = new HashMap[9][9];
		tempPossibilities = CreateHash(tempPossibilities);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int n = 1; n < 10; n++) {
					String V = (String) Possibilities[i][j].get(n);
					tempPossibilities[i][j].put((n), V);
					// System.out.println(tempPossibilities[i][j].get(n+20));
				}

			}
		}

		int Startx = (int) Math.floor((x) / 3) * 3;
		int Starty = (int) Math.floor((y) / 3) * 3;
		List Outputs = new List();
		boolean checked = false;
		// Row Check

		if (Values[Startx][y] == 0 && Values[Startx + 1][y] == 0 && Values[Startx + 2][y] == 0) {
			int off1 = 0;
			int off2 = 0;
			if (Startx == 0) {
				off1 = 1;
				off2 = 2;
			}
			if (Startx == 3) {
				off1 = 2;
				off2 = 0;
			}
			if (Startx == 6) {
				off1 = 0;
				off2 = 1;
			}

			for (int j = 0; j < 3; j++) {
				if (j != ry) {
					boolean added = false;
					if (Values[Startx][Starty + j] == 0 && Values[Startx + 1][Starty + j] == 0
							&& Values[Startx + 2][Starty + j] == 0) {
						if (Values[off1 * 3][Starty + j] != 0 && Values[off1 * 3 + 1][Starty + j] != 0
								&& Values[off1 * 3 + 2][Starty + j] != 0 && added == false) {
							for (int n = 0; n < 3; n++) {
								// System.out.println(j +""+ (Startx+n));
								tempPossibilities[Startx + n][y].put(Values[off1 * 3][Starty + j], "Invalid");
								tempPossibilities[Startx + n][y].put(Values[off1 * 3 + 1][Starty + j], "Invalid");
								tempPossibilities[Startx + n][y].put(Values[off1 * 3 + 2][Starty + j], "Invalid");
								checked = true;
								added = true;
							}

						}
						if (Values[off2 * 3][Starty + j] != 0 && Values[off2 * 3 + 1][Starty + j] != 0
								&& Values[off2 * 3 + 2][Starty + j] != 0 && added == false) {
							for (int n = 0; n < 3; n++) {
								// System.out.println(j +""+ (Startx+n));
								tempPossibilities[Startx + n][y].put(Values[off2 * 3][Starty + j], "Invalid");
								tempPossibilities[Startx + n][y].put(Values[off2 * 3 + 1][Starty + j], "Invalid");
								tempPossibilities[Startx + n][y].put(Values[off2 * 3 + 2][Starty + j], "Invalid");
								checked = true;
								added = true;
							}
						}
					}

				}

			}
			if (checked == true) {
				String V = "";
				for (int i = 0; i < 3; i++) {
					for (int n = 1; n < 10; n++) {
						V = (String) tempPossibilities[Startx + i][y].get((n));

						if (!V.equals(null)) {
							if (V.equals("Valid")) {

								if (Possibilities[Startx + i][y].get(n).equals("Valid")) {
									Possibilities[Startx + i][y].put(n, "Rule2");
									Outputs.add(CreateOutput(Startx + i, y, n, "-"));
								}

							}
						}

					}
				}
			}

		}
		// Column Check
		if (Values[x][Starty] == 0 && Values[x][Starty + 1] == 0 && Values[x][Starty + 2] == 0 && checked == false) {
			int off1 = 0;
			int off2 = 0;
			if (Starty == 0) {
				off1 = 1;
				off2 = 2;
			}
			if (Starty == 3) {
				off1 = 2;
				off2 = 0;
			}
			if (Starty == 6) {
				off1 = 0;
				off2 = 1;
			}

			for (int i = 0; i < 3; i++) {
				if (i != rx) {
					boolean added = false;
					if (Values[Startx + i][Starty] == 0 && Values[Startx + i][Starty + 1] == 0
							&& Values[Startx + i][Starty + 2] == 0) {
						if (Values[Startx + i][off1 * 3] != 0 && Values[Startx + i][off1 * 3 + 1] != 0
								&& Values[Startx + i][off1 * 3 + 2] != 0 && added == false) {
							for (int n = 0; n < 3; n++) {
								tempPossibilities[x][Starty + n].put(Values[Startx + i][off1 * 3], "Invalid");
								tempPossibilities[x][Starty + n].put(Values[Startx + i][off1 * 3 + 1], "Invalid");
								tempPossibilities[x][Starty + n].put(Values[Startx + i][off1 * 3 + 2], "Invalid");
								// System.out.println("5");
								checked = true;
								added = true;
							}

						}
						if (Values[Startx + i][off2 * 3] != 0 && Values[Startx + i][off2 * 3 + 1] != 0
								&& Values[Startx + i][off2 * 3 + 2] != 0 && added == false) {
							for (int n = 0; n < 3; n++) {
								// System.out.println("6");
								tempPossibilities[x][Starty + n].put(Values[Startx + i][off2 * 3], "Invalid");
								tempPossibilities[x][Starty + n].put(Values[Startx + i][off2 * 3 + 1], "Invalid");
								tempPossibilities[x][Starty + n].put(Values[Startx + i][off2 * 3 + 2], "Invalid");
								checked = true;
								added = true;
							}
						}
					}

				}

			}
			if (checked == true) {
				String V = "";
				for (int j = 0; j < 3; j++) {
					for (int n = 1; n < 10; n++) {
						V = (String) tempPossibilities[x][Starty + j].get((n));

						if (!V.equals(null)) {
							if (V.equals("Valid")) {

								if (Possibilities[x][Starty + j].get(n).equals("Valid")) {
									Possibilities[x][Starty + j].put(n, "Rule2");
									Outputs.add(CreateOutput(x, Starty + j, n, "-"));
								}

							}
						}

					}
				}
			}

		}

		PrintOutput(Outputs);
	}

	public static void Rule3(int x, int y, int rx, int ry) {
		int rowc[] = new int[9];
		int columnc[] = new int[9];
		int gridc[] = new int[9];
		int number[] = new int[3];
		List Outputs = new List();
		boolean checked = false;

		// Rowcheck

		boolean possible = true;
		int px[] = new int[2];
		for (int b = 0; b < rowc.length; b++) {
			rowc[b] = 0;
		}
		for (int i = 0; i < 9; i++) {
			for (int n = 1; n < 10; n++) {
				if (Possibilities[i][y].get(n).equals("Valid")) {
					rowc[n - 1]++;
				}
			}
		}

		int d = 0;
		for (int n = 0; n < 9; n++) {
			if (rowc[n] == 2) {
				number[d] = n + 1;
				d++;
				// System.out.println(number[b]);
				if (d == 2) {

					break;
				}

			}
		}
		int c = 0;
		// System.out.println(number[0]);
		// System.out.println(number[1]);
		if (possible == true && number[0] != 0 && number[1] != 0) {
			for (int i = 0; i < 9; i++) {
				if (Possibilities[i][y].get(number[0]).equals("Valid")
						&& Possibilities[i][y].get(number[1]).equals("Valid")) {
					px[c] = i;
					// System.out.println(px[c]);
					c++;
					if (c == 2) {
						break;
					}
				}
			}
			for (int p = 0; p < 2; p++) {
				for (int n = 1; n < 10; n++) {
					if (Possibilities[px[p]][y].get(n).equals("Valid") && n != number[0] && n != number[1]
							&& (px[0] == x || px[1] == x)) {
						Possibilities[px[p]][y].put(n, "Rule3");
						Outputs.add(CreateOutput(px[p], y, n, "-"));
						checked = true;
					}
				}
			}
		}

		// Column Check
		if (checked == false) {
			int py[] = new int[2];
			for (int b = 0; b < columnc.length; b++) {
				columnc[b] = 0;
			}
			for (int j = 0; j < 9; j++) {
				for (int n = 1; n < 10; n++) {
					if (Possibilities[x][j].get(n).equals("Valid")) {
						columnc[n - 1]++;
					}
				}
			}

			int b = 0;
			for (int n = 0; n < 9; n++) {
				if (columnc[n] == 2) {
					number[b] = n + 1;
					b++;
					// System.out.println(number[b]);
					if (b == 2) {

						break;
					}

				}
			}
			int z = 0;
			// System.out.println(number[0]);
			// System.out.println(number[1]);
			if (number[0] != 0 && number[1] != 0) {
				for (int j = 0; j < 9; j++) {
					if (Possibilities[x][j].get(number[0]).equals("Valid")
							&& Possibilities[x][j].get(number[1]).equals("Valid")) {
						py[z] = j;

						z++;
						if (z == 2) {
							break;
						}
					}
				}
				for (int p = 0; p < 2; p++) {
					for (int n = 1; n < 10; n++) {
						if (Possibilities[x][py[p]].get(n).equals("Valid") && n != number[0] && n != number[1]
								&& (py[0] == y || py[1] == y)) {
							Possibilities[x][py[p]].put(n, "Rule3");
							Outputs.add(CreateOutput(x, py[p], n, "-"));
							checked = true;
						}
					}
				}
			}
		}

		// Subgrid Check
		int Startx = (int) Math.floor((x) / 3) * 3;
		int Starty = (int) Math.floor((y) / 3) * 3;
		int py[] = new int[2];
		px[0] = 0;
		px[1] = 0;
		number[0] = 0;
		number[1] = 0;
		if (checked == false) {
			for (int b = 0; b < gridc.length; b++) {
				gridc[b] = 0;
			}
			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < 3; i++) {
					for (int n = 1; n < 10; n++) {
						if (Possibilities[Startx + i][Starty + j].get(n).equals("Valid")) {
							gridc[n - 1]++;
						}
					}
				}
			}
			int b = 0;
			for (int n = 0; n < 9; n++) {
				if (gridc[n] == 2) {
					number[b] = n + 1;
					b++;
					// System.out.println(number[b]);
					if (b == 2) {

						break;
					}

				}
			}
			int z = 0;
			if (number[0] != 0 && number[1] != 0) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (Possibilities[Startx + i][Starty + j].get(number[0]).equals("Valid")
								&& Possibilities[Startx + i][Starty + j].get(number[1]).equals("Valid")) {
							py[z] = Starty + j;
							px[z] = Startx + i;
							z++;
							if (z == 2) {
								break;
							}
						}
					}
				}
				for (int p = 0; p < 2; p++) {
					for (int n = 1; n < 10; n++) {
						if (Possibilities[px[p]][py[p]].get(n).equals("Valid") && n != number[0] && n != number[1]
								&& (py[0] == y || py[1] == y) && (px[0] == x || px[1] == x)) {
							Possibilities[px[p]][py[p]].put(n, "Rule3");
							Outputs.add(CreateOutput(px[p], py[p], n, "-"));
							checked = true;
						}
					}
				}
			}

		}
		PrintOutput(Outputs);

	}

	public static void Rule4a(int x, int y, int n) {
		List Outputs = new List();
		if (Possibilities[x][y].get(n).equals("Invalid")) {
			PrintOutput(Outputs);
			return;
		}
		int row = 100;
		DrawGraph DG = new DrawGraph("Rule4a Graph");
		for (int g = 1; g < 10; g++) {
			DG.addNode(Integer.toString(g), row, 200);
			row = row + 55;
		}
		row = 100;
		for (int g = 10; g < 19; g++) {
			DG.addNode(Integer.toString(g), row, 50);
			row = row + 55;
		}
		DG.setSize(700, 300);
		DG.setName("GraphDisplay");
		DG.setVisible(true);
		Graph g = new Graph(18);
		// We start adding edges
		// System.out.println(x + "" + y);
		g.addEdge(x, 9 + y);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Values[i][j] == n) {
					g.addEdge(i, j + 9);
					if (i < 8) {
						i++;
					}
				}
				if ((i != x) && (j != y)) {
					int location1 = relative(x, y);
					int location2 = relative(i, j);
					if (location1 != location2) {
						if (Possibilities[i][j].get(n).equals("Valid")) {
							g.addEdge(i, 9 + j);
							DG.addEdge(i, j + 9);
						}
					}
				}
				// if(Possibilities[i][j].get(n).equals("Valid")){
				// g.addEdge(i, 9+j);
				// }
			}
		}
		BipartiteMatching b = new BipartiteMatching(g);
		if (!b.isPerfect()) {
			// System.out.println("ye");
			Possibilities[x][y].put(n, "Rule4");
			Outputs.add(CreateOutput(x, y, n, "-"));

		} else {

		}
		PrintOutput(Outputs);
	}

	public static void Rule4b(int x, int y, int n) {
		List Outputs = new List();
		boolean checked = true;

		// Start adding Edges
		int Startx = (int) Math.floor((x) / 3) * 3;
		int Starty = (int) Math.floor((y) / 3) * 3;
		// Row Check
		Graph g = new Graph(18);
		for (int i = 0; i < 9; i++) {
			if (Values[i][y] == 0) {
				for (int p = 1; p < 10; p++) {
					if (Possibilities[i][y].get(p).equals("Valid")) {
						g.addEdge(p - 1, i + 9);

					}
				}
			}
		}
		Bipartite c = new Bipartite(g);
		if (c.isBipartite()) {
			BipartiteMatching b = new BipartiteMatching(g);
			if (b.isPerfect()) {

				checked = false;
			}
		}

		// Column Check

		if (checked == true) {
			g = new Graph(18);
			for (int j = 0; j < 9; j++) {
				if (Values[x][j] == 0) {
					for (int p = 1; p < 10; p++) {
						if (Possibilities[x][j].get(p).equals("Valid")) {
							g.addEdge(p - 1, j + 9);

						}
					}
				}
			}
			c = new Bipartite(g);
			if (c.isBipartite()) {
				BipartiteMatching b = new BipartiteMatching(g);
				if (b.isPerfect()) {

					checked = false;
				}
			}

		}

		// SubGrid Check

		if (checked == true) {
			g = new Graph(18);
			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < 3; i++) {
					if (Values[Startx + i][Starty + j] == 0) {
						int grid_index = ((j)) * 3 + ((i));
						for (int p = 1; p < 10; p++) {
							if (Possibilities[Startx + i][Starty + j].get(p).equals("Valid")) {
								g.addEdge(p - 1, grid_index + 9);

							}
						}
					}
				}
			}
			c = new Bipartite(g);
			if (c.isBipartite()) {
				BipartiteMatching b = new BipartiteMatching(g);
				if (b.isPerfect()) {
					checked = false;
				}
			}

		}

		if (checked == true) {
			Possibilities[x][y].put(n, "Rule4");
			Outputs.add(CreateOutput(x, y, n, "-"));

		}
		PrintOutput(Outputs);

	}
}
