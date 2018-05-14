package rvc;

import java.awt.Color;

public class Params {
	// Board
	public static final int BORDER_X = 50;
	public static final int BORDER_Y = 50;
	public static final int BLOCK_WIDTH = 100;
	public static final int BLOCK_HEIGHT = 100;
	public static final Color BOARD_BORDER = new Color(45, 67, 94);
	
	// Buttons
	public static final Color BUTTON_BG = new Color(194, 129, 66);
	public static final Color BUTTON_PRESSED = new Color(0, 0, 45);
	
	// Power
	public static final int INITIAL_POWER = 100;

	// Robot
	public static final Color ROBOT_HEALTH = new Color(0, 0, 144);
	public static final int POWER_ROBOT_RECHARGE = 1000;
	public static final int POWER_ROBOT_POWER = 7000;
	public static final int HP_FREQUENCY = (int) gcd(POWER_ROBOT_RECHARGE, POWER_ROBOT_POWER);
	public static final int POWER_ROBOT_HEALTH = 100;
	public static final int DRONE_ROBOT_HEALTH = 150;
	public static final int BOMB_ROBOT_COST = 30;
	public static final int POWER_ROBOT_COST = 40;
	public static final int DRONE_ROBOT_COST = 50;

	// Cats
	public static final String NORMAL_CAT_SOUND= "NormalCat-meow.wav";
	public static final Color CAT_HEALTH = new Color(144, 0, 0);
	public static final int NORMAL_CAT_HEALTH = 70;
	public static final int SUPER_CAT_HEALTH = 120;
	public static final int NORMAL_CAT_SPEED = 2400;
	public static final int SUPER_CAT_SPEED = 1600;
	public static final int CATPRODUCER_INTIAL_WAIT = 6000;
	public static final int SUPER_FREQUENCY = 4400;
	public static final int NORMAL_FREQUENCY = 3200;
	public static final int SN_FREQUENCY = (int) gcd(SUPER_FREQUENCY, NORMAL_FREQUENCY);
	public static final int SUPER_FREQUENCY4 = 2400;
	public static final int NORMAL_FREQUENCY4 = 4000;
	public static final int SN_FREQUENCY4 = (int) gcd(SUPER_FREQUENCY4, NORMAL_FREQUENCY4);
	
	//works out the gcd of two ints
	public static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

}
