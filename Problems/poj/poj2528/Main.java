import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private static final int MAXN = 10010;
    private static Node nodes[];

    private static class Seg {
        int pos, type, index;
    }

    private static class Node {
        int l, r, left, right;
        boolean covered;
    }

    private static void build(int index, int l, int r) {
        nodes[index].l = l;
        nodes[index].r = r;
        nodes[index].covered = false;
        if (l != r) {
            int mid = (l + r) / 2;
            nodes[index].left = index * 2;
            nodes[index].right = index * 2 + 1;
            build(nodes[index].left, l, mid);
            build(nodes[index].right, mid + 1, r);
        }
    }

    private static boolean insert(int index, int l, int r) {
        int mid = (nodes[index].l + nodes[index].r) / 2;
        if (nodes[index].covered)
            return false;
        
        boolean ret;
        if (nodes[index].l == l && nodes[index].r == r) {
            nodes[index].covered = true;
            return true;
        } else if (r <= mid) {
            ret = insert(nodes[index].left, l, r);
        } else if (l > mid) {
            ret = insert(nodes[index].right, l, r);
        } else {
            boolean r1 = insert(nodes[index].left, l, mid);
            boolean r2 = insert(nodes[index].right, mid + 1, r);
            ret = r1 || r2;
        }
        nodes[index].covered = nodes[nodes[index].left].covered && nodes[nodes[index].right].covered; 
        return ret;
    }

    public static void main(String[] arg) throws IOException {
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        int T = in.nextInt();
        nodes = new Node[MAXN * 8];
        for (int i = 0; i < MAXN * 8; i++)
            nodes[i] = new Node();
        Seg[] segs = new Seg[MAXN * 2];
        for (int i = 0; i < MAXN * 2; i++)
            segs[i] = new Seg();
        int[] x = new int[MAXN];
        int[] y = new int[MAXN];
        while (T-- != 0) {
            int N = in.nextInt();
            for (int i = 0; i < N; i++) {
                x[i] = in.nextInt();
                y[i] = in.nextInt();
                segs[i * 2].pos = x[i];
                segs[i * 2].type = 0;
                segs[i * 2].index = i;
                segs[i * 2 + 1].pos = y[i];
                segs[i * 2 + 1].type = 1;
                segs[i * 2 + 1].index = i;
            }
            Arrays.sort(segs, 0, 2 * N, new Comparator<Seg>() {
                @Override
                public int compare(Seg o1, Seg o2) {
                    return o1.pos - o2.pos;
                }
            });
            int last = 0, num = 1;
            for (int i = 0; i < 2 * N; i++) {
                if (segs[i].pos != segs[last].pos) {
                    last = i;
                    num++;
                }
                if (segs[i].type == 0)
                    x[segs[i].index] = num;
                else
                    y[segs[i].index] = num;
            }
            build(1, 1, num);
            int ret = 0;
            for (int i = N - 1; i >= 0; i--)
                ret += insert(1, x[i], y[i])? 1: 0;
            System.out.println(ret);
            
        }
    }
}
