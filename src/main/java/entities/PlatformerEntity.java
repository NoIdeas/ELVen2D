package entities;

import behaviors.PhysicsBehaviour;
import behaviors.PlatformerPhysics;

/**
 * Created by NoIdeas.
 */

public abstract class PlatformerEntity extends MovableEntity
{
    private PhysicsBehaviour physicsBehaviour;

    private boolean isDying;
    private int timesJumped;

    public PlatformerEntity()
    {
        physicsBehaviour = new PlatformerPhysics(this);

        this.resetJumps();
    }

    @Override
    public void update(float delay)
    {
        if (isDying)
        {
            super.hide();
        }

        physicsBehaviour.gravity();
        physicsBehaviour.friction();

        super.update(delay);
    }

    public void die()
    {
        this.isDying = true;
    }

    public void jump()
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

    public void resetJumps()
    {
        this.timesJumped = 0;
    }
}
