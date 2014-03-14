package com.kalllx.exception;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Count {
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        final JLabel label = new JLabel("", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 36));
        contentPane.add(label, BorderLayout.CENTER);
        ActionListener listener = new ActionListener() {
            int count = 0;

            public void actionPerformed(ActionEvent e) {
        	System.out.println(Thread.currentThread().getName());
                count++;
                label.setText(Integer.toString(count));
                try
		{
		    Thread.sleep(1000);
		}
		catch (InterruptedException e1)
		{
		    e1.printStackTrace();
		}
                
                label.setText("222");
            }
        };
        
        System.out.println(Thread.currentThread().getName());
        Timer timer = new Timer(500, listener);
        timer.start();
        frame.setSize(300, 100);
        frame.setVisible(true);
    }
}