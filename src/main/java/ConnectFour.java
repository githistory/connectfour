import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectFour {
    private static final char[] players = new char[] { 'R', 'G' };

    private char[][] board;
    private int width;
    private int height;
    private int lastRow = -1;
    private int lastCol = -1;

    public ConnectFour(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new char[width][];
        for (int i=0; i<width; i++) {
            this.board[i] = new char[height];
            Arrays.fill(this.board[i], ' ');
        }
    }

    public String toString() {
        String footer = " ";
        footer += IntStream.range(1, this.width+1)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(" "));
        String serializedBoard = "";
        for (int i=height-1; i>=0; i--) {
            serializedBoard += "|";
            for (int j=0; j<width; j++) {
                serializedBoard += board[j][i] + "|";
            }
            serializedBoard += "\n";
        }
        return serializedBoard + footer;
    }

    public char getCharAt(int x, int y) {
        return board[x][y];
    }

    public void move(char player, int col) {
        for (int i=0; i<height; i++) {
            if (board[col][i] == ' ') {
                lastRow = i;
                lastCol = col;
                this.board[col][i] = player;
                return;
            }
        }
    }

    public boolean isWinningMove() {
        if (lastCol == -1) {
            throw new IllegalStateException("No move has been made yet");
        }
        char player = board[lastCol][lastRow];
        String streak = String.format("%c%c%c%c", player, player, player, player);
        return horizontal().contains(streak) ||
                vertical().contains(streak) ||
                slash().contains(streak) ||
                backslash().contains(streak);
    }

    public String horizontal() {
        String str = "";
        for (int i=0; i<width; i++) {
            str += board[i][lastRow];
        }
        return str;
    }

    public String vertical() {
        return new String(board[lastCol]);
    }

    public String slash() {
        String str = "";
        for (int i=0; i<width; i++) {
            int j = lastRow - lastCol + i;
            if (j>=0 && j<this.height) {
                str += board[i][j];
            }
        }
        return str;
    }

    public String backslash() {
        String str = "";
        for (int i=0; i<width; i++) {
            int j = lastRow + lastCol - i;
            if (j>=0 && j<this.height) {
                str += board[i][j];
            }
        }
        return str;
    }

    public static void main(String[] args) {
        try {
            int width = 7;
            int height = 6;
            int movesLeft = width * height;
            Scanner input = new Scanner(System.in);
            ConnectFour connectFour = new ConnectFour(width, height);
            System.out.println("Enter column number (1~"+width+")");
            System.out.println(connectFour);

            int currentPlayerIndex = 0;
            while(movesLeft>0) {
                char player = players[currentPlayerIndex];
                System.out.print("Player " + player + "'s turn: ");

                // get valid column number
                int col = 0;
                boolean invalidInput = true;
                while(invalidInput) {
                    col = input.nextInt();
                    // out of width bound?
                    if(col<1 || col>width) {
                        System.out.print("Enter column number between 1 and " + width + ": ");
                        continue;
                    }
                    // overflow column?
                    if(connectFour.getCharAt(col-1, height-1)!=' ') {
                        System.out.print("Column " + col + " is full. Enter another: ");
                        continue;
                    }
                    invalidInput = false;
                }
                connectFour.move(player, col-1);
                System.out.println(connectFour);
                if (connectFour.isWinningMove()) {
                    System.out.println("Player " + player + " wins!");
                    return;
                }
                currentPlayerIndex = 1 - currentPlayerIndex;
                movesLeft--;
            }
            System.out.println("Draw!");
        } catch (Throwable t){
            System.out.print(t);
        }
    }
}
