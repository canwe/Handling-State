package core;

public interface Func {

    <T, TResult> TResult exec(T tParam);

    <T1, T2, TResult> TResult exec(T1 tParam1, T2 tParam2);

    <T1, T2, T3, TResult> TResult exec(T1 tParam1, T2 tParam2, T3 tParam3);

    <T1, T2, T3, T4, TResult> TResult exec(T1 tParam1, T2 tParam2, T3 tParam3, T4 tParam4);

}
