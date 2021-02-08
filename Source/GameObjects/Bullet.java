package GameObjects;

import Main.Game;

import java.awt.*;

public class Bullet extends GameObject
{

    float velocityX = 0;
    float velocityY = 0;

    int frames = 150;

    public Bullet(int x, int y, double angle, float[] velocity)
    {
        super(x, y, angle);
        setVelocity(velocity);
    }

    public Bullet(int x, int y, double angle)
    {
        super(x, y, angle);
        setVelocity();
    }

    @Override
    public void update(Game game)
    {
        // Frame counter for bullet life-span (2.5 sec)
        frames--;
        if (frames <= 0) {game.removeGameObject(this);}

        // Movement
        x += velocityX;
        y += velocityY;

        if (x > 900) {x = 0;}
        if (x < 0) {x = 900;}
        if (y > 900) {y = 0;}
        if (y < 0) {y = 900;}
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int) x, (int) y, 3, 3);

    }

    private void setVelocity()
    {
        velocityX += (7 * Math.cos(angle));
        velocityY += (7 * Math.sin(angle));
    }

    private void setVelocity(float[] velocity)
    {
        velocityX += (7 * Math.cos(angle)) + velocity[0];
        velocityY += (7 * Math.sin(angle)) + velocity[1];
    }

    public Rectangle getHitBox()
    {
        return new Rectangle((int) x, (int) y, 3, 3);
    }

}