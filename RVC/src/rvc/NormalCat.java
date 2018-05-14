package rvc;

public class NormalCat extends Cat {

	public NormalCat(int row, int col) {
		super(row, col);
		Health = 70;
		Damage = 12;
		
		
	}

	@Override
	public int getSpeed() {
		return Params.NORMAL_CAT_SPEED;
	}

}