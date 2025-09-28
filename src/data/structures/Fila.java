package data.structures;

import exceptions.FilaCheiaException;
import exceptions.FilaVaziaException;

public class Fila <T>{
    private static final int TAM_DEFAULT = 100;
    private int qtde;
    private int inicio;
    private int fim;
    private T[] arr;

    public Fila(int tam){
        arr = (T[]) new Object[tam];
        inicio = 0;
        fim = 0;
        qtde = 0;
    }

    public Fila(){
        this(TAM_DEFAULT);
    }

    public boolean isEmpty(){
        return qtde == 0;
    }

    public boolean isFull() {
        return qtde == arr.length;
    }

    public void enqueue(T e) throws FilaCheiaException {
        if(isFull()) throw new FilaCheiaException("Overflow - Fila Cheia");

        arr[fim++] = e;
        fim %= arr.length;
        qtde++;
    }

    public T dequeue() throws FilaVaziaException {
        if(isEmpty()) throw new FilaVaziaException("Underflow - Fila Vazia");
        T aux = arr[inicio++];
        inicio %= arr.length;
        qtde--;

        return aux;
    }

    public T front() throws FilaVaziaException{
        if(isEmpty()) throw new FilaVaziaException("Underflow - Fila Vazia");

        return arr[inicio];
    }

    public T rear() throws FilaVaziaException{
        if(isEmpty()) throw new FilaVaziaException("Underflow - Fila Vazua");

        int idxAux = (fim==0) ? arr.length-1 : fim-1;
        return arr[idxAux];
    }

    public int size() {
        return qtde;
    }

    public void invert(){

    }
}
