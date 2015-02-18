package entities;

import behaviors.PlatformerPhysics;

/**
 * Created by NoIdeas.
 */

public abstract class PlatformerEntity extends RigidBodyEntity
{
    private boolean dying;
    private boolean jumping;
    private int timesJumped;

    public PlatformerEntity()
    {
        super.physicsBehaviour = new PlatformerPhysics(this);

        this.setDying(false);
        this.setJumping(false);
        this.resetJumps();
    }

    @Override
    public void update(float delay)
    {
        this.getPhysics().gravity();
        this.getPhysics().friction();

        super.update(delay);
    }

    public PlatformerPhysics getPhysics()
    {
        return (PlatformerPhysics)super.physicsBehaviour;
    }

    protected void die()
    {
        this.dying = true;
    }

    protected void jump()
    {
        this.jumping = true;
    }

    public void resetJumps()
    {
        this.timesJumped = 0;
    }

    public boolean isDying()
    {
        return dying;
    }

    public void setDying(boolean dying)
    {
        this.dying = dying;
    }

    public boolean isJumping()
    {
        return jumping;
    }

    public void setJumping(boolean jumping)
    {
        this.jumping = jumping;
    }

    public int getTimesJumped()
    {
        return timesJumped;
    }

    public void addTimesJumped()
    {
        this.timesJumped++;
    }
}
