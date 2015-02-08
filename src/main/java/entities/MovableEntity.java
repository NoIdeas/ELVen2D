package entities;

import enums.CollisionSide;
import enums.MoveDirection;
import javafx.geometry.Rectangle2D;
import world.Gravity;

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

        this.checkMoveDirection();
        this.findCollisionSide();

        if (!this.tryMove())
        {
            this.move();
        }

        if (this.getColliding() != CollisionSide.NONE)
        {
            this.setMoveDirection(MoveDirection.STOPPED);
        }

        //this.checkCollision();
        //this.doGravity();
    }

    public void doGravity()
    {
        if (this.getColliding() != CollisionSide.DOWN )
        {
            this.forceY += Gravity.gravityY;
            //this.directionY = 1;
        }
        else
        {
            this.forceY = 0;
        }
    }

    public void checkMoveDirection()
    {
        if (this.getColliding() == CollisionSide.NONE)
        {
            this.setVelocityX(this.getVelocityX() + this.getForceX());
            this.setVelocityY(this.getVelocityY() + this.getForceY());
            this.setForceX(0);
            this.setForceY(0);
        }

        if (this.getVelocityX() != 0 || this.getVelocityY() != 0)
        {
            if (this.getVelocityX() == 0 || this.getVelocityY() == 0)
            {
                if (this.getVelocityX() != 0)
                {
                    if (this.getVelocityX() < 0)
                    {
                        this.setMoveDirection(MoveDirection.LEFT);
                    }
                    else if (this.getVelocityX() > 0)
                    {
                        this.setMoveDirection(MoveDirection.RIGHT);
                    }
                }
                else
                {
                    if (this.getVelocityY() < 0)
                    {
                        this.setMoveDirection(MoveDirection.UP);
                    }
                    else if (this.getVelocityY() > 0)
                    {
                        this.setMoveDirection(MoveDirection.DOWN);
                    }
                }
            }
        }
        else
            this.setMoveDirection(MoveDirection.STOPPED);
    }

    public void findCollisionSide()
    {
        if (this.getMoveDirection() == MoveDirection.LEFT && !this.isSideFree(CollisionSide.LEFT))
        {
            this.setColliding(CollisionSide.LEFT);
        }
        else if (this.getMoveDirection() == MoveDirection.RIGHT && !this.isSideFree(CollisionSide.RIGHT))
        {
            this.setColliding(CollisionSide.RIGHT);
        }

        if (this.getMoveDirection() == MoveDirection.UP && !this.isSideFree(CollisionSide.UP))
        {
            this.setColliding(CollisionSide.UP);
        }
        else if (this.getMoveDirection() == MoveDirection.DOWN && !this.isSideFree(CollisionSide.DOWN))
        {
            this.setColliding(CollisionSide.DOWN);
        }

        if (this.getMoveDirection() == MoveDirection.STOPPED)
        {
            this.setColliding(CollisionSide.NONE);
        }
    }

    public boolean tryMove()
    {
        boolean moved = false;

        float absVelocityX = Math.abs(this.getVelocityX());
        float absVelocityY = Math.abs(this.getVelocityY());
        Rectangle2D nextStep = null;
        Rectangle2D entityCollisionBox = this.getCollisionBox();

        switch (this.getMoveDirection())
        {
            case LEFT:
                nextStep = new Rectangle2D(entityCollisionBox.getMinX() - absVelocityX, entityCollisionBox.getMinY(), absVelocityX, this.getHeight());
                break;
            case RIGHT:
                nextStep = new Rectangle2D(entityCollisionBox.getMinX() + entityCollisionBox.getWidth(), entityCollisionBox.getMinY(), absVelocityX, this.getHeight());
                break;
            case UP:
                nextStep = new Rectangle2D(entityCollisionBox.getMinX(), entityCollisionBox.getMinY() - absVelocityY, this.getWidth(), absVelocityY);
                break;
            case DOWN:
                nextStep = new Rectangle2D(entityCollisionBox.getMinX(), entityCollisionBox.getMinY() + entityCollisionBox.getHeight(), this.getWidth(), absVelocityY);
                break;
        }

        if (nextStep != null)
        {
            float tempVelocity = 0.0f;
            for (Sprite sprite : gameBoard.sprites)
            {
                Rectangle2D spriteCollisionBox = sprite.getCollisionBox();
                if (spriteCollisionBox.intersects(nextStep))
                {
                    switch (this.getMoveDirection())
                    {
                        case LEFT:
                            tempVelocity = (float) nextStep.getMaxX() - (float) spriteCollisionBox.getMaxX();
                            super.setPositionX(super.getPositionX() - tempVelocity);
                            super.setPositionY(super.getPositionY() - this.getVelocityY());
                            break;
                        case RIGHT:
                            tempVelocity = (float) spriteCollisionBox.getMinX() - (float) nextStep.getMinX();
                            super.setPositionX(super.getPositionX() + tempVelocity);
                            super.setPositionY(super.getPositionY() - this.getVelocityY());
                            break;
                        case UP:
                            tempVelocity = (float) nextStep.getMaxY() - (float) spriteCollisionBox.getMaxY();
                            super.setPositionY(super.getPositionY() - tempVelocity);
                            super.setPositionX(super.getPositionX() - this.getVelocityX());
                            break;
                        case DOWN:
                            tempVelocity = (float) spriteCollisionBox.getMinY() - (float) nextStep.getMinY();
                            super.setPositionY(super.getPositionY() + tempVelocity);
                            super.setPositionX(super.getPositionX() - this.getVelocityX());
                            break;
                    }
                    if (tempVelocity > 0.0f)
                        moved = true;
                    else
                        this.setVelocity(0,0);
                }
            }
        }

        return moved;
    }

    public void move()
    {
        super.setPositionX(super.getPositionX() + this.getVelocityX());
        super.setPositionY(super.getPositionY() + this.getVelocityY());
    }

    public boolean isSideFree()
    {
        if (this.getVelocityX() < 0 && this.isSideFree(CollisionSide.LEFT))
        {
            return true;
        }
        else if (this.getVelocityX() > 0 && this.isSideFree(CollisionSide.RIGHT))
        {
            return true;
        }

        if (this.getVelocityY() < 0 && this.isSideFree(CollisionSide.UP))
        {
            return true;
        }
        else if (this.getVelocityY() > 0 && this.isSideFree(CollisionSide.DOWN))
        {
            return true;
        }

        return false;
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
            if (sprite.getCollisionBox().intersects(collisionLine) && sprite != this)
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
