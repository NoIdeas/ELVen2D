package models;

import javafx.geometry.Rectangle2D;
import main.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by NoIdeas.
 */

public abstract class Sprite
{
    public GameBoard gameBoard;

    private boolean visible = true;

    private SpriteAnimation animation;

    //private int imagePosition;
    private int width;
    private int height;
    private float positionX;
    private float positionY;

    public Sprite()
    {
        animation = new SpriteAnimation();
    }

    public void update(float delay)
    {
        // TODO: Update this so the frame delay comes from the caller of the update method
        updateAnimation(delay);
    }

    private void updateAnimation(float delay)
    {
        animation.updateAnimation(delay);
    }

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
        URL imageUrl = this.getClass().getClassLoader().getResource(imagePath);
        Image image = new ImageIcon(imageUrl).getImage();

        this.animation.clear();
        this.animation.addFrame(image);

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public Image getImage()
    {
        return this.animation.getCurrentFrame();
    }

    public void setImage(String imagePath)
    {
        loadImage(imagePath);
    }

    public float getPositionX()
    {
        return this.positionX;
    }

    public SpriteAnimation getAnimation()
    {
        return animation;
    }

    public void setAnimation(SpriteAnimation animation)
    {
        this.animation = animation;
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

    public int getWidth()
    {
        Image image = animation.getCurrentFrame();
        return image == null ? 0 : image.getWidth(null);
    }

    public int getHeight()
    {
        Image image = animation.getCurrentFrame();
        return image == null ? 0 : image.getHeight(null);
    }

    public Rectangle2D getBounds()
    {
        return new Rectangle2D(this.positionX, this.positionY, this.getWidth(), this.getHeight());
    }

    /**
     * Returns a rectangle that represents the intersection between two other rectangles.
     * Returns an empty rectangle, if the rectangles are not intersecting
     *
     * @param a A rectangle
     * @param b Another rectangle
     * @return A rectangle that represents the intersection of the two rectangles, that is, the area that they are colliding on
     */
    public static Rectangle2D intersect(Rectangle2D a, Rectangle2D b)
    {
        if (!a.intersects(b))
            return Rectangle2D.EMPTY;

        double x = Math.max(a.getMinX(), b.getMinX());
        double y = Math.max(a.getMinY(), b.getMinY());
        double w = Math.min(a.getMaxX(), b.getMaxX()) - x;
        double h = Math.min(a.getMaxY(), b.getMaxY()) - y;

        return new Rectangle2D(x, y, w, h);
    }
}
