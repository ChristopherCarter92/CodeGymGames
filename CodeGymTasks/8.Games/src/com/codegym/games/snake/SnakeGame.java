package com.codegym.games.snake;


import com.codegym.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;

    @Override
    public void initialize() {
        super.initialize();
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int turnDelay){
        snake.move(apple);
        if (apple.isAlive == false){
            this.turnDelay = this.turnDelay - 10;
            setTurnTimer(this.turnDelay);
            createNewApple();
            score = score + 5;
            setScore(score);

        }

        if (snake.isAlive == false){
            gameOver();
        }
        if (snake.getLength() > GOAL){
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key){
        if (key == Key.SPACE && isGameStopped == true){
            createGame();
        }
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
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.DARKCYAN, "YOU WIN!", Color.BLACK, 75);
        showMessageDialog(Color.DARKCYAN, "Press SPACEBAR to Play Again", Color.BLACK, 30);

    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; i++){
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i,j,Color.DARKCYAN, "");
                
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createGame() {
        score = 0;
        setScore(score);
        snake = new Snake(WIDTH/2, HEIGHT/2);
        turnDelay = 300;
        createNewApple();
        isGameStopped = false;
        drawScene();
        setTurnTimer(turnDelay);


    }
    private void createNewApple() {
        Apple newApple;
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x, y);
        } while (snake.checkCollision(newApple));
        apple = newApple;
    }
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.DARKCYAN, "GAME OVER", Color.BLACK, 75);

        showMessageDialog(Color.DARKCYAN, "Press SPACEBAR to Play Again", Color.BLACK, 30);

    }

}
