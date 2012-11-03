package by.yakimchik.algorithms;

import java.util.ArrayList;

import by.yakimchik.data.Coordinates;

public class BezeAlgotithm {
	
	private ArrayList<Integer> _X;
	private ArrayList<Integer> _Y;
	
	public BezeAlgotithm(int N){
		
		_X = new ArrayList<Integer>();
		_Y = new ArrayList<Integer>();
		
		Coordinates.isCoordinates = false;
		
		int P1x = Coordinates.x_1;
		int P2x = Coordinates.x_2;
		int P3x = Coordinates.x_3;
		int P4x = Coordinates.x_4;
		
		int P1y = Coordinates.y_1;
		int P2y = Coordinates.y_2;
		int P3y = Coordinates.y_3;
		int P4y = Coordinates.y_4;
		
		double step =(double) 1/N;
		int x, y;
		
		for(double t=0; t<=1; t+=step){
			x = (int) Math.round((Math.pow(1-t, 3)*P1x+3*t*Math.pow(t-1, 2)*P2x+3*Math.pow(t, 2)*(1-t)*P3x+Math.pow(t, 3)*P4x));
			y = (int) Math.round((Math.pow(1-t, 3)*P1y+3*t*Math.pow(t-1, 2)*P2y+3*Math.pow(t, 2)*(1-t)*P3y+Math.pow(t, 3)*P4y));
			
			_X.add(x);
			_Y.add(y);
		}
		
		_X.add(P4x);
		_Y.add(P4y);
		
		
		Coordinates.isCoordinates = true;
	}
	
	public ArrayList<Integer> getXList(){
		return _X;
	}
	
	public ArrayList<Integer> getYList(){
		return _Y;
	}

}
