package by.yakimchik.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import by.yakimchik.data.Coordinates;

/*
 * class describe one cell in frame
 */

public class Cell extends JComponent implements MouseListener{

	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private boolean isLiving;
	
	private Color color;
	
	public Cell(int r, int c) {
	    this.setRow(r);
	    this.setCol(c);
	    this.addMouseListener(this);
	}
	
	public void setAlive(boolean alive) {
	    isLiving = alive;
	}

	public boolean isLiving() {
	    return this.isLiving;
	}
	
	public void paintComponent(Graphics g) {
	    if (this.isLiving) {
	    	g.setColor(getColor());
	    	g.fillRect(0, 0, getWidth(), getHeight());
	    } else {
	    	g.drawRect(0, 0, getWidth(), getHeight());
	    }
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!Frame.getEnableItems()){
			this.isLiving = true;
			setColor(Color.red);
		    repaint();
		    
		    if(Coordinates.isCoordinates){
			    if(Coordinates.isFirst){
			    	Coordinates.x_1 = getColumn();
			    	Coordinates.y_1 = getRow();
			    	Coordinates.isFirst = false;
			    	Frame.updateStatus("Select second point of line");
			    }
			    else{
			    	Coordinates.x_2 = getColumn();
			    	Coordinates.y_2 = getRow();
			    	Coordinates.isFirst = true;
			    	Frame.updateStatus("Select Draw->Line->... and select algorithm.");
			    	Frame.EnableDrawLine(true);
			    }
		    }
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public void drawLine(){
		this.isLiving = true;
		repaint();
	}
	
	public void clearCell(){
		this.isLiving = false;
		repaint();
	}

}
