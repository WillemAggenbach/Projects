package rvc;

public class PowerRobot extends Robot {
	private int waitforPower;
	private int waitforHealth;

	public PowerRobot(int row, int col) {
		super(row, col);
		Health = 100;
		Damage = 10;
	}

	public void HealthRegen() {
		Health = Health + 5;
		if (Health > 100) {
			Health = 100;
		}
	}

	@Override
	public void execute() {
		//checks if it should produce power of regen health
		waitforHealth -= Params.HP_FREQUENCY;
		if (waitforHealth <= 0) {
			waitforHealth = Params.POWER_ROBOT_RECHARGE;
			HealthRegen();
		}
		waitforPower -= Params.HP_FREQUENCY;
		if (waitforPower <= 0) {
			waitforPower = Params.POWER_ROBOT_POWER;
			game.recharge();
		}
		reschedule(Params.HP_FREQUENCY);

	}
}
