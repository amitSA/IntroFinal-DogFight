package planes;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.io.*;

import javax.swing.ImageIcon;

import otherstuff.Smoke;





public abstract class FighterPlane implements Serializable {

	
	static double [] sin = new double[720]; // techinally it can be 360
	static double [] cos = new double[720];		
	
	private double xCoord;
	private double yCoord;
	
    transient public Image planeImage;
	private double radians;

	private double width;
	private double height;
	
	private ArrayList<Ammo> bullets;
	
	private int health;
	
	private String team;
	private int timesHit;
	

	private boolean goingUp;
	private boolean goingDown;
	private boolean isShooting;
	
	
	
	
	private Point2D.Double cornerP1;
	private Point2D.Double cornerP2;
	private Point2D.Double cornerP3;
	private Point2D.Double cornerP4;
	

	public FighterPlane(Image plane,int startX,int startY, ArrayList<Ammo> bullets,String team,int startDegrees,int health)
	{
		xCoord = startX;
		yCoord = startY;
		radians = Math.toRadians(startDegrees);
		planeImage = plane;
		width = 48.75;
		height = 28.125;   // * 3/4
	    this.bullets = bullets;
	    
	    this.health = health;
	    this.team = team;
	    
	    goingUp = false;
		goingDown = false;
		isShooting = false;
		timesHit = 0;
		
		cornerP1=null;
	    cornerP2=null;
		cornerP3=null;
		cornerP4=null;
		// Initializes above fields
		checkInSystemStatus();
		
		
		//MAKE SIN/COS TABLES
		if(sin[30]==0)
		{
			for(int i = 0;i<720;i++)
			{
				sin[i] = Math.sin(Math.toRadians(i/2.0));
				cos[i] = Math.cos(Math.toRadians(i/2.0));
			}
			
			for(int i = 0;i<720;i++)
			{
				System.out.println("Angle: " + i/2.0 + "   s: " +sin[i] );
			}
		}
		
	}

	public void moveForward() 
	{
		double toDegrees = Math.toDegrees(radians);
		int degTemp = (int)(toDegrees*2+0.5)%720;
		//System.out.println(toDegrees%360);
		double moveX = 2.5*cos[degTemp]; // original was 5
		double moveY = 2.5*sin[degTemp]; // original was 5
		xCoord += moveX;
		yCoord += moveY;
	}

	public void tiltNozzleUp()
	{
		radians+= Math.toRadians(-3.0) + Math.PI*2;   // original was -4.5    -2.25, INSTEAD, YOU SHOULD CHANGE EVERYTHING TO RADIANS
	} 


	public void tiltNozzleDown()
	{
		radians+=Math.toRadians(3.0) + Math.PI*2;     // original was 4.5    2.25
	}

	public void drawImage(Graphics2D g2d, ImageObserver io)
	{
       
		AffineTransform saved = g2d.getTransform();  
		g2d.translate(xCoord+width/2, yCoord+height/2);   // gives me the center coordinates
		g2d.rotate(radians);
		g2d.drawImage(planeImage,(int)(-width/2),(int)(-height/2),(int)width,(int)height,io);
		g2d.setTransform(saved);
		
/*		g2d.setColor(Color.BLACK);
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke((float) 5.0));
		g2d.drawLine((int)(cornerP3.getX()), (int)(cornerP3.getY()), (int)(cornerP4.getX()), (int)(cornerP4.getY()));
		g2d.setStroke(stroke);  */
		
	}
	public void checkOutofBounds(int panelWidth,int panelHeight)
	{
		if(xCoord-width>panelWidth)
		{
			radians += Math.PI;
			xCoord = panelWidth;
		}
		else if(xCoord+width<0)  		 // the only time when the plane is going left is when it is backwards
		{
			radians += Math.PI;
			xCoord = 0;
		}
		if(yCoord-width>panelHeight)
		{
			radians += Math.PI;
			yCoord= panelHeight-50;
		}
		else if(yCoord+width<0)
		{
			radians += Math.PI;
			yCoord = 0;
		}
	}
	public void fireBullets()
	{
		double centerX = xCoord + width/2;
		double centerY = yCoord + height/2;
		
		double xAdd = Math.cos(radians)*width/2;
		double yAdd = Math.sin(radians)*width/2;
		
		double startX = centerX+xAdd;
		double startY = centerY+yAdd;
	
		bullets.add(new Ammo(startX, startY,radians,team));
	}
	public Ammo checkForHits()
	{
		 
		for(int i = 0;i<bullets.size();i++)
		{
			if(bullets.get(i).getTeam().equals(team) == true  || bullets.get(i).getLineCoord().ptLineDist(getCenterPoint())  > 20)
			{
				continue;
			}
			 Line2D.Double line = bullets.get(i).getLineCoord();
			 				
			 // checking for any intersections between bullets and certain plane object
			 if(line.intersectsLine(cornerP1.getX(),cornerP1.getY(),cornerP2.getX(),cornerP2.getY()))     
			 {
				 Ammo bullet = bullets.get(i);
				 bullets.remove(i);
				 return  bullet;
			 }
			 if(line.intersectsLine(cornerP3.getX(),cornerP3.getY(),cornerP4.getX(),cornerP4.getY()))
			 {
				 Ammo bullet = bullets.get(i);
				 bullets.remove(i);
				 return  bullet;
			 }
			 		
			 if(line.intersectsLine(cornerP1.getX(),cornerP1.getY(),cornerP4.getX(),cornerP4.getY()))
			 {
				 Ammo bullet = bullets.get(i);
				 bullets.remove(i);
				 return  bullet;
			 }
			 		
			 if(line.intersectsLine(cornerP2.getX(),cornerP2.getY(),cornerP3.getX(),cornerP3.getY()))
			 {
				 Ammo bullet = bullets.get(i);
				 bullets.remove(i);
				 return  bullet;
			 }
		}
		return null;
	}
	
