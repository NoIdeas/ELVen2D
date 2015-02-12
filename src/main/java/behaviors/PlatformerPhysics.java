package behaviors;

import entities.RigidBodyEntity;
import world.Gravity;

/**
 * Created by NoIdeas.
 */

public class PlatformerPhysics implements PhysicsBehaviour
{
    private RigidBodyEntity rigidBodyEntity;

    private float frictionX;
    private float frictionY;

    public PlatformerPhysics(RigidBodyEntity rigidBodyEntity)
    {
        this.rigidBodyEntity = rigidBodyEntity;

        this.setFrictionX(0.4f);
        this.setFrictionY(0.2f);
    }

    public void gravity()
    {
        rigidBodyEntity.addForce(Gravity.GRAVITY_X, Gravity.GRAVITY_Y);
    }

    public void friction()
    {
        if (rigidBodyEntity.getVelocityX() != 0)
        {
            if (rigidBodyEntity.getVelocityX() < 0)
                if (rigidBodyEntity.getVelocityX() + this.getFrictionX() > 0)
                    rigidBodyEntity.setVelocityX(0);
                else
                    rigidBodyEntity.addForce(this.getFrictionX(), 0);
            else
            if (rigidBodyEntity.getVelocityX() + this.getFrictionX() < 0)
                rigidBodyEntity.setVelocityX(0);
            else
                rigidBodyEntity.addForce(-this.getFrictionX(), 0);
        }

        if (rigidBodyEntity.getVelocityY() != 0)
        {
            if (rigidBodyEntity.getVelocityY() < 0)
                if (rigidBodyEntity.getVelocityY() + this.getFrictionY() > 0)
                    rigidBodyEntity.setVelocityY(0);
                else
                    rigidBodyEntity.addForce(0, this.getFrictionY());
            else
            if (rigidBodyEntity.getVelocityY() + this.getFrictionY() < 0)
                rigidBodyEntity.setVelocityY(0);
            else
                rigidBodyEntity.addForce(0, -this.getFrictionY());
        }
    }

    public float getFrictionX()
    {
        return frictionX;
    }

    public void setFrictionX(float frictionX)
    {
        this.frictionX = frictionX;
    }

    public float getFrictionY()
    {
        return frictionY;
    }

    public void setFrictionY(float frictionY)
    {
        this.frictionY = frictionY;
    }
}
