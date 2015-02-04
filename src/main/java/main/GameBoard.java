package main;

import models.KeyboardListener;
import models.PlataformerObject;

import javax.swing.*;
import java.awt.*;

/**
 * Created by NoIdeas.
 */

public class GameBoard extends JPanel implements Runnable
{
    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 500;
    private final int DELAY = 17;

    private Thread animator;
    private PlataformerObject plataformer;

    public GameBoard()
    {
        addKeyListener(KeyboardListener.getInstance());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);

        plataformer = new PlataformerObject("C:/Users/duds410/Desktop/mario.png", 0, 0);
    }

    @Override
    public void addNotify()
    {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(plataformer.getImage(), (int) plataformer.getPositionX(), (int) plataformer.getPositionY(), this);
        Toolkit.getDefaultToolkit().sync();
    }

    private void cycle()
    {
        plataformer.moveAction();
        plataformer.setSpeed(2);


        if (plataformer.getPositionY() > B_HEIGHT)
        {
            plataformer.setPositionY(0);
        }

        if (plataformer.getPositionX() > B_WIDTH)
        {
            plataformer.setPositionX(0);
        }
    }

    @Override
    public void run()
    {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true)
        {
            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0)
            {
                sleep = 2;
            }

            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e)
            {
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }
    }
}