	public void isHit()
	{
		health--;
		timesHit++;
	}
	
	//update each of the planes 4 coordinates
	public void checkInSystemStatus()
	{   
							//UPDATING 4 POINTS
		 double centerX = xCoord + width/2;
	     double centerY = yCoord + height/2;
		// first set of Points
		 double diagHalfLength = Math.sqrt(width*width + height*height)/2;
		 double innerHalfAngle = Math.atan(height/width);
		 double innerAngle = radians-innerHalfAngle;
		 
		 double yAdd = Math.sin(innerAngle) * diagHalfLength;
		 double xAdd = Math.cos(innerAngle) * diagHalfLength;
		 
		 double startX1 = centerX + xAdd;
		 double startY1 = centerY + yAdd;
		 cornerP1 = new Point2D.Double(startX1,startY1);
		 		 
		 //second set of Points
		 innerAngle = radians + innerHalfAngle;
		 
		 yAdd = Math.sin(innerAngle) * diagHalfLength;
		 xAdd = Math.cos(innerAngle) * diagHalfLength;
		 
		 double endX1 = centerX + xAdd;
		 double endY1 = centerY + yAdd;
		 cornerP2 = new Point2D.Double(endX1,endY1);
		
		 
		          // CHECKING FOR INTERSECT IN SIDE 2
		 // first set of Points
		 innerAngle = radians + Math.toRadians(180)-innerHalfAngle;
		 yAdd = Math.sin(innerAngle) * diagHalfLength;
		 xAdd = Math.cos(innerAngle) * diagHalfLength;
		 
		 double startX2 = centerX + xAdd;
		 double startY2 = centerY + yAdd;
		 cornerP3 = new Point2D.Double(startX2,startY2);
		 
					 //second set of Points
		 innerAngle = radians + Math.toRadians(180)+innerHalfAngle;
		 
		 yAdd = Math.sin(innerAngle) * diagHalfLength;
		 xAdd = Math.cos(innerAngle) * diagHalfLength;
		 
		 double endX2 = centerX + xAdd;
		 double endY2 = centerY + yAdd;
		 cornerP4 = new Point2D.Double(endX2,endY2);
	}
	
