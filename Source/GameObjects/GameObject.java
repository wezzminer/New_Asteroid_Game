package GameObjects;

import Main.Game;

import java.awt.*;

public abstract class GameObject
{
    float x, y;
    double angle = 0;

    GameObject(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    GameObject(int x, int y, double angle)
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public abstract void update(Game game);
    public abstract void draw(Graphics2D g2d);
}
