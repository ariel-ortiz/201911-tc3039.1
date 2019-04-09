#ifdef _OPENMP
#include <omp.h>
#endif

#include <stdio.h>

int main(void) {
    #ifdef _OPENMP
        printf("Versión de OpenMP: %d\n", _OPENMP);
        printf("# de procesadores: %d\n", omp_get_num_procs());
        printf("# de threads: %d\n", omp_get_num_threads());
    #else
        printf("No hay soporte para OpenMP");
    #endif
    return 0;
}
