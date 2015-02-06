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

        super.setPositionX(super.getPositionX() + (this.getSpeedX() * this.getDirectionX()));
        super.setPositionY(super.getPositionY() + (this.getSpeedY() * this.getDirectionY()));
    }

    public float getSpeed()
    {
        if (this.getSpeedY() == this.getSpeedX())
        {
            return this.getSpeedX();
        }
        else
        {
            return 0;
        }
    }

    public void setSpeed(float speed)
    {
        this.speedX = speed;
        this.speedY = speed;
    }

    public CollisionSide checkCollision()
    {
        Rectangle2D collisionRectangle = this.getBounds();

        float collisionCenterX = super.getPositionX() + super.getWidth() / 2;
        float collisionCenterY = super.getPositionY() + super.getHeight() / 2;

        for (Sprite sprite : gameBoard.sprites)
        {
            float obstacleCenterX = sprite.getPositionX() + sprite.getWidth() / 2;
            float obstacleCenterY = sprite.getPositionY() + sprite.getHeight() / 2;

            Rectangle2D aqueleRectangle = sprite.getBounds();

            if (sprite.getBounds().intersects(collisionRectangle) && sprite != this)
            {
                float diffX = (obstacleCenterX - collisionCenterX);
                float diffY = (obstacleCenterY - collisionCenterY);

                Rectangle2D intersection = intersect(collisionRectangle, aqueleRectangle);

                if(intersection.getWidth() < intersection.getHeight())
                {
                    if(diffX < 0)
                    {
                        super.setPositionX(super.getPositionX() + (float)intersection.getWidth());
                        this.colliding = CollisionSide.LEFT;
                        return CollisionSide.LEFT;
                    }
                    else
                    {
                        super.setPositionX(super.getPositionX() - (float)intersection.getWidth());
                        this.colliding = CollisionSide.RIGHT;
                        return CollisionSide.RIGHT;
                    }
                }
                else
                {
                    if(diffY < 0)
                    {
                        super.setPositionY(super.getPositionY() + (float)intersection.getHeight());
                        this.colliding = CollisionSide.UP;
                        return CollisionSide.UP;
                    }
                    else
                    {
                        super.setPositionY(super.getPositionY() - (float)intersection.getHeight());
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
        this.directionX = directionX;
    }

    public int getDirectionY()
    {
        return directionY;
    }

    public void setDirectionY(int directionY)
    {
        this.directionY = directionY;
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
