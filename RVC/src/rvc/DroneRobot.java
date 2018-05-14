package rvc;

public class DroneRobot extends Robot {

	public DroneRobot(int row, int col) {
		super(row, col);
		Health = 150;
		Damage = 10;
	}

	public void HealthRegen() {
		Health = Health + 6;
		if (Health > 150) {
			Health = 150;
		}
	}

	public void execute() {
		HealthRegen();
		reschedule(750);
	}
}
