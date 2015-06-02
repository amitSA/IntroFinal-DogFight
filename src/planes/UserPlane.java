package planes;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import main.main;

import javax.swing.ImageIcon;






public class UserPlane extends FighterPlane{



	private int upKey;
	private int downKey;
	private int fireKey;
	
	private Image userTriangle;
	

	public UserPlane(Image plane,int startX,int startY,int upKey,int downKey,int fireKey,ArrayList<Ammo> bullets,String team,int startDegrees,int health)
	{
		super(plane,startX,startY,bullets,team,startDegrees,health);
		
		this.upKey = upKey;
		this.downKey = downKey;
		this.fireKey = fireKey;
		String preFix = main.RESOURCES_PICS_PREFIX;
		userTriangle= (new ImageIcon(preFix+"/User-Triangle.png")).getImage();
	}
	
	public void drawImage(Graphics2D g2d, ImageObserver io){
		//GUESS THE POINTS
		Point2D.Double c2 = this.getCornerP1();           //p1 and p2, 	
		Point2D.Double c1 = this.getCornerP4();
		
	    Point2D.Double cenP = new Point2D.Double((c2.x+c1.x)/2,(c2.y+c1.y)/2);
	    double slope = (c2.y-c1.y)/(c2.x-c1.x);
        
	    slope = -1.0/slope;
	    double r = 11;
	    
	    double cY = c2.getY()-c1.getY();
		double cX = c2.getX()-c1.getX();
		double dist = c1.distance(c2);
		double theta = Math.asin(cY/dist);

		if((cY<0 && cX<0) | (cY>0 && cX<0))
			theta = Math.PI-theta;
	    theta-=Math.PI/2.0;
	    double nCY = cenP.y+r*Math.sin(theta);
	    double nCX = cenP.x+r*Math.cos(theta);
	    //g2d.drawLine((int)cenP.x,(int)cenP.y,(int)nCX,(int)nCY);
        cenP.setLocation(nCX,nCY);	  

		int sideDim = 15;
		double toMiddleDim = sideDim/Math.sqrt(3);
		double newX = toMiddleDim*cosD(150);
		double newY = -toMiddleDim*sinD(150);
		Point2D.Double drawP = new Point2D.Double(newX,newY);
		
		AffineTransform saved = g2d.getTransform();  
		g2d.translate(cenP.x,cenP.y);   // gives me the center coordinates
		g2d.rotate(getRadians());
		g2d.drawImage(userTriangle,(int)drawP.x,(int)drawP.y,sideDim,sideDim,io);
		g2d.setTransform(saved);
		
		super.drawImage(g2d, io);
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
   
    /*Use  Math Api methods to abstract out trig function calls I often have to do*/
     private double cosD(int deg){
    	 return Math.cos(Math.toRadians(deg));
     }
     private double sinD(int deg){
    	 return Math.sin(Math.toRadians(deg));
     }



}

