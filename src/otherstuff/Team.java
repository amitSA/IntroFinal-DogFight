package otherstuff;
import java.awt.Image;
import java.util.ArrayList;

import planes.Ammo;
import planes.FighterPlane;
import planes.UserPlane;
import planes.Wingman;


public class Team 
{
	private int killCount;
	private int losses;
	private String team;
	private Image image;
	private ArrayList<Ammo> bullets;
	private ArrayList<FighterPlane> planes;
	private int degrees;
	private int width;
	private int height;
	private Image wingImage;
	
	private boolean isWidthRand;
	private boolean isHeightRand;
	
	private int frameW;
	private int frameH;
	
	private int up;
	private int down;
	private int shoot;
	
	private int wingHealth;
	private int userHealth;
	
	public Team(String team,int up, int down, int shoot,ArrayList<Ammo> bullets,Image userImage,int degrees,int width,int height,ArrayList<FighterPlane> planes, Image wingImage,boolean isWidthRand,boolean isHeightRand,int frameW,int frameH,int wH,int uH)
	{
		this.team = team;  // also the team in other classes
		this.image = userImage;
		this.bullets = bullets;
		this.degrees = degrees;
		
		wingHealth = wH;
		userHealth = uH;
		
		this.isWidthRand = isWidthRand;
		this.isHeightRand = isHeightRand;
		
		this.width = width;
		this.height = height;
	    
		this.frameW = frameW;
		this.frameH = frameH;
		
		this.up = up;
		this.down = down;
		this.shoot = shoot;
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
	public UserPlane makeNewUserPlane()
	{
		if(isWidthRand)
		{
			width = (int)(Math.random()*frameW);
		}
		if(isHeightRand)
		{
			height = (int)(Math.random()*frameH);
		}
		return new UserPlane(image,width,height,up,down,shoot,bullets,team,degrees,userHealth);
	}
	public Wingman makeNewWingman()
	{
		if(isWidthRand)
		{
			width = (int)(Math.random()*frameW);
		}
		if(isHeightRand)
		{
			height = (int)(Math.random()*frameH);
		}
//		if(country.equals("Japan"))
//		System.out.println(width + " " + height);
		return new Wingman(wingImage,width,height,bullets,team,planes,degrees,wingHealth);
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
	
	public boolean isUserPlaneAlive()
	{
		boolean bool = false;
		for(int i = 0;i<planes.size();i++){
			FighterPlane p = planes.get(i);
			if(p instanceof UserPlane && p.getTeam().equals(team)){
				bool = true;
				break;
			}
		}
		return bool;
	}
	
	public int getPlanesAlive()
	{
		int num =  0;
		for(int i = 0;i<planes.size();i++)
		{
			if(planes.get(i).getTeam().equals(team))
				num++;
		}
		return num;
	}
}
