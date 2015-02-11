package entities;

import enums.CollisionSide;
import helpers.KeyboardListener;
import main.GameBoard;

/**
 * Created by NoIdeas.
 */

public class PlatformerEntity extends MovableEntity
{
    private int timesJumped = 0;

    public PlatformerEntity(GameBoard gameBoard, String imagePath, float positionX, float positionY, float acceleration)
    {
        super.gameBoard = gameBoard;
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
        super.setAccelerationX(acceleration);
        super.setAccelerationY(acceleration);
    }

    @Override
    public void update(float delay)
    {
        KeyboardListener keyboardListener = KeyboardListener.getInstance();

        super.resetForce();

        if (super.getCollisionSide() == CollisionSide.DOWN)
            this.timesJumped = 0;

        if (keyboardListener.isLeftKeyPressed())
            super.addForce(-getAccelerationX(), 0.0f);
        if (keyboardListener.isRigthKeyPressed())
            super.addForce(getAccelerationX(), 0.0f);
        if (keyboardListener.isUpKeyPressed())
            this.jump();
        if (keyboardListener.isDownKeyPressed())
            super.addForce(0.0f, getAccelerationY());

        

        super.update(delay);
    }

    private void jump()
    {
        if (timesJumped <= 3)
        {
            super.setVelocityY(0);
            super.addForce(0, -10);
            this.timesJumped++;
            System.out.println("JUMPED BITCH");
        }
        System.out.println("enough..");
    }
}
