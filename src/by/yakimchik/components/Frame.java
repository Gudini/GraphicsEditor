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
	
	private static JButton drawButton;
	private static JButton nextStepButton;
	private static JButton prevStepButton;
	
	private Container content;
	
	private boolean isFirstStep;
	
	private static int cur = 0;
	private ArrayList<Integer> _X;
	private ArrayList<Integer> _Y;
	
	private static boolean enable = false;
	
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
		drawButton.setAction(new DrawButton());
		
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
		drawButton.setAlignmentX(CENTER_ALIGNMENT);
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
		JMenu curveDraw = new JMenu("Curve");
		JMenu colorMenu = new JMenu("Color");
		
		algorithmDDA = new JCheckBoxMenuItem();
		algorithmDDA.setAction(new AbstractAction() {
			
			/**
			 * @serial default id
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(algorithmBrezenhem.isSelected()){
					algorithmBrezenhem.setSelected(false);
					algorithmDDA.setSelected(true);
				}
				else{
					algorithmDDA.setSelected(true);
				}
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
				if(algorithmDDA.isSelected()){
					algorithmDDA.setSelected(false);
					algorithmBrezenhem.setSelected(true);
				}
				else{
					algorithmBrezenhem.setSelected(true);
				}
			}
		});
		algorithmBrezenhem.setText("Algorithm Brezenhema");
		lineDraw.add(algorithmBrezenhem);
		
		algorithmCircle = new JCheckBoxMenuItem();
		curveDraw.add(algorithmCircle);
		
		JMenuItem selectColor = new JMenuItem("Select color");
		selectColor.setAction(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//EnableDrawLine(false);
		
		draw.add(lineDraw);
		//draw.add(curveDraw);
		//sdraw.add(colorMenu);
		
		return draw;
	}
	
	private JMenu createWindowMenu(){
		JMenu window = new JMenu("Window");
		JMenuItem clearItem = new JMenuItem(new ClearAction());
		window.add(clearItem);
		
		return window;
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
	}
}
