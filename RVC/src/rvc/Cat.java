package rvc;

import java.util.Random;

public abstract class Cat extends Actor {

	private Random rng = new Random();
	private int row;
	private int col;
	protected int Health;
	protected int Damage;

	public Cat(int row, int col) {
		this.row = row;
		this.col = col;

	}

	public void TakeDamage(int d, int r, int c) {
		// Decreseas the Health of the cat and the facing robot
		Health -= d;
		if (Health <= 0) {
			game.remove(this, r, c);
		}
	}

	public int getHealth() {
		return Health;
	}

	@Override
	public void prepare() {
		game.place(this, row, col);
	}

	public abstract int getSpeed();

	@Override
	public void execute() {

		if (game.getBoard(row, col - 1) == null) {
			col--;
			game.move(this, row, col + 1, row, col);
		} else {
			// checks for if there is a robot infront of it
			Actor actor = game.getBoard(row, col - 1);
			if (actor instanceof Robot) {
				Actor adjacent = game.getBoard(row, col + 1);
				Robot robot = (Robot) actor;
				if (robot instanceof BombRobot) {
					game.remove(this, row, col);
					game.remove(robot, row, col - 1);
					game.remove(adjacent, row, col + 1);
				} else {
					robot.TakeDamage(Damage);
					TakeDamage(robot.Damage, row, col);
				}
			}
		}
		if (col >= 0) {
			if (game.getBoard(row, col) != null) {
				reschedule(getSpeed()+rng.nextInt(100));
			}

		} else if(col<0) {
			
			game.gameOver(1);
		}
	}

}
