/*

  Para indicar el número de threads desde una variable de
  ambiente, en la terminal (bash) teclear:

    export OMP_NUM_THREADS=8
    
*/

#include <stdio.h>
#include <omp.h>

int main(void) {
    printf("# de procesadores: %d\n", omp_get_num_procs());
    printf("# de threads: %d\n", omp_get_num_threads());

    omp_set_num_threads(2);

    #pragma omp parallel num_threads(4)
    {
        printf("Hola Mundo desde thread %d/%d\n", 
            omp_get_thread_num(),
            omp_get_num_threads());
    }

    return 0;
}