package otherstuff;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import planes.*;



public class GamePanelClient extends JPanel implements ActionListener
{
	Timer timer;
	JFrame frame;
	
	ArrayList<FighterPlane> planes;
	ArrayList<Ammo> bullets;
	ArrayList<Smoke> smoke;
	
	ObjectInputStream input;
	ObjectOutputStream output;
	
	KeyInteraction keys;
	
	int seconds;
	String team;
                            
	public GamePanelClient(String team,Socket socket,JFrame frame) 
	{
		super();	
		
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush(); // no idea why i do this
	
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Error in GameClient, constructor");
			e.printStackTrace();
			 System.exit(0); 
		}
		
		seconds = 0;
		this.team = team;
		this.planes = new ArrayList<FighterPlane>();
		this.bullets = new ArrayList<Ammo>();
		this.smoke = new ArrayList<Smoke>();
		
		keys = new KeyInteraction(planes,null); 
		frame.addKeyListener(keys);

		this.frame = frame;
		this.team = team;
		
		timer = new Timer(15,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g;
		g.setColor(new Color(198,226,255));
    	g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		
		
    	
		for(int i = 0;i<smoke.size();i++)
		{   
			smoke.get(i).drawClouds(g2d);
		}
		
		for(int i = 0;i<planes.size();i++)
		{  
			planes.get(i).loadImageInObject();
			planes.get(i).drawImage(g2d,this);
		}
		
		for(int i = 0;i<bullets.size();i++)
		{
			Ammo ammo = bullets.get(i);
			ammo.drawAmmo(g2d, this);
		}
	}
    
	
	public void actionPerformed(ActionEvent e)
	{   
		// onlybupdate your own planes and bullets
				seconds+=15;
				
				for(int i = 0;i<planes.size();i++)
				{   
					if(!planes.get(i).getTeam().equals(team))
						continue;
					
					    planes.get(i).updateRoute();
					    if(seconds%45==0)
					    planes.get(i).checkForSmoke(smoke);	
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
				planes.get(i).moveForward();
				planes.get(i).checkOutofBounds(getWidth(),getHeight());
				}
				
				// moves bullets
				for(int i = 0;i<bullets.size();i++)
				{
					if(!bullets.get(i).getTeam().equals(team))
						continue;
					
					bullets.get(i).moveForward();
					if(bullets.get(i).isInDistance() == false)
					{
						bullets.remove(i);
					}
				}
				
				// FOR KEY INTERACTION
				ArrayList<FighterPlane> planeTemp = new ArrayList<FighterPlane>();
				for(int ip = 0;ip<planes.size();ip++)
				{
					if(planes.get(ip).getTeam().equals(team))
					planeTemp.add(planes.get(ip));
				}
				keys.updatePlanes(planeTemp);
		        //
		
		
	    // after a sendInfo(), there has to be a recieveInfo()
		sendInformation();
		recieveInformation();
		
		
		
		repaint();
 }
	
	public void recieveInformation()
	{   // THIS COULD BE MADE MORE EFFICIENT
		planes.clear();
		bullets.clear();
		smoke.clear();
		
		try {
			ArrayList<FighterPlane> planeTemp = (ArrayList<FighterPlane>) input.readObject();
			for(int i = 0;i<planeTemp.size();i++)
			{
					planes.add(planeTemp.get(i));
			}  
			
			ArrayList<Ammo> bulletTemp = (ArrayList<Ammo>) input.readObject();
			for(int i = 0;i<bulletTemp.size();i++)
			{
					bullets.add(bulletTemp.get(i));
			}
			
			smoke = (ArrayList<Smoke>) input.readObject();  
			
		} catch (ClassNotFoundException e) {
			System.out.println("Error in GamePanelClient, ClasNotFound, recieveInformation");
			e.printStackTrace();
			 System.exit(0); 
		} catch (IOException e) {
			System.out.println("Error in GamePanelClient, IOException, receiveInformation");
			e.printStackTrace();
			 System.exit(0); 
		}
	}
	
	public void sendInformation()
	{
		// only send your own planes and bullets
		// changing the arrays because their anyways going to be reseted in recieveInformation()
		try
		{
			for(int i = 0;i<planes.size();i++)
			{
				if(!planes.get(i).getTeam().equals(team))
				planes.remove(i);
			}
			
		
			output.writeObject(planes);
			
			for(int i = 0;i<bullets.size();i++)
			{
				if(!bullets.get(i).getTeam().equals(team))
				bullets.remove(i);
				
			}
			output.writeObject(bullets);
			
			output.writeObject(smoke);
		}
		catch(IOException e)
		{
			System.out.println("Error in GamePanelClient, sendInformation");
			 System.exit(0); 
		}
	}
}

/*
  
 System.out.println(planes.size() + "   after"); 
 
 System.out.println(planes.size() + "  before");
 
 
 
             //testing
			System.out.println("size: " + planes.size());
			if(planes.size()>0)  
			System.out.println(planes.get(0).toString());
			//
 */

