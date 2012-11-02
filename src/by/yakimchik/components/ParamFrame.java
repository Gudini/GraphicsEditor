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
	
	private Container content;

	public ParamFrame(int _param){
		super("Enter parametr");
		
		content = getContentPane();
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,2));
		
		JLabel paramLabel = new JLabel("p: ");
		final JTextField paramField = new JTextField();
		paramField.setText(_param+"");
		
		JButton okButton = new JButton(new AbstractAction() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				param = paramField.getText();
				try{
					Integer.parseInt(param);
					dispose();
					setVisible(false);
					setOkButtonClick(true);
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
		p.add(okButton);
		p.add(cancelButton);
		
        
        content.add(p);
        
        Dimension dim = new Dimension(600, 600);
		
		int w = this.getSize().width;
		int h = this.getSize().height;
		
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		
		setLocation(x, y);
		
		setSize(230, 90);
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
}
