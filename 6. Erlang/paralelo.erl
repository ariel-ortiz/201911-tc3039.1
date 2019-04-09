-module(paralelo).
-export([fibo/1, separacion_por_longitud/0, 
         separacion_por_letra_inicial/0, meses_con/1]).

fibo(0) -> 0;
fibo(1) -> 1;
fibo(N) when N > 1 -> 
    fibo(N - 1) + fibo(N - 2).

% Ejemplos con mapreduce

meses() -> [
    "enero", "febrero", "marzo", "abril",
    "mayo", "junio", "julio", "agosto",
    "septiembre", "octubre", "noviembre", "diciembre"
].

separacion_por_longitud() ->
    dict:to_list(
        plists:mapreduce(fun (X) -> {length(X), X} end, 
                         meses())).

separacion_por_letra_inicial() ->
    dict:to_list(
        plists:mapreduce(fun (X) -> {[hd(X)], X} end,
                         meses())).

meses_con(Letra) ->
    D = plists:mapreduce(fun (X) -> {hd(X), X} end, meses()),
    case dict:find(Letra, D) of
        {ok, Value} -> Value;
        error -> not_found
    end.
