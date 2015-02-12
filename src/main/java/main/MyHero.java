package main;

import entities.PlatformerEntity;
import enums.CollisionSide;
import helpers.KeyboardListener;

/**
 * Created by NoIdeas.
 */

public class MyHero extends PlatformerEntity
{
    KeyboardListener keyboardListener = KeyboardListener.getInstance();

    public MyHero(String imagePath, float positionX, float positionY, float acceleration)
    {
        super.setImage(imagePath);
        super.setPositionX(positionX);
        super.setPositionY(positionY);
        super.setAccelerationX(acceleration);
        super.setAccelerationY(acceleration);
    }

    @Override
    public void update(float delay)
    {
        super.resetForce();
        if (super.getCollisionSide() == CollisionSide.DOWN)
            resetJumps();

        if (this.keyboardListener.isLeftKeyPressed())
            super.addForce(-super.getAccelerationX(), 0.0f);
        if (this.keyboardListener.isRightKeyPressed())
            super.addForce(super.getAccelerationX(), 0.0f);
        if (this.keyboardListener.isUpKeyPressed())
            super.jump();
        if (this.keyboardListener.isDownKeyPressed())
            super.addForce(0.0f, super.getAccelerationY());

        super.update(delay);
    }




}
