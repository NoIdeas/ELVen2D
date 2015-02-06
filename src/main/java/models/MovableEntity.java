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

    public void update()
    {
        super.setPositionX(super.getPositionX() + (this.getSpeedX() * this.getDirectionX()));
        super.setPositionY(super.getPositionY() + (this.getSpeedY() * this.getDirectionY()));
    }

    public CollisionSide checkCollision()
    {
        Rectangle2D entityRectangle = this.getBounds();
        float entityCenterX = super.getPositionX() + super.getWidth() / 2;
        float entityCenterY = super.getPositionY() + super.getHeight() / 2;

        for (Sprite obstacle : gameBoard.sprites)
        {
            Rectangle2D obstacleRectangle = obstacle.getBounds();
            float obstacleCenterX = obstacle.getPositionX() + obstacle.getWidth() / 2;
            float obstacleCenterY = obstacle.getPositionY() + obstacle.getHeight() / 2;

            if (obstacleRectangle.intersects(entityRectangle) && obstacle != this)
            {
                Rectangle2D collisionIntersection = intersect(entityRectangle, obstacleRectangle);
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

    public boolean isSideFree(CollisionSide side)
    {
        Rectangle2D area;

        if(side == CollisionSide.LEFT)
        {
            area = new Rectangle2D(this.getBounds().getMinX() - 1, this.getBounds().getMinY(), 1, this.getHeight());
        }
        else if(side == CollisionSide.RIGHT)
        {
            area = new Rectangle2D(this.getBounds().getMaxX() + 1, this.getBounds().getMinY(), 1, this.getHeight());
        }
        else if(side == CollisionSide.UP)
        {
            area = new Rectangle2D(this.getBounds().getMinX(), this.getBounds().getMinY() - 1, this.getWidth(), 1);
        }
        else
        {
            area = new Rectangle2D(this.getBounds().getMinX(), this.getBounds().getMaxY() + 1, this.getWidth(), 1);
        }

        for (Sprite sprite : gameBoard.sprites)
        {
            if (sprite.getBounds().intersects(area) && sprite != this)
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
