package models;

import main.GameBoard;

/**
 * Created by NoIdeas.
 */

public class PlataformerObject extends MovableObject
{
    public PlataformerObject(GameBoard gameBoard, String imagePath, float positionX, float positionY)
    {
        super.gameBoard = gameBoard;
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
    }

    @Override
    public void moveAction()
    {
        KeyboardListener keyboardListener = KeyboardListener.getInstance();

        if (keyboardListener.isLeftKeyPressed())
            super.setDirectionX(-1);
        if (keyboardListener.isRigthKeyPressed())
            super.setDirectionX(1);
        if (keyboardListener.isLeftKeyPressed() == keyboardListener.isRigthKeyPressed())
            super.setDirectionX(0);

        if (keyboardListener.isUpKeyPressed())
            super.setDirectionY(-1);
        if (keyboardListener.isDownKeyPressed())
            super.setDirectionY(1);
        if (keyboardListener.isUpKeyPressed() == keyboardListener.isDownKeyPressed())
            super.setDirectionY(0);

        if(super.getDirectionX() < 0 && !super.isSideFree(CollisionSide.LEFT))
            super.setDirectionX(0);
        if(super.getDirectionX() > 0 && !super.isSideFree(CollisionSide.RIGHT))
            super.setDirectionX(0);

        if(super.getDirectionY() < 0 && !super.isSideFree(CollisionSide.UP))
            super.setDirectionY(0);
        if(super.getDirectionY() > 0 && !super.isSideFree(CollisionSide.DOWN))
            super.setDirectionY(0);

        super.moveAction();

        super.checkCollision();
    }
}
