/*
This program will take in user input of a sudoku puzzle, and solve the puzzle based on the backtracking/brute force
algorithm (https://en.wikipedia.org/wiki/Sudoku_solving_algorithms#Backtracking). This was a weekend project for myself
to see if I could use 2D arrays of ints to work out problems, and the algorithm itself fascinated me when I happened
upon the article from browsing random Wikipedia articles. While this solution is less than optimal, trying out many more
possibilities than the other algorithms on that page provide, the computer that I have could solve the handful of
problems I tested it on in less than a second.

need to fix:
-add check to see if puzzle is solvable or not
-input/output formatting (will the sudoku grid look better with lines in between?)
 */

import java.util.Scanner;

public class Sudoku {
    public static boolean legalNumber(int[][] sudokuGrid, int i, int j){
        if (sudokuGrid[i][j] == 0){
            return false;
        }

        for(int x = 0; x <= 8; x++){
            if(x != i && sudokuGrid[i][j] == sudokuGrid[x][j]){
                return false;
            }
        }

        for(int y = 0; y <= 8; y++){
            if(y != j && sudokuGrid[i][j] == sudokuGrid[i][y]){
                return false;
            }
        }

        for(int subgridY = (j / 3) * 3; subgridY < (j / 3) * 3 + 3; subgridY++) {
            for (int subgridX = (i / 3) * 3; subgridX < (i / 3) * 3 + 3; subgridX++) {
                if(subgridX != i && subgridY != j && sudokuGrid[subgridX][subgridY] == sudokuGrid[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public static int[][] sudokuSolve(int[][] sudokuGrid){
        int[][] givenValues = new int [9][9];
        boolean backtrack = false;
        int x = 0;
        int y = 0;

        for(int j = 0; j <= 8; j++) {
            for (int i = 0; i <= 8; i++) {
                if(sudokuGrid[i][j] != 0){
                    givenValues[i][j] = 1;
                }
            }
        }

        while(y <= 8) {
            while (x <= 8) {
                while (backtrack) {
                    if (givenValues[x][y] == 1) {
                        if (x == 0) {
                            --y;
                            x = 8;
                            continue;
                        } else {
                            --x;
                            continue;
                        }
                    }
                    if (sudokuGrid[x][y] == 9) {
                        sudokuGrid[x][y] = 0;
                        if (x == 0) {
                            --y;
                            x = 8;
                            continue;
                        } else {
                            --x;
                            continue;
                        }
                    }
                    if (sudokuGrid[x][y] <= 8) {
                        ++sudokuGrid[x][y];
                        if (legalNumber(sudokuGrid, x, y)) {
                            backtrack = false;
                            if (x == 8) {
                                ++y;
                                x = 0;
                                break;
                            } else {
                                ++x;
                                break;
                            }
                        }
                    }
                }
                if (!legalNumber(sudokuGrid, x, y)) {
                    if (sudokuGrid[x][y] == 9) {
                        sudokuGrid[x][y] = 0;
                        backtrack = true;
                        if(x == 0) {
                            --y;
                            x = 8;
                            continue;
                        } else {
                            --x;
                            continue;
                        }
                    }
                    ++sudokuGrid[x][y];
                }
                else {
                    if (x == 8) {
                        ++y;
                        x = 0;
                        break;
                    } else {
                        ++x;
                    }
                }
            }
        }
        return sudokuGrid;
    }
    public static void main(String args[]) {
        int[][] sudokuGrid = new int[9][9];
        String userGrid = "";
        Scanner scnr = new Scanner(System.in);
        char puzzleCorrect;

        while(true) {
            try {
                for (int j = 0; j <= 8; j++){
                    System.out.println("Enter the numbers in line " + (j + 1) + "(ex: 1234556789). For blank spaces, enter 0.");
                    userGrid = scnr.nextLine();
                    if (userGrid.length() != 9) {
                        throw new Exception();
                    }
                    for (int i = 0; i <= 8; i++) {
                        sudokuGrid[i][j] = Character.getNumericValue(userGrid.charAt(i));
                        if (sudokuGrid[i][j] > 9 || sudokuGrid[i][j] < 0) {
                            throw new Exception();
                        }
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input!");
                for (int j = 0; j <= 8; j++) {
                    for (int i = 0; i <= 8; i++) {
                        sudokuGrid[i][j] = 0;
                    }
                }
                continue;
            }

            for (int j = 0; j <= 8; j++) {
                for (int i = 0; i <= 8; i++) {
                    System.out.print(sudokuGrid[i][j] + " ");
                }
                System.out.print("\n");
            }

            System.out.println("Is this puzzle correct? Type (Y/N) to continue.");
            puzzleCorrect = scnr.next().charAt(0);
            if(Character.toUpperCase(puzzleCorrect) == 'Y'){
                break;
            }
            else{
                scnr.nextLine();
            }
        }

        sudokuGrid = sudokuSolve(sudokuGrid);

        for(int j = 0; j <= 8; j++) {
            for(int i = 0; i <= 8; i++) {
                System.out.print(sudokuGrid[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}