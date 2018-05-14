package rvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class RVC extends JFrame implements ActionListener {

	private static final long serialVersionUID = 4401857723714205936L;

	private static final int BUTTON_WIDTH = 120;
	private static final int BUTTON_HEIGHT = 60;

	private static final Dimension BUTTON_SIZE = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
	private static final Font BUTTON_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);
	private JButton activeButton = null;
	private JButton quitButton;
	private JButton powerButton;
	private JButton powerRobotButton;
	private JButton droneRobotButton;
	private JButton BombRobotButton;
	private JButton PauseButton;
	private JButton RestartButton;
	private PowerSupply Supply;
	private Game game;
	private Board board;
	private GameTask gameTask;
	private CatProducer Producer;
	private boolean Pause = false;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new RVC().setVisible(true);
			}
		});
	}

	public RVC() {
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
			MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
		} catch (UnsupportedLookAndFeelException x) {
			x.printStackTrace();
		} catch (Exception x) {
			x.printStackTrace();
		}
		StartGame();

	}

	private JPanel makeButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(129, 74, 46));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(powerRobotButton = makeButton("PowerRobot"));
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 1)));
		buttonPanel.add(droneRobotButton = makeButton("DroneRobot"));
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 1)));
		buttonPanel.add(BombRobotButton = makeButton("BombRobot"));
		buttonPanel.add(Box.createVerticalGlue());
		return buttonPanel;
	}

	public void placeActiveButton(int r, int c) {
		//Checks which robot wants to be placed and if the player can 
		//afford it
		if (activeButton == powerRobotButton) {
			game.add(new PowerRobot(r, c));
			game.decreasePower(Params.POWER_ROBOT_COST);
		}
		if (activeButton == droneRobotButton) {
			game.add(new DroneRobot(r, c));
			game.decreasePower(Params.DRONE_ROBOT_COST);
		}
		if (activeButton == BombRobotButton) {
			game.add(new BombRobot(r, c));
			game.decreasePower(Params.BOMB_ROBOT_COST);
		}
		activeButton.setBackground(Params.BUTTON_BG);
		activeButton = null;
	}

	public JButton getActiveButton() {
		return activeButton;
	}

	public void StartGame() {
		// Starts the game over
		game = new Game();
		game.add(Supply = new PowerSupply());
		game.add(Producer = new CatProducer());
		board = new Board(game, this);
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(makeButtons(), BorderLayout.LINE_START);
		mainPanel.add(makeStatus(), BorderLayout.PAGE_END);
		mainPanel.add(board, BorderLayout.CENTER);
		gameTask = new GameTask(game);
		gameTask.execute();
		setContentPane(mainPanel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getRootPane().setDefaultButton(null);
		pack();
		int w = board.getMinimumSize().width + BUTTON_WIDTH;
		int h = board.getMinimumSize().height + BUTTON_WIDTH;
		setMinimumSize(new Dimension(w, h));
		setLocationRelativeTo(null);
		setLocationRelativeTo(null);
	}

	public void gameOver(int results) {
		gameTask.cancel(true);
		if (results == 0) {
			//Player pressed the quit button
			dispose();
		}
		if (results == 1) {
			//Player Lost to cats
			JOptionPane.showMessageDialog(null, "You Lost.");
		}
		if (results == 2) {
			//Player won to cats
			JOptionPane.showMessageDialog(null, "You Won!");
		}
		if (results == 3) {
			//Player Restarted the game.
			StartGame();
		}

	}

	private JPanel makeStatus() {
		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(new Color(129, 74, 46));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));
		statusPanel.add(powerButton = makeButton("Power"));
		
		statusPanel.add(Box.createHorizontalGlue());
		statusPanel.add(PauseButton = makeButton("Pause"));
		statusPanel.add(RestartButton = makeButton("Restart"));
		statusPanel.add(quitButton = makeButton("Quit"));
		return statusPanel;
	}

	private JButton makeButton(String text) {
		JButton button = new JButton(text);
		button.setFont(BUTTON_FONT);
		button.setBackground(new Color(194, 129, 66));
		button.setBorder(BorderFactory.createLineBorder(new Color(153, 102, 51), 3));
		button.setFocusable(false);
		button.addActionListener(this);
		button.setMinimumSize(BUTTON_SIZE);
		button.setPreferredSize(BUTTON_SIZE);
		button.setMaximumSize(BUTTON_SIZE);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//checks which button has been pressed
		Object source = e.getSource();
		if (source == quitButton) {
			gameOver(0);
		} else if (source == powerRobotButton) {
			if (activeButton == powerRobotButton) {
				activeButton.setBackground(Params.BUTTON_BG);
				activeButton = null;
			} else if (game.getPower() > Params.POWER_ROBOT_COST) {
				if (activeButton != null) {
					activeButton.setBackground(Params.BUTTON_BG);

				}
				activeButton = powerRobotButton;
				activeButton.setBackground(Params.BUTTON_PRESSED);
			}
		} else if (source == droneRobotButton) {
			if (activeButton == droneRobotButton) {
				activeButton.setBackground(Params.BUTTON_BG);
				activeButton = null;
			} else if (game.getPower() > Params.DRONE_ROBOT_COST) {
				if (activeButton != null) {
					activeButton.setBackground(Params.BUTTON_BG);
				}
				activeButton = droneRobotButton;
				activeButton.setBackground(Params.BUTTON_PRESSED);
			}
		} else if (source == BombRobotButton) {
			if (activeButton == BombRobotButton) {
				activeButton.setBackground(Params.BUTTON_BG);
				activeButton = null;
			} else if (game.getPower() > Params.BOMB_ROBOT_COST) {
				if (activeButton != null) {
					activeButton.setBackground(Params.BUTTON_BG);
				}
				activeButton = BombRobotButton;
				activeButton.setBackground(Params.BUTTON_PRESSED);
			}
		} else if (source == PauseButton) {
			if (Pause == true) {
				Pause = false;
				PauseButton.setBackground(Params.BUTTON_BG);

			} else if (Pause == false) {
				Pause = true;
				PauseButton.setBackground(Params.BUTTON_PRESSED);
			}
		} else if (source == RestartButton) {
			gameOver(3);
		}

	}

	private class GameTask extends SwingWorker<Void, Void> {

		private final Game game;

		public GameTask(Game game) {
			super();
			this.game = game;
			game.add(new Updater());
		}

		@Override
		protected Void doInBackground() throws Exception {
			while (!isCancelled() && (game.getResult() == -1) && game.iterate(Pause)) {
				// empty loop body
			}
			return null;
		}

		@Override

		protected void done() {
			if (game.getResult() != -1) {
				gameOver(game.getResult());
			}
		}

		@Override
		protected void process(List<Void> dummy) {
			powerButton.setText("Power: " + Integer.toString(game.getPower()));
			board.repaint();
		}

		private class Updater extends Actor {

			@Override
			public void prepare() {
			}

			@Override
			public void execute() {
				publish();
				reschedule(200);
			}

		}

	}

}
