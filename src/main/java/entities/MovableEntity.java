package entities;

import enums.CollisionSide;
import enums.MoveDirection;
import javafx.geometry.Rectangle2D;

/**
 * Created by NoIdeas.
 */

public abstract class MovableEntity extends Sprite
{
    private CollisionSide colliding = CollisionSide.NONE;
    private MoveDirection moveDirection = MoveDirection.STOPPED;
    private float velocityX = 0;
    private float velocityY = 0;
    private float forceX = 0;
    private float forceY = 0;

    @Override
    public void update(float delay)
    {
        super.update(delay);

        if (this.getColliding() == CollisionSide.NONE)
        {
            this.applyForce();
        }
        this.updateMoveDirection();
        this.updateCollisionSide();

        this.tryMove();
        this.move();
        //this.checkCollision();
        this.airFriction();
    }

    private void applyForce()
    {
        this.setVelocityX(this.getVelocityX() + this.getForceX());
        this.setVelocityY(this.getVelocityY() + this.getForceY());
        this.setForceX(0);
        this.setForceY(0);
    }

    private void airFriction()
    {

    }

    private void updateMoveDirection()
    {
        if (this.getVelocityX() != 0 || this.getVelocityY() != 0)
        {
            if (this.getVelocityX() == 0 || this.getVelocityY() == 0)
            {
                if (this.getVelocityX() != 0)
                    this.setMoveDirection(this.getVelocityX() < 0 ? MoveDirection.LEFT : MoveDirection.RIGHT);
                else
                    this.setMoveDirection(this.getVelocityY() < 0 ? MoveDirection.UP : MoveDirection.DOWN);
            }
            else
            {
                if (this.getVelocityX() < 0)
                    this.setMoveDirection(this.getVelocityY() < 0 ? MoveDirection.LEFTUP : MoveDirection.LEFTDOWN);
                else
                    this.setMoveDirection(this.getVelocityY() < 0 ? MoveDirection.RIGHTUP : MoveDirection.RIGHTDOWN);
            }
        }
        else
            this.setMoveDirection(MoveDirection.STOPPED);
    }

    private void updateCollisionSide()
    {
        switch (this.getMoveDirection())
        {
            case LEFT:
                this.setColliding(this.isSideFree(CollisionSide.LEFT) ? CollisionSide.NONE : CollisionSide.LEFT);
                break;
            case RIGHT:
                this.setColliding(this.isSideFree(CollisionSide.RIGHT) ? CollisionSide.NONE : CollisionSide.RIGHT);
                break;
            case UP:
                this.setColliding(this.isSideFree(CollisionSide.UP) ? CollisionSide.NONE : CollisionSide.UP);
                break;
            case DOWN:
                this.setColliding(this.isSideFree(CollisionSide.DOWN) ? CollisionSide.NONE : CollisionSide.DOWN);
                break;
            case STOPPED:
                this.setColliding(CollisionSide.NONE);
                break;
        }
    }

    private void tryMove()
    {
        float absVelocityX = Math.abs(this.getVelocityX());
        float absVelocityY = Math.abs(this.getVelocityY());
        Rectangle2D nextStepRect = null;
        Rectangle2D collisionBox = this.getCollisionBox();

        switch (this.getMoveDirection())
        {
            case LEFT:
                nextStepRect = new Rectangle2D(collisionBox.getMinX() - absVelocityX, collisionBox.getMinY(), absVelocityX, this.getHeight());
                break;
            case RIGHT:
                nextStepRect = new Rectangle2D(collisionBox.getMinX() + collisionBox.getWidth(), collisionBox.getMinY(), absVelocityX, this.getHeight());
                break;
            case UP:
                nextStepRect = new Rectangle2D(collisionBox.getMinX(), collisionBox.getMinY() - absVelocityY, this.getWidth(), absVelocityY);
                break;
            case DOWN:
                nextStepRect = new Rectangle2D(collisionBox.getMinX(), collisionBox.getMinY() + collisionBox.getHeight(), this.getWidth(), absVelocityY);
                break;
        }

        if (nextStepRect != null)
        {
            float tempVelocity;
            for (Sprite sprite : gameBoard.sprites)
            {
                Rectangle2D spriteCollisionBox = sprite.getCollisionBox();
                if (spriteCollisionBox.intersects(nextStepRect))
                {
                    switch (this.getMoveDirection())
                    {
                        case LEFT:
                            tempVelocity = (float) spriteCollisionBox.getMaxX() - (float) nextStepRect.getMaxX();
                            this.setVelocityX(tempVelocity != 0 ? tempVelocity : -this.getVelocityX());
                            break;
                        case RIGHT:
                            tempVelocity = (float) spriteCollisionBox.getMinX() - (float) nextStepRect.getMinX();
                            this.setVelocityX(tempVelocity);
                            break;
                        case UP:
                            tempVelocity = (float) spriteCollisionBox.getMaxY() - (float) nextStepRect.getMaxY();
                            this.setVelocityY(tempVelocity);
                            break;
                        case DOWN:
                            tempVelocity = (float) spriteCollisionBox.getMinY() - (float) nextStepRect.getMinY();
                            //this.setVelocityY(tempVelocity);
                            this.setVelocityY(tempVelocity != 0 ? tempVelocity : this.getVelocityY()/2);
                            break;
                    }
                }
            }
        }
    }

