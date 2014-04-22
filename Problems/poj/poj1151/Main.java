import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private static int MAXN = 110;
    private static Node[] nodes;
    private static double[] y;

    private static class Node {
        int l, r, left, right;
        double len;
        int cover;
    }

    private static class Seg {
        double x, y1, y2;
        int flag;
    }

    private static void build(int index, int l, int r) {
        nodes[index].l = l;
        nodes[index].r = r;
        nodes[index].len = 0.0;
        nodes[index].cover = 0;
        if (l + 1 == r)
            return;
        int mid = (l + r) / 2;
        nodes[index].left = index * 2;
        nodes[index].right = index * 2 + 1;
        build(nodes[index].left, l, mid);
        build(nodes[index].right, mid, r);
    }

    private static void update(int index) {
        if (nodes[index].cover > 0)
            nodes[index].len = y[nodes[index].r] - y[nodes[index].l];
        else if (nodes[index].l + 1 == nodes[index].r)
            nodes[index].len = 0.0;
        else
            nodes[index].len = nodes[nodes[index].left].len
                    + nodes[nodes[index].right].len;
    }

    private static void query(int index, double lVal, double rVal, int flag) {
        if (y[nodes[index].l] == lVal && y[nodes[index].r] == rVal) {
            nodes[index].cover += flag;
            update(index);
            return;
        }
        int mid = (nodes[index].l + nodes[index].r) / 2;
        double midVal = y[mid];
        if (rVal <= midVal)
            query(nodes[index].left, lVal, rVal, flag);
        else if (lVal >= midVal)
            query(nodes[index].right, lVal, rVal, flag);
        else {
            query(nodes[index].left, lVal, midVal, flag);
            query(nodes[index].right, midVal, rVal, flag);
        }
        update(index);
    }

    public static void main(String[] arg) {
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        int N, cas = 0;
        double x1, x2, y1, y2;
        y = new double[MAXN * 2];
        nodes = new Node[MAXN * 8];
        for (int i = 0; i < MAXN * 8; i++)
            nodes[i] = new Node();
        Seg[] segs = new Seg[MAXN * 2];
        while (in.hasNext()) {
            N = in.nextInt();
            if (N == 0)
                break;
            for (int i = 0; i < N; i++) {
                x1 = in.nextDouble();
                y1 = in.nextDouble();
                x2 = in.nextDouble();
                y2 = in.nextDouble();
                segs[i * 2] = new Seg();
                segs[i * 2].x = x1;
                segs[i * 2].y1 = y1;
                segs[i * 2].y2 = y2;
                segs[i * 2].flag = 1;
                segs[i * 2 + 1] = new Seg();
                segs[i * 2 + 1].x = x2;
                segs[i * 2 + 1].y1 = y1;
                segs[i * 2 + 1].y2 = y2;
                segs[i * 2 + 1].flag = -1;
                y[i * 2] = y1;
                y[i * 2 + 1] = y2;
            }
            Arrays.sort(segs, 0, N * 2, new Comparator<Seg>() {
                public int compare(Seg o1, Seg o2) {
                    if (o1.x == o2.x)
                        return o2.flag - o1.flag;
                    else if (o1.x < o2.x)
                        return -1;
                    else
                        return 1;
                }
            });
            Arrays.sort(y, 0, N * 2);
            int num = 0;
            for (int i = 1; i < N * 2; i++) {
                if (y[i] != y[i - 1])
                    y[++num] = y[i];
            }

            build(1, 0, num);
            double ans = 0.0;
            query(1, segs[0].y1, segs[0].y2, segs[0].flag);
            for (int i = 1; i < N * 2; i++) {
                ans += nodes[1].len * (segs[i].x - segs[i - 1].x);
                query(1, segs[i].y1, segs[i].y2, segs[i].flag);
            }
            System.out.println("Test case #" + (++cas));
            System.out.println("Total explored area: "
                    + String.format("%.2f", ans) + "\n");
        }
    }
}
