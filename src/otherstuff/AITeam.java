package otherstuff;

import java.awt.Image;
import java.util.ArrayList;

import planes.AICaptain;
import planes.AIPlane;
import planes.AIWingman;
import planes.Ammo;
import planes.FighterPlane;
import planes.IsCaptain;
import planes.IsWingman;
import planes.UserCaptain;
import planes.UserPlane;

public class AITeam extends Team {
	
	
	
	
	
	
	
	public AITeam(String team, ArrayList<Ammo> bullets, Image userImage,int degrees, int width, int height, ArrayList<FighterPlane> planes,Image wingImage, boolean isWidthRand, boolean isHeightRand,int frameW, int frameH, int wH, int uH) {
		super(team, bullets, userImage, degrees, width, height, planes, wingImage,isWidthRand, isHeightRand, frameW, frameH, wH, uH);
		
	}

	
	@Override
	public IsCaptain makeNewCaptainPlane() {
		
		if(isWidthRand)
		{
			width = (int)(Math.random()*frameW);
		}
		if(isHeightRand)
		{
			height = (int)(Math.random()*frameH);
		}
		return new AICaptain(captainImage,width,height,bullets,team,planes,degrees,captainHealth);
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
