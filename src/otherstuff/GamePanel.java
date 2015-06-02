package otherstuff;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import main.main;
import planes.Ammo;
import planes.FighterPlane;
import planes.Wingman;


// This GamePanel is the standard gameplanel
public class GamePanel extends JPanel implements  ActionListener,MouseListener {
 	
	protected ArrayList<Ammo> bullets;
	protected ArrayList<FighterPlane> planes;
	protected JFrame frame;
	protected Timer timer;
	protected boolean isPaused;
	protected int seconds;
	protected KeyInteraction keys;
	protected ArrayList<Team> teams;
	protected Rules rules;
	protected ArrayList<Smoke> clouds;
	protected boolean isGameEnded;
	
	
	//temporary
	Wingman wingman;

	public GamePanel(JFrame frame)
	{   
		super();
	    this.frame = frame;
	    frame.addMouseListener(this);
	    clouds = new ArrayList<Smoke>();
	    isGameEnded = false;
	    teams = new ArrayList<Team>();
		frame.setBackground(new Color(198,226,255));
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		
	//	Ammo bullet = new Ammo(0.0,0.0,,"");
	//	bu
			
		
		
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d=(Graphics2D)g;
		g.setColor(new Color(198,226,255));
    	g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		
		
		
		for(int i = 0;i<clouds.size();i++)
		{   
			clouds.get(i).drawClouds(g2d);
		}
		
		for(int i = 0;i<planes.size();i++)
		{
			planes.get(i).drawImage(g2d,this);
		}
		
		for(int i = 0;i<bullets.size();i++)
		{
			Ammo ammo = bullets.get(i);
			ammo.drawAmmo(g2d, this);
		}
		
		
		//rules.checkEndGame(g2d);		  
	}

	public void actionPerformed(ActionEvent arg0)
	{   
		keys.updatePlanes(planes);
		seconds+=15;
		//CHECK IF GAME IS OVER
		
		//CHECK IF GAME IS OVER
		
		for(int i = 0;i<teams.size();i++)
		{
	//		System.out.print(teams.get(i).getCountry() + ": " + teams.get(i).getPlanesAlive() + "    ");
		}
		//System.out.print("\n");
		
		
		
		// need to update all plane's coordinates first
		for(int i = 0;i<planes.size();i++)
		{
			planes.get(i).checkInSystemStatus();
		}
		
		
		for(int i = 0;i<planes.size();i++)
		{   
			    planes.get(i).updateRoute();
			    if(seconds%45==0)
			    planes.get(i).checkForSmoke(clouds);	
				if(planes.get(i).isGoingUp())
				{
					planes.get(i).tiltNozzleUp();
				}
				else if(planes.get(i).isGoingDown())
				{
					planes.get(i).tiltNozzleDown();
				}
				if(planes.get(i).getShootingStatus())
				{
					if(seconds%90 == 0)
					planes.get(i).fireBullets();
				}
				
				Ammo bullet =  planes.get(i).checkForHits();
				if(bullet != null)
				{   
					planes.get(i).isHit();
					if(planes.get(i).checkForDeath())
					{
						
						for(int op = 0;op<teams.size();op++)
						{
							if(bullet.getTeam().equals(teams.get(op).getCountry()))
							{
								teams.get(op).increaseKillCount();
								
							}
							if(planes.get(i).getTeam().equals(teams.get(op).getCountry()))
							{
								teams.get(op).planeLost();
								
							}
						}
					
						rules.updateGame(); // based on the current rules, you can put this method call any where in this method
						planes.remove(i);
						continue;
					}
				}
				planes.get(i).moveForward();
				planes.get(i).checkOutofBounds(getWidth(),getHeight());
        }
		// does everything for clouds
		for(int op = 0;op<clouds.size();op++)
		{
			boolean bool = clouds.get(op).developCloud();        
			if(!bool)
			{
				clouds.remove(op);
			}
		}
		for(int i = 0;i<bullets.size();i++)
		{
			bullets.get(i).moveForward();
			if(bullets.get(i).isInDistance() == false)
			{
				bullets.remove(i);
			}
		}
		
		
		repaint();
	}
	
