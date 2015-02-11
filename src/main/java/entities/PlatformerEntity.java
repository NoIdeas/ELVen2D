package entities;

import enums.CollisionSide;
import helpers.KeyboardListener;
import main.GameBoard;

import java.awt.event.KeyEvent;

/**
 * Created by NoIdeas.
 */

public class PlatformerEntity extends MovableEntity
{
    private boolean hasJumped = false;

    public PlatformerEntity(GameBoard gameBoard, String imagePath, float positionX, float positionY, float speedX, float speedY)
    {
        super.gameBoard = gameBoard;
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
        super.setSpeedX(speedX);
        super.setSpeedY(speedY);
    }

    @Override
    public void update(float delay)
    {
        KeyboardListener keyboardListener = KeyboardListener.getInstance();

        super.setForce(0,0);

        if (super.getCollisionSide() == CollisionSide.DOWN)
            hasJumped = false;

        if (keyboardListener.isLeftKeyPressed())
            super.addForce(-getSpeedX(), 0);
        if (keyboardListener.isRigthKeyPressed())
            super.addForce(getSpeedX(),0);

        if (keyboardListener.isUpKeyPressed())
            this.jump();
        if (keyboardListener.isDownKeyPressed())
            super.addForce(0, getSpeedY());

        super.update(delay);
    }

    private void jump()
    {
        if (!hasJumped)
        {
            super.addForce(0, -10);
            this.hasJumped = true;
        }
    }
}
