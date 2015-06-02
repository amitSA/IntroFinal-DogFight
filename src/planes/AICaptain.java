package planes;

import java.awt.Image;
import java.util.ArrayList;

public class AICaptain extends AIPlane implements IsCaptain{
	
	public AICaptain(Image plane, int startX,int startY, ArrayList<Ammo> bullets, String team,ArrayList<FighterPlane> planes,int startDegrees,int health) 
	{
		super(plane, startX,startY, bullets, team,planes,startDegrees,health);	
	}

}
