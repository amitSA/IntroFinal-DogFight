package otherstuff;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.JFrame;



import planes.FighterPlane;




public class EliminationRules implements Rules
{
	ArrayList<Team> teams;
	ArrayList<FighterPlane> planes;
	
	private ArrayList<int[]> superArray;
	
	double endGameCount;

	public EliminationRules(ArrayList<Team> teams, ArrayList<FighterPlane> planes)
	{
		this.teams = teams;
		this.planes = planes;
		
		
		superArray = new ArrayList<int[]>();
		for(int i = 0;i<teams.size();i++)
		{
			int [] a = {0,0};  // first element denotes the kills, second element denotes the losses
			superArray.add(a);
		}
	endGameCount = 0;	
	
	}
	
	
	public void updateGame() 
	{
		int [] planesAlive = new int[teams.size()];
		
		for(int i = 0;i<teams.size();i++)
			System.out.println("Team Name: " + teams.get(i).team + "   kill-count : " + teams.get(i).getKillCount());
		
		for(int i = 0;i<teams.size();i++)
		{
			planesAlive[i] = teams.get(i).getPlanesAlive();
			//System.out.print(teams.get(i).getCountry() + ": " + teams.get(i).getPlanesAlive() + "    ");
		}
		//System.out.print("\n");
		
		
		
		for(int i = 0;i<teams.size();i++)
		{  boolean bool = true;
			for(int ip = 0;ip<planesAlive.length;ip++)
			{
				if(ip == i)
					continue;
				if(planesAlive[i] > planesAlive[ip])
				    bool = false;
				
			}
			int kills = teams.get(i).getKillCount();
			int losses = teams.get(i).getLosses();
			// checking kills
			if(superArray.get(i)[0] != kills)
			{
				superArray.get(i)[0] = kills;
				if(teams.get(i).getKillCount() % 4 == 0)
				{
					if(teams.get(i).isCaptainPlaneAlive())
					{
						planes.add((FighterPlane) teams.get(i).makeNewWingman());
					}
					else
					{
						planes.add((FighterPlane) teams.get(i).makeNewCaptainPlane());
					}
				}

			}
			// checking lossess`
			if(superArray.get(i)[1] != losses && bool)
			{
				superArray.get(i)[1] = losses;
				if(teams.get(i).getLosses() % 3 == 0)
				{
					if(teams.get(i).isCaptainPlaneAlive())
					{
						planes.add((FighterPlane) teams.get(i).makeNewWingman());
					}
					else
					{
						planes.add((FighterPlane) teams.get(i).makeNewCaptainPlane());
					}
				}
			}
		}
	}
	
	public void checkEndGame(Graphics2D g2d) 
	{
		boolean bool = true;
		for(int i = 0;i<planes.size();i++)
		{
			if(!planes.get(0).getTeam().equals(planes.get(i).getTeam()))
			{
				bool = false;
			}
		}
		if(bool)
		{
			String country = planes.get(0).getTeam();
			g2d.setColor(Color.BLACK); //CHANGE
			
			Font font = new Font(Font.SERIF,Font.PLAIN,100);
			Font oldFont = g2d.getFont();
			g2d.setFont(font);
		//	g2d.drawString("Victory For ",60,60);
			if(country.equals("Japan"))
				g2d.setColor(Color.MAGENTA);
			else if(country.equals("Germany"))
				g2d.setColor(Color.GREEN);
			else
				g2d.setColor(Color.BLUE);
//			g2d.drawString(country,60,160);
			g2d.setFont(oldFont);
			
			endGameCount+=0.015;
			if(endGameCount>3)
			{	
				//((Menu) frame).resetMenuScreen();     buggy, the program laggs the second time 
			}
		}
		
	}
	
	
    }

