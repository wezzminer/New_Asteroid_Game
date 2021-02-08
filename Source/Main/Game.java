package Main;

import GameObjects.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.LinkedList;

public class Game
{
    private final int FRAMESIZE = 900;

    public LinkedList<GameObject> gameObjects = new LinkedList<>();

    private LinkedList<GameObject> removeObjects = new LinkedList<>();
    private LinkedList<GameObject> addObjects = new LinkedList<>();

    public boolean[] keycodes = new boolean[222];

    private JFrame frame = new JFrame();
    private JPanel content = new JPanel() {
        // This method will 'paint' the panel when content.repaint() is called.
        @Override
        protected void paintComponent(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0,0, getWidth(), getHeight());

            g2d.setColor(Color.RED);
            g2d.drawString("Prototype", 830, 15);

            for (GameObject gameObject : gameObjects)
            {
                gameObject.draw(g2d);
            }
        }
    };

    GameStateMachine gameStateMachine = new GameStateMachine();

    Game()
    {
        start();
    }

    private void start()
    {

        Arrays.fill(keycodes, false);

        frame.add(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                keycodes[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keycodes[e.getKeyCode()] = false;
            }
        });
        frame.setSize(FRAMESIZE, FRAMESIZE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Asteroids : Wessley Alexander");
        frame.setVisible(true);

        // This is a game tick clock. It continuously adds the change in nanoseconds to delta, where
        // if delta is one or greater it updates the game and reduces delta by one.
        // Note: 1000000000 is a second in nanoseconds.
        long previousTime = System.nanoTime();
        double ticksPerSecond = 60;
        double nanosecondsPerTick = 1000000000 / ticksPerSecond;
        double delta = 0;
        while (true)
        {
            long currentTime = System.nanoTime();
            delta += (currentTime - previousTime) / nanosecondsPerTick;
            previousTime = currentTime;
            if (delta >= 1)
            {
                gameStateMachine.runChecks(this);
                update();
                delta--;
                content.repaint();
            }
        }
    }

    private void update()
    {
        gameObjects.removeAll(removeObjects);
        removeObjects.clear();

        gameObjects.addAll(addObjects);
        addObjects.clear();

        for (GameObject gameObject : gameObjects)
        {
            gameObject.update(this);
        }
    }

    public void addGameObject(GameObject gameObject)
    {
        addObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject)
    {
        removeObjects.add(gameObject);
    }

}
