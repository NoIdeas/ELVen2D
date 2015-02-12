package behaviors;

import entities.MovableEntity;
import world.Gravity;

/**
 * Created by NoIdeas.
 */

public class PlatformerPhysics implements PhysicsBehaviour
{
    private MovableEntity movableEntity;

    private float frictionX;
    private float frictionY;

    public PlatformerPhysics(MovableEntity movableEntity)
    {
        this.movableEntity = movableEntity;

        this.setFrictionX(0.4f);
        this.setFrictionY(0.2f);
    }

    public void gravity()
    {
        movableEntity.addForce(Gravity.GRAVITY_X, Gravity.GRAVITY_Y);
    }

    public void friction()
    {
        if (movableEntity.getVelocityX() != 0)
        {
            if (movableEntity.getVelocityX() < 0)
                if (movableEntity.getVelocityX() + this.getFrictionX() > 0)
                    movableEntity.setVelocityX(0);
                else
                    movableEntity.addForce(this.getFrictionX(), 0);
            else
            if (movableEntity.getVelocityX() + this.getFrictionX() < 0)
                movableEntity.setVelocityX(0);
            else
                movableEntity.addForce(-this.getFrictionX(), 0);
        }

        if (movableEntity.getVelocityY() != 0)
        {
            if (movableEntity.getVelocityY() < 0)
                if (movableEntity.getVelocityY() + this.getFrictionY() > 0)
                    movableEntity.setVelocityY(0);
                else
                    movableEntity.addForce(0, this.getFrictionY());
            else
            if (movableEntity.getVelocityY() + this.getFrictionY() < 0)
                movableEntity.setVelocityY(0);
            else
                movableEntity.addForce(0, -this.getFrictionY());
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
