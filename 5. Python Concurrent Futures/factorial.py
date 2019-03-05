from time import time
from concurrent.futures import ProcessPoolExecutor
from functools import reduce
from operator import mul

N = 200_000
NUM_PROCS = 8

def measure_time(fun, *args):
    start = time()
    result = fun(*args)
    end = time()
    return (result, end - start)

def count_bits(n):
    return bin(n).count('1')

def factorial_sequential(n):
    r = 1
    for i in range(1, n + 1):
        r *= i
    return count_bits(r)

def factorial_range(range_tuple):
    start, end = range_tuple
    r = 1
    for i in range(start, end + 1):
        r *= i
    return r

def make_ranges(total, chunks):
    assert total % chunks == 0, f'{total} is not exactly divisible by {chunks}.'
    size = total // chunks
    return [(i * size + 1, (i + 1) * size) for i in range(chunks)]

def factorial_parallel():
    with ProcessPoolExecutor() as pool:
        results = list(pool.map(factorial_range,
                                make_ranges(N, NUM_PROCS)))
    return count_bits(reduce(mul, results)) 
   
def main():
    rs, ts = measure_time(factorial_sequential, N)
    print(f'T1={ts:.4f}, Result={rs}')
    rp, tp = measure_time(factorial_parallel)
    print(f'T{NUM_PROCS}={tp:.4f}, Result={rp}')
    print(f'S{NUM_PROCS}={ts/tp:.4f}')
    print(f'Are the results the same? ' +
          'Yes' if rs == rp else 'No')
main()  
