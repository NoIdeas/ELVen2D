package models;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by NoIdeas.
 */

public class SpriteAnimation
{
    private ArrayList<Image> frames;
    /**
     * Delay between frames, in milliseconds
     */
    private float frameDelay;
    /**
     * The current delay of the animation, between [0, duration)
     */
    private float currentDelay;

    public SpriteAnimation()
    {
        frames = new ArrayList<>();
        frameDelay = 1000.0f / 24;
    }

    /**
     * Updates the animation, advancing the current frame based on a given interval, expressed in milliseconds
     * @param interval The interval to advance the animation by
     */
    public void updateAnimation(float interval)
    {
        currentDelay = (currentDelay + interval) % getDuration();
    }

    public void clear()
    {
        frames.clear();
    }

    public void addFrame(Image image)
    {
        frames.add(image);
    }

    public void addFrameFromPath(String path)
    {
        URL imageUrl = this.getClass().getClassLoader().getResource(path);
        Image image = new ImageIcon(imageUrl).getImage();

        addFrame(image);
    }

    public void removeFrame(Image image)
    {
        frames.remove(image);
    }

    public void removeFrameAt(int index)
    {
        frames.remove(index);
    }

    public Image frameAt(int index)
    {
        return frames.get(index);
    }

    public int getFrameCount()
    {
        return frames.size();
    }

    /**
     * Gets this animation's frames
     * @return An array containing this animation's frames
     */
    public Image[] getImages()
    {
        return (Image[])frames.toArray();
    }

    /**
     * Gets the image representing the current frame of this animation.
     * Returns null, in case the animation has no frames
     * @return The image representing this animation's current frame, or null, if there are no frames in the animation
     */
    public Image getCurrentFrame()
    {
        if(getFrameCount() == 0)
            return null;

        // Truncate the value so we don't crash with an index out of bounds error
        currentDelay = currentDelay % getDuration();

        return frameAt((int)Math.floor(currentDelay / frameDelay));
    }

    public float getFrameDelay()
    {
        return frameDelay;
    }

    public void setFrameDelay(float frameDelay)
    {
        this.frameDelay = frameDelay;
    }

    public float getCurrentDelay()
    {
        return currentDelay;
    }

    public void setCurrentDelay(float currentDelay)
    {
        this.currentDelay = currentDelay;
    }

    /**
     * Gets the total duration of the animation, expressed in milliseconds.
     * The total duration is a result of the multiplication of the frame delay and the frame count
     * @return The total duration of the animation, in milliseconds
     */
    public float getDuration()
    {
        return frames.size() * frameDelay;
    }
}