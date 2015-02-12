package entities;

/**
 * Created by NoIdeas.
 */

public class SceneEntity extends Sprite
{
    public SceneEntity(String imagePath, float positionX, float positionY)
    {
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
    }
}
