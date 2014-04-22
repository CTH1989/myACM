import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class Main {
    private static final int MAXN = 50010;

    public static void main(String[] arg) throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(
                new InputStreamReader(System.in)));
        int N, Q;
        int[][] dpMax = new int[MAXN][20];
        int[][] dpMin = new int[MAXN][20];
        int[] num = new int[MAXN];
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            N = (int) st.nval;
            st.nextToken();
            Q = (int) st.nval;
            for (int i = 1; i <= N; i++) {
                st.nextToken();
                num[i] = (int) st.nval;
            }
            for (int i = 1; i <= N; i++) {
                dpMax[i][0] = num[i];
                dpMin[i][0] = num[i];
            }

            for (int j = 1; j < Math.log(1.0 * N) / Math.log(2.0); j++)
                for (int i = 1; i + (1 << j) - 1 <= N; i++)
                    dpMax[i][j] = Math.max(dpMax[i][j - 1], dpMax[i
                            + (1 << (j - 1))][j - 1]);

            for (int j = 1; j < Math.log(1.0 * N) / Math.log(2.0); j++)
                for (int i = 1; i + (1 << j) - 1 <= N; i++)
                    dpMin[i][j] = Math.min(dpMin[i][j - 1], dpMin[i
                            + (1 << (j - 1))][j - 1]);

            int x, y;
            for (int i = 0; i < Q; i++) {
                st.nextToken();
                x = (int) st.nval;
                st.nextToken();
                y = (int) st.nval;
                int k = (int) (Math.log(1.0 * (y - x + 1)) / Math.log(2.0));
                int max = Math.max(dpMax[x][k], dpMax[y - (1 << k) + 1][k]);
                int min = Math.min(dpMin[x][k], dpMin[y - (1 << k) + 1][k]);
                System.out.println(max - min);
            }
        }
    }
}