    public void move()
    {
        super.setPositionX(super.getPositionX() + this.getVelocityX());
        super.setPositionY(super.getPositionY() + this.getVelocityY());
    }

    public boolean isSideFree(CollisionSide side)
    {
        Rectangle2D collisionLine = null;
        Rectangle2D entityCollisionBox = this.getCollisionBox();

        switch (side)
        {
            case LEFT:
                collisionLine = new Rectangle2D(entityCollisionBox.getMinX() - 1, entityCollisionBox.getMinY(), 1, this.getHeight());
                break;
            case RIGHT:
                collisionLine = new Rectangle2D(entityCollisionBox.getMaxX(), entityCollisionBox.getMinY(), 1, this.getHeight());
                break;
            case UP:
                collisionLine = new Rectangle2D(entityCollisionBox.getMinX(), entityCollisionBox.getMinY() - 1, this.getWidth(), 1);
                break;
            case DOWN:
                collisionLine = new Rectangle2D(entityCollisionBox.getMinX(), entityCollisionBox.getMaxY(), this.getWidth(), 1);
                break;
            case NONE:
                collisionLine = this.getCollisionBox();
                break;
        }

        for (Sprite sprite : gameBoard.sprites)
        {
            if (sprite.getCollisionBox().intersects(collisionLine))
            {
                return false;
            }
        }

        return true;
    }

    public void checkCollision()
    {
        Rectangle2D entityCollisionBox = this.getCollisionBox();
        float entityCenterX = super.getPositionX() + super.getWidth() / 2;
        float entityCenterY = super.getPositionY() + super.getHeight() / 2;

        for (Sprite obstacle : gameBoard.sprites)
        {
            Rectangle2D obstacleCollisionBox = obstacle.getCollisionBox();
            float obstacleCenterX = obstacle.getPositionX() + obstacle.getWidth() / 2;
            float obstacleCenterY = obstacle.getPositionY() + obstacle.getHeight() / 2;

            if (obstacleCollisionBox.intersects(entityCollisionBox) && obstacle != this)
            {
                Rectangle2D collisionIntersection = intersect(entityCollisionBox, obstacleCollisionBox);
                float diffX = (obstacleCenterX - entityCenterX);
                float diffY = (obstacleCenterY - entityCenterY);

                if (collisionIntersection.getWidth() < collisionIntersection.getHeight())
                {
                    if (diffX < 0)
                    {
                        super.setPositionX(super.getPositionX() + (float) collisionIntersection.getWidth());
                        this.colliding = CollisionSide.LEFT;
                    }
                    else
                    {
                        super.setPositionX(super.getPositionX() - (float) collisionIntersection.getWidth());
                        this.colliding = CollisionSide.RIGHT;
                    }
                }
                else
                {
                    if (diffY < 0)
                    {
                        super.setPositionY(super.getPositionY() + (float) collisionIntersection.getHeight());
                        this.colliding = CollisionSide.UP;
                    }
                    else
                    {
                        super.setPositionY(super.getPositionY() - (float) collisionIntersection.getHeight());
                        this.colliding = CollisionSide.DOWN;
                    }
                }
            }
        }
    }

    public float getVelocity()
    {
        if (this.getVelocityY() == this.getVelocityX())
            return this.getVelocityX();
        else
            return 0;
    }

    public void setVelocity(float speedX, float speedY)
    {
        this.setVelocityX(speedX);
        this.setVelocityY(speedY);
    }

    public float getVelocityX()
    {
        return this.velocityX;
    }

    public void setVelocityX(float velocityX)
    {
        this.velocityX = velocityX;
    }

    public float getVelocityY()
    {
        return this.velocityY;
    }

    public void setVelocityY(float velocityY)
    {
        this.velocityY = velocityY;
    }

    public void addForce(float forceX, float forceY)
    {
        this.forceX += forceX;
        this.forceY += forceY;
    }

    public float getForceX()
    {
        return this.forceX;
    }

    public void setForceX(float forceX)
    {
        this.forceX = forceX;
    }

    public float getForceY()
    {
        return this.forceY;
    }

    public void setForceY(float forceY)
    {
        this.forceY = forceY;
    }

    public MoveDirection getMoveDirection()
    {
        return this.moveDirection;
    }

    public void setMoveDirection(MoveDirection moveDirection)
    {
        this.moveDirection = moveDirection;
    }

    public CollisionSide getColliding()
    {
        return this.colliding;
    }

    public String getStringColliding()
    {
        String r = "NONE";

        switch (colliding)
        {
            case LEFT:
                r = "LEFT";
                break;
            case RIGHT:
                r = "RIGHT";
                break;
            case UP:
                r = "UP";
                break;
            case DOWN:
                r = "DOWN";
                break;
            case NONE:
                r = "NONE";
                break;
        }

        return r;
    }

    public void setColliding(CollisionSide colliding)
    {
        this.colliding = colliding;
    }
}
