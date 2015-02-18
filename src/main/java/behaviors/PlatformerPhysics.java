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
    private float externalFrictionX;
    private float externalFrictionY;

    public PlatformerPhysics(RigidBodyEntity rigidBodyEntity)
    {
        this.rigidBodyEntity = rigidBodyEntity;

        this.setFrictionX(0.2f);
        this.setFrictionY(0.1f);
        this.externalFrictionX = 0;
        this.externalFrictionY = 0;
    }

    public void gravity()
    {
        this.rigidBodyEntity.addForce(Gravity.GRAVITY_X, Gravity.GRAVITY_Y);
    }

    public void friction()
    {
        float frictionX = this.getFrictionX() + this.externalFrictionX;
        if (this.rigidBodyEntity.getVelocityX() != 0)
        {
            if (this.rigidBodyEntity.getVelocityX() < 0)
                if (this.rigidBodyEntity.getVelocityX() + frictionX > 0)
                    this.rigidBodyEntity.setVelocityX(0);
                else
                    this.rigidBodyEntity.addForce(frictionX, 0);
            else
                if (this.rigidBodyEntity.getVelocityX() + frictionX < 0)
                    this.rigidBodyEntity.setVelocityX(0);
                else
                    this.rigidBodyEntity.addForce(-frictionX, 0);
        }

        float frictionY = this.getFrictionY() + this.externalFrictionY;
        if (this.rigidBodyEntity.getVelocityY() != 0)
        {
            if (this.rigidBodyEntity.getVelocityY() < 0)
                if (this.rigidBodyEntity.getVelocityY() + frictionY > 0)
                    this.rigidBodyEntity.setVelocityY(0);
                else
                    this.rigidBodyEntity.addForce(0, frictionY);
            else
                if (this.rigidBodyEntity.getVelocityY() + frictionY < 0)
                    this.rigidBodyEntity.setVelocityY(0);
                else
                    this.rigidBodyEntity.addForce(0, -frictionY);
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

    public void addFriction(float frictionX, float frictionY)
    {
        this.externalFrictionX += frictionX;
        this.externalFrictionY += frictionY;
    }

    public void resetFriction()
    {
        externalFrictionX = 0;
        externalFrictionY = 0;
    }
}
