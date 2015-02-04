package models;

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
        KeyboardListener keyboardListener = KeyboardListener.getInstance();

        if (keyboardListener.isLeftKeyPressed())
            this.directionX = -1;
        if (keyboardListener.isRigthKeyPressed())
            this.directionX = 1;
        if (keyboardListener.isLeftKeyPressed() == keyboardListener.isRigthKeyPressed())
            this.directionX = 0;

        if (keyboardListener.isUpKeyPressed())
            this.directionY = -1;
        if (keyboardListener.isDownKeyPressed())
            this.directionY = 1;
        if (keyboardListener.isUpKeyPressed() == keyboardListener.isDownKeyPressed())
            this.directionY = 0;

        super.setPositionX(super.getPositionX() + (this.speedX * this.directionX));
        super.setPositionY(super.getPositionY() + (this.speedY * this.directionY));
    }

    public void setSpeed(float speed)
    {
        this.speedX = speed;
        this.speedY = speed;
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
