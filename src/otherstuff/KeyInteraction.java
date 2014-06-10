package otherstuff;



import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;



import planes.FighterPlane;
import planes.UserPlane;



public class KeyInteraction implements KeyListener
{

	private ArrayList<UserPlane> planes;
	private GamePanel gamePanel;

	public KeyInteraction(ArrayList<FighterPlane> allPlanes,GamePanel gamePanel)
	{
		
		this.planes = new ArrayList<UserPlane>();
		
		for(int i = 0;i<allPlanes.size();i++)
		{
			if(allPlanes.get(i) instanceof UserPlane)
				planes.add((UserPlane) allPlanes.get(i));
	    }
		this.gamePanel = gamePanel;
	}

	public void keyPressed(KeyEvent e)
	{  
		for(int i = 0;i<planes.size();i++)
		{
			if(e.getKeyCode() == planes.get(i).getUpkey() )
			{
				planes.get(i).changeIsGoingUp(true);
				planes.get(i).changeIsGoingDown(false);
			}
			else if(e.getKeyCode() == planes.get(i).getDownKey() )
			{
				planes.get(i).changeIsGoingUp(false);
				planes.get(i).changeIsGoingDown(true);
			}
			
			if(e.getKeyCode() == planes.get(i).getFireKey())
			{
				planes.get(i).changeShootingStatus(true);
				
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P) 
			gamePanel.toggleIsPaused();
	}


	public void keyReleased(KeyEvent e)
	{
		for(int i = 0;i<planes.size();i++)
		{
			if(e.getKeyCode() == planes.get(i).getUpkey() )
			{
				planes.get(i).changeIsGoingUp(false);
			}
			else if(e.getKeyCode() ==  planes.get(i).getDownKey() )
			{
				planes.get(i).changeIsGoingDown(false);
			}
			if(e.getKeyCode() == planes.get(i).getFireKey())
			{
				planes.get(i).changeShootingStatus(false);
				
			}
		}

	}


	public void keyTyped(KeyEvent arg0)
	{


	}
	
	public void updatePlanes(ArrayList<FighterPlane> allPlanes)
	{
		planes.clear();
		for(int i = 0;i<allPlanes.size();i++)
		{
			if(allPlanes.get(i) instanceof UserPlane)
				planes.add((UserPlane)allPlanes.get(i));
		}
	}

}