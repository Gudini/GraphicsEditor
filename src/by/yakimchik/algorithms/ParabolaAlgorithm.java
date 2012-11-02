package by.yakimchik.algorithms;

import java.util.ArrayList;

import by.yakimchik.data.Coordinates;

public class ParabolaAlgorithm {
	
	private ArrayList<Integer> _X;
	private ArrayList<Integer> _Y;
	
	public ParabolaAlgorithm(int _p){
		_X = new ArrayList<Integer>();
		_Y = new ArrayList<Integer>();
		
		Coordinates.isCoordinates = false;
		
		int x2 = 0;
		int y2 = 0;
		
		double Sh, Sv, Sd, p=_p;
		
		Sd = Math.pow(y2+1, 2) - 2*p*(x2+1);
		Sv = Math.pow(y2+1, 2) - 2*p*x2;
		Sh = Math.pow(y2, 2) - 2*p*(x2+1);
		
		_X.add(x2+Coordinates.x_1);
		_Y.add(y2+Coordinates.y_1);
		
		int y1;
		y1 = y2;
		
		while(x2+Coordinates.x_1<69){
			if(Math.abs(Sh) - Math.abs(Sv)<=0){
				if(Math.abs(Sd)-Math.abs(Sh)<0){
					y2++;
					y1--;
				}
				x2++;
			}
			else{
				if(Math.abs(Sv)-Math.abs(Sd)>0){
					x2++;
				}
				y2++;
				y1--;
			}
			
			_X.add(x2+Coordinates.x_1);
			_Y.add(y2+Coordinates.y_1);
			
			if(y1+Coordinates.y_1>=0){
				_X.add(x2+Coordinates.x_1);
				_Y.add(y1+Coordinates.y_1);
			}
			
			Sd = (int) (Math.pow(y2+1, 2) - 2*p*(x2+1));
			Sv = (int) (Math.pow(y2+1, 2) - 2*p*x2);
			Sh = (int) (Math.pow(y2, 2) - 2*p*(x2+1));
		}
		
		Coordinates.isCoordinates = true;
	}
	
	public ArrayList<Integer> getXList(){
		return _X;
	}
	
	public ArrayList<Integer> getYList(){
		return _Y;
	}

}
