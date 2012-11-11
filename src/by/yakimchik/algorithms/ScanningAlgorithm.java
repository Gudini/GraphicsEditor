package by.yakimchik.algorithms;

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
		
		stack.push(pixels[yz][xz]);
		
		int[] _mas = {-1, 1};
		
		ArrayList<Integer> zatravkaX = new ArrayList<Integer>();
		
		while(!stack.isEmpty()){
			Cell cur = stack.pop();
			if(!cur.isFill()){
				cur.setFill();
				_X.add(cur.getRow());
				_Y.add(cur.getColumn());
				zatravkaX.add(cur.getRow());
			}
			int x = cur.getRow();
			int yl = cur.getColumn()-1;
			
			while(yl>=0 && !pixels[x][yl].isFill()){
				_X.add(x);
				_Y.add(yl);
				
				pixels[x][yl].setFill();
				
				yl--;
			}
			
			int yr = cur.getColumn()+1;
			
			while(yr<=69 && !pixels[x][yr].isFill()){
				_X.add(x);
				_Y.add(yr);
				
				pixels[x][yr].setFill();
				
				yr++;
			}
			
			for(int k=0; k<2; k++){
				int dx = x+_mas[k];
				
				if(dx<0 || dx>69){
					continue;
				}
				
				int col = 0;
				
				if(checkDuplicate(zatravkaX, dx)){
					for(int j=0; j<=69; j++){
						if(pixels[dx][j].isFill()){
							col++;
						}
						else{
							if(col>0){
								for(int i=j; i<69; i++){
									if(pixels[dx][j+1].isFill()){
										stack.push(pixels[dx][j]);
										break;
									}
								}
							}
							if(j==69 && col==0){
								stack.push(pixels[dx][j]);
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
	
	private boolean checkDuplicate(ArrayList<Integer> list, int vx){
		for(int i=0; i<list.size(); i++){
			if(list.get(i)==vx){
				return false;
			}
		}
		return true;
	}
}
