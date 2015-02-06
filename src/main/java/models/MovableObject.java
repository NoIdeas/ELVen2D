package models;
import javafx.geometry.Rectangle2D;

/**
 * Created by NoIdeas.
 */

public abstract class MovableObject extends Sprite
{
    private int directionX;
    private int directionY;
    private float speedX = 10;
    private float speedY = 10;

    public void moveAction()
    {
        super.setPositionX(super.getPositionX() + (this.getSpeedX() * this.getDirectionX()));
        super.setPositionY(super.getPositionY() + (this.getSpeedY() * this.getDirectionY()));
    }

    public void setSpeed(float speed)
    {
        this.speedX = speed;
        this.speedY = speed;
    }

    public boolean checkCollision()
    {
        float movementX = this.getSpeedX() * this.getDirectionX();
        float movementY = this.getSpeedY() * this.getDirectionY();
        Rectangle2D bound = new Rectangle2D(this.getPositionX(), this.getPositionY(), this.getWidth(), this.getHeight());

        for (Sprite item : this.gameBoard.sprites)
        {
            Rectangle2D otherBound = new Rectangle2D(item.getPositionX(), item.getPositionY(), item.getWidth(), item.getHeight());
            Rectangle2D movementBox = new Rectangle2D(this.getPositionX(), this.getPositionY(), this.getWidth() + movementX, this.getHeight() + movementY);

            if (movementBox.intersects(otherBound) && item != this)
            {
                float x = (float) (otherBound.getMinX() - movementBox.getMaxX());
                this.setSpeed(x);
                return true;
            }
        }
        return false;
    }

    public float getSpeedX()
    {
        return speedX;
    }

    public void setSpeedX(float speedX)
    {
        this.speedX = speedX;
    }

    public float getSpeedY()
    {
        return speedY;
    }

    public void setSpeedY(float speedY)
    {
        this.speedY = speedY;
    }

    public int getDirectionX()
    {
        return directionX;
    }

    public void setDirectionX(int directionX)
    {
        this.directionX = directionX;
    }

    public int getDirectionY()
    {
        return directionY;
    }

    public void setDirectionY(int directionY)
    {
        this.directionY = directionY;
    }
}
