package geekbrains.java_1.lesson_3.home_work;

import java.util.Random;
import java.util.Scanner;

public class NoughtsAndCrosses {

    private static final char HUMAN_DOT = 'X';
    private static final char AI_DOT = 'O';
    private static final char EMPTY_DOT = '*';
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rnd = new Random();
    private static int field_size_y;
    private static int field_size_x;
    private static int win_len;
    private static char[][] field;
    private static int x;
    private static int y;

    public static void main(String[] args) {
        initMap(3, 3, 3);
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(HUMAN_DOT)) {
                System.out.println("Выиграл игрок!!!");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья!!!");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(AI_DOT)) {
                System.out.println("Выиграл компьютер!!!");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья!!!");
                break;
            }
        }
        sc.close();
    }

    private static void initMap(int size_y, int size_x, int win_length) {
        field_size_y = size_y;
        field_size_x = size_x;
        win_len = win_length;
        field = new char[field_size_y][field_size_x];
        for (int i = 0; i < field_size_y; i++) {
            for (int j = 0; j < field_size_x; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    private static void printMap() {
        System.out.print("+ ");
        for (int i = 1; i <= field_size_x; i++) System.out.print(i + " ");
        System.out.println();
        for (int i = 0; i < field_size_y; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < field_size_x; j++) System.out.print(field[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    private static void humanTurn() {
        //int x, y;
        do {
            System.out.println("Введите координаты X и Y:");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isValidCell(x, y) || !isEmptyCell(x, y));
        field[y][x] = HUMAN_DOT;
    }

    private static void aiTurn() {
        if(turnAIWinCell()) return;
        if(turnHumanWinCell()) return;
        //int x, y;
        do {
            x = rnd.nextInt(field_size_x);
            y = rnd.nextInt(field_size_y);
        } while (!isEmptyCell(x, y));
        field[y][x] = AI_DOT;
    }

    private static boolean turnAIWinCell() {
        for (int i = 0; i < field_size_y; i++) {
            for (int j = 0; j < field_size_x; j++) {
                if (isEmptyCell(j, i)) {
                    field[i][j] = AI_DOT;
                    if (checkWin(AI_DOT)) return true;
                    field[i][j] = EMPTY_DOT;
                }
            }
        }
        return false;
    }

    private static boolean turnHumanWinCell() {
        for (int i = 0; i < field_size_y; i++) {
            for (int j = 0; j < field_size_x; j++) {
                if (isEmptyCell(j, i)) {
                    field[i][j] = HUMAN_DOT;
                    if (checkWin(HUMAN_DOT)) {
                        field[i][j] = AI_DOT;
                        return true;
                    }
                    field[i][j] = EMPTY_DOT;
                }
            }
        }
        return false;
    }

    private static boolean checkWin(char c) {


                if (checkLine(1, 0, win_len, c)) return true;
                if (checkLine( 1, 1, win_len, c)) return true;
                if (checkLine(0, 1, win_len, c)) return true;
                if (checkLine(1, -1, win_len, c)) return true;


        return false;
    }

    private static boolean checkLine( int vx, int vy, int len, char c) {

        int count = 0; int xCurrent = x; int yCurrent = y;

        while (isValidCell(xCurrent, yCurrent) && (field[xCurrent][yCurrent] == c)) {
            xCurrent += vx; yCurrent += vy; count++;}
             vx = -vx; vy = -vy; xCurrent = x; yCurrent = y; count --;
            while (isValidCell(xCurrent, yCurrent) && (field[xCurrent][yCurrent] == c)) {
                xCurrent += vx; yCurrent += vy; count++;}
                if (count >= len) return true;
            return false;



    }

    private static boolean isMapFull() {
        for (int i = 0; i < field_size_y; i++) {
            for (int j = 0; j < field_size_x; j++) {
                if (field[i][j] == EMPTY_DOT) return false;
            }
        }
        return true;
    }

    private static boolean isValidCell(int x, int y) { return x >= 0 && x < field_size_x && y >= 0 && y < field_size_y; }

    private static boolean isEmptyCell(int x, int y) { return field[y][x] == EMPTY_DOT; }
}
