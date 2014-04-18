import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static int MAXN = 100010;
    private static Node[] nodes;
    private static long[] sum;

    private static class Node {
        int l, r, left, right;
        long inc, sum;
    }

    private static void build(int index, int l, int r) {
        nodes[index].l = l;
        nodes[index].r = r;
        nodes[index].inc = 0;
        nodes[index].sum = sum[r] - sum[l - 1];
        if (l != r) {
            nodes[index].left = index * 2;
            nodes[index].right = index * 2 + 1;
            int mid = (l + r) / 2;
            build(nodes[index].left, l, mid);
            build(nodes[index].right, mid + 1, r);
        }
    }

    private static void update(int index, int l, int r, long c) {
        int mid = (nodes[index].l + nodes[index].r) / 2;
        if (nodes[index].l == l && nodes[index].r == r) {
            nodes[index].inc += c;
            return;
        }
        nodes[index].sum += c * (r - l + 1);
        if (r <= mid)
            update(nodes[index].left, l, r, c);
        else if (l > mid)
            update(nodes[index].right, l, r, c);
        else {
            update(nodes[index].left, l, mid, c);
            update(nodes[index].right, mid + 1, r, c);
        }
    }

    private static long query(int index, int l, int r) {
        int mid = (nodes[index].l + nodes[index].r) / 2;
        if (nodes[index].l == l && nodes[index].r == r) {
            return nodes[index].sum + nodes[index].inc * (r - l + 1);
        }
        if (nodes[index].inc != 0) {
            nodes[nodes[index].left].inc += nodes[index].inc;
            nodes[nodes[index].right].inc += nodes[index].inc;
            nodes[index].sum += nodes[index].inc
                    * (nodes[index].r - nodes[index].l + 1);
            nodes[index].inc = 0;
        }
        if (r <= mid)
            return query(nodes[index].left, l, r);
        else if (l > mid)
            return query(nodes[index].right, l, r);
        else {
            return query(nodes[index].left, l, mid)
                    + query(nodes[index].right, mid + 1, r);
        }
    }

    public static void main(String[] arg) throws IOException {
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        int N, Q;
        nodes = new Node[MAXN * 3];
        for (int i = 0; i < MAXN * 3; i++)
            nodes[i] = new Node();
        sum = new long[MAXN];
        while (in.hasNext()) {
            N = in.nextInt();
            Q = in.nextInt();
            for (int i = 1; i <= N; i++)
                sum[i] = sum[i - 1] + in.nextLong();
            build(1, 1, N);
            String np;
            int a, b;
            long c;
            for (int i = 0; i < Q; i++) {
                np = in.next();
                if (np.equals("Q")) {
                    a = in.nextInt();
                    b = in.nextInt();
                    long ret = query(1, a, b);
                    System.out.println(ret);
                } else {
                    a = in.nextInt();
                    b = in.nextInt();
                    c = in.nextInt();
                    update(1, a, b, c);
                }
            }
        }
    }
}
