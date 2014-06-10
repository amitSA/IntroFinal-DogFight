package planes;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.ImageObserver;
import java.io.Serializable;


// should have extended from Line2D
public class Ammo implements Serializable  {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double startX;    // starting point of bullet
    private double startY;    // starting point of bullet
    
    private double endX;
    private double endY;
    private double radians;
    private String team;
    private double distanceTraveled;
 
    
    private final double length = 12;
    private final static double  distanceStatic = 800;  // distance ammo can travel, change this field
    private final double distance = distanceStatic; 
    
	public Ammo(double x, double y, double rad,String team) 
	{
		startX = x;
		startY = y;
		radians = rad;
		
		endX = Math.cos(radians)*length;
		endY = Math.sin(radians)*length;
		
	    endX+=startX;
	    endY+=startY;
	    
	    this.team = team;
	    
	    distanceTraveled = length;
	    
	    //radomizing the bullets a bit
	    int degrees = (int)(Math.random()*4.0);
	    if((int)(Math.random()*3) == 1)
	    {
	    	degrees *= -1;
	    }
	    else
	    {
	    	//degrees stays the same
	    }
	    
	    radians += Math.toRadians(degrees);
	    radians = (radians%(Math.PI*2)+Math.PI*2)%(Math.PI*2);  //just in case radians is negetive
	    
		
		//System.out.println("xPoint: " + (int)(startX) + "   yPoint: " + (int)(startY));
	//	System.out.println("newX: " + (int)(endX) + "   newY: " + (int)(endY));
	//	System.out.println(Math.toDegrees(radians%Math.PI));
		
	}
	
	public void drawAmmo(Graphics2D g2d,ImageObserver io)
	{
		Stroke stroke = g2d.getStroke();
		g2d.setColor(new Color (238,118,0));
		g2d.setStroke(new BasicStroke((float) 3.0));
		
		
	    g2d.drawLine((int)(startX+0.5), (int)(startY+0.5), (int)(endX+0.5), (int)(endY+0.5));   
		g2d.setStroke(stroke);
	}
	
	public  Line2D.Double getLineCoord()
	{
		
		return new Line2D.Double(startX,startY,endX,endY);
	}
	
	public String getTeam()
	{
		return team;
	}
	
	public void moveForward()
	{
		startX = endX;
		startY = endY;
		
		//TECHNICALLY WE SHOULD HAVE JUST USED A DIFFERENT CLASS TO STORE THE COS AND SIN CONSTANTS
		double toDegrees = Math.toDegrees(radians);
		int degTemp = (int)(toDegrees*2+0.5)%720;
		
		
		double yAdd = FighterPlane.sin[degTemp] * length*4/3.0; 
		double xAdd = FighterPlane.cos[degTemp] * length*4/3.0;
		
		endX = startX + xAdd;
		endY = startY + yAdd;
		
		distanceTraveled += length;
		
	}
	
	public boolean isInDistance()
	{
		if(distanceTraveled >=distance )
		{
			return false;
		}
		return true;
	}
	
	public static double getAmmoRange()
	{
		return distanceStatic + 0.0;
		
	}

	

	

}
