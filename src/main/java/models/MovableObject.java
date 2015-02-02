package models;

import java.util.Timer;

/**
 * Created by NoIdeas.
 */
public abstract class MovableObject extends Sprite
{
    //private int direction;
    private float speedX;
    private float speedY;

    public void movableAction()
    {
        this.speedX = 10;
        this.speedY = 10;

        super.setPositionX(super.getPositionX()+this.speedX);
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
}
