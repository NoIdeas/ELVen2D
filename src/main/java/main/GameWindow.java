package main;

import models.Sprite;

import javax.swing.*;

/**
 * Created by duds410.
 */

public class GameWindow extends JFrame
{

    public GameWindow()
    {
        this.add(new GameBoard());

        setSize(500, 500);
        setTitle("Game Test");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Sprite s = new Sprite();
        s.show();
    }
}
