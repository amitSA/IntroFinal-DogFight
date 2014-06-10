package menus;

// Amit Saxena - Intro Final Project

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;

import menus.SingleComp.CenterPanel;

import otherstuff.*;






public class MultiCompMenu extends JFrame 
{
	
	
ServerSocket serverSoc; 

Socket [] clientSocs;
  

	public MultiCompMenu()
	{	
		super("From MultiCompMenu");
		
		//ASSUME ONLY 2 CLIENTS WILL CONNECT
		clientSocs = new Socket[2];
		
		// establish connection
	
	    
		
		
		
		
		
		
		try {
			// other computer
			serverSoc = new ServerSocket(4442);
			clientSocs[0] = serverSoc.accept();
			System.out.println("Connection established with: " + clientSocs[0].getInetAddress().getHostName() + "(ServerComp)");
			
			
			// this computer
			clientSocs[1] = new Socket(InetAddress.getLocalHost().getHostAddress(),serverSoc.getLocalPort());
			serverSoc.accept();
			System.out.println("Connection established with: " + clientSocs[1].getInetAddress().getHostName()+ "(ServerComp)");
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
		
                                                                                                       		    
		
		
		//STUFFFFF
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//STUFFFF
		
		 boolean isJapanPlaying = false;
		 boolean isUSPlaying = false;
		 boolean isGermPlaying = false;
		 
         int JapanWingmen = 0;
         int USWingmen = 0;
         int GermWingmen = 0;
         
         int frameW = getWidth();
         int frameH = getHeight();
         int numPlayers = 2;
		
         
        
         
         
	//    GamePanelServer server = new GamePanelServer(clientSocs,isJapanPlaying,isUSPlaying,isGermPlaying,JapanWingmen,USWingmen,GermWingmen,frameW,frameH,numPlayers);
		
	    //this team will be Japan
	    GamePanelClient clientGP = new GamePanelClient("Japan",clientSocs[1],this);
	    
	    getContentPane().add(BorderLayout.CENTER,clientGP);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	public static void main(String[] args)
    {
		MultiCompMenu menu = new MultiCompMenu();
	}


	
	
	
	
}

/*WHAT TO DO
 * make GamePanelClient extend GamePanel
 * Clean up code
 * make a menu
 * 
 * 
 **/
