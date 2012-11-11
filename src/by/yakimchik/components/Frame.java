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
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import by.yakimchik.data.Coordinates;
import by.yakimchik.algorithms.*;

/*
 * main window
 */
public class Frame extends JFrame{
	
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** Array of components */
	private Cell[][] pixels;
	
	/** Numbers pixels in window of width and height */
	private final int s = 70;
	
	/** Menu in window */
	private JMenuBar menuBar;
	
	/** Status label in window */
	private static JLabel status;
	
	/** Menu of Draw menu */
	private static JCheckBoxMenuItem algorithmBrezenhem;
	private static JCheckBoxMenuItem algorithmDDA;
	private static JCheckBoxMenuItem algorithmCircle;
	private static JCheckBoxMenuItem algorithmParabola;
	private static JCheckBoxMenuItem algorithmBeze;
	private static JCheckBoxMenuItem algorithmBSpline;
	
	/** Menu of Fill menu */
	private static JCheckBoxMenuItem algorithmZatravki;
	private static JCheckBoxMenuItem algorithmScan;
	
	private static JButton drawButton;
	public static JButton fillButton;
	private static JButton nextStepButton;
	private static JButton prevStepButton;
	
	private Container content;
	
	private boolean isFirstStep;
	
	private static int cur = 0;
	private ArrayList<Integer> _X;
	private ArrayList<Integer> _Y;
	
	private static boolean enable = false;
	
	private static int paramParabola = 8;
	private static int paramBeze = 50;
	private static int paramSpline = 50;
	private static int paramCountPoints = 4;
	
