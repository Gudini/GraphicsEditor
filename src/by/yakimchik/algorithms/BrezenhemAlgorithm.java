package by.yakimchik.algorithms;

import java.util.ArrayList;

import by.yakimchik.data.Coordinates;

public class BrezenhemAlgorithm {
	
	private ArrayList<Integer> _X;
	private ArrayList<Integer> _Y;

	public BrezenhemAlgorithm(){
		_X = new ArrayList<Integer>();
		_Y = new ArrayList<Integer>();
		
		int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;
		
		Coordinates.isCoordinates = false;
		
		int x1 = Coordinates.x_1;
		int x2 = Coordinates.x_2;
		
		int y1 = Coordinates.y_1;
		int y2 = Coordinates.y_2;
		
		dx = x2 - x1;
		dy = y2 - y1;
		
		incx = sign(dx);
		incy = sign(dy);
		
		if (dx < 0) dx = -dx;
		if (dy < 0) dy = -dy;
		
		if (dx > dy){
			pdx = incx;     pdy = 0;
            es = dy;        el = dx;
		}
		else{
			pdx = 0;        pdy = incy;
            es = dx;        el = dy;
		}
		
		x = x1;
		y = y1;
		
		err = el/2;
		
		_X.add(x);
		_Y.add(y);
		
		for (int t = 0; t < el; t++){
			err -= es;
			if (err < 0){
				err += el;
				x+=incx;
				y+=incy;
			}
			else{
				x+=pdx;
				y+=pdy;
			}
			
			_X.add(x);
			_Y.add(y);
		}
		
		Coordinates.isCoordinates = true;
	}
	
	private int sign (int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
	}
	
	public ArrayList<Integer> getXList(){
		return _X;
	}
	
	public ArrayList<Integer> getYList(){
		return _Y;
	}
}
