
public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int y = 0;
        while (x < 9) {
            System.out.print(y + " ");
            y = x+y;
            x = x + 1;
        }
    }
}