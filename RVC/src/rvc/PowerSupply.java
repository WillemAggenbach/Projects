package rvc;

public class PowerSupply extends Actor {

	public void setPower() {

	}

	@Override
	public void prepare() {
	}

	@Override
	public void execute() {
		game.incPower();
		reschedule(1000);
	}

}
