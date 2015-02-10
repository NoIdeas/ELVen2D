package entities;

import enums.CollisionSide;
import helpers.KeyboardListener;
import main.GameBoard;

/**
 * Created by NoIdeas.
 */

public class PlatformerEntity extends MovableEntity
{
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

        if (keyboardListener.isLeftKeyPressed())
            super.addForce(-getSpeedX(), 0);
        if (keyboardListener.isRigthKeyPressed())
            super.addForce(getSpeedX(),0);

        if (keyboardListener.isUpKeyPressed())
            super.addForce(0, -getSpeedY());
        if (keyboardListener.isDownKeyPressed())
            super.addForce(0, getSpeedY());

        super.update(delay);
    }
}
