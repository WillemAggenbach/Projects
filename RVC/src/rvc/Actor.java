package rvc;

import java.util.Queue;

public abstract class Actor implements Comparable<Actor> {

	private long time;
	
	private Queue<Actor> heap;

	protected Game game;

	public Actor() {
		this.heap = null;
		this.time = 0;
		this.game = null;
	}

	public void setHeap(Queue heap, long startTime) {
		this.time += startTime;
		this.heap = heap;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public long getTime() {
		return time;
	}

	public void reschedule(long delay) {
		time += delay;
		if (heap != null) {
			heap.add(this);
		}
	}

	public abstract void prepare();

	public abstract void execute();

	@Override
	public int compareTo(Actor actor) {
		return Long.compare(this.time, actor.time);
	}

}
