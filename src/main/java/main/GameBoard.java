package main;

import entities.*;
import helpers.KeyboardListener;

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
    private final int DELAY = 16;
    private Thread animator;

    // Sprites
    public ArrayList<Sprite> sprites;
    private PlataformerEntity plataformerEntity;
    private SceneEntity sceneEntity;

    public GameBoard()
    {
        addKeyListener(KeyboardListener.getInstance());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);

        plataformerEntity = new PlataformerEntity(this, "mario.png", 0, 250);
        plataformerEntity.getAnimation().addFrameFromPath("mario_2.png");
        sceneEntity = new SceneEntity(this, "mario.png", 250, 250);

        sprites = new ArrayList<>();
        sprites.add(plataformerEntity);
        sprites.add(sceneEntity);
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

        if (plataformerEntity.isVisible())
            g.drawImage(plataformerEntity.getImage(), (int) plataformerEntity.getPositionX(), (int) plataformerEntity.getPositionY(), this);
        if (sceneEntity.isVisible())
            g.drawImage(sceneEntity.getImage(), (int) sceneEntity.getPositionX(), (int) sceneEntity.getPositionY(), this);

        g.setColor(Color.white);

        // dev
        g.drawString("Colliding: " + plataformerEntity.getStringColliding(), 10, 20);
        g.drawString("Speed: " + plataformerEntity.getSpeed(), 10, 35);
        g.drawString("DirectionX: " + plataformerEntity.getDirectionX(), 10, 50);
        g.drawString("DirectionY: " + plataformerEntity.getDirectionY(), 10, 65);
        g.drawString("moveDirection: " + plataformerEntity.getMoveDirection(), 10, 80);

        KeyboardListener kListener = KeyboardListener.getInstance();
        g.drawString("isLeftKeyPressed: " + kListener.isLeftKeyPressed(), 200, 20);
        g.drawString("isRigthKeyPressed: " + kListener.isRigthKeyPressed(), 200, 35);
        g.drawString("isUpKeyPressed: " + kListener.isUpKeyPressed(), 200, 50);
        g.drawString("isDownpKeyPressed: " + kListener.isDownKeyPressed(), 200, 65);

        Toolkit.getDefaultToolkit().sync();
    }

    private void cycle(float delay)
    {
        plataformerEntity.update(delay);

        if (plataformerEntity.getPositionY() > B_HEIGHT)
            plataformerEntity.setPositionY(0);
        if (plataformerEntity.getPositionX() > B_WIDTH)
            plataformerEntity.setPositionX(0);
        if (plataformerEntity.getPositionY() < 0)
            plataformerEntity.setPositionY(B_HEIGHT);
        if (plataformerEntity.getPositionX() < 0)
            plataformerEntity.setPositionX(B_WIDTH);
    }

    @Override
    public void run()
    {
        long beforeTime, beforeSleepTime = System.currentTimeMillis(), timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

        plataformerEntity.setSpeed(70);

        while (true)
        {
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            cycle(System.currentTimeMillis() - beforeSleepTime);
            repaint();

            beforeSleepTime = System.currentTimeMillis();

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
