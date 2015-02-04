package models;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by duds410.
 */

public class KeyboardListener extends KeyAdapter
{
    private boolean leftKeyPressed = false;
    private boolean rigthKeyPressed = false;
    private boolean upKeyPressed = false;
    private boolean downpKeyPressed = false;

    private static KeyboardListener instance;

    public static KeyboardListener getInstance()
    {
        if (instance == null)
        {
            instance = new KeyboardListener();
        }

        return instance;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            leftKeyPressed = true;

        if (key == KeyEvent.VK_RIGHT)
            rigthKeyPressed = true;

        if (key == KeyEvent.VK_UP)
            upKeyPressed = true;

        if (key == KeyEvent.VK_DOWN)
            downpKeyPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            leftKeyPressed = false;

        if (key == KeyEvent.VK_RIGHT)
            rigthKeyPressed = false;

        if (key == KeyEvent.VK_UP)
            upKeyPressed = false;

        if (key == KeyEvent.VK_DOWN)
            downpKeyPressed = false;
    }

    public boolean isLeftKeyPressed()
    {
        return leftKeyPressed;
    }

    public boolean isRigthKeyPressed()
    {
        return rigthKeyPressed;
    }

    public boolean isUpKeyPressed()
    {
        return upKeyPressed;
    }

    public boolean isDownKeyPressed()
    {
        return downpKeyPressed;
    }
}
