package ai.eightpuzzle;

import java.util.Iterator;

public  class Stack<T> implements Iterable<T>{

    private T[] s;
    private int N;

    public Stack(int capacity){
        s = ((T[]) new Object[capacity]);

    }

    public Iterator<T> iterator() { return null;}


    public T pop(){
        T t = s[--N];
        s[N] = null;
        if(N > 0 && N == s.length / 4)
            resize(s.length  / 2);
        return t;
    }

    public T peek(){
        T t = s[N - 1];
        return t;
    }

    public void push(T t){

        if(N == s.length )
            resize(s.length * 2);

        s[N++] = t;

    }



    public void resize(int capacity){
        T[] copyS = ((T[])(new Object[capacity]));

        for(int i = 0; i < N; i++)
            copyS[i] = s[i];

        s = copyS;
    }

    public boolean isEmpty() {
        return N == 0;
    }
    public int size(){ return N;}

    public void printStack(){
        for(int i = 0; i < N; i++){
            System.out.println(s[i]);
        }
    }


    public static void main(String[] args){
        Stack stack = new Stack(2);
        stack.push(18);
        stack.printStack();
        stack.pop();
        stack.printStack();
        stack.push(89);
        stack.push(56);
        stack.printStack();
        stack.push(555);
        stack.push(566);
        stack.printStack();

    }
}


