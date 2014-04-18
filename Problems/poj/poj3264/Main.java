import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class Main {
    private static final int MAXN = 50000;
    private static int maxRet, minRet;
    private static Node[] nodes;

    private static class Node {
        int min, max;
        int l, r;
        int left, right;
    }

    private static void build(int index, int l, int r) {
        nodes[index].l = l;
        nodes[index].r = r;
        nodes[index].max = Integer.MIN_VALUE;
        nodes[index].min = Integer.MAX_VALUE;
        int mid = (l + r) / 2;
        if (l != r) {
            nodes[index].left = index * 2;
            nodes[index].right = index * 2 + 1;
            build(nodes[index].left, l, mid);
            build(nodes[index].right, mid + 1, r);
        }
    }

    private static void insert(int node, int index, int v) {
        if (nodes[node].l == index && nodes[node].r == index) {
            nodes[node].max = v;
            nodes[node].min = v;
            return;
        }
        nodes[node].max = Math.max(nodes[node].max, v);
        nodes[node].min = Math.min(nodes[node].min, v);
        int mid = (nodes[node].l + nodes[node].r) / 2;
        if (index <= mid)
            insert(nodes[node].left, index, v);
        else
            insert(nodes[node].right, index, v);
    }

    private static void query(int node, int l, int r) {
        if (nodes[node].min >= minRet && nodes[node].max <= maxRet)
            return;
        int mid = (nodes[node].l + nodes[node].r) / 2;
        if (nodes[node].l == l && nodes[node].r == r) {
            minRet = Math.min(nodes[node].min, minRet);
            maxRet = Math.max(nodes[node].max, maxRet);
        } else if (r <= mid) {
            query(nodes[node].left, l, r);
        } else if (l > mid) {
            query(nodes[node].right, l, r);
        } else {
            query(nodes[node].left, l, mid);
            query(nodes[node].right, mid + 1, r);
        }
    }

    public static void main(String[] args) throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(
                new InputStreamReader(System.in)));
        int N, Q;
        nodes = new Node[MAXN * 3 + 500];
        for (int i = 0; i < MAXN * 3; i++)
            nodes[i] = new Node();
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            N = (int) st.nval;
            st.nextToken();
            Q = (int) st.nval;
            build(1, 1, N);
            for (int i = 0; i < N; i++) {
                st.nextToken();
                int np = (int) st.nval;
                insert(1, i + 1, np);
            }
            for (int i = 0; i < Q; i++) {
                int pp, np;
                st.nextToken();
                pp = (int) st.nval;
                st.nextToken();
                np = (int) st.nval;
                maxRet = Integer.MIN_VALUE;
                minRet = Integer.MAX_VALUE;
                query(1, pp, np);
                System.out.println(maxRet - minRet);
            }
        }
    }
}
