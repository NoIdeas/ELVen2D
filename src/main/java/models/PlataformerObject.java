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
            super.setDirectionX(-1);
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            super.setDirectionX(1);
        }

        if (key == KeyEvent.VK_UP)
        {
            super.setDirectionY(-1);
        }

        if (key == KeyEvent.VK_DOWN)
        {
            super.setDirectionY(1);
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
            super.setDirectionX(0);
        }

        if (key == KeyEvent.VK_RIGHT)
        {
            super.setDirectionX(0);
        }

        if (key == KeyEvent.VK_UP)
        {
            super.setDirectionY(0);
        }

        if (key == KeyEvent.VK_DOWN)
        {
            super.setDirectionY(0);
        }
    }
}
