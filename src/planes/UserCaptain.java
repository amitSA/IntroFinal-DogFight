package planes;

import java.awt.Image;
import java.util.ArrayList;

public class UserCaptain extends UserPlane implements IsCaptain{
	
	public UserCaptain(Image plane,int startX,int startY,int upKey,int downKey,int fireKey,ArrayList<Ammo> bullets,String team,int startDegrees,int health){
		super(plane,startX,startY,upKey,downKey,fireKey,bullets,team,startDegrees,health);
	
	}

}
