package main;

import entities.PlatformerEntity;
import enums.CollisionSide;
import helpers.KeyboardControlled;
import helpers.KeyboardListener;

import java.awt.event.KeyEvent;

/**
 * Created by NoIdeas.
 */

public class MyHero extends PlatformerEntity implements KeyboardControlled
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
        if (super.isDying())
            super.hide();

        super.resetForce();
        super.getPhysics().resetFriction();

        if (this.keyboardListener.isLeftKeyPressed())
            super.addForce(-super.getAccelerationX(), 0.0f);
        if (this.keyboardListener.isRightKeyPressed())
            super.addForce(super.getAccelerationX(), 0.0f);
        if (this.keyboardListener.isDownKeyPressed())
            super.addForce(0.0f, super.getAccelerationY());

        if (super.getCollisionSide() == CollisionSide.DOWN)
            resetJumps();
        if (super.isJumping())
            this.jump();

        if (this.getCollisionSide() == CollisionSide.DOWN)
            super.getPhysics().addFriction(0.3f, 0.0f);


        super.update(delay);
    }

    public void jump()
    {
        if (super.getTimesJumped() < 1)
        {
            super.setVelocityY(0);
            super.addForce(0, -10);
            super.addTimesJumped();
            System.out.println("JUMPED BITCH");
        }
        super.setJumping(false);
    }


    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            super.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
