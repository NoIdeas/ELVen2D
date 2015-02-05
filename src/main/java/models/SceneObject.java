package models;

import main.GameBoard;

/**
 * Created by NoIdeas.
 */

public class SceneObject extends Sprite
{
    private boolean solid;

    public SceneObject(GameBoard gameBoard, String imagePath, float positionX, float positionY)
    {
        super.gameBoard = gameBoard;
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
    }
}
