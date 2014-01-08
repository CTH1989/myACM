#include <stdio.h>
#define N 110
#define inf 999

void merge(int *A, int p, int q, int r)
{
    int n1 = q - p + 1;
    int n2 = r - q;
    int L[N], R[N];
    int i, j, k;
    for (i = 0; i < n1; i ++)
    {
        L[i] = A[p + i]; 
    }
    for (j = 0; j < n2; j ++)
    {
        R[j] = A[q + j + 1];
    }
    L[n1] = inf;
    R[n2] = inf;
    i = 0;
    j = 0;
    for (k = p ; k <= r; k ++)
    {
        if (L[i] <= R[j])
        {
            A[k] = L[i];
            i ++;
        }
        else
        {
            A[k] = R[j];
            j ++;
        }
    }
}

void merge_sort(int *A, int p, int r)
{
    if (p < r)
    {
        int q = (p + r) / 2;
        merge_sort(A, p, q);
        merge_sort(A, q + 1, r);
        merge(A, p, q, r);
    }
}

int main()
{
    int A[] = {5, 2, 4, 7, 1, 3, 2, 6};
    merge_sort(A, 0, 7);
    for (int i = 0; i < 8; i ++)
    {
        printf("%d\n", A[i]);
    }
}
