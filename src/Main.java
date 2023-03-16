import java.util.Random;
import java.util.Scanner;

public class Main {

    static int m_col;
    static int m_row;
    static int m_mineCount;
    static char[][] backField;
    static char[][] frontField;

    public static void main(String[] args) {
        getSettings();
        createFields();
        playGame();
    }

    public static void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (!isGameFinished()) {
            printFrontField();
            System.out.print("Enter x coordinate:");
            int x = scanner.nextInt();
            System.out.print("Enter y coordinate:");
            int y = scanner.nextInt();
            if (x < 0 || x >= m_row || y < 0 || y >= m_col) {
                System.out.print("Outside of field. Enter new coordinates.");
                continue;
            }
            if (backField[x][y] == 'm') {
                System.out.println("Game Over, You find mine!");
                printBackField();
                break;
            } else {
                revealBoxes(x, y);
            }
        }
    }

    public static boolean isGameFinished() {
        boolean result = true;
        for (int i = 0; i < m_row; i++) {
            for (int j = 0; j < m_col; j++) {
                if (frontField[i][j] == 'X' && backField[i][j] != 'm') {
                    result = false;
                }
            }
            System.out.print("\n");
        }
        if(result)
        {
            System.out.println("You survived!");
            printBackField();
        }

        return result;
    }

    public static void revealBoxes(int x, int y) {
        if (frontField[x][y] == 'X' && backField[x][y] == '0') {
            frontField[x][y] = backField[x][y];
            if (x > 0)
                revealBoxes(x - 1, y);
            if (x < m_row - 1)
                revealBoxes(x + 1, y);
            if (y > 0)
                revealBoxes(x, y - 1);
            if (y < m_col - 1)
                revealBoxes(x, y + 1);
        } else if (frontField[x][y] == 'X' && backField[x][y] != 'm') {
            frontField[x][y] = backField[x][y];
        }
    }

    public static void printFrontField() {
        for (int i = 0; i < m_row; i++) {
            for (int j = 0; j < m_col; j++) {
                System.out.print(frontField[i][j]);
            }
            System.out.print("\n");
        }
    }

    public static void printBackField() {
        for (int i = 0; i < m_row; i++) {
            for (int j = 0; j < m_col; j++) {
                System.out.print(backField[i][j]);
            }
            System.out.print("\n");
        }
    }

    public static void getSettings() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter row:");
        m_row = scanner.nextInt();

        while (m_row < 1) {
            System.out.print("Row number must be positive.Enter new row:");
            m_row = scanner.nextInt();
        }

        System.out.print("Enter col:");
        m_col = scanner.nextInt();

        while (m_col < 1) {
            System.out.print("Col number must be positive.Enter new row:");
            m_col = scanner.nextInt();
        }

        System.out.print("Enter mine count:");
        m_mineCount = scanner.nextInt();

        while (m_mineCount < 1 || m_mineCount > m_col * m_row) {
            System.out.print("Mine count must be between 0 and field size. Enter new mine count:");
            m_mineCount = scanner.nextInt();
        }
    }

    public static void createFields() {
        backField = new char[m_row][m_col];
        frontField = new char[m_row][m_col];

        for (int i = 0; i < m_row; i++) {
            for (int j = 0; j < m_col; j++) {
                backField[i][j] = '0';
                frontField[i][j] = 'X';
            }
        }

        putMines();
    }

    public static void putMines() {

        Random rand = new Random();
        int count = 0;
        while (count < m_mineCount) {
            int x = Math.abs(rand.nextInt()) % m_row;
            int y = Math.abs(rand.nextInt()) % m_col;
            if (backField[x][y] == 'm') {
                continue;
            }
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (x + i >= 0 && x + i < m_row && y + j >= 0 && y + j < m_col) {
                        if(backField[x + i][y + j] != 'm') {
                            backField[x + i][y + j]++;
                        }
                    }
                }
            }
            count++;
        }

    }

}