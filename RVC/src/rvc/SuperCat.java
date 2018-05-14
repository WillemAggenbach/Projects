package rvc;

public class SuperCat extends Cat {

	public SuperCat(int row, int col) {
		super(row, col);
		Health = 120;
		Damage = 15;
	}

	@Override
	public int getSpeed() {
		return Params.SUPER_CAT_SPEED;
	}
}
