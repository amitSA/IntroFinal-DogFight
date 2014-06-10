package menus.SingleComp;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// NOTE: JFrame doesnt have a paintComponent method so i had to make a separate class for the title of the menu


public class MenuTitle extends JPanel 
{
	int titleWidth;
	public MenuTitle(int width)
	{
		add(Box.createVerticalStrut(75));
		titleWidth = width;
		
	}

	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g.setFont(new Font(Font.SERIF,Font.BOLD + Font.ITALIC,50));
		g.setColor(new Color(128,128,128));
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(Color.BLACK);
		g.drawString("Choose Your Allegiance",(getWidth()-titleWidth)/2, getHeight()/2 + 10);
	
		
	}

}