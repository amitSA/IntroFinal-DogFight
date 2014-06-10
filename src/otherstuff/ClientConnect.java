package otherstuff;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;

import javax.swing.*;




public class ClientConnect extends JFrame
{
Socket socket;
ObjectInputStream input;
ObjectOutputStream output;
	public ClientConnect()
	{
		super("From Client Connect");
		String ipaddress = JOptionPane.showInputDialog(null, "Type in an IP Address");
		try
		{
		socket = new Socket(ipaddress,4443);
		System.out.println("Connection Established With: " + socket.getInetAddress().getHostName()+ "(ClientConnect)");
		System.out.println("local port: " + socket.getLocalPort() + "     port connected to: " + socket.getPort());
		}
		catch (IOException e)
		{
			System.out.println("Connection Failed in ClientConnect");
		}
		
		
		
		GamePanelClient clientGP = new GamePanelClient("Germany",socket,this);
		
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		getContentPane().add(BorderLayout.CENTER,clientGP);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	
	public static void main(String[] args)
    {
		ClientConnect menu = new ClientConnect();
	}
}
