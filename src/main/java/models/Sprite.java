package models;

import javax.swing.*;
import java.awt.*;

/**
 * Created by duds410.
 */

public abstract class Sprite
{
    private boolean visible;
    private Image image;
    private int imagePosition;
    private float positionX;
    private float positionY;
    /*
    private int direction;
    private float speedX;
    private float speedY;
    */

    public void show()
    {
        this.visible = true;
    }

    public void hide()
    {
        this.visible = false;
    }

    public boolean isVisible()
    {
        return this.visible;
    }

    public void setImage(String image)
    {
        this.image = new ImageIcon(image).getImage();
    }

    public Image getImage()
    {
        return this.image;
    }

    public void setPositionX(int positionX)
    {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY)
    {
        this.positionY = positionY;
    }

    public float getPositionX()
    {
        return this.positionX;
    }

    public float getPositionY()
    {
        return this.positionY;
    }
}
