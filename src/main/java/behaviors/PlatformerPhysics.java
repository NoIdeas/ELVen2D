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
        this.rigidBodyEntity.addForce(Gravity.GRAVITY_X, Gravity.GRAVITY_Y);
    }

    public void friction()
    {
        if (this.rigidBodyEntity.getVelocityX() != 0)
        {
            if (this.rigidBodyEntity.getVelocityX() < 0)
                if (this.rigidBodyEntity.getVelocityX() + this.getFrictionX() > 0)
                    this.rigidBodyEntity.setVelocityX(0);
                else
                    this.rigidBodyEntity.addForce(this.getFrictionX(), 0);
            else
            if (this.rigidBodyEntity.getVelocityX() + this.getFrictionX() < 0)
                this.rigidBodyEntity.setVelocityX(0);
            else
                this.rigidBodyEntity.addForce(-this.getFrictionX(), 0);
        }

        if (this.rigidBodyEntity.getVelocityY() != 0)
        {
            if (this.rigidBodyEntity.getVelocityY() < 0)
                if (this.rigidBodyEntity.getVelocityY() + this.getFrictionY() > 0)
                    this.rigidBodyEntity.setVelocityY(0);
                else
                    this.rigidBodyEntity.addForce(0, this.getFrictionY());
            else
            if (this.rigidBodyEntity.getVelocityY() + this.getFrictionY() < 0)
                this.rigidBodyEntity.setVelocityY(0);
            else
                this.rigidBodyEntity.addForce(0, -this.getFrictionY());
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
