package models;

import javax.swing.*;
import java.awt.*;

/**
 * Created by NoIdeas.
 */

public abstract class Sprite
{
    private boolean visible;
    private Image image;
    private int imagePosition;
    private float positionX;
    private float positionY;

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

    private void loadImage(String imagePath)
    {
        this.image = new ImageIcon(imagePath).getImage();
    }

    public void setImage(String imagePath)
    {
        loadImage(imagePath);
    }

    public Image getImage()
    {
        return this.image;
    }

    public void setPositionX(float positionX)
    {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY)
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
