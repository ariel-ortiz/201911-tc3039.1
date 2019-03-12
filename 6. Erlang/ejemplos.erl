-module(ejemplos).
-export([fact/1, last/1, dup/1, range/2]).

fact(0) -> 1;
fact(N) when N > 0 -> N * fact(N - 1).

last([X]) -> X;
last([_ | T]) -> last(T).

dup([]) -> [];
dup([H | T]) -> [H, H | dup(T)].

range(A, B) when A > B -> [];
range(A, B) -> [A | range(A + 1, B)].