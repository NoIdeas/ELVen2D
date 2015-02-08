package entities;

import enums.CollisionSide;
import helpers.KeyboardListener;
import main.GameBoard;

/**
 * Created by NoIdeas.
 */

public class PlatformerEntity extends MovableEntity
{
    private float speedX;
    private float speedY;

    public PlatformerEntity(GameBoard gameBoard, String imagePath, float positionX, float positionY, float speedX, float speedY)
    {
        super.gameBoard = gameBoard;
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
        this.setSpeedX(speedX);
        this.setSpeedY(speedY);
    }

    @Override
    public void update(float delay)
    {
        KeyboardListener keyboardListener = KeyboardListener.getInstance();

        if (keyboardListener.isLeftKeyPressed())
        {
            super.addForce(-getSpeedX(), 0);
            super.setColliding(CollisionSide.NONE);
        }
        if (keyboardListener.isRigthKeyPressed())
        {
            super.addForce(getSpeedX(),0);
            super.setColliding(CollisionSide.NONE);
        }

        if (keyboardListener.isUpKeyPressed())
        {
            super.addForce(0, -getSpeedY());
            super.setColliding(CollisionSide.NONE);
        }
        if (keyboardListener.isDownKeyPressed())
        {
            super.addForce(0, getSpeedY());
            super.setColliding(CollisionSide.NONE);
        }

        super.setVelocity(0,0);
        super.update(delay);
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
