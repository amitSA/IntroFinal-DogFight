package otherstuff;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import planes.*;

public class GamePanelServer // does not use a timer
{
    
    
    
    Socket [] clientSocs;
    ObjectOutputStream [] outputs;
    ObjectInputStream [] inputs;
    
    Team [] teams;
    
    EliminationRules rules;
    
    ArrayList<Ammo> bullets;
    ArrayList<FighterPlane> planes;
    ArrayList<Smoke> smoke;
	
	public GamePanelServer(Socket [] clientSocs,
			               boolean isJapanPlaying,boolean isUSPlaying,boolean isGermPlaying,
			               int JapanWingmen,      int USWingmen,      int GermWingmen, 
			               
			               int frameW, int frameH, int numPlayers)
	{
		smoke = new ArrayList<Smoke>();
		
		
		
		// CONSTRUCTING TEAMS AND ADDING PLANES TO ARRAYLISTS
		Image JapanImage = (new ImageIcon("Japanese Plane.png")).getImage();
		Image USAImage = (new ImageIcon("U.S Plane.png")).getImage();
		Image GermanyImage = (new ImageIcon("Germany Plane.png")).getImage();
		Image wingJapan =  (new ImageIcon("Japanese Wingmen.png")).getImage();
    	Image wingUSA =  (new ImageIcon("U.S Wingmen.png")).getImage();
    	Image wingGermany =  (new ImageIcon("Germany Wingmen.png")).getImage();
		
		Image [] images = {JapanImage, USAImage,GermanyImage};
		Image [] wingImages = {wingJapan,wingUSA,wingGermany};
		String [] countries = {"Japan","USA","Germany"};
		
		int ranW = (int)(Math.random() * frameW);
		int ranH = (int)(Math.random() * frameH);
		
		teams = new Team[numPlayers];
		for(int i = 0; i<teams.length;i++)
		{
			if(isJapanPlaying)
			{   // equivalent to player one
				teams[i] = new Team(countries[0],KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_SPACE,bullets,images[0],0,0,ranH,planes,wingImages[0],false,true,frameW,frameH,75,100);
				for(int ip = 0;ip<JapanWingmen;ip++)
				{
					planes.add(teams[i].makeNewWingman());
				}
				planes.add(teams[i].makeNewUserPlane());
				isJapanPlaying = false;
			}
			else if(isUSPlaying)
			{   // equivalent to player two
				teams[i] = new Team(countries[1],KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_SPACE,bullets,images[1],90,ranW,0+10,planes,wingImages[1],true,false,frameW,frameH,75,100);
				for(int ip = 0;ip<USWingmen;ip++)
				{
					planes.add(teams[i].makeNewWingman());
				}
				planes.add(teams[i].makeNewUserPlane());
				isUSPlaying = false;
			}
			else if(isGermPlaying)
			{   // equivalent to player three
				teams[i] = new Team(countries[2],KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_SPACE,bullets,images[2],180,frameW-66,ranH,planes,wingImages[2],false,true,frameW,frameH,75,100);
				for(int ip = 0;ip<GermWingmen;ip++)
				{
					planes.add(teams[i].makeNewWingman());
				}
				planes.add(teams[i].makeNewUserPlane());
				isGermPlaying = false;
			}
		}//
		
		
		
		// Rules
				ArrayList<Team> teamList = new ArrayList<Team>();
				for(int i = 0;i<teams.length;i++)
					teamList.add(teams[i]);
				rules = new EliminationRules(teamList,planes);
		//
		
		
		System.out.println("CheckPoint 1");
		/* Constructing Streams		
		 for now it is assumed there are only 2 elements in clientSocket */
	    this.clientSocs = clientSocs;
		try
		{
		    outputs = new ObjectOutputStream[2];
		    inputs = new ObjectInputStream[2];
		   	
		    for(int i = 0;i<clientSocs.length;i++)
		    {
		    	outputs[i] = new ObjectOutputStream(clientSocs[i].getOutputStream());
		    	outputs[i].flush(); // no idea why i do this
		    	System.out.println("CheckPoint 2:  " +  i);
		    	inputs[i] = new ObjectInputStream(clientSocs[i].getInputStream());
		    }
		}
		catch(IOException e)
		{
			System.out.println("Exception Occured");
		}
	    //
		 System.out.println("CheckPoint 3");
		
		
		
		
		
		//start
		System.out.println("server constructor finished");
		sendInformation();
	}
	
	public void executeGameProcesses()
	{
		for(int i = 0;i<planes.size();i++)
		{     
				Ammo bullet =  planes.get(i).checkForHits();
				if(bullet != null)
				{   
					planes.get(i).isHit();
					if(planes.get(i).checkForDeath())
					{
						
						for(int op = 0;op<teams.length;op++)
						{
							if(bullet.getTeam().equals(teams[op].getCountry()))
							{
								teams[op].increaseKillCount();
								
							}
							if(planes.get(i).getTeam().equals(teams[op].getCountry()))
							{
								teams[op].planeLost();
								
							}
						}
					
						rules.updateGame(); 
						planes.remove(i);
						continue;
					}
				}
        }
		// does everything for clouds
				for(int op = 0;op<smoke.size();op++)
				{
					boolean bool = smoke.get(op).developCloud();        
					if(!bool)
					{
						smoke.remove(op);
					}
				}
		
		sendInformation();
	}
	
	// convention: read planes,ammo, smoke respectively 
	public void recieveInformation()
	{
		planes.clear();
		bullets.clear();
		smoke.clear();
		for(int i = 0;i<clientSocs.length;i++)
		{
			try
			{
				ArrayList<FighterPlane> planesTemp = new ArrayList<FighterPlane>();
				planesTemp = (ArrayList<FighterPlane>) inputs[i].readObject();
				for(int ip = 0;ip<planesTemp.size();ip++)
				{
					planes.add(planesTemp.get(ip));
				}
				
				ArrayList<Ammo> bulletsTemp = new ArrayList<Ammo>();
				bulletsTemp = (ArrayList<Ammo>) inputs[i].readObject();
				for(int ip = 0;ip<bulletsTemp.size();ip++)
				{
					bullets.add(bulletsTemp.get(ip));
				}
				
				ArrayList<Smoke> smokeTemp = new ArrayList<Smoke>();
				smokeTemp = (ArrayList<Smoke>) inputs[i].readObject();
				for(int ip = 0;ip<smokeTemp.size();ip++)
				{
					smoke.add(smokeTemp.get(ip));
				}
				
				
			}
			catch(IOException e)
			{
				System.out.println("Error in GamePanelServer");
			}
			catch(ClassNotFoundException e)
			{
				System.out.println("Error in GamePanelServer");
			}
			 
		}
		executeGameProcesses();
		
	}
	// convention: send planes,ammo, smoke respectively 
	public void sendInformation()
	{
		for(int i = 0;i<clientSocs.length;i++)
		{
			try
			{
				outputs[i].writeObject(planes);
				outputs[i].writeObject(bullets);
				outputs[i].writeObject(smoke);
			}
			catch(IOException e)
			{
				System.out.println("Error in GamePanelServer");
			}
		}
		recieveInformation();
	}
	
	
	
	
	
	
	
	
}
