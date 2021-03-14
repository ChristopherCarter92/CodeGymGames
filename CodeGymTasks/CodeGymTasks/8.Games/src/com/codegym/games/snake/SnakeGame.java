package com.codegym.games.snake;


import com.codegym.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay = 0;

    @Override
    public void initialize() {
        super.initialize();
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int turnDelay){
        snake.move();
        drawScene();
    }

    @Override
    public void onKeyPress(Key key){
        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        }
        if (key ==Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        }
        if (key ==Key.UP){
            snake.setDirection(Direction.UP);
        }
        if (key ==Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        }

    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; i++){
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i,j,Color.DARKCYAN, "");
                
            }
        }
        snake.draw(this);
    }

    private void createGame() {
        snake = new Snake(WIDTH/2, HEIGHT/2);
        turnDelay = 300;
        drawScene();
        setTurnTimer(turnDelay);


    }


}
