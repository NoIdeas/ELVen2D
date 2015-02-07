package entities;

import enums.CollisionSide;
import helpers.KeyboardListener;
import main.GameBoard;

/**
 * Created by NoIdeas.
 */

public class PlatformerEntity extends MovableEntity
{
    public PlatformerEntity(GameBoard gameBoard, String imagePath, float positionX, float positionY)
    {
        super.gameBoard = gameBoard;
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
    }

    @Override
    public void update(float delay)
    {
        KeyboardListener keyboardListener = KeyboardListener.getInstance();

        if (keyboardListener.isLeftKeyPressed())
        {
            super.setDirectionX(-1);
            super.setColliding(CollisionSide.NONE);
        }
        if (keyboardListener.isRigthKeyPressed())
        {
            super.setDirectionX(1);
            super.setColliding(CollisionSide.NONE);
        }
        if (keyboardListener.isLeftKeyPressed() == keyboardListener.isRigthKeyPressed())
        {
            super.setDirectionX(0);
            super.setColliding(CollisionSide.NONE);
        }

        if (keyboardListener.isUpKeyPressed())
        {
            super.setDirectionY(-1);
            super.setColliding(CollisionSide.NONE);
        }
        if (keyboardListener.isDownKeyPressed())
        {
            super.setDirectionY(1);
            super.setColliding(CollisionSide.NONE);
        }
        if (keyboardListener.isUpKeyPressed() == keyboardListener.isDownKeyPressed())
        {
            super.setDirectionY(0);
            super.setColliding(CollisionSide.NONE);
        }

        super.update(delay);
    }
}
