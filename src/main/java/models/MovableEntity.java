package models;
import javafx.geometry.Rectangle2D;

/**
 * Created by NoIdeas.
 */

public abstract class MovableEntity extends Sprite
{
    private CollisionSide colliding = CollisionSide.NONE;
    private int directionX;
    private int directionY;
    private float speedX = 10;
    private float speedY = 10;

    @Override
    public void update(float delay)
    {
        super.update(delay);

        if (!this.tryMove())
        {
            this.move();
        }
    }

    public void move()
    {
        super.setPositionX(super.getPositionX() + (this.getSpeedX() * this.getDirectionX()));
        super.setPositionY(super.getPositionY() + (this.getSpeedY() * this.getDirectionY()));
    }

    public CollisionSide checkCollision()
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

                if(collisionIntersection.getWidth() < collisionIntersection.getHeight())
                {
                    if(diffX < 0)
                    {
                        super.setPositionX(super.getPositionX() + (float)collisionIntersection.getWidth());
                        this.colliding = CollisionSide.LEFT;

                        return CollisionSide.LEFT;
                    }
                    else
                    {
                        super.setPositionX(super.getPositionX() - (float)collisionIntersection.getWidth());
                        this.colliding = CollisionSide.RIGHT;

                        return CollisionSide.RIGHT;
                    }
                }
                else
                {
                    if(diffY < 0)
                    {
                        super.setPositionY(super.getPositionY() + (float)collisionIntersection.getHeight());
                        this.colliding = CollisionSide.UP;

                        return CollisionSide.UP;
                    }
                    else
                    {
                        super.setPositionY(super.getPositionY() - (float)collisionIntersection.getHeight());
                        this.colliding = CollisionSide.DOWN;

                        return CollisionSide.DOWN;
                    }
                }
            }
        }

        return CollisionSide.NONE;
    }

    public boolean tryMove()
    {
        boolean moved = false;
        float tempSpeed;
        MoveDirection moveDirection = null;

        Rectangle2D nextStep = null;
        Rectangle2D entityCollisionBox = this.getCollisionBox();

        if (this.getDirectionX() != 0)
        {
            if (this.getDirectionX() < 0)
            {
                nextStep = new Rectangle2D(entityCollisionBox.getMinX() - this.getSpeedX(), entityCollisionBox.getMinY(), this.getSpeedX(), this.getHeight());
                moveDirection = MoveDirection.LEFT;
            }
            else
            {
                nextStep = new Rectangle2D(entityCollisionBox.getMinX() + entityCollisionBox.getWidth(), entityCollisionBox.getMinY(), this.getSpeedX(), this.getHeight());
                moveDirection = MoveDirection.RIGHT;
            }
        }
        else if (this.getDirectionY() != 0)
        {
            if (this.getDirectionY() < 0)
            {
                nextStep = new Rectangle2D(entityCollisionBox.getMinX(), entityCollisionBox.getMinY() - this.getSpeedY(), this.getWidth(), this.getSpeedY());
                moveDirection = MoveDirection.UP;
            }
            else
            {
                nextStep = new Rectangle2D(entityCollisionBox.getMinX(), entityCollisionBox.getMinY() + entityCollisionBox.getHeight(), this.getWidth(), this.getSpeedY());
                moveDirection = MoveDirection.DOWN;
            }
        }

        if (moveDirection != null)
        {
            for (Sprite sprite : gameBoard.sprites)
            {
                Rectangle2D spriteCollisionBox = sprite.getCollisionBox();
                if (spriteCollisionBox.intersects(nextStep))
                {
                    switch (moveDirection)
                    {
                        case LEFT:
                            tempSpeed = (float) nextStep.getMaxX() - (float) spriteCollisionBox.getMaxX();
                            super.setPositionX(super.getPositionX() + (tempSpeed * this.getDirectionX()));
                            break;
                        case RIGHT:
                            tempSpeed = (float) spriteCollisionBox.getMinX() - (float) nextStep.getMinX();
                            super.setPositionX(super.getPositionX() + (tempSpeed * this.getDirectionX()));
                            break;
                        case UP:
                            tempSpeed = (float) nextStep.getMaxY() - (float) spriteCollisionBox.getMaxY();
                            super.setPositionY(super.getPositionY() + (tempSpeed * this.getDirectionY()));
                            break;
                        case DOWN:
                            tempSpeed = (float) spriteCollisionBox.getMinY() - (float) nextStep.getMinY();
                            super.setPositionY(super.getPositionY() + (tempSpeed * this.getDirectionY()));
                            break;
                    }
                    moved = true;
                }
            }
        }

        return moved;
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
                collisionLine = new Rectangle2D(entityCollisionBox.getMaxX() + 1, entityCollisionBox.getMinY(), 1, this.getHeight());
                break;
            case UP:
                collisionLine = new Rectangle2D(entityCollisionBox.getMinX(), entityCollisionBox.getMinY() - 1, this.getWidth(), 1);
                break;
            case DOWN:
                collisionLine = new Rectangle2D(entityCollisionBox.getMinX(), entityCollisionBox.getMaxY() + 1, this.getWidth(), 1);
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

    public float getSpeed()
    {
        if (this.getSpeedY() == this.getSpeedX())
            return this.getSpeedX();
        else
            return 0;
    }

    public void setSpeed(float speed)
    {
        this.setSpeedX(speed);
        this.setSpeedY(speed);
    }

    public float getSpeedX()
    {
        return speedX;
    }

    public void setSpeedX(float speedX)
    {
        this.speedX = speedX;
    }

    public float getSpeedY()
    {
        return speedY;
    }

    public void setSpeedY(float speedY)
    {
        this.speedY = speedY;
    }

    public int getDirectionX()
    {
        return directionX;
    }

    public void setDirectionX(int directionX)
    {
        if (directionX == 1 || directionX == -1 || directionX == 0)
            this.directionX = directionX;
        else
            this.directionX = 0;
    }

    public int getDirectionY()
    {
        return directionY;
    }

    public void setDirectionY(int directionY)
    {
        if (directionY == 1 || directionY == -1 || directionY == 0)
            this.directionY = directionY;
        else
            this.directionY =0;
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
