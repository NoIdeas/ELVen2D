package main;

import models.PlataformerObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        addKeyListener(new TAdapter());
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

        g.drawImage(plataformer.getImage(), (int)plataformer.getPositionX(), (int)plataformer.getPositionY(), this);
        Toolkit.getDefaultToolkit().sync();
    }

    private void cycle()
    {
        plataformer.setPositionX(plataformer.getPositionX() + plataformer.getSpeedX());
        plataformer.setPositionY(plataformer.getPositionY() + plataformer.getSpeedY());

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

    private class TAdapter extends KeyAdapter
    {
        public void keyReleased(KeyEvent e) {
            plataformer.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            plataformer.keyPressed(e);
        }
    }
}
