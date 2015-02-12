package main;

import entities.*;
import helpers.KeyboardControlled;
import helpers.KeyboardListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by NoIdeas.
 */

public class GameBoard extends JPanel implements Runnable
{
    // Board Configs
    public final int B_WIDTH = 500;
    public final int B_HEIGHT = 500;
    public final int DELAY = 16;
    public Thread animator;

    // Sprites
    public static ArrayList<Sprite> canCollideList;
    private PlatformerEntity myHero;
    private SceneEntity sceneEntity;
    private SceneEntity sceneEntity1;
    private SceneEntity sceneEntity2;

    public GameBoard()
    {
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);

        KeyboardListener keyboardListener = KeyboardListener.getInstance();
        addKeyListener(keyboardListener);

        myHero = new MyHero("mario.png", 250, 0, 0.7f);
        myHero.getAnimation().addFrameFromPath("mario_2.png");
        keyboardListener.addKeyboardControlled((KeyboardControlled) myHero);
        sceneEntity = new SceneEntity("mario.png", 250, 250);
        sceneEntity1 = new SceneEntity("mario.png", 274, 250);
        sceneEntity2 = new SceneEntity("mario.png", 298, 250);

        canCollideList = new ArrayList<>();
        canCollideList.add(myHero);
        canCollideList.add(sceneEntity);
        canCollideList.add(sceneEntity1);
        canCollideList.add(sceneEntity2);
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

        if (myHero.isVisible())
            g.drawImage(myHero.getImage(), (int) myHero.getPositionX(), (int) myHero.getPositionY(), this);
        if (sceneEntity.isVisible())
            g.drawImage(sceneEntity.getImage(), (int) sceneEntity.getPositionX(), (int) sceneEntity.getPositionY(), this);
        if (sceneEntity1.isVisible())
            g.drawImage(sceneEntity1.getImage(), (int) sceneEntity1.getPositionX(), (int) sceneEntity1.getPositionY(), this);
        if (sceneEntity2.isVisible())
            g.drawImage(sceneEntity2.getImage(), (int) sceneEntity2.getPositionX(), (int) sceneEntity2.getPositionY(), this);

        g.setColor(Color.white);

        // dev
        g.drawString("Colliding: " + myHero.getCollisionSide(), 10, 20);
        g.drawString("VelocityX: " + myHero.getVelocityX(), 10, 35);
        g.drawString("VelocityY: " + myHero.getVelocityY(), 10, 50);
        g.drawString("ForceX: " + myHero.getForceX(), 10, 65);
        g.drawString("ForceY: " + myHero.getForceY(), 10, 80);
        g.drawString("moveDirection: " + myHero.getMoveDirection(), 10, 95);

        KeyboardListener kListener = KeyboardListener.getInstance();
        g.drawString("isLeftKeyPressed: " + kListener.isLeftKeyPressed(), 200, 20);
        g.drawString("isRightKeyPressed: " + kListener.isRightKeyPressed(), 200, 35);
        g.drawString("isUpKeyPressed: " + kListener.isUpKeyPressed(), 200, 50);
        g.drawString("isDownpKeyPressed: " + kListener.isDownKeyPressed(), 200, 65);

        Toolkit.getDefaultToolkit().sync();
    }

    private void cycle(float delay)
    {
        myHero.update(delay);

        if (myHero.getPositionY() > B_HEIGHT)
            myHero.setPositionY(0);
        if (myHero.getPositionX() > B_WIDTH)
            myHero.setPositionX(0);
        if (myHero.getPositionY() < 0)
            myHero.setPositionY(B_HEIGHT);
        if (myHero.getPositionX() < 0)
            myHero.setPositionX(B_WIDTH);
    }

    @Override
    public void run()
    {
        long beforeTime, beforeSleepTime = System.currentTimeMillis(), timeDiff, sleep;
        beforeTime = System.currentTimeMillis();

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
