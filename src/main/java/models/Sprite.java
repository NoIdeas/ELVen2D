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

    private boolean visible;
    private Image image;
    private int imagePosition;
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
    /*
    public CollisionSide checkCollision()
    {
        Rectangle2D esseRectangle = this.getBounds();

        float esseCentroX = this.positionX + this.width / 2;
        float esseCentroY = this.positionY + this.height / 2;

        for (Sprite sprite : gameBoard.sprites)
        {
            float aqueleCentroX = sprite.positionX + sprite.width / 2;
            float aqueleCentroY = sprite.positionY + sprite.height / 2;

            Rectangle2D aqueleRectangle = sprite.getBounds();

            if (sprite.getBounds().intersects(esseRectangle) && sprite != this)
            {
                float diffX = (aqueleCentroX - esseCentroX);
                float diffY = (aqueleCentroY - esseCentroY);

                Rectangle2D intersecao = intersect(esseRectangle, aqueleRectangle);

                if(intersecao.getWidth() < intersecao.getHeight())
                {
                    if(diffX < 0)
                    {
                        this.setPositionX(this.positionX + (float)intersecao.getWidth());

                        return CollisionSide.LEFT;
                    }
                    else
                    {
                        this.setPositionX(this.positionX - (float)intersecao.getWidth());

                        return CollisionSide.RIGHT;
                    }
                }
                else
                {
                    if(diffY < 0)
                    {
                        this.setPositionY(this.positionY + (float)intersecao.getHeight());

                        return CollisionSide.UP;
                    }
                    else
                    {
                        this.setPositionY(this.positionY - (float)intersecao.getHeight());

                        return CollisionSide.DOWN;
                    }
                }
            }
        }

        return CollisionSide.NONE;
    }

    public boolean isSideFree(CollisionSide side)
    {
        Rectangle2D area;

        if(side == CollisionSide.LEFT)
        {
            area = new Rectangle2D(this.getBounds().getMinX() - 1, this.getBounds().getMinY(), 1, this.getHeight());
        }
        else if(side == CollisionSide.RIGHT)
        {
            area = new Rectangle2D(this.getBounds().getMaxX() + 1, this.getBounds().getMinY(), 1, this.getHeight());
        }
        else if(side == CollisionSide.UP)
        {
            area = new Rectangle2D(this.getBounds().getMinX(), this.getBounds().getMinY() - 1, this.getWidth(), 1);
        }
        else
        {
            area = new Rectangle2D(this.getBounds().getMinX(), this.getBounds().getMaxY() + 1, this.getWidth(), 1);
        }

        Rectangle2D esseRectangle = this.getBounds();

        for (Sprite sprite : gameBoard.sprites)
        {
            if (sprite.getBounds().intersects(area) && sprite != this)
            {
                return false;
            }
        }

        return true;
    }*/

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
