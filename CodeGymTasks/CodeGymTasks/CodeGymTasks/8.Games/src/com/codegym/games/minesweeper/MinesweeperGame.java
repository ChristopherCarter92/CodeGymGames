package com.codegym.games.minesweeper;

import com.codegym.engine.cell.*;
import com.codegym.engine.cell.Color;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField = 0;
    private static final String MINE = "U+26C8";
    //thunder symbol
    private static final String FLAG = "U+263C";
    //sunshine symbol
    private int countFlags = 0;
    private boolean isGameStopped;
    private int countClosedTiles = SIDE * SIDE;
    private int score = 0;


    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        if (isGameStopped) {
            restart();
            return;
        }
        openTile(x, y);

    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x,y);
    }


    private void createGame() {
        // isGameStopped = false;
        //removed this in step 15
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                countFlags = countMinesOnField;
                int randomNumber = getRandomNumber(10);
                boolean isMine = randomNumber < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x,y , Color.PURPLE);
                setCellValue(x,y, "");
            }
        }
        countMineNeighbors();
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1 ; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1 ; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (x == gameObject.x && y == gameObject.y) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }

    private void countMineNeighbors() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                GameObject gameObject = gameField[y][x];

                if (!gameObject.isMine) {
                    gameObject.countMineNeighbors = Math.toIntExact(getNeighbors(gameObject).stream().filter(neighbor -> neighbor.isMine).count());
                }
            }
        }
    }

    private void openTile(int x, int y) {
        GameObject gameObject = gameField[y][x];
        if (gameObject.isOpen || gameObject.isFlag || isGameStopped) {
            return;
        }
        gameObject.isOpen = true;
        countClosedTiles--;
        setCellColor(x, y, Color.GREEN);
        if (gameObject.isMine) {
            setCellValueEx(gameObject.x, gameObject.y, Color.RED, MINE);
            gameOver();
            return;
        } else if (gameObject.countMineNeighbors == 0) {
            setCellValue(gameObject.x, gameObject.y, "");
            List<GameObject> neighbors = getNeighbors(gameObject);
            for (GameObject neighbor : neighbors) {
                if (!neighbor.isOpen) {
                    openTile(neighbor.x, neighbor.y);
                }
            }
        } else {
            setCellNumber(x, y, gameObject.countMineNeighbors);
        }

        score += 5;
        setScore(score);

        if (countClosedTiles == countMinesOnField) {
            win();
        }

    }

    private void markTile(int x, int y) {
        if (isGameStopped == true) {
            return;
        }
        GameObject gameObject = gameField[y][x];
        if (gameObject.isOpen || (countFlags == 0 && !gameObject.isFlag)) {
            return;
        }
        if(gameObject.isFlag){
            gameObject.isFlag = false;
            countFlags = countFlags + 1;
            setCellValue(gameObject.x, gameObject.y, "");
            setCellColor(gameObject.x, gameObject.y, Color.PURPLE);
        }else {
            gameObject.isFlag = true;
            countFlags = countFlags -1;
            setCellValue(gameObject.x, gameObject.y, FLAG);
            setCellColor(gameObject.x, gameObject.y, Color.YELLOW);

        }
    }

    private void gameOver() {
        showMessageDialog(Color.WHITE, "GAME OVER", Color.BLUE, 50);
        isGameStopped = true;
    }

    private void win() {
        showMessageDialog(Color.WHITE, "YOU WIN", Color.BLUE, 50);
        isGameStopped = true;

    }

    private void restart() {
        isGameStopped = false;
        countClosedTiles = SIDE * SIDE;
        score = 0;
        countMinesOnField = 0;
        setScore(score);
        createGame();
    }
}