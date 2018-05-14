package rvc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import rvc.NormalCat;
import rvc.SuperCat;

public class Board extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = -3420746639299063375L;
	private int cursorRow;
	private int cursorCol;
	private static final int BORDER_X = Params.BORDER_X;
	private static final int BORDER_Y = Params.BORDER_Y;
	private static final int BLOCK_WIDTH = Params.BLOCK_WIDTH;
	private static final int BLOCK_HEIGHT = Params.BLOCK_HEIGHT;
	private static final int MIN_WIDTH = BORDER_X * 2 + BLOCK_WIDTH * 9;
	private static final int MIN_HEIGHT = BORDER_Y * 2 + BLOCK_HEIGHT * 5;
	private static final Dimension MIN_SIZE = new Dimension(MIN_WIDTH, MIN_HEIGHT);
	private RVC rvc;
	private static final Color LIGHT_GREEN = new Color(37, 213, 98);
	private static final Color MID_GREEN = new Color(21, 121, 56);
	private static final Color DARK_GREEN = new Color(13, 75, 34);
	private final Image SuperCatImage;
	private final Image NormalCatImage;
	private final Image PowerRobotImage;
	private final Image DroneRobotImage;
	private final Image BombRobotImage;
	private final Game game;

	public Board(Game game, RVC rvc) {
		super();
		this.rvc = rvc;
		this.game = game;
		addMouseListener(this);
		addMouseMotionListener(this);
		setMinimumSize(MIN_SIZE);
		NormalCatImage = Toolkit.getDefaultToolkit().createImage(RVC.class.getResource("/images/NormalCat.png"));
		SuperCatImage = Toolkit.getDefaultToolkit().createImage(RVC.class.getResource("/images/SuperCat.png"));
		PowerRobotImage = Toolkit.getDefaultToolkit().createImage(RVC.class.getResource("/images/PowerRobot.png"));
		DroneRobotImage = Toolkit.getDefaultToolkit().createImage(RVC.class.getResource("/images/DroneRobot.png"));
		BombRobotImage = Toolkit.getDefaultToolkit().createImage(RVC.class.getResource("/images/BombRobot.png"));
	}

	@Override
	public void paint(Graphics g) {
		// Paints the Board
		int NormalCatHealth = Params.NORMAL_CAT_HEALTH;
		int SuperCatHealth = Params.SUPER_CAT_HEALTH;
		int PowerRobotHealth = Params.POWER_ROBOT_HEALTH;
		int DroneRobotHealth = Params.DRONE_ROBOT_HEALTH;
		int w = getWidth(), h = getHeight();
		g.setColor(DARK_GREEN);
		g.fillRect(0, 0, w, h);
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 9; x++) {
				if ((x + y) % 2 > 0) {
					g.setColor(LIGHT_GREEN);
				} else {
					g.setColor(MID_GREEN);
				}
				g.fillRect(BORDER_X + x * BLOCK_WIDTH, BORDER_Y + y * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
			}
		}
		// Checks for where Objects are Placed on the Board and paints them.
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c < 9; c++) {
				Actor a = game.getBoard(r, c);
				if (a != null) {
					if (a instanceof NormalCat) {
						a = (Cat) a;
						g.setColor(Params.CAT_HEALTH);
						g.fillRect(Params.BORDER_X + 85 + c * Params.BLOCK_WIDTH,
								Params.BORDER_Y + 10 + r * Params.BLOCK_HEIGHT, 10,
								(Params.BLOCK_HEIGHT - 20) * ((NormalCat) a).getHealth() / NormalCatHealth);
						g.drawImage(NormalCatImage, BORDER_X + 10 + c * BLOCK_WIDTH, BORDER_Y + +10 + r * BLOCK_HEIGHT,
								null);
					} else if (a instanceof SuperCat) {
						a = (Cat) a;
						g.setColor(Params.CAT_HEALTH);
						g.fillRect(Params.BORDER_X + 85 + c * Params.BLOCK_WIDTH,
								Params.BORDER_Y + 10 + r * Params.BLOCK_HEIGHT, 10,
								(Params.BLOCK_HEIGHT - 20) * ((SuperCat) a).getHealth() / SuperCatHealth);
						g.drawImage(SuperCatImage, BORDER_X + 10 + c * BLOCK_WIDTH, BORDER_Y + +10 + r * BLOCK_HEIGHT,
								null);
					} else if (a instanceof PowerRobot) {
						a = (Robot) a;
						g.setColor(Params.ROBOT_HEALTH);
						g.fillRect(Params.BORDER_X + 5 + c * Params.BLOCK_WIDTH,
								Params.BORDER_Y + 10 + r * Params.BLOCK_HEIGHT, 10,
								(Params.BLOCK_HEIGHT - 20) * ((PowerRobot) a).getHealth() / PowerRobotHealth);
						g.drawImage(PowerRobotImage, BORDER_X + 10 + c * BLOCK_WIDTH, BORDER_Y + +10 + r * BLOCK_HEIGHT,
								null);
					} else if (a instanceof DroneRobot) {
						a = (Robot) a;
						g.setColor(Params.ROBOT_HEALTH);
						g.fillRect(Params.BORDER_X + 5 + c * Params.BLOCK_WIDTH,
								Params.BORDER_Y + 10 + r * Params.BLOCK_HEIGHT, 10,
								(Params.BLOCK_HEIGHT - 20) * ((DroneRobot) a).getHealth() / DroneRobotHealth);

						g.drawImage(DroneRobotImage, BORDER_X + 10 + c * BLOCK_WIDTH, BORDER_Y + +10 + r * BLOCK_HEIGHT,
								null);
					} else if (a instanceof BombRobot) {
						g.drawImage(BombRobotImage, BORDER_X + 10 + c * BLOCK_WIDTH, BORDER_Y + +10 + r * BLOCK_HEIGHT,
								null);
					}
				}
			}
		}
		// Highlights board spaces
		if (cursorRow != -1) {
			g.setColor(Params.BOARD_BORDER);
			int y = cursorRow * Params.BLOCK_HEIGHT + Params.BORDER_Y;
			int x = cursorCol * Params.BLOCK_WIDTH + Params.BORDER_X;
			g.drawRect(x, y, Params.BLOCK_WIDTH, Params.BLOCK_HEIGHT);
			g.drawRect(x + 1, y + 1, Params.BLOCK_WIDTH - 2, Params.BLOCK_HEIGHT - 2);
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Aquires the position of the mouse within the board
		int r = (e.getY() - Params.BORDER_Y) / Params.BLOCK_HEIGHT;
		int c = (e.getX() - Params.BORDER_X) / Params.BLOCK_WIDTH;

		if ((r >= 0) && (r < 5) && (c >= 0) && (c < 9) && (game.getBoard(r, c) == null)
				&& (rvc.getActiveButton() != null)) {
			cursorRow = r;
			cursorCol = c;

		} else {
			// If the mouse is out of the board
			cursorRow = -1;
			cursorCol = -1;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if ((cursorRow != -1) && (game.getBoard(cursorRow, cursorCol) == null)) {
			rvc.placeActiveButton(cursorRow, cursorCol);
			cursorRow = -1;
			cursorCol = -1;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
