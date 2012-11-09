package by.yakimchik.algorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

import by.yakimchik.components.Cell;
import by.yakimchik.data.Coordinates;

public class ScanningAlgorithm {
	
	private Stack<Cell> stack;
	
	private ArrayList<Integer> _X;
	private ArrayList<Integer> _Y;

	public ScanningAlgorithm(Cell[][] pixels) {
		
		_X = new ArrayList<Integer>();
		_Y = new ArrayList<Integer>();
		
		stack = new Stack<Cell>();
		
		int xz = Coordinates.x_1;
		int yz = Coordinates.y_1;
		
		pixels[yz][xz].clearCell();
		
		int[] nextRows = {-1, 1};
		
		//circle for all points
		for(int i=0; i<69; i++){
			for(int j=0; j<69; j++){
				//if zatravka
				if(i==xz && j==yz){
					stack.push(pixels[yz][xz]);
					while(!stack.empty()){
						Cell cur = stack.pop();
						if(!cur.isFill()){
							cur.setFill();
							_X.add(cur.getRow());
							_Y.add(cur.getColumn());
						}
						
						int x1 = cur.getColumn()-1;
						
						while(x1>=0 && !pixels[cur.getRow()][x1].isFill()){
							pixels[cur.getRow()][x1].setFill();
							_X.add(cur.getRow());
							_Y.add(x1);
							x1--;
						}
						
						int x2 = cur.getColumn()+1;
						
						while(x2<=69 && !pixels[cur.getRow()][x2].isFill()){
							pixels[cur.getRow()][x2].setFill();
							_X.add(cur.getRow());
							_Y.add(x2);
							x2++;
						}
						
						for(int k=0; k<nextRows.length; k++){
							int dy = cur.getRow()+nextRows[k];
							
							if(dy<0 || dy>=69){
								continue;
							}
							
							int x = x1+1;
							while(x<x2){
								int x0 = x;
								
								while(x<x2 && !pixels[dy][x].isFill()){
									pixels[dy][x].setFill();
									_X.add(dy);
									_Y.add(x);
									x++;
								}
								
								if(x0!=x){
									if(dy+1<=69 && !pixels[dy][x-1].isFill())
										stack.push(pixels[dy][x-1]);
									if(dy-1>=0 && !pixels[dy][x-1].isFill())
										stack.push(pixels[dy][x-1]);
								}
								
								while(x<x2 && pixels[dy][x].isFill()){
									x++;
								}
							}
							
						}
					}
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
