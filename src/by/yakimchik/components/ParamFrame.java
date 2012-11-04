package by.yakimchik.components;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ParamFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isOkButtonClick;

	private String param;
	
	private String count;
	
	private Container content;

	public ParamFrame(String title, String textLabel, int _param, int _count, final boolean isSpline){
		super(title);
		
		content = getContentPane();
		JPanel p = new JPanel();
		JLabel paramLabel = new JLabel(textLabel);
		final JTextField paramField = new JTextField();
		paramField.setText(_param+"");
		
		JLabel countPoints = new JLabel("Points:");
		final JTextField countField = new JTextField();
		countField.setText(_count+"");
		
		if(!isSpline){
			p.setLayout(new GridLayout(2,2));		
			setSize(230, 90);
		}
		else{
			p.setLayout(new GridLayout(3,2));
			setSize(230, 110);
		}
		
		JButton okButton = new JButton(new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				param = paramField.getText();
				count = countField.getText();
				try{
					Integer.parseInt(param);
					if(isSpline){
						int k = Integer.parseInt(count);
						if(k<4){
							JOptionPane.showMessageDialog(getWindows()[1], "Please, input number more or equals.", "Input error",JOptionPane.ERROR_MESSAGE);
						}
						else{
							dispose();
							setVisible(false);
							setOkButtonClick(true);
						}
					}
					else{
						dispose();
						setVisible(false);
						setOkButtonClick(true);
					}
				}
				catch (NumberFormatException e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(getWindows()[1], "Please, input number.", "Input error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		okButton.setText("Ok");
		JButton cancelButton = new JButton(new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setOkButtonClick(false);
				dispose();
				setVisible(false);
				
			}
		});
		cancelButton.setText("Cancel");
		
		p.add(paramLabel);
		p.add(paramField);
		
		if(isSpline){
			p.add(countPoints);
			p.add(countField);
		}
		
		p.add(okButton);
		p.add(cancelButton);
		
        
        content.add(p);
        
        Dimension dim = new Dimension(600, 600);
		
		int w = this.getSize().width;
		int h = this.getSize().height;
		
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		
		setLocation(x, y);
		
		setVisible(true);
	}

	public boolean isOkButtonClick() {
		return isOkButtonClick;
	}

	public void setOkButtonClick(boolean isOkButtonClick) {
		this.isOkButtonClick = isOkButtonClick;
	}
	
	public String getParam(){
		return param;
	}
	
	public String getCount(boolean isSpline){
		if(isSpline){
			return count;
		}
		else{
			return null;
		}
	}
}
