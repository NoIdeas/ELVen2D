package main;

import entities.PlatformerEntity;
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
        super.resetForce();

        if (this.keyboardListener.isLeftKeyPressed())
            super.addForce(-super.getAccelerationX(), 0.0f);
        if (this.keyboardListener.isRightKeyPressed())
            super.addForce(super.getAccelerationX(), 0.0f);
        if (this.keyboardListener.isDownKeyPressed())
            super.addForce(0.0f, super.getAccelerationY());

        super.update(delay);
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
