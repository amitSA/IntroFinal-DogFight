package otherstuff;
import java.awt.*;
import java.io.Serializable;




public class Smoke implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x;
    int y;
    Color color;
    double length;
    double width;
    int life; 
   float alpha;
	
	public Smoke(int x, int y,Color color,double l,double w)
	{
		this.x = x;
		this.y = y;
		length = l;
		width = w;
		this.color = color;
		life = 5000;  // 3 seconds
		alpha = 1.0f;
	}
	
	
	public boolean developCloud()
	{
		
		// general rules, the smaller the interval the bigger and longer a cloud object last
		life -= 90;    // minus 90 seconds
		
		
		if(life>1)
		{
			length+=0.3;   // original 0.1
			width+=0.3;
			if(alpha>0.0f)
			if(alpha>0.02f)	
			alpha -= 0.02f;
		}
		else if(life<=2000 && life>0)
		{// doesnt reach here
			length-=0.5;
			width-=0.5;
		}
		else if(life<=0)
		{
			return false;   // wasnt able to develop cloud
		}
		return true;
	}
	
	public void drawClouds(Graphics2D g2d)
	{
		g2d.setColor(color);
		
	//	System.out.println("xCoord: " + x + "   yCoord: " + y + "    length: " + (int)(length+0.5) + "   width: " + (int)(width+0.5));
		
		int type = AlphaComposite.SRC_OVER; 
		AlphaComposite composite = AlphaComposite.getInstance(type, alpha);
		AlphaComposite compOld = (AlphaComposite) g2d.getComposite();
		g2d.setComposite(composite);
		g2d.fillOval(x,y,(int)(length+0.5),(int)(width+0.5));
		g2d.setComposite(compOld);
	}
}
