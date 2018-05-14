package rvc;

public class CatProducer extends Actor {
	public int catsProduced = -1;
	public int catsAlive = 0;
	private int waitforSuper = Params.SUPER_FREQUENCY;
	private int waitforNormal = Params.NORMAL_FREQUENCY;
	

	@Override
	public void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		// Checks how many cats have been produced and in which part of the
		// phases they are
		if (game.CatsProduced == -1) {
			game.CatsProduced++;
			reschedule(Params.CATPRODUCER_INTIAL_WAIT);
		} else if (game.CatsProduced <= 20) {
			phase4();
		} else if (game.CatsProduced > 20 && game.CatsProduced <= 40) {
			phase2();
		} else if (game.CatsProduced > 40 && game.CatsProduced <= 60) {
			phase3();
		} else if (game.CatsProduced > 60 && game.CatsProduced <= 80) {
			phase4();
		} else if (game.CatsAlive > 0) {
			// Wait or all cats to die
			reschedule(1000);
		} else {
			// When you win the game
			game.gameOver(2);
		}
	}

	public void phase1() {
		int r = (int) ((Math.random() * (5 - 0)));
		
		game.add(new NormalCat(r, 9));
		
		reschedule(3200);
	}
	
	public void phase2() {
		int r = (int) ((Math.random() * (5 - 0)));
		game.add(new NormalCat(r, 9));
		
		reschedule(2400);
	}

	public void phase3() {
		int r = (int) ((Math.random() * (5 - 0)));
		int r2 = (int) ((Math.random() * (5 - 0)));
		waitforSuper -= Params.SN_FREQUENCY;
		if (waitforSuper <= 0) {
			game.add(new SuperCat(r, 9));
			waitforSuper = Params.SUPER_FREQUENCY;
			System.out.println(game.CatsAlive);
		}
		waitforNormal -= Params.SN_FREQUENCY;
		if (waitforNormal <= 0) {
			game.add(new NormalCat(r2, 9));
			waitforNormal = Params.NORMAL_FREQUENCY;
		}
		reschedule(Params.SN_FREQUENCY);
	}

	public void phase4() {
		int r = (int) ((Math.random() * (5 - 0)));
		int r2 = (int) ((Math.random() * (5 - 0)));
		waitforSuper -= Params.SN_FREQUENCY4;
		if (waitforSuper <= 0) {
			game.add(new SuperCat(r, 9));
			
			waitforSuper = Params.SUPER_FREQUENCY4;	
		}
		waitforNormal -= Params.SN_FREQUENCY4;
		if (waitforNormal <= 0) {
			game.add(new NormalCat(r2, 9));	
			waitforNormal = Params.NORMAL_FREQUENCY4;
		}
		reschedule(Params.SN_FREQUENCY4);
	}
}
