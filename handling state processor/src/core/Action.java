package core;

public interface Action {

    <B> void exec(B b);

    <B, A> void exec(B entityType, A contextType);

    <B, A, C> void exec(B b, A a, C c);

    <B, A, C, D> void exec(B b, A a, C c, D d);
}
