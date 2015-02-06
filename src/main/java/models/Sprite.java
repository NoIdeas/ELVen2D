package models;

import javafx.geometry.Rectangle2D;
import main.GameBoard;

import java.awt.*;

/**
 * Created by NoIdeas.
 */

public abstract class Sprite
{
    public GameBoard gameBoard;
    private boolean visible;
    private SpriteAnimation animation;
    //private int imagePosition;
    private int width;
    private int height;
    private float positionX;
    private float positionY;

    public Sprite()
    {
        this.show();
        this.setPositionX(0);
        this.setPositionY(0);
        animation = new SpriteAnimation();
    }

    public void update(float delay)
    {
        updateAnimation(delay);
    }

    private void updateAnimation(float delay)
    {
        animation.updateAnimation(delay);
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

    private void loadImage(String imagePath)
    {
        this.animation.clear();
        this.animation.addFrameFromPath(imagePath);
    }

    public Image getImage()
    {
        return this.animation.getCurrentFrame();
    }

    public void setImage(String imagePath)
    {
        loadImage(imagePath);
    }

    public SpriteAnimation getAnimation()
    {
        return animation;
    }

    public void setAnimation(SpriteAnimation animation)
    {
        this.animation = animation;
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
        Image image = animation.getCurrentFrame();
        return image == null ? 0 : image.getWidth(null);
    }

    public int getHeight()
    {
        Image image = animation.getCurrentFrame();
        return image == null ? 0 : image.getHeight(null);
    }

    public Rectangle2D getCollisionBox()
    {
        return new Rectangle2D(this.positionX, this.positionY, this.getWidth(), this.getHeight());
    }

    /**
     * Returns a rectangle that represents the intersection between two other rectangles.
     * Returns an empty rectangle, if the rectangles are not intersecting
     *
     * @param a A rectangle
     * @param b Another rectangle
     * @return A rectangle that represents the intersection of the two rectangles, that is, the area that they are
     * colliding on
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
