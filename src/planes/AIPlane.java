package planes;



import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import otherstuff.*;

public abstract class AIPlane extends FighterPlane implements AIControlled
{
       
	FlightPlan plan;
	ArrayList<FighterPlane> planes;
	

	

	
	
	public AIPlane(Image plane, int startX,int startY, ArrayList<Ammo> bullets, String team,ArrayList<FighterPlane> planes,int startDegrees,int health) 
	{
		super(plane, startX,startY, bullets, team,startDegrees,health);	
		
		plan = new FlightPlan(planes,this);
		this.planes = planes;
		
    }	
	
	// u will have to test out the shooting part of this method heavily if u change dc or hyp
	public void updateRoute()
	{
		Point2D.Double point = plan.updatePlan();
		plan.changeTime(-0.015);    
		double outerX = point.getX();
		double outerY = point.getY();
		

		int dC = 1;  // degreesConstant
		int hyp = 20; // hypotenuse
	    Point2D.Double centerPoint = getCenterPoint();
	    
	    //subtrating # of degrees
	    Point2D.Double outerP1 = new Point2D.Double(centerPoint.getX() + Math.cos(getRadians() - Math.toRadians(dC))*hyp,centerPoint.getY() + Math.sin(getRadians() - Math.toRadians(dC))*hyp ); 
		
		//adding # of degrees
	    Point2D.Double innerP1 = new Point2D.Double(centerPoint.getX() + Math.cos(getRadians() + Math.toRadians(dC))*hyp, centerPoint.getY() + Math.sin(getRadians() + Math.toRadians(dC))*hyp ); 
		
		if(outerP1.distance(outerX,outerY) < innerP1.distance(outerX,outerY))
		{
			setIsGoingUp(true);
			setIsGoingDown(false);
		}
		else if(outerP1.distance(outerX,outerY) > innerP1.distance(outerX,outerY))
		{
			setIsGoingDown(true);
			setIsGoingUp(false);
		}
		
		toggleShooting();
		
		stablePlane(point);
		
	}
	
	
	private void toggleShooting()   // determines whether it should start shooting 
	{
		boolean bool = true;
		Line2D.Double rangeLine = new Line2D.Double(getCenterPoint(),new Point2D.Double(getXCoord() + Math.cos(getRadians())*Ammo.getAmmoRange(),getYCoord() + Math.sin(getRadians())*Ammo.getAmmoRange()));
		setIsShooting(false);
		
		// more randomness for the AI
		for(int i = 0;i<planes.size()&&bool;i++)
		{
			if(planes.get(i) == this || getTeam().equals(planes.get(i).getTeam())  ||  planes.get(i).getCenterPoint().distance(getCenterPoint())  > Ammo.getAmmoRange())
			{
				continue;
			}
	//		planes.get(i).
			 if(rangeLine.intersectsLine(planes.get(i).getCornerP1().getX(),planes.get(i).getCornerP1().getY(),planes.get(i).getCornerP2().getX(),planes.get(i).getCornerP2().getY()))
			 {
				 setIsShooting(true);	
				 setIsGoingUp(false);  
				 setIsGoingDown(false);
				 bool = false;
				 
			 }
			 if(rangeLine.intersectsLine(planes.get(i).getCornerP3().getX(),planes.get(i).getCornerP3().getY(),planes.get(i).getCornerP4().getX(),planes.get(i).getCornerP4().getY()))
			 {
				 setIsShooting(true);	
				 setIsGoingUp(false);
				 setIsGoingDown(false);
				 bool = false;
			 }
			 if(rangeLine.intersectsLine(planes.get(i).getCornerP1().getX(),planes.get(i).getCornerP1().getY(),planes.get(i).getCornerP4().getX(),planes.get(i).getCornerP4().getY()))
			 {
				 setIsShooting(true);
				 setIsGoingUp(false);
				 setIsGoingDown(false);
				 bool = false;
			 }
			 if(rangeLine.intersectsLine(planes.get(i).getCornerP2().getX(),planes.get(i).getCornerP2().getY(),planes.get(i).getCornerP3().getX(),planes.get(i).getCornerP3().getY()))
			 {
				 
				 setIsShooting(true);	
				 setIsGoingUp(false);
				 setIsGoingDown(false);
				 bool = false;
			 }
	   }
	}
	
	private void stablePlane(Point2D.Double point)
	{
		int distance = 3000;  // very large number so never incorrect
		double endX = getXCoord() + Math.cos(getRadians())*distance;
		double endY = getYCoord() + Math.sin(getRadians())*distance;
		
		Line2D.Double line = new Line2D.Double(getCenterPoint(),new Point2D.Double(endX,endY));
		
		if(line.ptLineDist(point) < 0.1)
		{
			setIsGoingUp(false);
			setIsGoingDown(false);
		}	
	}
	
	 public String toString()
	 {
		 String s = "country: " + getTeam()  + "\n" +
	                "type: " + " Wingman(AI) \n" + 
				    "X-Coord: " + (int)(getCenterPoint().getX()+0.5) + "\n" + 
				    "Y-Coord: " + (int)(getCenterPoint().getY()+0.5) + "\n\n";
		return s;
		 
	 }
	
	
	
	
	
	
	
	
	
}

