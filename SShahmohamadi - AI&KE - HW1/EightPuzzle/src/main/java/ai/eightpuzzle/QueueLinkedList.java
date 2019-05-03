package ai.eightpuzzle;

import java.util.Iterator;

public class QueueLinkedList<T> implements Iterable<T> {

    private Node first;
    private Node last;
    private int size;
    public QueueLinkedList(){}


    public Iterator<T> iterator() {
        Iterator<T> iterator = iterator();
        return iterator;
    }

    class Node{
        T item;
        Node next;
    }

    public void enqueue(T item){
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if(isEmpty())
            first = last;
        else
            oldLast.next = last;
        size++;
    }

    public T dequeue(){
        T item = first.item;
        first = first.next;
        size--;
        return item;
    }

    public boolean isEmpty(){
        return size == 0;
    }


    public void displayQueueLinkedList(Iterator<T> iterator){
        while (iterator.hasNext()){
            T item = iterator.next();
            System.out.println(item);
        }
    }

    public static void main(String[] args){

        QueueLinkedList queueLinkedList = new QueueLinkedList();
        queueLinkedList.enqueue(4);
        Iterator iterator = queueLinkedList.iterator();


        queueLinkedList.displayQueueLinkedList(queueLinkedList.iterator());
        queueLinkedList.enqueue(5);
        queueLinkedList.dequeue();
    }


}
