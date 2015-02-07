package entities;

import main.GameBoard;

/**
 * Created by NoIdeas.
 */

public class SceneEntity extends Sprite
{
    public SceneEntity(GameBoard gameBoard, String imagePath, float positionX, float positionY)
    {
        super.gameBoard = gameBoard;
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
    }
}
