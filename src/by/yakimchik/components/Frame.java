package by.yakimchik.components;

import java.awt.BorderLayout;
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
	private final int s = 70;
	
	private JMenuBar menuBar;
	
	private static JLabel status;
	
	private static JMenuItem algorithmBrezenhem;
	private static JMenuItem algorithmDDA;
	
	private static JMenuItem algorithmCircle;
	
	private Container content;
	
	public Frame(){
		super("Graphics Editor");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		menuBar = new JMenuBar();
		
		menuBar.add(createFileMenu());
		menuBar.add(createDrawMenu());
		menuBar.add(createWindowMenu());
		
		status = new JLabel("Select first point of line");
		status.setBorder(BorderFactory.createEmptyBorder());
		
		setJMenuBar(menuBar);
		
		setSize(600, 600);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		int w = this.getSize().width;
		int h = this.getSize().height;
		
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		
		setLocation(x, y);
		
		content = getContentPane();
		
		JScrollPane pane = new JScrollPane(creatPanelCells());
		content.add(pane);
		
		content.add(status, BorderLayout.SOUTH);
		
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
		JMenu curveDraw = new JMenu("Curve");
		
		algorithmBrezenhem = new JMenuItem(new DrawLineBrezenhemAction());
		lineDraw.add(algorithmBrezenhem);
		algorithmDDA = new JMenuItem(new DrawLineDDA());
		lineDraw.add(algorithmDDA);
		
		algorithmCircle = new JMenuItem(new CircleAlgorithm());
		curveDraw.add(algorithmCircle);
		
		EnableDrawLine(false);
		
		draw.add(lineDraw);
		draw.add(curveDraw);
		
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
	              cell.setPreferredSize(new Dimension(15,15));
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
			Object[] options = {"Yes", "No"};
			if(JOptionPane.showOptionDialog(getWindows()[0], "Close this window?", 
					"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0])==JOptionPane.YES_OPTION){
				getWindows()[0].setVisible(false);
				System.exit(0);
			}
		}
	}
	
	private class DrawLineBrezenhemAction extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public DrawLineBrezenhemAction(){
			putValue(NAME, "Algorithm DDA");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Coordinates.isCoordinates = false;
			
			int x1 = Coordinates.x_1;
			int x2 = Coordinates.x_2;
			
			int y1 = Coordinates.y_1;
			int y2 = Coordinates.y_2;
			
			if(x1>x2){
				int h = x1;
				x1 = x2;
				x2 = h;
				
				h = y1;
				y1 = y2;
				y2 = h;
			}
			
			int dx = x2 - x1;
			int dy = y2 - y1;
			
			float m = dy/(float)dx;
			float b = y1 - m*x1;
			if(Math.abs(m)<1){
				int x;
				float y = 0;
				for (x = x1; x <= x2; x++){
					y = m*x+b;
					if(x==x1){
						biosphere[Math.round(y)][x].setColor(Color.green);
					}
					else{
						if(x==x2){
							biosphere[Math.round(y)][x].setColor(Color.green);
						}
						else{
							biosphere[Math.round(y)][x].setColor(Color.black);
						}
					}
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
					if(y==y1){
						biosphere[y][Math.round(x)].setColor(Color.green);
					}
					else{
						if(y==y2){
							biosphere[y][Math.round(x)].setColor(Color.green);
						}
						else{
							biosphere[y][Math.round(x)].setColor(Color.black);
						}
					}
					biosphere[y][Math.round(x)].drawLine();
				}
			}
			
			Coordinates.isCoordinates = true;
			
			EnableDrawLine(false);
			updateStatus("Select first point of line");
		}
		
	}
	
	private class DrawLineDDA extends AbstractAction{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DrawLineDDA(){
			putValue(NAME, "Algorithm Brezenhema");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
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
			
			biosphere[y][x].setColor(Color.green);
			biosphere[y][x].drawLine();
			
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
				
				biosphere[y][x].setColor(Color.black);
				biosphere[y][x].drawLine();
			}
			
			biosphere[y][x].setColor(Color.green);
			biosphere[y][x].drawLine();
			
			Coordinates.isCoordinates = true;
			
			EnableDrawLine(false);
			updateStatus("Select first point of line");
		}
		
	}
	
	private int sign (int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
	}
	
	private class ClearAction extends AbstractAction{
		
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
			updateStatus("Select first point of line");
			
			Coordinates.isCoordinates = true;
			
			EnableDrawLine(false);
		}
	}
	
	public static void updateStatus(String text){
		status.setText(text);
	}
	
	public static void EnableDrawLine(boolean b){
		algorithmBrezenhem.setEnabled(b);
		algorithmDDA.setEnabled(b);
		algorithmCircle.setEnabled(b);
	}
	
	public static boolean getEnableItems(){
		return algorithmBrezenhem.isEnabled();
	}
	
	private class CircleAlgorithm extends AbstractAction{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CircleAlgorithm(){
			putValue(NAME, "Circle");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			Coordinates.isCoordinates = false;
			
			int x1 = Coordinates.x_1;
			int x2 = Coordinates.x_2;
			
			int y1 = Coordinates.y_1;
			int y2 = Coordinates.y_2;
			
			int radius = (int) Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
			
			int p = 1-radius;
			
			ScreenPt circPt = new ScreenPt();
			
			circPt.setCoords(0, radius);
			
			circlePlotPoints(x1, y1, circPt, Color.green);
			
			while(circPt.getX()<circPt.getY()){
				circPt.incrementX();
				if(p<0){
					p+=2*circPt.getX()+1;
				}
				else{
					circPt.decrementY();
					p+=2*(circPt.getX()-circPt.getY())+1;
				}
				circlePlotPoints(x1, y1, circPt, Color.black);
			}
			
			Coordinates.isCoordinates = true;
			
			EnableDrawLine(false);
			updateStatus("Select first point of line");
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
		
		private void setPixel(int xCoord, int yCoord, Color color){
			biosphere[yCoord][xCoord].setColor(color);
			biosphere[yCoord][xCoord].drawLine();
		}
		
		private void circlePlotPoints(int xc, int yc, ScreenPt circPt, Color color){
			setPixel(xc+circPt.getX(), yc+circPt.getY(), color);
			setPixel(xc-circPt.getX(), yc+circPt.getY(), color);
			setPixel(xc+circPt.getX(), yc-circPt.getY(), color);
			setPixel(xc-circPt.getX(), yc-circPt.getY(), color);
			setPixel(xc+circPt.getY(), yc+circPt.getX(), color);
			setPixel(xc-circPt.getY(), yc+circPt.getX(), color);
			setPixel(xc+circPt.getY(), yc-circPt.getX(), color);
			setPixel(xc-circPt.getY(), yc-circPt.getX(), color);
		}
		
	}
}
