import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final int MAXN = 10010;

    public static void main(String[] arg) throws IOException {
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        int T, a, b;
        T = in.nextInt();
        while (T-- != 0) {
            int n;
            n = in.nextInt();
            int[] p = new int[n + 1];
            boolean[] flag = new boolean[n + 1];
            for (int i = 0; i < n - 1; i++) {
                a = in.nextInt();
                b = in.nextInt();
                p[b] = a;
            }
            a = in.nextInt();
            b = in.nextInt();
            while (true) {
                flag[a] = true;
                a = p[a];
                if (a == 0)
                    break;
            }
            while (true) {
                if (flag[b])
                    break;
                b = p[b];
            }
            System.out.println(b);
        }
    }
}
