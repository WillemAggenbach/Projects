package rvc;

import java.util.Random;

public class Robot extends Actor {
	private int row;
	private int col;
	protected int Health;
	protected int Damage;

	public void TakeDamage(int d) {
		//Decreases health according to the cat infront of it
		Health -= d;

		if (Health <= 0) {
			game.remove(this, row, col);
			reschedule(500);
		}
	}

	public int getHealth() {
		return Health;
	}

	@Override
	public void prepare() {
		game.place(this, row, col);
	}

	public Robot(int row, int col) {
		this.row = row;
		this.col = col;
	}

	@Override
	public void execute() {

	}

}
