package com.codegym.games.snake;

import com.codegym.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {


    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "/uD83D/uDC7E";
    private static final String BODY_SIGN = "/u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;


    public void setDirection(Direction direction) {
        
        if ((direction == Direction.UP && this.direction == Direction.DOWN)
                || (direction == Direction.LEFT && this.direction == Direction.RIGHT)
                || (direction == Direction.RIGHT && this.direction == Direction.LEFT)
                || (direction == Direction.DOWN && this.direction == Direction.UP))
            return;
        this.direction = direction;
    }




    public void move() {
        GameObject newHead = createNewHead();
        if (newHead.x >= SnakeGame.WIDTH
                || newHead.x < 0
                || newHead.y >= SnakeGame.HEIGHT
                || newHead.y < 0) {
            isAlive = false;
            return;
        }
        snakeParts.add(0, newHead);
        removeTail();
    }

    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    public void draw(Game game) {
        Color color = isAlive ? Color.BLACK : Color.RED;
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;
            game.setCellValueEx(part.x, part.y, Color.NONE, sign, color, 75);
        }


    }

    public GameObject createNewHead() {
        GameObject oldHead = snakeParts.get(0);
        if (direction == Direction.LEFT) {
            return new GameObject(oldHead.x -1, oldHead.y);
        }else if(direction == Direction.RIGHT) {
            return new GameObject(oldHead.x + 1, oldHead.y);
        } else if(direction == Direction.UP) {
            return new GameObject(oldHead.x, oldHead.y-1); 
        } else {
            return new GameObject(oldHead.x, oldHead.y + 1);
        }

    }

    public void removeTail() {
        
            snakeParts.remove(snakeParts.size()-1);
        
    }
}
