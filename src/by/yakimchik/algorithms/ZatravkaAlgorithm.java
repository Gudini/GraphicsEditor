package by.yakimchik.algorithms;
import java.util.ArrayList;
import java.util.Stack;

import by.yakimchik.components.Cell;
import by.yakimchik.data.Coordinates;

public class ZatravkaAlgorithm {
	
	private ArrayList<Integer> _X;
	private ArrayList<Integer> _Y;
	
	private Stack<Cell> stack;
	
	public ZatravkaAlgorithm(Cell[][] pixels){
		_X = new ArrayList<Integer>();
		_Y = new ArrayList<Integer>();
		stack = new Stack<Cell>();
		
		int xz = Coordinates.x_1;
		int yz = Coordinates.y_1;
		
		pixels[yz][xz].clearCell();
		
		stack.push(pixels[yz][xz]);
		
		
		while(!stack.empty()){
			Cell cur = stack.pop();
			if(!cur.isFill()){
				cur.setFill();
				_X.add(cur.getRow());
				_Y.add(cur.getColumn());
			}
			
			if(cur.getRow()-1>=0){
				if(!pixels[cur.getRow()-1][cur.getColumn()].isFill()){
					stack.push(pixels[cur.getRow()-1][cur.getColumn()]);
				}
			}
			
			if(cur.getRow()+1<=69){
				if(!pixels[cur.getRow()+1][cur.getColumn()].isFill()){
					stack.push(pixels[cur.getRow()+1][cur.getColumn()]);
				}
			}
			
			if(cur.getColumn()-1>=0){
				if(!pixels[cur.getRow()][cur.getColumn()-1].isFill()){
					stack.push(pixels[cur.getRow()][cur.getColumn()-1]);
				}
			}
			
			if(cur.getColumn()+1<=69){
				if(!pixels[cur.getRow()][cur.getColumn()+1].isFill()){
					stack.push(pixels[cur.getRow()][cur.getColumn()+1]);
				}
			}
		}
		
	}
	
	public ArrayList<Integer> getXList(){
		return _X;
	}
	
	public ArrayList<Integer> getYList(){
		return _Y;
	}

}
