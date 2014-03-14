package com.kalllx.exception;



import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * @author wateray
 * 
 */
public class CFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3172874700605827735L;

	/** The logger. */
	// private static Logger logger = new Logger();
	/**
	 * Default constructor
	 */
	public CFrame() {
		super();
		//setIconImage(ImageManager.getImage(Constant.APP_ICON));
	}

	/**
	 * Default constructor
	 */
	public CFrame(String title) {
		super(title);
		//setIconImage(ImageManager.getImage(Constant.APP_ICON));
	}

	/**
	 * Instantiates a new custom frame.
	 * 
	 * @param title
	 * @param width
	 * @param height
	 * @param owner
	 */
	public CFrame(String title, int width, int height, Component owner) {
		super(title);
		setSize(width, height);
		setLocationRelativeTo(owner);
		//setIconImage(ImageManager.getImage(Constant.APP_ICON));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
	    CFrame f = new CFrame("我的窗口",200,400,null);
	    f.setLayout(new BorderLayout());
	   final JLabel jLabel = new JLabel();
	    jLabel.setText("<html>"+Test.getContent()+"</html>");
	    
	    
	    f.add(jLabel,BorderLayout.CENTER);
	    
	    JButton jBtn = new JButton("refresh");
	    jBtn.addActionListener(new ActionListener()
	    {
	      
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {
	            jLabel.setText("loading...");
	            System.out.println("111"+Thread.currentThread().getName());
	             new Thread(new Runnable()
		    {
		        
		        @Override
		        public void run()
		        {
		     /*       SwingUtilities.invokeLater(new Runnable()
			    {
			        {"btcLast":"6249","btcVolume":"52753.49","buy":"6240","high":"6500","last":"6249","low":"5430","ltcLast":"264","ltcVolume":"3446028.51","sell":"6249","vol":"52753.49"}
			        @Override
			        public void run()
			        {
			            jLabel.setText("loading...");
			    	
			        }
			    });*/
		           
		            
				SwingUtilities.invokeLater(new Runnable()
				{
				    
				    @Override
				    public void run()
				    {
					jLabel.setText("loading...");
					jLabel.setVisible(true);
					 final String _res = "<html><table><tr><td>222</td><td>333</td></tr><tr><td>222</td><td>333</td></tr></table></html>";
				        
				       // System.out.println(_res);
				        jLabel.setText(_res);
					
				    }
				});
			 
		    	
		        }
		    }).start();
			
		     
		   
	    	
	        }
	    });
	    f.add(jBtn,BorderLayout.SOUTH);
		    f.setVisible(true);
		    
		    
	    

	}

}
