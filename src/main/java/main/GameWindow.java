package main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by NoIdeas.
 */

public class GameWindow extends JFrame
{
    public GameWindow()
    {
        this.add(new GameBoard());

        pack();
        setTitle("Elven2D");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            JFrame gw = new GameWindow();
            gw.setVisible(true);
        });
    }
}