	public void checkForSmoke(ArrayList<Smoke> clouds)
	{
		 // CHECKING SMOKE		
		Color color = null;
		int increment = 30;
		int num = 0;
		
		int origHealth = timesHit + health;
		if((health+0.0)/origHealth < 0.05)
		{
			color = new Color(num,num,num);
		}
		else if((health+0.0)/origHealth < 0.1)
		{
			num += increment;
			color = new Color(num,num,num);
		}
		else if((health+0.0)/origHealth < 0.2)
		{
			num += increment*2;
			color = new Color(num,num,num);
		}
		else if((health+0.0)/origHealth < 0.3)
		{
			num += increment*4;
			color = new Color(num,num,num);
		}
		else if((health+0.0)/origHealth < 0.4)
		{
			num += increment*5;
			color = new Color(num,num,num);
		}
		else if((health+0.0)/origHealth < 0.5)
		{  
		   num += increment*6;
			color = new Color(num,num,num);
		}
		if(color != null)
		{
			double midX = (cornerP3.getX() + cornerP4.getX())/2;
			double midY = (cornerP3.getY() + cornerP4.getY())/2;
			
			// calulate dimensions of cloud based on its origins size (for now its only a fighterplane, but if i add other types of planes
			                                                           // you still want to the smoke to be proportional to its plane's size)
			double realSize = ((this.width+this.height)/2)/6.410416667;
		
			midX -= realSize/2;
			midY -= realSize/2;
			
			clouds.add(new Smoke((int)(midX+0.5),(int)(midY+0.5),color,realSize,realSize));
		}
	}
	public boolean checkForDeath()
	{
		if(health<1)
		{
			return true;
		}
		return false;
	}
	// Getter methods
	 public boolean getShootingStatus()
	 {
	    	return isShooting;
	 }
	 public boolean isGoingDown()
	 {
	    	return goingDown;
	 }
	 public boolean isGoingUp()
	 {
	    	return goingUp;
	 }
	 public double getRadians()
	 {
		 return radians;
	 }
	 public Point2D.Double getCenterPoint()
	 {
		 return new Point2D.Double(xCoord+width/2,yCoord+height/2);
	 }
	 public String getTeam()
	 {
		 return team;
	 }
	 public double getXCoord()
	 {
		 return xCoord;
	 }
	 public double getYCoord()
	 {
		 return yCoord;
	 }
	 public double getWidth()
	 {
		 return width;
	 }
	 public double getHeight()
	 {
		 return height;
	 }
	 
	 
	 // Setter Methods
	 public void setIsGoingUp(boolean bool)
	 {
		 goingUp = bool;
	 }
	 
	 public void setIsGoingDown(boolean bool)
	 {
		 goingDown = bool;
	 }
	 
	 public void setIsShooting(boolean bool)
	 {
		 isShooting = bool;
	 }
	 
	 
	 public Point2D.Double getCornerP1()
	 {
		 return cornerP1;
	 }
	 public Point2D.Double getCornerP2()
	 {
		 return cornerP2;
	 }
	 public Point2D.Double getCornerP3()
	 {
		 return cornerP3;
	 }
	 public Point2D.Double getCornerP4()
	 {
		 return cornerP4;
	 }
	 
	 //abstract methods
	 abstract public void updateRoute();  // for the AI planes, but need UserPlane to have it
	 
	 
	 
	 
	 
	 
	 
	 public void loadImageInObject()
	 {
		    Image JapanImage = (new ImageIcon("Japanese Plane.png")).getImage();
			Image USAImage = (new ImageIcon("U.S Plane.png")).getImage();
			Image GermanyImage = (new ImageIcon("Germany Plane.png")).getImage();
			Image wingJapan =  (new ImageIcon("Japanese Wingmen.png")).getImage();
	    	Image wingUSA =  (new ImageIcon("U.S Wingmen.png")).getImage();
	    	Image wingGermany =  (new ImageIcon("Germany Wingmen.png")).getImage();
		 
		 try
		 { 
			 UserPlane user = (UserPlane) this;
			 if(user.getTeam().equals("USA"))
			 {
				 user.planeImage = USAImage;
			 }
			 else if(user.getTeam().equals("Germany"))
			 {
				 user.planeImage = GermanyImage;
			 }
			 else if(user.getTeam().equals("Japan"))
			 {
				 user.planeImage = JapanImage;
			 }
			 
		 }
		 catch(Exception e)
		 {
			 if(getTeam().equals("USA"))
			 {
				 planeImage = wingUSA;
			 }
			 else if(getTeam().equals("Germany"))
			 {
				 planeImage = wingGermany;
			 }
			 else if(getTeam().equals("Japan"))
			 {
				 planeImage = wingJapan;
			 }
		 }
		 
	 }
	 public String toString()
	 {
		 String s = "country: " + team + "\n" +
	                "type: " + " cant dertermine \n" + 
				    "X-Coord: " + (int)(getCenterPoint().getX()+0.5) + "\n" + 
				    "Y-Coord: " + (int)(getCenterPoint().getY()+0.5) + "\n\n";
		return s;
	 }
	      
		 
		 
	 
}
