package by.yakimchik.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GraphicsScene extends JPanel{
	
	public GraphicsScene() {
		// TODO Auto-generated constructor stub
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
