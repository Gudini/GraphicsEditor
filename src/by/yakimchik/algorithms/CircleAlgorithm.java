package by.yakimchik.algorithms;

import java.util.ArrayList;

import by.yakimchik.data.Coordinates;

public class CircleAlgorithm {
	
	private ArrayList<Integer> _X;
	private ArrayList<Integer> _Y;

	public CircleAlgorithm(){
		_X = new ArrayList<Integer>();
		_Y = new ArrayList<Integer>();
		
		Coordinates.isCoordinates = false;
		
		int x1 = Coordinates.x_1;
		int x2 = Coordinates.x_2;
		
		int y1 = Coordinates.y_1;
		int y2 = Coordinates.y_2;
		
		_X.add(x1);
		_Y.add(y1);
		
		int radius = (int) Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
		
		int p = 1-radius;
		
		ScreenPt circPt = new ScreenPt();
		
		circPt.setCoords(0, radius);
		
		circlePlotPoints(x1, y1, circPt);
		
		while(circPt.getX()<circPt.getY()){
			circPt.incrementX();
			if(p<0){
				p+=2*circPt.getX()+1;
			}
			else{
				circPt.decrementY();
				p+=2*(circPt.getX()-circPt.getY())+1;
			}
			circlePlotPoints(x1, y1, circPt);
		}
		
		Coordinates.isCoordinates = true;
	}
	
	private class ScreenPt{
		
		private int x;
		private int y;
		
		public ScreenPt(){
			x = y = 0;
		}
		
		public void setCoords(int xCoordValue, int yCoordValue){
			x = xCoordValue;
			y = yCoordValue;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
		
		public void incrementX(){
			x++;
		}
		
		public void decrementY(){
			y--;
		}
	}
	
	private void setPixel(int xCoord, int yCoord){
		if((xCoord>=0 && yCoord>=0) && (xCoord<=69 && yCoord<=69)){
			_X.add(xCoord);
			_Y.add(yCoord);
		}
	}
	
	private void circlePlotPoints(int xc, int yc, ScreenPt circPt){
		setPixel(xc+circPt.getX(), yc+circPt.getY());
		setPixel(xc-circPt.getX(), yc+circPt.getY());
		setPixel(xc+circPt.getX(), yc-circPt.getY());
		setPixel(xc-circPt.getX(), yc-circPt.getY());
		setPixel(xc+circPt.getY(), yc+circPt.getX());
		setPixel(xc-circPt.getY(), yc+circPt.getX());
		setPixel(xc+circPt.getY(), yc-circPt.getX());
		setPixel(xc-circPt.getY(), yc-circPt.getX());
	}
	
	public ArrayList<Integer> getXList(){
		return _X;
	}
	
	public ArrayList<Integer> getYList(){
		return _Y;
	}
}
