package planes;
import java.awt.*;
import java.util.ArrayList;






public class UserPlane extends FighterPlane{



	private int upKey;
	private int downKey;
	private int fireKey;
	
	
	

	public UserPlane(Image plane,int startX,int startY,int upKey,int downKey,int fireKey,ArrayList<Ammo> bullets,String team,int startDegrees,int health)
	{
		super(plane,startX,startY,bullets,team,startDegrees,health);
		
		this.upKey = upKey;
		this.downKey = downKey;
		this.fireKey = fireKey;
		
		
		


	}
	
	public int getDownKey()
	{
        return downKey;
	}
	
	public int getUpkey()
	{
		return upKey;
	}
    public void changeIsGoingUp(boolean bool)
    {
    	setIsGoingUp(bool);
    }
    
    public void changeIsGoingDown(boolean bool)
    {
    	setIsGoingDown(bool);
    }
   
    
    public int getFireKey()
    {
    	return fireKey;
    }
    
    public void changeShootingStatus(boolean bool)
    {
    	setIsShooting(bool);
    }
    
    public void updateRoute()
    {
    	// do nothing
    }
    
    public String toString()
	 {
		 String s = "country: " + getTeam() + "\n" +
	                "type: " + " UserPlane \n" + 
				    "X-Coord: " + (int)(getCenterPoint().getX()+0.5) + "\n" + 
				    "Y-Coord: " + (int)(getCenterPoint().getY()+0.5) + "\n\n";
		return s;
	 }
   




}

