package helpers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by NoIdeas.
 */

public class KeyboardListener extends KeyAdapter
{
    private boolean leftKeyPressed = false;
    private boolean rightKeyPressed = false;
    private boolean upKeyPressed = false;
    private boolean downKeyPressed = false;

    private static KeyboardListener instance;

    public static KeyboardListener getInstance()
    {
        if (instance == null)
            instance = new KeyboardListener();

        return instance;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            leftKeyPressed = true;

        if (key == KeyEvent.VK_RIGHT)
            rightKeyPressed = true;

        if (key == KeyEvent.VK_UP)
            upKeyPressed = true;

        if (key == KeyEvent.VK_DOWN)
            downKeyPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            leftKeyPressed = false;

        if (key == KeyEvent.VK_RIGHT)
            rightKeyPressed = false;

        if (key == KeyEvent.VK_UP)
            upKeyPressed = false;

        if (key == KeyEvent.VK_DOWN)
            downKeyPressed = false;
    }

    public boolean isLeftKeyPressed()
    {
        return leftKeyPressed;
    }

    public boolean isRightKeyPressed()
    {
        return rightKeyPressed;
    }

    public boolean isUpKeyPressed()
    {
        return upKeyPressed;
    }

    public boolean isDownKeyPressed()
    {
        return downKeyPressed;
    }
}