	public Frame(){
		super("Graphics Editor");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		menuBar = new JMenuBar();
		
		menuBar.add(createFileMenu());
		menuBar.add(createDrawMenu());
		menuBar.add(createFillMenu());
		menuBar.add(createWindowMenu());
		
		status = new JLabel("Select first point of line");
		status.setBorder(BorderFactory.createEmptyBorder());
		
		setJMenuBar(menuBar);
		
		setSize(800, 600);
		
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
		isFirstStep = true;
		
		drawButton = new JButton("Draw");
		drawButton.setAction(new DrawButton());
		
		fillButton = new JButton("Fill");
		fillButton.setAction(new FillButton());
		fillButton.setEnabled(false);
		
		
		nextStepButton = new JButton(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				doStep(true);
			}
		});
		nextStepButton.setText("Next");
		prevStepButton = new JButton(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				doStep(false);
			}
		});
		prevStepButton.setText("Prev");
		
		enable = false;
		
		JPanel debugPanel = new JPanel();
		Border eastBorder = BorderFactory.createTitledBorder("Debug");
		debugPanel.setBorder(eastBorder);
		debugPanel.setLayout(new BoxLayout(debugPanel, BoxLayout.X_AXIS));
		debugPanel.add(prevStepButton);
		debugPanel.add(nextStepButton);
		prevStepButton.setAlignmentX(-20);
		nextStepButton.setAlignmentX(20);
		
		
		JPanel east_panel = new JPanel();
		east_panel.setLayout(new BoxLayout(east_panel, BoxLayout.Y_AXIS));
		east_panel.add(drawButton);		
		east_panel.add(fillButton);
		drawButton.setAlignmentX(CENTER_ALIGNMENT);
		fillButton.setAlignmentX(CENTER_ALIGNMENT);
		EnableButtons(false);
		east_panel.add(debugPanel, BorderLayout.NORTH);
		
		//clear button
		JButton clearButton = new JButton();
		east_panel.add(clearButton);
		clearButton.setAlignmentX(CENTER_ALIGNMENT);
		clearButton.setAction(new ClearAction());
		
		content.add(east_panel, BorderLayout.EAST);
		
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
		JMenu conicDraw = new JMenu("Conic");
		JMenu curveDraw = new JMenu("Curve");
		//JMenu colorMenu = new JMenu("Color");
		
		algorithmDDA = new JCheckBoxMenuItem();
		algorithmDDA.setAction(new AbstractAction() {
			
			/**
			 * @serial default id
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(algorithmDDA.isSelected()){
					algorithmDDA.setSelected(false);
				}
				if(!algorithmDDA.isSelected()){
					algorithmDDA.setSelected(true);
				}
				algorithmBrezenhem.setSelected(false);
				algorithmCircle.setSelected(false);
				algorithmParabola.setSelected(false);
				algorithmBeze.setSelected(false);
				algorithmBSpline.setSelected(false);
				algorithmZatravki.setSelected(false);
				algorithmScan.setSelected(false);
				
				Coordinates.numberOfAlgorithm = 1;
			}
		});
		algorithmDDA.setText("Algorithm DDA");
		algorithmDDA.setSelected(true);
		
		lineDraw.add(algorithmDDA);
		
		algorithmBrezenhem = new JCheckBoxMenuItem("Algorithm Brezenhema");
		algorithmBrezenhem.setAction(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(algorithmBrezenhem.isSelected()){
					algorithmBrezenhem.setSelected(false);
				}
				if(!algorithmBrezenhem.isSelected()){
					algorithmBrezenhem.setSelected(true);
				}
				algorithmDDA.setSelected(false);
				algorithmCircle.setSelected(false);
				algorithmParabola.setSelected(false);
				algorithmBeze.setSelected(false);
				algorithmBSpline.setSelected(false);
				algorithmZatravki.setSelected(false);
				algorithmScan.setSelected(false);
				
				fillButton.setEnabled(false);
				
				Coordinates.numberOfAlgorithm = 1;
			}
		});
		algorithmBrezenhem.setText("Algorithm Brezenhema");
		lineDraw.add(algorithmBrezenhem);
		
		algorithmCircle = new JCheckBoxMenuItem(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(algorithmCircle.isSelected()){
					algorithmCircle.setSelected(false);
				}
				if(!algorithmCircle.isSelected()){
					algorithmCircle.setSelected(true);
				}
				algorithmBrezenhem.setSelected(false);
				algorithmDDA.setSelected(false);
				algorithmParabola.setSelected(false);
				algorithmBeze.setSelected(false);
				algorithmBSpline.setSelected(false);
				algorithmZatravki.setSelected(false);
				algorithmScan.setSelected(false);
				
				fillButton.setEnabled(false);
				
				Coordinates.numberOfAlgorithm = 1;
			}
		});
		algorithmCircle.setText("Circle");
		
		algorithmParabola = new JCheckBoxMenuItem(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(algorithmParabola.isSelected()){
					algorithmParabola.setSelected(false);
				}
				if(!algorithmParabola.isSelected()){
					algorithmParabola.setSelected(true);
				}
				algorithmBrezenhem.setSelected(false);
				algorithmDDA.setSelected(false);
				algorithmCircle.setSelected(false);
				algorithmBeze.setSelected(false);
				algorithmBSpline.setSelected(false);
				algorithmZatravki.setSelected(false);
				algorithmScan.setSelected(false);
				
				fillButton.setEnabled(false);
				
				Coordinates.numberOfAlgorithm = 2;
				
				updateStatus("Select vertex of parabola");
				
				final ParamFrame paramFrame = new ParamFrame("Enter parametr", "p:", paramParabola, 0,false);
				paramFrame.addWindowListener(new WindowListener() {
					
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
					public void windowClosing(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent arg0) {
						// TODO Auto-generated method stub
						if(paramFrame.isOkButtonClick()){
							paramParabola = Integer.parseInt(paramFrame.getParam());
						}
					}
					
					@Override
					public void windowActivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		algorithmParabola.setText("Parabola");
		
		algorithmBeze = new JCheckBoxMenuItem(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(algorithmBeze.isSelected()){
					algorithmBeze.setSelected(false);
				}
				if(!algorithmBeze.isSelected()){
					algorithmBeze.setSelected(true);
				}
				algorithmBrezenhem.setSelected(false);
				algorithmDDA.setSelected(false);
				algorithmCircle.setSelected(false);
				algorithmParabola.setSelected(false);
				algorithmBSpline.setSelected(false);
				algorithmZatravki.setSelected(false);
				algorithmScan.setSelected(false);
				
				fillButton.setEnabled(false);
				
				Coordinates.numberOfAlgorithm = 3;
				
				final ParamFrame paramFrame = new ParamFrame("Enter correctness", "N:", paramBeze, 0,false);
				paramFrame.addWindowListener(new WindowListener() {
					
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
					public void windowClosing(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent arg0) {
						// TODO Auto-generated method stub
						if(paramFrame.isOkButtonClick()){
							paramBeze = Integer.parseInt(paramFrame.getParam());
						}
					}
					
					@Override
					public void windowActivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		algorithmBeze.setText("Algorithm Bezier");
		
		algorithmBSpline = new JCheckBoxMenuItem(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(algorithmBSpline.isSelected()){
					algorithmBSpline.setSelected(false);
				}
				if(!algorithmBSpline.isSelected()){
					algorithmBSpline.setSelected(true);
				}
				algorithmBrezenhem.setSelected(false);
				algorithmDDA.setSelected(false);
				algorithmCircle.setSelected(false);
				algorithmParabola.setSelected(false);
				algorithmBeze.setSelected(false);
				algorithmZatravki.setSelected(false);
				algorithmScan.setSelected(false);
				
				fillButton.setEnabled(false);
				
				Coordinates.numberOfAlgorithm = 4;
				
				final ParamFrame paramFrame = new ParamFrame("Enter correctness", "N:", paramSpline, paramCountPoints,true);
				paramFrame.addWindowListener(new WindowListener() {
					
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
					public void windowClosing(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent arg0) {
						// TODO Auto-generated method stub
						if(paramFrame.isOkButtonClick()){
							paramSpline = Integer.parseInt(paramFrame.getParam());
							paramCountPoints = Integer.parseInt(paramFrame.getCount(true));
							Coordinates.countPointsOfSegment = paramCountPoints;
						}
					}
					
					@Override
					public void windowActivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		algorithmBSpline.setText("B-spline");
		
		conicDraw.add(algorithmCircle);
		conicDraw.add(algorithmParabola);
		
		curveDraw.add(algorithmBeze);
		curveDraw.add(algorithmBSpline);
		
		//EnableDrawLine(false);
		
		draw.add(lineDraw);
		draw.add(conicDraw);
		draw.add(curveDraw);
		
		return draw;
	}
	
	private JMenu createWindowMenu(){
		JMenu window = new JMenu("Window");
		JMenuItem clearItem = new JMenuItem(new ClearAction());
		window.add(clearItem);
		
		return window;
	}
	
	private JMenu createFillMenu(){
		JMenu fill = new JMenu("Fill");
		
		algorithmZatravki = new JCheckBoxMenuItem(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(algorithmZatravki.isSelected()){
					algorithmZatravki.setSelected(false);
				}
				if(!algorithmZatravki.isSelected()){
					algorithmZatravki.setSelected(true);
				}
				algorithmScan.setSelected(false);
				algorithmBrezenhem.setSelected(false);
				algorithmDDA.setSelected(false);
				algorithmCircle.setSelected(false);
				algorithmParabola.setSelected(false);
				algorithmBeze.setSelected(false);
				algorithmBSpline.setSelected(false);
				
				Coordinates.numberOfAlgorithm = 5;
			}
		});
		algorithmZatravki.setText("Zatravka");		
		
		algorithmScan = new JCheckBoxMenuItem(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(algorithmScan.isSelected()){
					algorithmScan.setSelected(false);
				}
				if(!algorithmScan.isSelected()){
					algorithmScan.setSelected(true);
				}
				algorithmZatravki.setSelected(false);
				algorithmBrezenhem.setSelected(false);
				algorithmDDA.setSelected(false);
				algorithmCircle.setSelected(false);
				algorithmParabola.setSelected(false);
				algorithmBeze.setSelected(false);
				algorithmBSpline.setSelected(false);
				
				Coordinates.numberOfAlgorithm = 5;
			}
		});
		algorithmScan.setText("Scanning");
		
		fill.add(algorithmZatravki);
		fill.add(algorithmScan);
		
		return fill;
	}
		
	private JPanel creatPanelCells(){
		pixels = new Cell[s][s];
		final JPanel panel = new JPanel(new GridLayout(s,s,1,1));
		
		for (int ii=0; ii<s; ii++) {
	          for (int jj=0; jj<s; jj++) {
	              Cell cell = new Cell(ii,jj);
	              cell.setPreferredSize(new Dimension(15,15));
	              panel.add(cell);
	              pixels[ii][jj] = cell;
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
					pixels[i][j].clearCell();
				}
			}
			updateStatus("Select first point of line");
			
			Coordinates.isCoordinates = true;
			
			EnableButtons(false);
		}
	}
	
	public static void updateStatus(String text){
		status.setText(text);
	}
	
	public static void EnableButtons(boolean b){
		drawButton.setEnabled(b);
		nextStepButton.setEnabled(b);
		prevStepButton.setEnabled(false);
		enable = b;
	}
	
	public static boolean getEnableItems(){
		return enable; 
	}
		
	private void DrawLineBrezenhemAlgorithm(){
		BrezenhemAlgorithm alg = new BrezenhemAlgorithm();
		
		ArrayList<Integer> X = alg.getXList();
		ArrayList<Integer> Y = alg.getYList();
		for(int i=0; i<X.size(); i++){
			pixels[Y.get(i)][X.get(i)].setColor(Color.black);
			pixels[Y.get(i)][X.get(i)].setPixel();
		}
		
		pixels[Y.get(0)][X.get(0)].setColor(Color.green);
		pixels[Y.get(0)][X.get(0)].setPixel();
		
		pixels[Y.get(Y.size()-1)][X.get(X.size()-1)].setColor(Color.green);
		pixels[Y.get(Y.size()-1)][X.get(X.size()-1)].setPixel();
		
		EnableButtons(false);
		updateStatus("Select first point of line");
	}
	
	private void DrawLineDDAAlgorithm(){
		
		DDAlgorithm alg = new DDAlgorithm();
		
		ArrayList<Integer> X = alg.getXList();
		ArrayList<Integer> Y = alg.getYList();
		for(int i=0; i<X.size(); i++){
			pixels[Y.get(i)][X.get(i)].setColor(Color.black);
			pixels[Y.get(i)][X.get(i)].setPixel();
		}
		
		pixels[Y.get(0)][X.get(0)].setColor(Color.green);
		pixels[Y.get(0)][X.get(0)].setPixel();
		
		pixels[Y.get(Y.size()-1)][X.get(X.size()-1)].setColor(Color.green);
		pixels[Y.get(Y.size()-1)][X.get(X.size()-1)].setPixel();
		
		EnableButtons(false);
		updateStatus("Select first point of line");
	}
	
	private void DrawCircleAlgorithm(){
		CircleAlgorithm alg = new CircleAlgorithm();
		
		ArrayList<Integer> X = alg.getXList();
		ArrayList<Integer> Y = alg.getYList();
		for(int i=0; i<X.size(); i++){
			pixels[Y.get(i)][X.get(i)].setColor(Color.black);
			pixels[Y.get(i)][X.get(i)].setPixel();
		}
		
		pixels[Y.get(0)][X.get(0)].setColor(Color.green);
		pixels[Y.get(0)][X.get(0)].setPixel();
		
		EnableButtons(false);
		updateStatus("Select first point of line");
	}
	
	private class DrawButton extends AbstractAction{
		
		/**
		 * @serial default id
		 */
		private static final long serialVersionUID = 1L;

		public DrawButton(){
			putValue(NAME, "Draw");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(algorithmBrezenhem.isSelected()){
				DrawLineBrezenhemAlgorithm();
			}
			if(algorithmDDA.isSelected()){
				DrawLineDDAAlgorithm();
			}
			if(algorithmCircle.isSelected()){
				DrawCircleAlgorithm();
			}
			if(algorithmParabola.isSelected()){
				DrawParabolAlgorithm();
				EnableButtons(false);
			}
			if(algorithmBeze.isSelected()){
				DrawBezeAlgorithm();
				EnableButtons(false);
			}
			if(algorithmBSpline.isSelected()){
				DrawSplineAlgorithm();
				EnableButtons(false);
				Coordinates.countPointsOfSegment = paramCountPoints;
			}
		}
		
	}
	
	private void doStep(boolean isStepNext){
		if(algorithmBrezenhem.isSelected()){
			if(isFirstStep){
				BrezenhemAlgorithm alg = new BrezenhemAlgorithm();
				_X = alg.getXList();
				_Y = alg.getYList();
				
				pixels[_Y.get(0)][_X.get(0)].setColor(Color.green);
				pixels[_Y.get(0)][_X.get(0)].setPixel();
				
				cur = 1;
				
				isFirstStep = false;
				drawButton.setEnabled(false);
				prevStepButton.setEnabled(true);
			}
			else{
				if(cur==_X.size()-1  && isStepNext){
					pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setColor(Color.green);
					pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setPixel();
					
					EnableButtons(false);
					
					isFirstStep = true;
					cur = 0;
				}
				else{
					if(isStepNext){
						pixels[_Y.get(cur)][_X.get(cur)].setColor(Color.black);
						pixels[_Y.get(cur)][_X.get(cur)].setPixel();
						cur++;
					}
					else{
						cur--;
						pixels[_Y.get(cur)][_X.get(cur)].clearCell();
						if(cur==0){
							isFirstStep = true;
							prevStepButton.setEnabled(false);
							drawButton.setEnabled(true);
							pixels[_Y.get(cur)][_X.get(cur)].setColor(Color.red);
							pixels[_Y.get(cur)][_X.get(cur)].setPixel();
						}
					}
				}
				
			}					
		}
		if(algorithmDDA.isSelected()){
			if(isFirstStep){
				DDAlgorithm alg = new DDAlgorithm();
				_X = alg.getXList();
				_Y = alg.getYList();
				
				pixels[_Y.get(0)][_X.get(0)].setColor(Color.green);
				pixels[_Y.get(0)][_X.get(0)].setPixel();
				
				cur = 1;
				
				isFirstStep = false;
				drawButton.setEnabled(false);
				prevStepButton.setEnabled(true);
			}
			else{
				if(cur==_X.size()-1 && isStepNext){
					pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setColor(Color.green);
					pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setPixel();
					
					EnableButtons(false);
					
					isFirstStep = true;
					cur = 0;
					prevStepButton.setEnabled(false);
				}
				else{
					if(isStepNext){
						pixels[_Y.get(cur)][_X.get(cur)].setColor(Color.black);
						pixels[_Y.get(cur)][_X.get(cur)].setPixel();
						cur++;
					}
					else{
						cur--;
						pixels[_Y.get(cur)][_X.get(cur)].clearCell();
						if(cur==0){
							isFirstStep = true;
							prevStepButton.setEnabled(false);
							drawButton.setEnabled(true);
							pixels[_Y.get(cur)][_X.get(cur)].setColor(Color.red);
							pixels[_Y.get(cur)][_X.get(cur)].setPixel();
						}
					}
				}
				
			}					
		}
		if(algorithmCircle.isSelected()){
			if(isFirstStep){
				CircleAlgorithm alg = new CircleAlgorithm();
				_X = alg.getXList();
				_Y = alg.getYList();
				
				pixels[_Y.get(0)][_X.get(0)].setColor(Color.green);
				pixels[_Y.get(0)][_X.get(0)].setPixel();
				
				cur = 1;
				
				isFirstStep = false;
				drawButton.setEnabled(false);
				prevStepButton.setEnabled(true);
			}
			else{
				if(cur==_X.size()-4  && isStepNext){					
					EnableButtons(false);
					
					isFirstStep = true;
					cur = 0;
				}
				else{
					if(isStepNext){
						pixels[_Y.get(cur)][_X.get(cur)].setColor(Color.black);
						pixels[_Y.get(cur)][_X.get(cur)].setPixel();
						cur++;
					}
					else{
						cur--;
						pixels[_Y.get(cur)][_X.get(cur)].clearCell();
						if(cur==0){
							isFirstStep = true;
							prevStepButton.setEnabled(false);
							drawButton.setEnabled(true);
							pixels[_Y.get(cur)][_X.get(cur)].setColor(Color.red);
							pixels[_Y.get(cur)][_X.get(cur)].setPixel();
						}
					}
				}
				
			}
		}
		if(algorithmBeze.isSelected()){
			if(isFirstStep){
				BezeAlgotithm alg = new BezeAlgotithm(paramBeze);
				_X = alg.getXList();
				_Y = alg.getYList();
				
				pixels[_Y.get(0)][_X.get(0)].setColor(Color.green);
				pixels[_Y.get(0)][_X.get(0)].setPixel();
				
				cur = 1;
				
				isFirstStep = false;
				drawButton.setEnabled(false);
				prevStepButton.setEnabled(true);
			}
			else{
				if(cur==_X.size()-1  && isStepNext){					
					EnableButtons(false);
					
					pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setColor(Color.green);
					pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setPixel();
					
					isFirstStep = true;
					cur = 0;
				}
				else{
					if(isStepNext){
						pixels[_Y.get(cur)][_X.get(cur)].setColor(Color.black);
						pixels[_Y.get(cur)][_X.get(cur)].setPixel();
						cur++;
					}
					else{
						cur--;
						pixels[_Y.get(cur)][_X.get(cur)].clearCell();
						if(cur==0){
							isFirstStep = true;
							prevStepButton.setEnabled(false);
							drawButton.setEnabled(true);
							pixels[_Y.get(cur)][_X.get(cur)].setColor(Color.red);
							pixels[_Y.get(cur)][_X.get(cur)].setPixel();
						}
					}
				}
				
			}
		}
		
		if(algorithmBSpline.isSelected()){
			if(isFirstStep){
				BSplainAlgorithm alg = new BSplainAlgorithm(paramSpline);
				_X = alg.getXList();
				_Y = alg.getYList();
				
				pixels[_Y.get(0)][_X.get(0)].setColor(Color.green);
				pixels[_Y.get(0)][_X.get(0)].setPixel();
				
				cur = 1;
				
				isFirstStep = false;
				drawButton.setEnabled(false);
				prevStepButton.setEnabled(true);
			}
			else{
				if(cur==_X.size()-1  && isStepNext){					
					EnableButtons(false);
					
					pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setColor(Color.green);
					pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setPixel();
					
					isFirstStep = true;
					cur = 0;
				}
				else{
					if(isStepNext){
						pixels[_Y.get(cur)][_X.get(cur)].setColor(Color.black);
						pixels[_Y.get(cur)][_X.get(cur)].setPixel();
						cur++;
					}
					else{
						cur--;
						pixels[_Y.get(cur)][_X.get(cur)].clearCell();
						if(cur==0){
							isFirstStep = true;
							prevStepButton.setEnabled(false);
							drawButton.setEnabled(true);
							pixels[_Y.get(cur)][_X.get(cur)].setColor(Color.red);
							pixels[_Y.get(cur)][_X.get(cur)].setPixel();
						}
					}
				}
				
			}
		}
		if(algorithmZatravki.isSelected()){
			if(isFirstStep){
				ZatravkaAlgorithm alg = new ZatravkaAlgorithm(pixels);
				_X = alg.getXList();
				_Y = alg.getYList();
				
				pixels[_X.get(0)][_Y.get(0)].setColor(Color.ORANGE);
				pixels[_X.get(0)][_Y.get(0)].setPixel();
				
				cur = 1;
				
				isFirstStep = false;
				drawButton.setEnabled(false);
				prevStepButton.setEnabled(true);
			}
			else{
				if(cur==_X.size()-1  && isStepNext){					
					EnableButtons(false);
					
					pixels[_X.get(_X.size()-1)][_Y.get(_Y.size()-1)].setColor(Color.ORANGE);
					pixels[_X.get(_X.size()-1)][_Y.get(_Y.size()-1)].setPixel();
					
					isFirstStep = true;
					cur = 0;
				}
				else{
					if(isStepNext){
						pixels[_X.get(cur)][_Y.get(cur)].setColor(Color.ORANGE);
						pixels[_X.get(cur)][_Y.get(cur)].setPixel();
						cur++;
					}
					else{
						cur--;
						pixels[_X.get(cur)][_Y.get(cur)].clearCell();
						if(cur==0){
							isFirstStep = true;
							prevStepButton.setEnabled(false);
							drawButton.setEnabled(true);
							pixels[_X.get(cur)][_Y.get(cur)].setColor(Color.ORANGE);
							pixels[_X.get(cur)][_Y.get(cur)].setPixel();
						}
					}
				}
				
			}
		}
		
		if(algorithmScan.isSelected()){
			if(isFirstStep){
				ScanningAlgorithm alg = new ScanningAlgorithm(pixels);
				_X = alg.getXList();
				_Y = alg.getYList();
				
				pixels[_X.get(0)][_Y.get(0)].setColor(Color.ORANGE);
				pixels[_X.get(0)][_Y.get(0)].setPixel();
				
				cur = 1;
				
				isFirstStep = false;
				drawButton.setEnabled(false);
				prevStepButton.setEnabled(true);
			}
			else{
				if(cur==_X.size()-1  && isStepNext){					
					EnableButtons(false);
					
					pixels[_X.get(_X.size()-1)][_Y.get(_Y.size()-1)].setColor(Color.ORANGE);
					pixels[_X.get(_X.size()-1)][_Y.get(_Y.size()-1)].setPixel();
					
					isFirstStep = true;
					cur = 0;
				}
				else{
					if(isStepNext){
						pixels[_X.get(cur)][_Y.get(cur)].setColor(Color.ORANGE);
						pixels[_X.get(cur)][_Y.get(cur)].setPixel();
						cur++;
					}
					else{
						cur--;
						pixels[_X.get(cur)][_Y.get(cur)].clearCell();
						if(cur==0){
							isFirstStep = true;
							prevStepButton.setEnabled(false);
							drawButton.setEnabled(true);
							pixels[_X.get(cur)][_Y.get(cur)].setColor(Color.ORANGE);
							pixels[_X.get(cur)][_Y.get(cur)].setPixel();
						}
					}
				}
				
			}
		}
	}
	
	private void DrawParabolAlgorithm(){
		ParabolaAlgorithm p = new ParabolaAlgorithm(paramParabola);
		_X = p.getXList();
		_Y = p.getYList();
		
		for(int i=0; i<_X.size(); i++){
			pixels[_Y.get(i)][_X.get(i)].setColor(Color.black);
			pixels[_Y.get(i)][_X.get(i)].setPixel();
		}
		
		pixels[_Y.get(0)][_X.get(0)].setColor(Color.green);
		pixels[_Y.get(0)][_X.get(0)].setPixel();
		
		pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setColor(Color.green);
		pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setPixel();
	}
	
	private void DrawBezeAlgorithm(){
		
		pixels[Coordinates.y_2][Coordinates.x_2].clearCell();
		pixels[Coordinates.y_3][Coordinates.x_3].clearCell();
		
		BezeAlgotithm b = new BezeAlgotithm(paramBeze);
		
		_X = b.getXList();
		_Y = b.getYList();
		
		for(int i=0; i<_X.size(); i++){
			pixels[_Y.get(i)][_X.get(i)].setColor(Color.black);
			pixels[_Y.get(i)][_X.get(i)].setPixel();
		}
		
		pixels[_Y.get(0)][_X.get(0)].setColor(Color.green);
		pixels[_Y.get(0)][_X.get(0)].setPixel();
		
		pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setColor(Color.green);
		pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setPixel();
	}
	
	private void DrawSplineAlgorithm(){
		BSplainAlgorithm bs = new BSplainAlgorithm(paramSpline);
		
		_X = bs.getXList();
		_Y = bs.getYList();
		
		for(int i=0; i<_X.size(); i++){
			pixels[_Y.get(i)][_X.get(i)].setColor(Color.black);
			pixels[_Y.get(i)][_X.get(i)].setPixel();
		}
		
		pixels[_Y.get(0)][_X.get(0)].setColor(Color.green);
		pixels[_Y.get(0)][_X.get(0)].setPixel();
		
		pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setColor(Color.green);
		pixels[_Y.get(_Y.size()-1)][_X.get(_X.size()-1)].setPixel();
	}
	
	private void FillZatravkaAlgorithm(){
		ZatravkaAlgorithm zt = new ZatravkaAlgorithm(pixels);
		
		_X = zt.getXList();
		_Y = zt.getYList();
		
		for(int i=0; i<_X.size(); i++){
			pixels[_X.get(i)][_Y.get(i)].setColor(Color.ORANGE);
			pixels[_X.get(i)][_Y.get(i)].setPixel();
		}
	}
	
	private class FillButton extends AbstractAction{
		
		public FillButton(){
			putValue(NAME, "Fill");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(algorithmZatravki.isSelected()){
				FillZatravkaAlgorithm();
			}
			if(algorithmScan.isSelected()){
				FillScanningAlgorithm();
			}
			
			EnableButtons(false);
			fillButton.setEnabled(false);
		}
		
	}
	
	private void FillScanningAlgorithm(){
		ScanningAlgorithm sc = new ScanningAlgorithm(pixels);
		
		_X = sc.getXList();
		_Y = sc.getYList();
		
		for(int i=0; i<_X.size(); i++){
			pixels[_X.get(i)][_Y.get(i)].setColor(Color.ORANGE);
			pixels[_X.get(i)][_Y.get(i)].setPixel();
		}
	}
}
