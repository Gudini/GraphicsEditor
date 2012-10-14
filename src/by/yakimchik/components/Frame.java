package by.yakimchik.components;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import by.yakimchik.data.Coordinates;

public class Frame extends JFrame{
	
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Cell[][] biosphere;
	private final int s = 30;
	
	private JMenuBar menuBar;
	
	private Container content;
	
	public Frame(){
		super("Graphics Editor");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		menuBar = new JMenuBar();
		
		menuBar.add(createFileMenu());
		menuBar.add(createDrawMenu());
		menuBar.add(createWindowMenu());
		
		setJMenuBar(menuBar);
		
		setSize(600, 600);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = this.getSize().width;
		int h = this.getSize().height;
		
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		
		setLocation(x, y);
		
		content = getContentPane();
		content.add(creatPanelCells());
		
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent event) {
				// TODO Auto-generated method stub
				Object[] options = {"Yes", "No"};
				if(JOptionPane.showOptionDialog(event.getWindow(), "Close this window?", 
						"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[0])==JOptionPane.YES_OPTION){
					event.getWindow().setVisible(false);
					System.exit(0);
				}
				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setVisible(true);
	}
	
	private JMenu createFileMenu(){
		JMenu file = new JMenu("File");
		JMenuItem exitItem = new JMenuItem(new ExitAction());
		file.add(exitItem);
		
		return file;
	}
	
	private JMenu createDrawMenu(){
		JMenu draw = new JMenu("Draw");
		JMenu lineDraw = new JMenu("Line");
		JMenuItem algorithmBrezenhem = new JMenuItem(new DrawLineBrezenhemAction());
		lineDraw.add(algorithmBrezenhem);
		draw.add(lineDraw);
		
		return draw;
	}
	
	private JMenu createWindowMenu(){
		JMenu window = new JMenu("Window");
		JMenuItem clearItem = new JMenuItem(new ClearAction());
		window.add(clearItem);
		
		return window;
	}
		
	private JPanel creatPanelCells(){
		biosphere = new Cell[s][s];
		final JPanel panel = new JPanel(new GridLayout(s,s,1,1));
		
		for (int ii=0; ii<s; ii++) {
	          for (int jj=0; jj<s; jj++) {
	              Cell cell = new Cell(ii,jj);
	              cell.setPreferredSize(new Dimension(40,40));
	              panel.add(cell);
	              biosphere[ii][jj] = cell;
	          }
	      }
		
		return panel;
	}
	
	private class ExitAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public ExitAction() {
			// TODO Auto-generated constructor stub
			putValue(NAME, "Exit");
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			setVisible(false);
			System.exit(0);
		}
	}
	
	private class DrawLineBrezenhemAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public DrawLineBrezenhemAction(){
			putValue(NAME, "Algorithm Brezenhema");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Coordinates.isCoordinates = false;
			
			int x1 = Coordinates.x_1;
			int x2 = Coordinates.x_2;
			
			int y1 = Coordinates.y_1;
			int y2 = Coordinates.y_2;
			
			int dx = x2 - x1;
			int dy = y2 - y1;
			
			float m = dy/(float)dx;
			float b = y1 - m*x1;
			if(Math.abs(m)<1){
				for (int x = x1; x <= x2; x++){
					float y = m*x+b;
					biosphere[Math.round(y)][x].setColor(Color.black);
					biosphere[Math.round(y)][x].drawLine();
				}
			}
			if(Math.abs(m)>=1){
				if(y1>y2){
					int k = y2;
					y2 = y1;
					y1 = k; 
				}
				for (int y = y1; y <= y2; y++){
					float x;
					if(dx!=0){
						x = (y-b)/m;
					}
					else{
						x = x1;
					}
					biosphere[y][Math.round(x)].setColor(Color.black);
					biosphere[y][Math.round(x)].drawLine();
				}
			}
			
			Coordinates.isCoordinates = true;
		}
		
	}
	
	private class ClearAction extends AbstractAction{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ClearAction(){
			putValue(NAME, "Clear screen");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			for(int i=0; i<s; i++){
				for(int j=0; j<s; j++){
					biosphere[i][j].clearCell();
				}
			}
		}
	}
}
