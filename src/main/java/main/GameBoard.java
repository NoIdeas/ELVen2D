package main;

import models.KeyboardListener;
import models.PlataformerObject;
import models.SceneObject;
import models.Sprite;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by NoIdeas.
 */

public class GameBoard extends JPanel implements Runnable
{
    public final int B_WIDTH = 500;
    public final int B_HEIGHT = 500;
    private final int DELAY = 17;
    private Thread animator;

    // Sprites
    public ArrayList<Sprite> sprites;
    private PlataformerObject plataformerObject;
    private SceneObject sceneObject;

    public GameBoard()
    {
        addKeyListener(KeyboardListener.getInstance());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);

        plataformerObject = new PlataformerObject(this, "C:/Users/duds410/Desktop/mario.png", 0, 250);
        sceneObject = new SceneObject(this, "C:/Users/duds410/Desktop/mario.png", 250, 250);

        sprites = new ArrayList<>();
        sprites.add(plataformerObject);
        sprites.add(sceneObject);
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

        g.drawImage(plataformerObject.getImage(), (int) plataformerObject.getPositionX(), (int) plataformerObject.getPositionY(), this);
        g.drawImage(sceneObject.getImage(), (int) sceneObject.getPositionX(), (int) sceneObject.getPositionY(), this);
        Toolkit.getDefaultToolkit().sync();
    }

    private void cycle()
    {
        plataformerObject.moveAction();

        if (plataformerObject.getPositionY() > B_HEIGHT)
        {
            plataformerObject.setPositionY(0);
        }

        if (plataformerObject.getPositionX() > B_WIDTH)
        {
            plataformerObject.setPositionX(0);
        }

        if (plataformerObject.getPositionY() < 0)
        {
            plataformerObject.setPositionY(B_HEIGHT);
        }

        if (plataformerObject.getPositionX() < 0)
        {
            plataformerObject.setPositionX(B_WIDTH);
        }
    }

    @Override
    public void run()
    {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        plataformerObject.setSpeed(70);

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
