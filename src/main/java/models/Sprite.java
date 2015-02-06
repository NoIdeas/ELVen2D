package models;

import javafx.geometry.Rectangle2D;
import main.GameBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Created by NoIdeas.
 */

public abstract class Sprite
{
    public GameBoard gameBoard;

    private boolean visible = true;
    private Image image;
    //private int imagePosition;
    private int width;
    private int height;
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
        this.width = this.image.getWidth(null);
        this.height = this.image.getHeight(null);
    }

    public Image getImage()
    {
        return this.image;
    }

    public void setImage(String imagePath)
    {
        loadImage(imagePath);
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

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public Rectangle2D getBounds()
    {
        return new Rectangle2D(this.positionX, this.positionY, this.width, this.height);
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
