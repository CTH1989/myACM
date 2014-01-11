#include <stdio.h>
#include <time.h>
#include <stdlib.h>

int partition(int *A, int p, int r)
{
    int x = A[r];
    int i = p - 1;
    int j;
    for (j = p; j < r; j ++)
    {
        if (A[j] <= x)
        {
            i ++;
            int tmp = A[j];
            A[j] = A[i];
            A[i] = tmp;
        }
    }
    A[r] = A[i + 1];
    A[i + 1] = x;
    return i + 1;
}

int randomized_partition(int *A, int p, int r)
{
    srand(time(NULL));
    int i = rand() % (r - p + 1) + p;
    int tmp = A[i];
    A[i] = A[r];
    A[r] = tmp;
    return partition(A, p, r);
}

void quicksort(int *A, int p, int r)
{
    if (p < r)
    {
        int q = partition(A, p, r);
        // int q = randomized_partition(A, p, r);
        quicksort(A, p, q - 1);
        quicksort(A, q + 1, r);
    }
}

int main()
{
    int A[] = {2,8,7,1,3,5,6,4};
    int i;
    quicksort(A, 0, 7);
    for (i = 0; i < 8; i ++)
    {
        printf("%d\n", A[i]);
    }
}
