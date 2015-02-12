package entities;

import behaviors.PhysicsBehaviour;
import behaviors.PlatformerPhysics;
import enums.CollisionSide;

/**
 * Created by NoIdeas.
 */

public abstract class PlatformerEntity extends RigidBodyEntity
{
    private PhysicsBehaviour physicsBehaviour;

    private boolean isJumping;
    private boolean isDying;
    private int timesJumped;

    public PlatformerEntity()
    {
        physicsBehaviour = new PlatformerPhysics(this);

        this.isDying = false;
        this.isJumping = false;
        this.resetJumps();
    }

    @Override
    public void update(float delay)
    {
        if (isDying)
            super.hide();

        if (super.getCollisionSide() == CollisionSide.DOWN)
            resetJumps();
        if (isJumping)
            this.jump();

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
        if (isJumping)
        {
            if (timesJumped < 1)
            {
                super.setVelocityY(0);
                super.addForce(0, -10);
                this.timesJumped++;
                System.out.println("JUMPED BITCH");
            }
            this.isJumping = false;
        }
        else
            this.isJumping = true;
    }

    public void resetJumps()
    {
        this.timesJumped = 0;
    }
}
