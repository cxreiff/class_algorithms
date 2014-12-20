
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Scatterplot extends JFrame {
	
	private Vector<double[]> points;

	public Scatterplot(Vector<double[]> c1) {
		this.points = c1;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel() { 
			public void paintComponent(Graphics g) {
				int width = getWidth();
				int height = getHeight();

				for(int a = 0; a < points.size(); a++)
				{
					
						if((int)points.elementAt(a)[points.elementAt(a).length-1]==0)g.setColor(Color.BLACK);
						else if((int)points.elementAt(a)[points.elementAt(a).length-1]==1)g.setColor(Color.GREEN);
						else if((int)points.elementAt(a)[points.elementAt(a).length-1]==2)g.setColor(Color.MAGENTA);
						else if((int)points.elementAt(a)[points.elementAt(a).length-1]==3)g.setColor(Color.ORANGE);
						else if((int)points.elementAt(a)[points.elementAt(a).length-1]==4)g.setColor(Color.PINK);
						else if((int)points.elementAt(a)[points.elementAt(a).length-1]==5)g.setColor(Color.RED);
						else if((int)points.elementAt(a)[points.elementAt(a).length-1]==6)g.setColor(Color.YELLOW);
						else if((int)points.elementAt(a)[points.elementAt(a).length-1]==7)g.setColor(Color.CYAN);
						else if((int)points.elementAt(a)[points.elementAt(a).length-1]==8)g.setColor(Color.BLUE);
						else if((int)points.elementAt(a)[points.elementAt(a).length-1]==9)g.setColor(Color.WHITE);
						
					g.drawString("*",
							(int)(points.elementAt(a)[0]*width/1.5)+150, 
							(int)(-points.elementAt(a)[1]*height/1.5+height)-150);
				}


				setVisible(true);

			}
		};

		setContentPane(panel);
		setBounds(20, 20, 800, 800);
		setVisible(true);       
	}
	
	public void setPoints(Vector<double[]> points){
		
		this.points = points;
	}
}

