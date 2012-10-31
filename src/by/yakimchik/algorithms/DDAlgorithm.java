package by.yakimchik.algorithms;

import java.util.ArrayList;

import by.yakimchik.data.Coordinates;

public class DDAlgorithm {
	
	private ArrayList<Integer> _X;
	private ArrayList<Integer> _Y;

	public DDAlgorithm(){
		_X = new ArrayList<Integer>();
		_Y = new ArrayList<Integer>();
		
		Coordinates.isCoordinates = false;
		
		int x1 = Coordinates.x_1;
		int x2 = Coordinates.x_2;
		
		int y1 = Coordinates.y_1;
		int y2 = Coordinates.y_2;
		
		int i, L, xstart, xend, ystart, yend;
		float dX, dY, x, y;
		
		xstart = Math.round(x1);
		ystart = Math.round(y1);
		
		xend = Math.round(x2);
		yend = Math.round(y2);
		
		if(Math.abs(xend-xstart)>Math.abs(yend-ystart)){
			L = Math.abs(xend-xstart);
		}
		else{
			L = Math.abs(yend-ystart);
		}
		
		dX = (x2-x1)/(float)L;
		dY = (y2-y1)/(float)L;
		i = 1;
		
		_X.add(x1);
		_Y.add(y1);
		
		x = x1;
		y = y1;
		
		while(i<L){
			x = x+dX;
			y = y+dY;
			_X.add(Round(x));
			_Y.add(Round(y));
			i++;
		}
		
		_X.add(x2);
		_Y.add(y2);
		
		Coordinates.isCoordinates = true;
	}
	
	private int Round(float a){
		return (int) (a+0.5);
	}
	
	public ArrayList<Integer> getXList(){
		return _X;
	}
	
	public ArrayList<Integer> getYList(){
		return _Y;
	}
	
}
