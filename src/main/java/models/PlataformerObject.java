package models;

import java.awt.event.KeyEvent;

/**
 * Created by NoIdeas.
 */

public class PlataformerObject extends MovableObject
{
    public PlataformerObject(String imagePath, float positionX, float positionY)
    {
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
    }

    public void keyPressed(KeyEvent e)
    {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            super.setSpeedX(-1);
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            super.setSpeedX(1);
        }

        if (key == KeyEvent.VK_UP)
        {
            super.setSpeedY(-1);
        }

        if (key == KeyEvent.VK_DOWN)
        {
            super.setSpeedY(1);
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            super.setSpeedX(0);
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            super.setSpeedX(0);
        }

        if (key == KeyEvent.VK_UP)
        {
            super.setSpeedY(0);
        }

        if (key == KeyEvent.VK_DOWN)
        {
            super.setSpeedY(0);
        }
    }
}
