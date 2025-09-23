package data.structures;

import exceptions.PilhaCheiaException;
import exceptions.PilhaVaziaException;

public class Pilha <T> {
    public static final int DEFAULT_SIZE = 50;
    private final T[] arr;
    private int top;

    public Pilha(int size){
        top = -1;
        arr = (T[]) new Object[size];
    }

    public Pilha(){
        this(DEFAULT_SIZE);
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public boolean isFull(){
        return top == (arr.length - 1);
    }

    public void push(T e) throws PilhaCheiaException {
        if(this.isFull()) throw new PilhaCheiaException("Overflow - Estouro de pilha");
        arr[++top] = e;
    }

    public T pop() throws PilhaVaziaException {
        if(this.isEmpty()) throw new PilhaVaziaException("Underflow - Estouro de pilha");
        return arr[top--];
    }

    public T top() throws PilhaVaziaException {
        if(this.isEmpty()) throw new PilhaVaziaException("Underflow - Estouro de pilha");
        return arr[top--];
    }

    public int sizeElements() {
        return (this.top + 1);
    }

}
