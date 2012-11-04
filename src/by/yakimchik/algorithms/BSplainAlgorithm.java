package by.yakimchik.algorithms;

import java.util.ArrayList;

import by.yakimchik.data.Coordinates;

public class BSplainAlgorithm {
	
	private ArrayList<Integer> _X;
	private ArrayList<Integer> _Y;
	
	public BSplainAlgorithm(int N){
		_X = new ArrayList<Integer>();
		_Y = new ArrayList<Integer>();
		
		double step =(double) 1/N;
		int x, y;
		double a3, a2, a1, a0;
		double b3, b2, b1, b0;
		
		int countSegments = Coordinates.xSegment.size() - 3;
		int i = 1;
		
		while(countSegments>0){
			a3 = (double)((-1)*Coordinates.xSegment.get(i-1)+3*Coordinates.xSegment.get(i)-3*Coordinates.xSegment.get(i+1)+Coordinates.xSegment.get(i+2))/6.0;
			a2 = (double)(Coordinates.xSegment.get(i-1)-2*Coordinates.xSegment.get(i)+Coordinates.xSegment.get(i+1))/2.0;
			a1 = (double)(Coordinates.xSegment.get(i+1)-Coordinates.xSegment.get(i-1))/2;
			a0 = (double)(Coordinates.xSegment.get(i-1)+4*Coordinates.xSegment.get(i)+Coordinates.xSegment.get(i+1))/6.0;
			
			b3 = (double)((-1)*Coordinates.ySegment.get(i-1)+3*Coordinates.ySegment.get(i)-3*Coordinates.ySegment.get(i+1)+Coordinates.ySegment.get(i+2))/6.0;
			b2 = (double)(Coordinates.ySegment.get(i-1)-2*Coordinates.ySegment.get(i)+Coordinates.ySegment.get(i+1))/2.0;
			b1 = (double)(Coordinates.ySegment.get(i+1)-Coordinates.ySegment.get(i-1))/2.0;
			b0 = (double)(Coordinates.ySegment.get(i-1)+4*Coordinates.ySegment.get(i)+Coordinates.ySegment.get(i+1))/6.0;
			
			for(double t=0; t<=1; t+=step){
				x = (int) Math.round(((a3*t+a2)*t+a1)*t+a0);
				y = (int) Math.round(((b3*t+b2)*t+b1)*t+b0);
				
				if(checkDuplicate(x,y)){
					_X.add(x);
					_Y.add(y);
				}
				
			}
			
			i++;
			
			countSegments--;
		}
		
		Coordinates.xSegment = new ArrayList<Integer>();
		Coordinates.ySegment = new ArrayList<Integer>();
		
	}
	
	public ArrayList<Integer> getXList(){
		return _X;
	}
	
	public ArrayList<Integer> getYList(){
		return _Y;
	}
	
	private boolean checkDuplicate(int vx, int vy){
		for(int i=0; i<_X.size(); i++){
			if(_X.get(i)==vx){
				if(_Y.get(i)==vy){
					return false;
				}
			}
		}
		return true;
	}

}
