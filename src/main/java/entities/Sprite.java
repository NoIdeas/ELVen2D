package entities;

import javafx.geometry.Rectangle2D;

import javax.swing.*;
import java.awt.*;

/**
 * Created by NoIdeas.
 */

public abstract class Sprite
{
    private boolean visible;
    private SpriteAnimation animation;
    private int width;
    private int height;
    private float positionX;
    private float positionY;

    public Sprite()
    {
        this.show();
        this.setPositionX(0);
        this.setPositionY(0);
        this.setAnimation(new SpriteAnimation());
        this.setWidth(0);
        this.setHeight(0);
    }

    public void update(float delay)
    {
        this.updateAnimation(delay);
    }

    private void updateAnimation(float delay)
    {
        this.getAnimation().updateAnimation(delay);
    }

    public boolean isVisible()
    {
        return this.visible;
    }

    public void show()
    {
        this.visible = true;
    }

    public void hide()
    {
        this.visible = false;
    }

    public Image getImage()
    {
        return this.getAnimation().getCurrentFrame();
    }

    private void loadImage(String imagePath)
    {
        this.getAnimation().clear();
        this.getAnimation().addFrameFromPath(imagePath);

        this.setWidth(new ImageIcon(imagePath).getImage().getWidth(null));
        this.setHeight(new ImageIcon(imagePath).getImage().getHeight(null));
    }

    public void setImage(String imagePath)
    {
        this.loadImage(imagePath);
    }

    public SpriteAnimation getAnimation()
    {
        return animation;
    }

    public void setAnimation(SpriteAnimation animation)
    {
        this.animation = animation;
    }

    public int getWidth()
    {
        if (this.width > 0)
            return this.width;

        Image image = animation.getCurrentFrame();
        return image == null ? 0 : image.getWidth(null);
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        if (this.height > 0)
            return this.height;
        Image image = animation.getCurrentFrame();
        return image == null ? 0 : image.getHeight(null);
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public float getPositionX()
    {
        return this.positionX;
    }

    public void setPositionX(float positionX)
    {
        this.positionX = positionX;
    }

    public float getPositionY()
    {
        return this.positionY;
    }

    public void setPositionY(float positionY)
    {
        this.positionY = positionY;
    }

    public Rectangle2D getCollisionBox()
    {
        return new Rectangle2D(this.getPositionX(), this.getPositionY(), this.getWidth(), this.getHeight());
    }
}
