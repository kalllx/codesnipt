package com.kalllx.exception;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

class JFrameAnimation extends JFrame implements ActionListener
{

    JPanel panel;
    Timer timer;
    int x, y;

    public JFrameAnimation ()
    {
        super ();
        setDefaultCloseOperation (EXIT_ON_CLOSE);
        timer = new Timer (15, this); //@ First param is the delay (in milliseconds) therefore this animation is updated every 15 ms. The shorter the delay, the faster the animation.
        //This class iplements ActionListener, and that is where the animation variables are updated. Timer passes an ActionEvent after each 15 ms.

    }


    public void run ()
    {

        panel = new JPanel ()
        {
            public void paintComponent (Graphics g)  //The JPanel paint method we are overriding.
            {
                g.setColor (Color.white);
                g.fillRect (0, 0, 500, 500); //Setting panel background (white in this case);
                //g.fillRect (-1 + x, -1 + y, 50, 50);  //This is to erase the black remains of the animation. (not used because the background is always redrawn.
                g.setColor (Color.black);
                g.fillRect (0 + x, 0 + y, 50, 50); //This is the animation.

            }

        }
        ;
        panel.setPreferredSize (new Dimension (500, 500)); //Setting the panel size

        getContentPane ().add (panel); //Adding panel to frame.
        pack ();
        setVisible (true);
        timer.start (); //This starts the animation.
    }


    public void actionPerformed (ActionEvent e)
    {
        x++;
        y++;
        if (x == 250)
            timer.stop (); //This stops the animation once it reaches a certain distance.
        panel.repaint ();  //This is what paints the animation again (IMPORTANT: won't work without this).
        panel.revalidate (); //This isn't necessary, I like having it just in case.

    }


    public static void main (String[] args)
    {
        new JFrameAnimation ().run (); //Start our new application.
    }
}