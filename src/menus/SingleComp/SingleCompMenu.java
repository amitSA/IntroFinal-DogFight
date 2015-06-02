/* Amit Saxena - Intro Final Project
   


*/
package menus.SingleComp;



import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import main.main;
import otherstuff.GamePanel;





public class SingleCompMenu extends JFrame implements ActionListener
{
	
	
  private JButton start;
  private CenterPanel centerPanel;
 
  private JPanel startPanel; 
  
  
  
  private JPanel cardPanel;
  private CardLayout c1;
  private JPanel everythingPanel;
  private GamePanel gamePanel;
  private boolean labelOn;
	public SingleCompMenu()
	{	
		
		
		
		super("Dog Fight Simulator");
		
	
		
		getContentPane().setBackground(new Color(128,128,128));
		
		cardPanel = new JPanel();
		c1 = new CardLayout();
		cardPanel.setLayout(c1);
		everythingPanel = new JPanel();
		everythingPanel.setLayout(new BoxLayout(everythingPanel,BoxLayout.Y_AXIS));		
		
		
		labelOn = false;
		
		start = new JButton("Start Battle");
		start.setMaximumSize(new Dimension(40,40));
		startPanel = new JPanel();
		startPanel.setBackground(new Color(128,128,128));
		start.addActionListener(this);
		startPanel.add(start);
		
		String preFix = main.RESOURCES_PICS_PREFIX;
		Image background = new ImageIcon(preFix+"/Background.jpg").getImage();
		centerPanel = new CenterPanel(background);
		
		MenuTitle menuTitle = new MenuTitle(501);
		menuTitle.setBackground(new Color(128,128,128));
		
		everythingPanel.add(menuTitle);
		everythingPanel.add(centerPanel);
		everythingPanel.add(startPanel);
	
		
		
		cardPanel.add(everythingPanel,"1");
		
		

		
		
		
		
	//	getContentPane().add(BorderLayout.NORTH,menuTitle);
		getContentPane().add(BorderLayout.CENTER,cardPanel);
	//	getContentPane().add(BorderLayout.SOUTH,startPanel);
		
		setBounds(100, 100, 900, 480);    // 800, 600
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		int AIGerm = centerPanel.getWingmanStatusGermany();
		int AIUSA = centerPanel.getWingmanStatusUSA();
		int AIJapan = centerPanel.getWingmanStatusJapan();
		
		
		
		String GermPlayerStat = centerPanel.getPlayerStatusGermany();
		String JapanPlayerStat = centerPanel.getPlayerStatusJapan();
		String USAPlayerStat = centerPanel.getPlayerStatusUSA();
		
		String[] array = {GermPlayerStat,JapanPlayerStat,USAPlayerStat};
		
		boolean bool = true;
		for(int i = 0;i<2;i++)
		{
			if(array[i].equals(array[(i+1)%3]) || array[i].equals(array[(i+2)%3]) )
			{
               bool = false;				
			}
		}
		if(bool)
		{
			gamePanel = new GamePanel(this);
			cardPanel.add(gamePanel,"2");
			setExtendedState(JFrame.MAXIMIZED_BOTH); 
			((CardLayout)cardPanel.getLayout()).next(cardPanel);
			requestFocus();
			gamePanel.initializeEverything(AIGerm,AIUSA,AIJapan,GermPlayerStat,JapanPlayerStat,USAPlayerStat,getWidth(),getHeight());
			
		}
		else if(!labelOn)
		{
			JLabel label = new JLabel("Team's cannot have matching players");
			label.setForeground(Color.WHITE);
			startPanel.add(label);
			((CardLayout)cardPanel.getLayout()).next(cardPanel);
			((CardLayout)cardPanel.getLayout()).next(cardPanel);
			requestFocus();
			labelOn = true;
			// only way the new label will show on the current panel
		}
	}
	
	public void resetMenuScreen()
	{
		((CardLayout)cardPanel.getLayout()).next(cardPanel);
		requestFocus();
		gamePanel = null;
		gamePanel = new GamePanel(this);
		setBounds(100, 100, 900, 480);
		
	}


}
