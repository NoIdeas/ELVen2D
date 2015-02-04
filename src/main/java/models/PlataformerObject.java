package models;

/**
 * Created by NoIdeas.
 */

public class PlataformerObject extends MovableObject
{
    public PlataformerObject(String imagePath, float positionX, float positionY)
    {
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
    }
}
