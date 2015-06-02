package otherstuff;

import java.awt.Image;
import java.util.ArrayList;

import planes.AIPlane;
import planes.AIWingman;
import planes.Ammo;
import planes.FighterPlane;
import planes.IsCaptain;
import planes.IsWingman;
import planes.UserCaptain;
import planes.UserPlane;

public class UserTeam extends Team{
	
	protected int up;
	protected int down;
	protected int shoot;
	
	public UserTeam(String team,int up, int down, int shoot,ArrayList<Ammo> bullets,Image userImage,int degrees,int width,int height,ArrayList<FighterPlane> planes, Image wingImage,boolean isWidthRand,boolean isHeightRand,int frameW,int frameH,int wH,int uH){
		super(team,bullets,userImage,degrees,width,height,planes,wingImage,isWidthRand,isHeightRand,frameW,frameH,wH,uH);
		this.up = up;
		this.down = down;
		this.shoot = shoot;
	}
	
	
	//public Team(String team,ArrayList<Ammo> bullets,Image userImage,int degrees,int width,int height,ArrayList<FighterPlane> planes, Image wingImage,boolean isWidthRand,boolean isHeightRand,int frameW,int frameH,int wH,int uH)
	
	
	
	public IsCaptain makeNewCaptainPlane()
	{
		if(isWidthRand)
		{
			width = (int)(Math.random()*frameW);
		}
		if(isHeightRand)
		{
			height = (int)(Math.random()*frameH);
		}
		return new UserCaptain(captainImage,width,height,up,down,shoot,bullets,team,degrees,captainHealth);
	}
	public IsWingman makeNewWingman()
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
		return new AIWingman(wingImage,width,height,bullets,team,planes,degrees,wingHealth);
	}

	

}
