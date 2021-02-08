package GameObjects;

import Main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class Player extends GameObject
{

    float velocityX = 0;
    float velocityY = 0;

    int bulletCooldownFrames = 0;

    boolean drawFire = false;

    public Player(int x, int y)
    {
        super(x, y, Math.toRadians(270));
    }

    @Override
    public void update(Game game)
    {
        // Rotation from left/right
        if (game.keycodes[KeyEvent.VK_LEFT]) {angle += -.075;}
        if (game.keycodes[KeyEvent.VK_RIGHT]) {angle += .075;}

        // Accelerate and update fire.
        if (game.keycodes[KeyEvent.VK_UP]) {
            velocityX += .3 * Math.cos(angle);
            velocityY += .3 * Math.sin(angle);
            drawFire = true;
        } else {
            drawFire = false;
        }

        // Shoot a bullet
        if (game.keycodes[KeyEvent.VK_SPACE] && bulletCooldownFrames == 0) {
            // Calculate bullet starting position and add cooldown frames.
            int bulletX = (int) (30 * Math.cos(angle)) + (int) x;
            int bulletY = (int) (30 * Math.sin(angle)) + (int) y;
            bulletCooldownFrames += 25;

            game.addGameObject(new Bullet(bulletX, bulletY, angle, new float[]{velocityX, velocityY}));
        }

        if (bulletCooldownFrames > 0) {bulletCooldownFrames--;}

        // Velocity clamp
        if (velocityX > 8) { velocityX = 8;}
        if (velocityX < -8) { velocityX = -8;}
        if (velocityY > 8) { velocityY = 8;}
        if (velocityY < -8) { velocityY = -8;}

        // Movement
        x += velocityX;
        y += velocityY;

        // Friction
        velocityX *= .99;
        velocityY *= .99;

        if (x > 900) {x = 0;}
        if (x < 0) {x = 900;}
        if (y > 900) {y = 0;}
        if (y < 0) {y = 900;}
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        AffineTransform old = g2d.getTransform();

        g2d.rotate(angle, x, y);
        g2d.setColor(Color.WHITE);
        g2d.drawPolygon(getPlayerPolygon());
        if (drawFire)
        {
            g2d.setColor(Color.RED);
            g2d.drawPolygon(getPlayerFire());
        }
        g2d.setTransform(old);
    }

    private Polygon getPlayerPolygon()
    {
        int[] px = {((int) x) + 20, ((int) x) - 20, ((int) x) - 20};
        int[] py = {((int) y), ((int) y) + 15, ((int) y) - 15};

        return new Polygon(px, py, 3);
    }

    private Polygon getPlayerFire()
    {
        int[] px = {((int) x) - 21, ((int) x) - 21, ((int) x) - 35};
        int[] py = {((int) y) + 10, ((int) y) - 10, ((int) y)};

        return new Polygon(px, py, 3);
    }

    public Rectangle getHitBox()
    {
        return new Rectangle((int) x - 20, (int) y - 10, 30, 20);
    }

}
