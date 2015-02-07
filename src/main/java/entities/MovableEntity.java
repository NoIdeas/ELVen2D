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
    private int directionX;
    private int directionY;
    private float speedX = 10;
    private float speedY = 10;
    MoveDirection moveDirection = MoveDirection.STOPPED;

    @Override
    public void update(float delay)
    {
        super.update(delay);

        this.isSideFree();
        if (!this.tryMove())
        {
            this.move();
        }
        this.checkCollision();
    }

    public void move()
    {
        super.setPositionX(super.getPositionX() + (this.getSpeedX() * this.getDirectionX()));
        super.setPositionY(super.getPositionY() + (this.getSpeedY() * this.getDirectionY()));
    }

    public boolean tryMove()
    {
        boolean moved = false;
        float tempSpeed;

        Rectangle2D nextStep = null;
        Rectangle2D entityCollisionBox = this.getCollisionBox();

        if ((this.getDirectionX() == 0 && this.getDirectionY() != 0) || (this.getDirectionY() == 0 && this.getDirectionX() != 0))
        {
            if (this.getDirectionX() != 0)
            {
                if (this.getDirectionX() < 0)
                {
                    nextStep = new Rectangle2D(entityCollisionBox.getMinX() - this.getSpeedX(), entityCollisionBox.getMinY(), this.getSpeedX(), this.getHeight());
                    this.setMoveDirection(MoveDirection.LEFT);
                }
                else
                {
                    nextStep = new Rectangle2D(entityCollisionBox.getMinX() + entityCollisionBox.getWidth(), entityCollisionBox.getMinY(), this.getSpeedX(), this.getHeight());
                    this.setMoveDirection(MoveDirection.RIGHT);
                }
            }
            else
            {
                if (this.getDirectionY() < 0)
                {
                    nextStep = new Rectangle2D(entityCollisionBox.getMinX(), entityCollisionBox.getMinY() - this.getSpeedY(), this.getWidth(), this.getSpeedY());
                    this.setMoveDirection(MoveDirection.UP);
                }
                else
                {
                    nextStep = new Rectangle2D(entityCollisionBox.getMinX(), entityCollisionBox.getMinY() + entityCollisionBox.getHeight(), this.getWidth(), this.getSpeedY());
                    this.setMoveDirection(MoveDirection.DOWN);
                }
            }
        }
        else if ((this.getDirectionX() < 0 || this.getDirectionX() > 0) && (this.getDirectionY() < 0 || this.getDirectionY() > 0))
        {
            if (this.getDirectionX() < 0)
            {
                if (this.getDirectionY() < 0)
                {
                    this.setMoveDirection(MoveDirection.LEFTUP);
                }
                else
                {
                    this.setMoveDirection(MoveDirection.LEFTDOWN);
                }
            }
            else if (this.getDirectionX() > 0)
            {
                if (this.getDirectionY() < 0)
                {
                    this.setMoveDirection(MoveDirection.RIGHTUP);
                }
                else
                {
                    this.setMoveDirection(MoveDirection.RIGHTDOWN);
                }
            }
        }
        else
            this.setMoveDirection(MoveDirection.STOPPED);

        if (nextStep != null)
        {
            for (Sprite sprite : gameBoard.sprites)
            {
                Rectangle2D spriteCollisionBox = sprite.getCollisionBox();
                if (spriteCollisionBox.intersects(nextStep))
                {
                    switch (this.getMoveDirection())
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

    public void isSideFree()
    {
        if(this.getDirectionX() < 0 && !this.isSideFree(CollisionSide.LEFT))
        {
            this.setDirectionX(0);
            this.setColliding(CollisionSide.LEFT);
        }
        else if(this.getDirectionX() > 0 && !this.isSideFree(CollisionSide.RIGHT))
        {
            this.setDirectionX(0);
            this.setColliding(CollisionSide.RIGHT);
        }

        if(this.getDirectionY() < 0 && !this.isSideFree(CollisionSide.UP))
        {
            this.setDirectionY(0);
            this.setColliding(CollisionSide.UP);
        }
        else if(this.getDirectionY() > 0 && !this.isSideFree(CollisionSide.DOWN))
        {
            this.setDirectionY(0);
            this.setColliding(CollisionSide.DOWN);
        }
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

                if(collisionIntersection.getWidth() < collisionIntersection.getHeight())
                {
                    if(diffX < 0)
                    {
                        super.setPositionX(super.getPositionX() + (float)collisionIntersection.getWidth());
                        this.colliding = CollisionSide.LEFT;
                    }
                    else
                    {
                        super.setPositionX(super.getPositionX() - (float)collisionIntersection.getWidth());
                        this.colliding = CollisionSide.RIGHT;
                    }
                }
                else
                {
                    if(diffY < 0)
                    {
                        super.setPositionY(super.getPositionY() + (float)collisionIntersection.getHeight());
                        this.colliding = CollisionSide.UP;
                    }
                    else
                    {
                        super.setPositionY(super.getPositionY() - (float)collisionIntersection.getHeight());
                        this.colliding = CollisionSide.DOWN;
                    }
                }
            }
        }
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

    public MoveDirection getMoveDirection()
    {
        return this.moveDirection;
    }

    public void setMoveDirection(MoveDirection moveDirection)
    {
        this.moveDirection = moveDirection;
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
