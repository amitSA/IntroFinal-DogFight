package otherstuff;
import java.awt.Image;
import java.util.ArrayList;

import planes.Ammo;
import planes.FighterPlane;
import planes.IsCaptain;
import planes.IsWingman;
import planes.UserPlane;
import planes.AIPlane;


public abstract class Team 
{
	protected int killCount;
	protected int losses;
	protected String team;
	
	protected ArrayList<Ammo> bullets;
	protected ArrayList<FighterPlane> planes;
	protected int degrees;
	protected int width;
	protected int height;
	protected Image captainImage;
	protected Image wingImage;
	
	protected boolean isWidthRand;
	protected boolean isHeightRand;
	
	protected int frameW;
	protected int frameH;
	
	
	
	protected int wingHealth;
	protected int captainHealth;
	
	public Team(String team,ArrayList<Ammo> bullets,Image userImage,int degrees,int width,int height,ArrayList<FighterPlane> planes, Image wingImage,boolean isWidthRand,boolean isHeightRand,int frameW,int frameH,int wH,int uH)
	{
		this.team = team;  // also the team in other classes
		this.captainImage = userImage;
		this.bullets = bullets;
		this.degrees = degrees;
		
		wingHealth = wH;
		captainHealth = uH;
		
		this.isWidthRand = isWidthRand;
		this.isHeightRand = isHeightRand;
		
		this.width = width;
		this.height = height;
	    
		this.frameW = frameW;
		this.frameH = frameH;
		
		
		this.planes = planes;
		this.wingImage = wingImage;
		killCount = 0;
		losses = 0;
	}
	
	public void increaseKillCount()
	{
		killCount++;
	}
	
	public void planeLost()
	{
		losses--;
	}
	
	
	public int getKillCount()
	{
		return killCount;
	}
	public int getLosses()
	{
		return losses;
	}
	public String getCountry()
	{
		return team;
	}
	
	public int getPlanesAlive(){
		int num =  0;
		for(int i = 0;i<planes.size();i++)
		{
			if(planes.get(i).getTeam().equals(team))
				num++;
		}
		return num;
	}
	
	public boolean isCaptainPlaneAlive()
	{
		boolean bool = false;
		for(int i = 0;i<planes.size();i++){
			FighterPlane p = planes.get(i);
			if(p instanceof IsCaptain && p.getTeam().equals(team)){
				bool = true;
				break;
			}
		}
		return bool;
	}

	
	public abstract IsCaptain makeNewCaptainPlane();

	public abstract IsWingman makeNewWingman();
	
	
}
