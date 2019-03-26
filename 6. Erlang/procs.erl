-module(procs).
-export([factorial/1, fibo_proc/0, fibo_send/2]).

factorial(N) ->
    P = spawn(fun fact_aux/0),
    P ! {fact, N, self()},
    receive
        Result -> Result
    end.

fact_aux() ->
    receive
        {fact, N, Remitente} -> Remitente ! fact(N)
    end.

fact(0) -> 1;
fact(N) when N > 0 ->
    N * fact(N - 1).


fibo_proc() ->
    spawn(fun () -> fibo_loop([1, 0]) end).

fibo_send(Proc, Mssg) ->
    case is_process_alive(Proc) of
        true ->
            Proc ! {Mssg, self()},
            receive
                X -> X
            end;
        false ->
            killed
    end.

fibo_loop(Nums) ->
    [X, Y | T] = Nums,
    receive
        {recent, Remitente} ->
            Remitente ! X,
            fibo_loop(Nums);
        {span, Remitente} ->
            Remitente ! length(Nums),
            fibo_loop(Nums);
        {_, Remitente} ->
            Remitente ! killed
    after 1000 ->
        fibo_loop([X + Y, X, Y | T])
    end.