import javax.swing.JOptionPane;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class TwoDukoGUI {
	
	
	public static boolean PVP(int GameValues[][])
	{
		//The main function for the mode when 2 human players are against each other.
		Gridbuild(GameValues);
		boolean game=true;
		boolean P1Lose=false;
		boolean P2Lose=false;
		while(game==true)
		{
			//Player 1 will go first and then Player 2. A routine check function name "TestGame" Will run before every move to see if the
			//game can continue.
			P1Lose=TestGame(GameValues);
			if(P1Lose==true)
			{
				StdDraw.clear();
				Gridbuild(GameValues);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(GameValues.length*2-4, GameValues.length*2+5, "Player 1 Loses.");
				game=false;
			}
			if(game==true)
			{
				StdDraw.clear();
				Gridbuild(GameValues);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(GameValues.length*2-4, GameValues.length*2+5, "Make your move, Player 1:");
				
				GameValues=PlayerMove(GameValues);
			}
			if(game==true)
			{
				Gridbuild(GameValues);
			}
			if(P1Lose==false&&game==true)
			{
				P2Lose=TestGame(GameValues);
			}
			if(P2Lose==true&&game==true)
			{
				StdDraw.clear();
				StdDraw.setPenColor(StdDraw.BLACK);
				Gridbuild(GameValues);
				StdDraw.text(GameValues.length*2-4, GameValues.length*2+5, "Player 2 Loses");
				game=false;
				P2Lose=true;
			}
			if(game==true)
			{
				StdDraw.clear();
				StdDraw.setPenColor(StdDraw.BLACK);
				Gridbuild(GameValues);
				StdDraw.text(GameValues.length*2-4, GameValues.length*2+5, "Make Your Move Player 2");
				GameValues=PlayerMove(GameValues);
			}
			if(game==true)
			{
				Gridbuild(GameValues);
			}
		}
		return P2Lose;
	}
	public static boolean PVE(int GameValues[][])
	{
		// The main function for the mode for when the AI is against a human.
		Gridbuild(GameValues);
		boolean game=true;
		boolean HumanLose=false;
		boolean AILose=false;
		while(game==true)
		{
			
			
			//The AI will play first. Then the human. A routine check function wil run before every move to see if the game can still proceed.
			AILose=TestGame(GameValues);
			
			if(AILose==true&&game==true)
			{
				StdDraw.clear();
				Gridbuild(GameValues);
				StdDraw.text(GameValues.length*2-4, GameValues.length*2+5, "AI Loses");
				game=false;
				break;
			}
			if(game==true)
			{
				GameValues=AIMove(GameValues);
				Gridbuild(GameValues);
			}
			if(game==true)
			{
				HumanLose=TestGame(GameValues);
			}
			
			if(HumanLose==true)
			{
				StdDraw.clear();
				Gridbuild(GameValues);
				StdDraw.text(GameValues.length*2-4, GameValues.length*2+5, "Human Loses");
				game=false;
				break;
			}
			if(game==true)
			{
				StdDraw.clear();
				Gridbuild(GameValues);
				StdDraw.text(GameValues.length*2-4, GameValues.length*2+5, "Make your move, Human");
				GameValues=PlayerMove(GameValues);
			}
			
		}
		return AILose;
	}
	public static boolean TestGame(int GameValues[][])
	{
		//Tests to see if the game is still possible to play
		boolean lose=true;
		boolean test=true;
		while(test==true)
		{
			//Checks every possible value at every possible cell where the value is 0. If one legal move is found during testing, 
			//the game is considered possible, testing will stop and the game will continue
			for(int value=0;value<GameValues.length;value++)
			{
				for(int x=0;x<GameValues.length;x++ )
				{
					for(int y=0;y<GameValues.length;y++)
					{
						boolean illegal=false;
						if(GameValues[x][y]!=0&&illegal==false)
						{
							
							illegal=true;
					
						}
						else
						{
							
							for(int c=0;c<GameValues.length;c++)
							{
								if(value==GameValues[x][c]&&illegal==false)
								{
									
									illegal=true;
									
								}
								if(value==GameValues[c][y]&&illegal==false)
								{
									
									illegal=true;
									
								}
							}
							if(illegal==false)
							{
								
								lose=false;
								test=false;
								break;
								
								
							}
							
							
						}
					}
			}
				
				
				
			}	
			break;
			
		}
		System.out.print("yo");
		return lose; 
	}
	public static int[][] AIMove(int GameValues[][])
	{
		boolean AI=false;
		while(AI==false)
		{
			//Randomises the value and placement of a move. if the move is illegal it will randomise again.
			int x=(int)Math.floor(Math.random()*(GameValues.length-1)+1);
			int y=(int)Math.floor(Math.random()*(GameValues.length-1)+1);
			int value=(int)Math.floor(Math.random()*(GameValues.length)+1);
			boolean illegal=false;
			if(GameValues[x][y]!=0&&illegal==false)
			{
				illegal=true;
			}
			else
			{
				
				for(int c=0;c<GameValues.length;c++)
				{
					if(value==GameValues[x][c]&&illegal==false)
					{
						illegal=true;
					}
					if(value==GameValues[c][y]&&illegal==false)
					{
						illegal=true;
					}
				}
				
			}	
			if(illegal==false)
			{
				GameValues[x][y]=value;
				AI=true;
			}
		}
		return GameValues;
	}
	public static int[][] PlayerMove(int GameValues[][])
	{
		boolean PlayerMove=true;
		boolean illegal=false;
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.filledRectangle(GameValues.length*2+3, GameValues.length*2-4, 1.5, 1);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(GameValues.length*2+3, GameValues.length*2-4, "Surrender?");
		while(PlayerMove==true)
		{
			if(StdDraw.mousePressed()==true&&PlayerMove==true)
			{
				if(StdDraw.mousePressed()==false)
				{
					double mx=StdDraw.mouseX();
					double my=StdDraw.mouseY();
					int mx2=(int) mx;
					int my2=(int) my;
					for(double x=1;x<GameValues.length*2;x=x+2)
					{
						int yy=0;
						for(double y=1;y<GameValues.length*2;y=y+2)
						{
							if(y<=my+0.5&&y>=my-0.5&&x<=mx+0.5&&x>=mx-0.5)
							{
								double xg=mx;
								double gy=my;
								if(GameValues[(mx2-1)/2][(my2-1)/2]==0)
								{
									int value=Integer.parseInt(JOptionPane.showInputDialog("What Number would you like to enter in the cell?"));
									if(value>0||value<=GameValues.length)
									{
										
										for(int c=0;c<GameValues.length;c++)
										{
											
											if(value==GameValues[(mx2-1)/2][c]&&illegal==false)
											{
												
												illegal=true;
												
											}
											if(value==GameValues[c][(my2-1)/2]&&illegal==false||value<=0||value>GameValues.length)
											{
												
												illegal=true;
												
											}
											
										}
										
										if(illegal==false)
										{
											GameValues[(mx2-1)/2][(my2-1)/2]=value;
											PlayerMove=false;
											
										}
										
										
									}
								}
								
								
							}
							if(mx2==GameValues.length*2+3&&my2==GameValues.length*2-4)
							{
								StdDraw.clear();
								StdDraw.text(0, 0, "YOU LOSE");
								System.exit(0);
							}
							
							
							
							
						}
						
					}
				}
				
				
			}
			StdDraw.mousePressed();
			if(illegal==true)
			{
				JOptionPane.showMessageDialog(null, "Illegal Move");
				illegal=false;
				
				
			}
			
		}
		return GameValues;
	}
	public static void Gridbuild(int GameValues[][])
	{
		StdDraw.clear();
		StdDraw.setXscale(-1,GameValues.length*2+8);
		StdDraw.setYscale(-1,GameValues.length*2+8);
		
		//StdDraw.fi
		
		StdDraw.setPenColor();
		for(int c=0;c<GameValues.length*2+1;c++)
		{
			if(c%2==0)
			{
				StdDraw.line(0, c, GameValues.length*2, c);
				StdDraw.line(c, 0, c, GameValues.length*2);
			}
			
		}
		int xx=0;
		
		for(int x=1;x<GameValues.length*2;x=x+2)
		{
			int yy=0;
			for(int y=1;y<GameValues.length*2;y=y+2)
			{
				if(GameValues[xx][yy]!=0)
				{
					StdDraw.text(x,y,String.valueOf(GameValues[xx][yy]));
				}
				yy++;
			}
			xx++;
		}
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(GameValues.length*2-4, GameValues.length*2+5, 6, 1);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(GameValues.length*2-6, GameValues.length*2+7, "Message Area:");
	}
	public static void main(String args[])
	{
		int x=0;
		int y=0;
		StdDraw.setXscale(0,6);
		StdDraw.setYscale(2,5);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.filledRectangle(1,3 , 0.5, 0.5);
		StdDraw.filledRectangle(3,3 , 0.5, 0.5);
		StdDraw.filledRectangle(5,3 , 0.5, 0.5);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(1, 3, "2x2");
		StdDraw.text(3, 3, "3x3");
		StdDraw.text(5, 3, "4x4");
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledRectangle(3,4 , 2, 0.4);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(3, 4, "Please Select Your Prefered Grid Size");
		boolean GameModes=true;
		while(GameModes=true)
		{
			if(StdDraw.mousePressed())
			{
				int mx=(int)StdDraw.mouseX();
				int my=(int)StdDraw.mouseY();
				if(mx==1&&my==3)
				{
					x=2;
					y=2;
					
					StdDraw.mousePressed();
					break;
					
				}
				if(mx==3&&my==3)
				{
					x=3;
					y=3;
					
					StdDraw.mousePressed();
					break;
				}
				if(mx==5&&my==3)
				{
					x=4;
					y=4;
					
					StdDraw.mousePressed();	
					break;
				}
				
			}
		}
		StdDraw.clear();
		StdDraw.setXscale(0,4);
		StdDraw.setYscale(0,3);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledRectangle(2, 2, 1, 0.5);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.filledRectangle(1, 1, 0.5, 0.5);
		StdDraw.filledRectangle(3, 1, 0.5, 0.5);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(2, 2, "Please Choice your prefered Mode");
		StdDraw.text(1, 1, "PVE");
		StdDraw.text(3, 1, "PVP");
		int Mode=0;
		while(GameModes==true)
		{
			if(StdDraw.mousePressed())
			{
				int mx=(int)StdDraw.mouseX();
				int my=(int)StdDraw.mouseY();
				if(mx==1&&my==1)
				{
					Mode=0;
					break;
					
				}
				if(mx==3&&my==1)
				{
					Mode=1;
					break;
					
				}
			}
			
		}
		StdDraw.clear();
		int[][] GameValues=new int[x*x][y*y];
		Gridbuild(GameValues);
		
		int Human=0;
		int Player1=0;
		int Player2=0;
		int AI=0;
		boolean game=true;
		if(Mode==0)
		{
			while(game==true)
			{
				StdDraw.setPenColor(StdDraw.BLACK);
				
				boolean points=PVE(GameValues);
				if(points==true)
				{
					Human++;
				}
				else
				{
					AI++;
				}
				StdDraw.text(GameValues.length*2+3, GameValues.length*2-1, "AI:"+AI);
				StdDraw.text(GameValues.length*2+3, GameValues.length*2-2, "Human:"+Human);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.filledRectangle(GameValues.length*2+3, GameValues.length*2-4, 1.5, 1);
				StdDraw.filledRectangle(GameValues.length*2+3, GameValues.length*2-6, 1.5, 1);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(GameValues.length*2+3, GameValues.length*2-4, "Play Again?");
				StdDraw.text(GameValues.length*2+3, GameValues.length*2-6, "Quit");
				while(game==true)
				{
					if(StdDraw.mousePressed())
					{
						int mx=(int)StdDraw.mouseX();
						int my=(int)StdDraw.mouseY();
						if(mx==GameValues.length*2+3||my==GameValues.length*2-4)
						{
							break;
						}
						else
						{
							if(mx==GameValues.length*2+3||my==GameValues.length*2-6)
							{
								game=false;
								break;
								
							}
						}
					}
				}
				
			}
			
		}
		if(Mode==1)
		{
			while(game==true)
			{
				boolean points=false;
				StdDraw.setPenColor(StdDraw.BLACK);
				GameValues=new int[x*x][y*y];
				Gridbuild(GameValues);
				points=PVP(GameValues);
				if(points==true)
				{
					Player1++;
				}
				else
				{
					Player2++;
				}
				StdDraw.text(GameValues.length*2+3, GameValues.length*2-1, "Player1"+Player1);
				StdDraw.text(GameValues.length*2+3, GameValues.length*2-2, "Player2"+Player2);
				StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
				StdDraw.filledRectangle(GameValues.length*2+3, GameValues.length*2-4, 1.5, 1);
				StdDraw.filledRectangle(GameValues.length*2+3, GameValues.length*2-6, 1.5, 1);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(GameValues.length*2+3, GameValues.length*2-4, "Play Again?");
				StdDraw.text(GameValues.length*2+3, GameValues.length*2-6, "Quit");
				while(game==true)
				{
					if(StdDraw.mousePressed())
					{
						if(StdDraw.mousePressed()==false)
						{
							int mx=(int)StdDraw.mouseX();
							int my=(int)StdDraw.mouseY();
							if(mx==GameValues.length*2+3&&my==GameValues.length*2-4)
							{
									GameValues=new int[x*x][y*y];
									break;
									
							}
							else
							{
								if(mx==GameValues.length*2+3&&my==GameValues.length*2-6)
								{
									GameValues=new int[x*x][y*y];
									game=false;
									System.exit(0);
									break;
									
									
								}
							}
						}
						
						
					}
				}
				if(game==false)
				{
					break;
				}
				
			}
		}
		
		
		
	}
}
