package models;

import main.GameBoard;

/**
 * Created by NoIdeas.
 */

public class PlataformerEntity extends MovableEntity
{
    public PlataformerEntity(GameBoard gameBoard, String imagePath, float positionX, float positionY)
    {
        super.gameBoard = gameBoard;
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
    }

    @Override
    public void update()
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

        if(super.getDirectionX() < 0 && !super.isSideFree(CollisionSide.LEFT))
        {
            super.setDirectionX(0);
            super.setColliding(CollisionSide.LEFT);
        }
        else if(super.getDirectionX() > 0 && !super.isSideFree(CollisionSide.RIGHT))
        {
            super.setDirectionX(0);
            super.setColliding(CollisionSide.RIGHT);
        }

        if(super.getDirectionY() < 0 && !super.isSideFree(CollisionSide.UP))
        {
            super.setDirectionY(0);
            super.setColliding(CollisionSide.UP);
        }
        else if(super.getDirectionY() > 0 && !super.isSideFree(CollisionSide.DOWN))
        {
            super.setDirectionY(0);
            super.setColliding(CollisionSide.DOWN);
        }

        super.update();
        super.checkCollision();
    }
}
