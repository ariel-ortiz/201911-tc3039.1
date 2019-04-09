#include <stdio.h>
#include <time.h>

#define NANOSECS_PER_SEC 1000000000

long get_time() {
    struct timespec t;
    clock_gettime(CLOCK_MONOTONIC, &t);
    return t.tv_sec * NANOSECS_PER_SEC + t.tv_nsec;
}

int main(void) {
    long num_rects = 1000000000;
    double width, area;
    double sum = 0.0;

    long start = get_time();
    width = 1.0 / (double) num_rects;
    #pragma omp parallel for reduction(+:sum)
    for (long i = 0; i < num_rects; i++) {
        double mid = (i + 0.5) * width;
        double height = 4.0 / (1.0 + mid * mid);
        sum += height;
    }
    area = width * sum;
    long end = get_time();
    printf("Pi = %.15f\n", area);
    printf("Time = %.2f\n", (double) (end - start) / NANOSECS_PER_SEC);
    return 0;
}