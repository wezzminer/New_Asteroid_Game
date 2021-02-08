package Main;

import GameObjects.Asteroid;
import GameObjects.Player;

import java.util.Random;

public class GameStateMachine
{

    Random rand = new Random();

    private enum States
    {
        START_LEVEL_ONE,
        LEVEL_ONE,
        START_LEVEL_TWO
    }

    States currentState = States.START_LEVEL_ONE;

    public void runChecks(Game game) {

        switch (currentState) {
            case START_LEVEL_ONE:
                game.addGameObject(new Player(450, 450));
                game.addGameObject(new Asteroid(rand.nextInt(400), rand.nextInt(400), 3));
                game.addGameObject(new Asteroid(rand.nextInt(400), rand.nextInt(400), 3));
                game.addGameObject(new Asteroid(rand.nextInt(400), rand.nextInt(400), 3));
                game.addGameObject(new Asteroid(rand.nextInt(400), rand.nextInt(400), 3));
                currentState = States.LEVEL_ONE;
                break;
            case LEVEL_ONE:

                break;
        }

    }

}
