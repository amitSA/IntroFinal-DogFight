package menus.SingleComp;

import java.awt.*;
import javax.swing.*;


public class TeamChoose extends JPanel
{
	String name;
	Color titleColor;
	int nameWidth;
	JComboBox playerList;
	JTextField wingmenField;
	
	
	//CONSTANTS
	final int SPACE_BELOW_NAME = 200;  
	final int SPACE_BELOW_LIST = 0;
	
	
	public TeamChoose(String name,Color color,int width,CenterPanel centerPanel) 
	{
        this.name = name;
        titleColor = color;
        nameWidth = width;
       
//        BoxLayout layout = new BoxLayout(this,BoxLayout.Y_AXIS);
//        setLayout(layout);        
        
        String[] stringList = {"No Player","Player 1", "Player 2", "Player 3"};
        playerList = new JComboBox(stringList);
        playerList.setSelectedIndex(0);
        wingmenField = new JTextField(5);
        JLabel label = new JLabel("# of Wingmen: ");
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        label.setForeground(Color.WHITE);
        
        wingmenField.setText("0");
        playerList.addActionListener(centerPanel);
              
        
  
        add(Box.createHorizontalStrut(25)); // so Germany is Visible
        add(Box.createVerticalStrut(300));
        
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
        		   layout.createSequentialGroup()
        		      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		    		   .addComponent(playerList)
        		               .addComponent(label))
        		      .addComponent(wingmenField)

        		               
        		);
        layout.setVerticalGroup(
        		   layout.createSequentialGroup()
        		   .addComponent(playerList)
        		   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        				   .addComponent(label)
        				   .addComponent(wingmenField))
        			);





     add(panel);
		
	}


	public void paintComponent(Graphics g)
	{
	//	super.paintComponents(g);
		 g.setColor(Color.GREEN);
///		 g.drawRect(0, 0, getWidth(), getHeight());
		 g.setFont(new Font(Font.MONOSPACED,Font.BOLD,50));
		 g.setColor(titleColor);
		 g.drawString(name, (getWidth()-nameWidth)/2, 50);
		 
		 
		 
		 
		 
//		 System.out.println(imageUpdate(image,this.HEIGHT,0,0,getWidth(),getWidth()));
//		 System.out.println("width: "+ getWidth() + "  height: " + getHeight());
	}
	
//(String) teamGermany.getPlayerStatus().getSelectedObjects()[0];
	
	public String getPlayerStatus()
	{ 
		return (String) playerList.getSelectedObjects()[0];
	}
	
	public String getFieldText()
	{   
		return wingmenField.getText();
		
	}

	
}