	public void toggleIsPaused()
	{
		isPaused = !isPaused;
		if(isPaused)
		{
			timer.stop();
		}
		else
		{
			timer.start();
		}
	}
	
	// if i made the GamePanel object in Menu's actionEvent method, then the frame doesnt resize properly so i tried
	// making GamePanel in the constructor of Menu and the frame worked fine, but i had to make a separate method
	// which kind of acts like the constructor
	public void initializeEverything(int numGerm, int numUSA , int numJapan,String germ, String japan, String USA,int width,int height)
	{
		    bullets = new ArrayList<Ammo>();
		    planes = new ArrayList<FighterPlane>();
		    String preFix = main.RESOURCES_PICS_PREFIX;
			Image JapanImage = (new ImageIcon(preFix+"/Japanese Plane.png")).getImage();
			Image USAImage = (new ImageIcon(preFix+"/U.S Plane.png")).getImage();
			Image GermanyImage = (new ImageIcon(preFix+"/Germany Plane.png")).getImage();
			Image wingJapan =  (new ImageIcon(preFix+"/Japanese Wingmen.png")).getImage();
        	Image wingUSA =  (new ImageIcon(preFix+"/U.S Wingmen.png")).getImage();
        	Image wingGermany =  (new ImageIcon(preFix+"/Germany Wingmen.png")).getImage();
			
			Image [] images = {JapanImage, USAImage,GermanyImage};
			Image [] wingImages = {wingJapan,wingUSA,wingGermany};
			String [] countries = {"Japan","USA","Germany"};
			String [] players = {japan,USA,germ};
			int [] wingmenNum = {numJapan,numUSA,numGerm};
			
			int ranW = (int)(Math.random() * width);
			int ranH = (int)(Math.random() * height);
		//	int wF = getWidth();
		//	int hF = getHeight();
		
			for(int ip = 0;ip<players.length;ip++)
			{
				if(players[ip].equals("Player 1"))
					{
				      teams.add(new Team(countries[ip],KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_1,bullets,images[ip],0,0,ranH,planes,wingImages[ip],false,true,width,height,75,100));
				      planes.add(teams.get(teams.size()-1).makeNewUserPlane());
				      for(int i = 0;i<wingmenNum[ip];i++)
				      {
				    	  planes.add(teams.get(teams.size()-1).makeNewWingman());
				      }
				     
					}
				else if(players[ip].equals("Player 2"))
					{
				      teams.add(new Team(countries[ip],KeyEvent.VK_I,KeyEvent.VK_K,KeyEvent.VK_SPACE,bullets,images[ip],90,ranW,0+10,planes,wingImages[ip],true,false,width,height,75,100));
				      planes.add(teams.get(teams.size()-1).makeNewUserPlane());
				      for(int i = 0;i<wingmenNum[ip];i++)
				      {
				    	  planes.add(teams.get(teams.size()-1).makeNewWingman());
				      }
					}
				else if(players[ip].equals("Player 3"))
					{				     
				      teams.add(new Team(countries[ip],KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_SLASH,bullets,images[ip],180,width-66,ranH,planes,wingImages[ip],false,true,width,height,75,100));
				      planes.add(teams.get(teams.size()-1).makeNewUserPlane());
				      for(int i = 0;i<wingmenNum[ip];i++)
				      {
				    	  planes.add(teams.get(teams.size()-1).makeNewWingman());
				      }
					}
			}
	
	        
		    rules = new EliminationRules(teams,planes);
			keys = new KeyInteraction(planes,this);
			frame.addKeyListener(keys);
			
			// if modifying this, change hardcoded value in flightplan
			timer = new Timer(15, this);  //15
			// if modifying this, change hardcoded value in flightplan
			
		isPaused = false;
		seconds = 0;
		if(isPaused == false)
			timer.start();
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("x: " + e.getX() + "    y: " + e.getY());
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	


	

}


 /* BUGS
- The second menu comes up, the program runs too laggy
 - AI planes still lobble a little bit
*/
