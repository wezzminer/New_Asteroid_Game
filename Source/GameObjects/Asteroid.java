package GameObjects;

import Main.Game;

import java.awt.*;
import java.util.Random;

public class Asteroid extends GameObject
{

    float velocityX = 0;
    float velocityY = 0;

    int size;

    public Asteroid(int x, int y, int size)
    {
        super(x, y);

        // Set random direction
        Random rand = new Random();
        this.angle = Math.toRadians(rand.nextInt(360));

        this.size = size;
        setVelocity();
    }

    @Override
    public void update(Game game)
    {

        // Movemnet
        x += velocityX;
        y += velocityY;

        if (x > 900) {x = 0;}
        if (x < 0) {x = 900;}
        if (y > 900) {y = 0;}
        if (y < 0) {y = 900;}

        // Creates hitboxes at the current location, asteroids have three boxes.
        Rectangle[] hitboxes = getHitBoxes();

        // Here we loop through each gameObject in Game, testing if the gameObject is a Bullet,
        // then testing if the bullet's hitbox collides with the asteroids.
        for (GameObject gameObject : game.gameObjects)
        {
            if (gameObject instanceof Bullet)
            {
                Bullet bullet = (Bullet) gameObject;
                if (hitboxes[0].intersects(bullet.getHitBox())
                        || hitboxes[1].intersects(bullet.getHitBox())
                        || hitboxes[2].intersects(bullet.getHitBox()))
                {
                    // If they collide, remove the bullet and split/remove the asteroid.
                    game.removeGameObject(bullet);
                    if (size > 1)
                    {
                        game.addGameObject(new Asteroid(((int) x), ((int) y), this.size - 1));
                        game.addGameObject(new Asteroid(((int) x), ((int) y), this.size - 1));
                    }
                    game.removeGameObject(this);
                }
            }
        }

    }

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.setColor(Color.WHITE);
        g2d.drawPolygon(getPolygon());
    }

    private Polygon getPolygon()
    {
        int[] px = {((int) x) + (10 * size), ((int) x) - (10 * size), ((int) x) - (20 * size),
                ((int) x) - (10 * size), ((int) x) + (10 * size), ((int) x) + (20 * size)};
        int[] py = {((int) y) + (20 * size), ((int) y) + (20 * size), ((int) y),
                ((int) y) - (20 * size), ((int) y) - (20 * size), ((int) y)};

        return new Polygon(px, py, 6);
    }

    private void setVelocity()
    {
        velocityX += 3 * Math.cos(angle);
        velocityY += 3 * Math.sin(angle);
    }

    public Rectangle[] getHitBoxes()
    {
        return new Rectangle[]{
                new Rectangle(((int) x) - (10 * size), ((int) y) - (20 * size),
                      (20 * size), (40 * size)),
                new Rectangle(((int) x) - (15 * size), ((int) y) - (12 * size),
                        (5 * size), (25 * size)),
                new Rectangle(((int) x) + (10 * size), ((int) y) - (12 * size),
                        (5 * size), (25 * size))};
    }

}
