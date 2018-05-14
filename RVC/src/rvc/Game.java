package rvc;

import java.util.concurrent.PriorityBlockingQueue;

public class Game {

	public int power;
	public int result = -1;
	private int x = 0;
	private PriorityBlockingQueue<Actor> heap = new PriorityBlockingQueue<>();
	private long curTime = 0;
	private Actor[][] board = new Actor[5][9];
	public int CatsProduced = -1;
	public int CatsAlive = 0;

	public Game() {
		// The initial power of the PowerSupply
		power = Params.INITIAL_POWER;
	}

	public int getResult() {
		return result;
	}

	public void gameOver(int result) {
		this.result = result;
	}

	public void add(Actor actor) {
		actor.setHeap(heap, curTime);
		actor.setGame(this);
		heap.add(actor);
		actor.prepare();
	}

	public void place(Actor actor, int row, int col) {
		if ((row >= 0) && (row < 5) && (col >= 0) && (col < 9) && (board[row][col] == null)) {
			board[row][col] = actor;
			if(col == 8&&actor instanceof Cat){
				CatsAlive++;
				CatsProduced++;
			}
		}
	}

	public void move(Actor actor, int frow, int fcol, int trow, int tcol) {
		if ((frow >= 0) && (frow < 5) && (fcol >= 0) && (fcol < 9) && (board[frow][fcol] == actor)) {
			board[frow][fcol] = null;
		}
		place(actor, trow, tcol);
	}

	public Actor getBoard(int row, int col) {
		if ((row >= 0) && (row < 5) && (col >= 0) && (col < 9)) {
			return board[row][col];
		} else {
			return null;
		}
	}

	public void remove(Actor actor, int r, int c) {
		// removes the Actor from the game
		board[r][c] = null;
		heap.remove(actor);
		if (actor instanceof NormalCat) {
			CatsAlive--;
		}
		if (actor instanceof SuperCat) {
			CatsAlive--;
		}
	}

	public boolean iterate(boolean Pause) {
		if (heap.isEmpty()) {
			return false;
		}
		if (Pause == false) {
			Actor actor = heap.remove();
			long time = actor.getTime();
			if (time > curTime) {
				try {
					Thread.sleep(time - curTime);
				} catch (InterruptedException e) {
					// ignore
				}
			}
			actor.execute();
			curTime = time;
		}
		return true;
	}

	public void recharge() {
		power = power + 10;
	}

	public void incPower() {

		power++;
	}

	public void decreasePower(int d) {
		power = power - d;
	}

	public int getPower() {
		return power;
	}

}
