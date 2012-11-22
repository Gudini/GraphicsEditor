package by.yakimchik.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GraphicsScene extends JPanel{
	
	private List<Point> points = new ArrayList<Point>();
	
	public GraphicsScene() {
		// TODO Auto-generated constructor stub
		points.add(new Point(100, 200));
		points.add(new Point(200, 200));
		points.add(new Point(200, 100));
		points.add(new Point(100, 100));
		points.add(new Point(100, 200));
		
		points.add(new Point(140, 230));
		points.add(new Point(240, 230));
		points.add(new Point(240, 130));
		points.add(new Point(140, 130));
		points.add(new Point(140, 230));
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.black);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(3));
		
		for(int i=0; i<points.size()-1; i++){
			g2D.drawLine(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(), points.get(i+1).getY());
		}
		
		for(int i=1; i<=4; i++){
			g2D.drawLine(points.get(i).getX(), points.get(i).getY(), points.get(i+5).getX(), points.get(i+5).getY());
		}
		
		/*g2D.drawLine(100, 200, 200, 200);
		g2D.drawLine(200, 200, 200, 100);
		g2D.drawLine(200, 100, 100, 100);
		g2D.drawLine(100, 100, 100, 200);
		
		g2D.drawLine(140, 230, 240, 230);
		g2D.drawLine(240, 230, 240, 130);
		g2D.drawLine(240, 130, 140, 130);
		g2D.drawLine(140, 130, 140, 230);
		
		g2D.drawLine(200, 200, 240, 230);
		g2D.drawLine(200, 100, 240, 130);
		g2D.drawLine(100, 100, 140, 130);
		g2D.drawLine(100, 200, 140, 230);
		*/
	}
}
