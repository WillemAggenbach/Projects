import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class TwoDukoStd {
	
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
			StdOut.println("Player 1 Loses");
			game=false;
		}
		if(game==true)
		{
			StdOut.println("Make your move, Player 1:");
			GameValues=Playermove(GameValues);
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
			StdOut.println("Player 2 Loses");
			game=false;
			P2Lose=true;
		}
		if(game==true)
		{
			StdOut.println("Make your move, Player 2:");
			GameValues=Playermove(GameValues);
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
			StdOut.println("AI Loses");
			game=false;
		}
		if(game==true)
		{
			GameValues=AImove(GameValues);
			Gridbuild(GameValues);
		}
		if(game==true)
		{
			HumanLose=TestGame(GameValues);
		}
		
		if(HumanLose==true)
		{
			StdOut.println("Human Loses");
			game=false;
		}
		if(game==true)
		{
			StdOut.println("Make your move, human:");
			GameValues=Playermove(GameValues);
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
							
						}
						
						
					}
				}
		}
		
			test=false;
			
		}	
	}
	return lose; 
}
public static int[][] AImove(int GameValues[][])
{
	//Function which is called for every AI move
	boolean AI=false;
	while(AI==false)
	{
		//Randomises the value and placement of a move. if the move is illegal it will randomise again.
		int x=(int)Math.floor(Math.random()*(GameValues.length-1)+1);
		int y=(int)Math.floor(Math.random()*(GameValues.length-1)+1);
		boolean illegal=false;
		if(GameValues[x][y]!=0&&illegal==false)
		{
			illegal=true;
		}
		else
		{
			int value=(int)Math.floor(Math.random()*(GameValues.length-1)+1);
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
				GameValues[x][y]=value;
				AI=true;
			}
		}	
	}
	return GameValues;
}
	
public static int[][] Playermove(int GameValues[][])
{
	//The function called whenever a human player makes a move. 
	boolean move=false;
	int xx=0;
	int yy=0;
	int v=0;
	
	while(move==false)
	{
		
		StdOut.print(">");
		String smove[]=StdIn.readLine().split(" ");
		int x=Integer.parseInt(smove[0])-1;
		int y=Integer.parseInt(smove[1])-1;
		int value=Integer.parseInt(smove[2]);
		//Checks if the move is legal
		boolean illegal=false;
		if(value>GameValues.length||value<1)
		{
			StdOut.print("Illegal move");
			illegal=true;
		}
		
		if(GameValues[x][y]!=0&&illegal==false)
		{
			StdOut.print("Illegal move");
			illegal=true;
		}
		for(int c=0;c<GameValues.length;c++)
		{
			
			if(value==GameValues[x][c]&&illegal==false)
			{
				
				StdOut.print("Illegal move");
				illegal=true;
			}
			if(value==GameValues[c][y]&&illegal==false)
			{
				StdOut.print("Illegal move");
				illegal=true;
			}
		}
		if(illegal==false)
		{
			xx=x;
			yy=y;
			
			v=value;
			move=true;
			
		}
		
		
	}
	//Adds the new value too the new grid.
	GameValues[xx][yy]=v;
	

	return GameValues;
}

public static void Gridbuild(int G[][])
{
	//Builds The current Grid after every Human Move
	int tt=0;
	int t=0;
	for(int grid=1;grid<G.length+G.length+1;grid++)
		{
			if(grid%2==1)
			{
				
				if(t==Math.sqrt(G.length))
					{
					for(int x=0;x<Math.sqrt(G.length);x++)
					{
						for(int y=0;y<Math.sqrt(G.length);y++)
						{
							StdOut.print("+===");
							
						}
						StdOut.print("+");
					}
					StdOut.println("");
					t=0;
					}
				else
				{
					for(int x=0;x<Math.sqrt(G.length);x++)
					{
						for(int y=0;y<Math.sqrt(G.length);y++)
						{
							StdOut.print("+---");
						}
						StdOut.print("+");
					}
					StdOut.println("");
				}
				t++;
				
			}
			else
			{
				int r=0;
				StdOut.print("|");
				
				{
					
					for(int x=0;x<G.length;x++)
					{
						StdOut.print(" ");
						if(G[x][tt]==0)
						{
							StdOut.print(" "+" |");
						}
						else
						{
							if(G[x][tt]>=10)
							{
								StdOut.print(G[x][tt]+"|");
							}
							else
							{
								StdOut.print(G[x][tt]+" |");
							}
							
						}
						
						r++;
						if(r==Math.sqrt(G.length)&&x!=G.length-1)
						{
							StdOut.print("|");
							r=0;
							
						}
						
					}
					
					
					
				}
				StdOut.println("");
				tt++;
			}
		}
	for(int x=0;x<Math.sqrt(G.length);x++)
	{
		for(int y=0;y<Math.sqrt(G.length);y++)
		{
			StdOut.print("+---");
		}
		StdOut.print("+");
	}
	StdOut.println("");		

}

public static void main(String args[])
{
	int mode = Integer.parseInt(args[0]);
	//Handles the type of grid and mode
	int AIScore=0;
	int HumanScore=0;
	int Player1=0;
	int Player2=0;
	boolean Game=true;
	StdOut.println("What mode would you like to Play?");
	String a[]=StdIn.readLine().split(" ");
	int Mode[]=new int[3];
	Mode[0]=Integer.parseInt(a[0]);
	Mode[1]=Integer.parseInt(a[1]);
	Mode[2]=Integer.parseInt(a[2]);	
	int GameValues[][]=new int[Mode[0]*Mode[0]][Mode[1]*Mode[1]];
	//Keeps track of score of PlayervsAI or PlayervsPlayer
	if(Mode[2]==0)
	{
		while(Game==true)
		{
			boolean PVEwin=PVE(GameValues);	
			if(PVEwin==true)
			{
				HumanScore++;
			}
			else
			{
				AIScore++;
			}
			StdOut.println("Scores:");
			StdOut.println("Human Score :"+HumanScore);
			StdOut.println("AI Score :"+AIScore);
			StdOut.println("Would you like to play again? Type 'Yes' or 'No' ");
			String Play=StdIn.readLine();
			if(Play.equals("No"))
			{
				Game=false;
			}
			GameValues=new int[Mode[0]*Mode[0]][Mode[1]*Mode[1]];
		}
		
	}
	if(Mode[2]==1)
	{
		while(Game==true)
		{
			boolean PVPwin=PVP(GameValues);
			if(PVPwin=true)
			{
				Player2++;
			}
			else
			{
				Player1++;
			}
			StdOut.println("Player 1:"+Player1);
			StdOut.println("Player 2:"+Player2);
			StdOut.println("Would you like to play again? Type 'Yes' or 'No' ");
			String Play=StdIn.readLine();
			if(Play.equals("No"))
			{
				Game=false;
			}
			GameValues=new int[Mode[0]*Mode[0]][Mode[1]*Mode[1]];
		}
		
	}
}
}
