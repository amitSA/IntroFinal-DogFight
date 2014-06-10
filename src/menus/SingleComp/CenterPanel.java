package menus.SingleComp;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;



public class CenterPanel extends JPanel implements ActionListener
{
	Image background;

	TeamChoose teamUSA,teamGermany,teamJapan;

	String statusUSA,statusJapan,statusGermany;

	int wingmanUSA,wingmanGermany,wingmanJapan;


	public CenterPanel(Image background) 
	{



		this.background = background;
		teamUSA = new TeamChoose("USA",Color.BLUE,94,this);  // height = 31
		teamGermany = new TeamChoose("GERMANY",Color.RED,207,this);
		teamJapan  = new TeamChoose("JAPAN",Color.WHITE,147,this);
		add(teamUSA);
		add(Box.createHorizontalStrut(10));
		add(teamGermany);
		add(Box.createHorizontalStrut(10));
		add(teamJapan);


		wingmanUSA = 0;
		wingmanJapan = 0;
		wingmanGermany = 0;
	}

	public void paintComponent(Graphics g)
	{
		g.drawImage(background,0,0,getWidth(),getHeight(),this);
	}

	public String getPlayerStatusGermany()
	{
		return  teamGermany.getPlayerStatus();
	}

	public String getPlayerStatusJapan()
	{
		return  teamJapan.getPlayerStatus();
	}

	public String getPlayerStatusUSA()
	{
		return  teamUSA.getPlayerStatus();
	}


	public int getWingmanStatusGermany()
	{
		String num = teamGermany.getFieldText();
		int realNum = Integer.parseInt(num);
		return realNum;
	}
	public int getWingmanStatusJapan()
	{
		String num = teamJapan.getFieldText();
		int realNum = Integer.parseInt(num);
		return realNum;
	}
	public int getWingmanStatusUSA()
	{
		String num = teamUSA.getFieldText();
		int realNum = Integer.parseInt(num);
		return realNum;
	}


	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}


}