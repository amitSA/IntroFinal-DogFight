package planes;



import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;


public class FlightPlan implements Serializable  // manages the current plan of the AI plan- modes are "pursue enemy","random movement","locate new enemy"
{
	
	
	private double timeLeft;
	private ArrayList<FighterPlane> planes;
	 
	private Wingman wingman;  // wingman is the wingman associated with this flightplan
	private FighterPlane target;
	
	public static int route1; // pursue enemy  
	public static int route2; // random movement
	public static int route3; // locate enemy ... this is a 1-time route, has a 0 second time limit and a route1 always follows
	
	private int currentPlan;
	
	private Point2D.Double randomMov;
		
	
	public FlightPlan(ArrayList<FighterPlane> planes,Wingman wing)
	{
		wingman = wing;
		this.planes = planes;
		
		route1 = 1;
		route2 = 2;
		route3 = 3;
		
		timeLeft = 1;
		currentPlan = FlightPlan.route3;
		
		randomMov = null;
	}
	
	
	public Point2D.Double updatePlan() // center coordinates of the plane
	{   
		Point2D.Double destination = null;
	//	System.out.println("Current Plan: " + currentPlan + "  timeLeft: " + timeLeft);
		
		
		if(timeLeft <=0 )
		{
			randomMov = null;
			generateNewPlan();
		}
		
		if(currentPlan == FlightPlan.route1)
		{
			destination = pursueEnemy();
			
		}
		else if(currentPlan == FlightPlan.route2)
		{
			destination = randomMovement();
			
		}
		else if(currentPlan == FlightPlan.route3)
		{
			destination = locateEnemies();
		//	System.out.println("xCoord: " + destination.getX() + "   yCoord: " + destination.getY());
		}
		
		return destination;
		
	}
	
	
	//getters
	public int getCurrentPlan()
	{
		return currentPlan;
	}
	
	//setters
	public void changeTime(double sec)
	{
		timeLeft += sec;    // sec is always negetive
	}
	
	//helpers
	private Point2D.Double locateEnemies()
	{
	
		int enemyLoc  = 0;
		do
		{   
			boolean bool = true;
		
			for(int i = 0;i<planes.size();i++)
			{
				if(!wingman.getTeam().equals(planes.get(i).getTeam()))
				{   
					bool = false;
					break;
				}
			}
			if(bool)
			{
				break; // a team has won
			}  
			
			//to avoid being stuck in an infinite loop
			
			
			enemyLoc = (int)(Math.random()*planes.size()); // picks a random plane to follow based on how far it is
		} while(planes.get(enemyLoc).getTeam().equals(wingman.getTeam()));
		
		target = planes.get(enemyLoc);
		currentPlan = FlightPlan.route1;
		return target.getCenterPoint();

	
	}
	
	private Point2D.Double pursueEnemy()
	{
		return target.getCenterPoint();
	}
	
	private Point2D.Double randomMovement()
	{
		// picks a random point next to the plane, and the plane goes to follow it
		if(!(randomMov == null))
		{
			Double xPoint = wingman.getCenterPoint().getX();
			Double yPoint = wingman.getCenterPoint().getY();
			
			if(Math.abs(xPoint-randomMov.getX()) < 2 && Math.abs(yPoint-randomMov.getY()) < 2)
			{
				randomMov = null;
			}
		
		}
		if(randomMov == null)
		{
			int minDist = 100;
			int maxDist = 200;
			
			double radius = (Math.random() * (maxDist-minDist+1)) + maxDist-minDist; // can check
			double randRadians = Math.toRadians((Math.random() * 360.0));
			
			double xPoint = wingman.getCenterPoint().getX() + Math.cos(randRadians)*radius;
			double yPoint = wingman.getCenterPoint().getY() + Math.sin(randRadians)*radius;
			
			randomMov = new Point2D.Double(xPoint,yPoint);       
		}
		return randomMov;
	}
	
	private void generateNewPlan()
	{
		currentPlan = (int) (Math.random() * 3 + 1);
		
		
		if(currentPlan == FlightPlan.route1 || currentPlan == FlightPlan.route3)
		{
			int minTime = 2;   
			int maxTime = 3;  
			timeLeft = (Math.random() * (maxTime-minTime+1)) + maxTime-minTime;
		}
		else
		{
			int minTime = 2;   
			int maxTime = 4;  
			timeLeft = (Math.random() * (maxTime-minTime+1)) + maxTime-minTime;
		}
		
		
	}
	
	
	
	
	
	

		
}


/*
public Point2D.Double updatePlan(Point2D.Double centerMe) // center coordinates of the plane
	{   
		
		// finding the closest airplane
	    Point2D.Double choosenPoint = null;
		for(int i = 0;i<planes.size();i++)
		{   
			if(wingman.getTeam().equals(planes.get(i).getTeam()))
			{
				continue; // dont what the AI following planes from its own team
			}
			if(choosenPoint == null) // set choosenPoint to something first
			{
				choosenPoint = planes.get(i).getCenterPoint();
				continue;
			}
			if(centerMe.distance(choosenPoint) > centerMe.distance(planes.get(i).getCenterPoint()))
			{
				choosenPoint = planes.get(i).getCenterPoint();
			}
		}
	
		return choosenPoint;  
		// return the center coordinates of the closest enemy plane
 */



 
 
