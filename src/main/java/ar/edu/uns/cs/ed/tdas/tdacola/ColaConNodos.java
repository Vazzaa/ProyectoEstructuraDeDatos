package ar.edu.uns.cs.ed.tdas.tdacola;


import ar.edu.uns.cs.ed.tdas.DNode;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;

public class ColaConNodos<E> implements Queue<E> {

    protected DNode<E> frente;
    protected DNode<E> back;
    protected int cant;

    public ColaConNodos(){
        frente=null;
        back=null;
        cant=0;
    }

    @Override
    public int size() {
        return cant;
    }

    @Override
    public boolean isEmpty() {
        return cant==0;
    }

    @Override
    public E front() {
        if(isEmpty()){
            throw new EmptyQueueException("esta vacio papucho");
        }
        E devolver= frente.getElemento();
        return devolver;
    }

    @Override
    public void enqueue(E element) {
        DNode<E> nuevo = new DNode<E>(element);
        if(isEmpty()){
            frente=nuevo;
        }
        else{
            back.SetSiguiente(nuevo);
        }
        back=nuevo;
        cant++;
    }

    @Override
    public E dequeue() {
        if(isEmpty()){
            throw new EmptyQueueException("papu vacio");
        }
        E devolver=frente.getElemento();
        frente=frente.getSiguiente();
        cant--;
        if(cant==0){
            back=null;
        }
        return devolver;
    }

    public int MaximoenCola(Queue<Integer> q){
        if(q.isEmpty()){
            throw new EmptyQueueException("cola vacia");
        }
        int max=q.front();
        Queue<Integer> aux= new ColaConNodos<>();
        while(q.size()>0){
            if(q.front()>max){
                max=q.front();
                aux.enqueue(q.dequeue());
            }
            else{
                aux.enqueue(q.dequeue());
            }
        } 
        while(aux.size()>0){
            q.enqueue(aux.dequeue());
        }
        return max;
    }

    
    public void agregarCola(ColaConNodos<E> q){
        while(q.cant>0){
            E devolver = q.back.getElemento();
            if(q.cant==1){
                q.frente=null;
                q.back=null;
            }
            else{
                DNode<E> actual=q.frente;
                while(actual.getSiguiente()!=q.back){
                    actual=actual.getSiguiente();
                }
                actual.SetSiguiente(null);
                q.back=actual;
            }
            DNode<E> meter=new DNode<E>(devolver);
            DNode<E> sig=frente.getSiguiente();
            frente=meter;
            frente.SetSiguiente(sig);
            q.cant--;
            cant++;
        }
    }

    public void agregarCola2(ColaConNodos<E> q) {
    while (q.frente != null) {
        DNode<E> actual = q.frente;
        q.frente = q.frente.getSiguiente();

        // Insertamos al frente de la cola actual
        actual.SetSiguiente(frente);
        frente = actual;

        // Si estaba vacía, actualizamos back también
        if (back == null) {
            back = actual;
        }

        cant++;
        q.cant--;
    }

    q.back = null;
    }


    public static void main(String[] args) {
        ColaConNodos<String> colaPrincipal = new ColaConNodos<>();
        ColaConNodos<String> colaExtra = new ColaConNodos<>();

        colaPrincipal.enqueue("X");
        colaPrincipal.enqueue("Y");

        colaExtra.enqueue("A");
        colaExtra.enqueue("B");
        colaExtra.enqueue("C");

        System.out.println("Cola principal antes:");
        mostrarCola(colaPrincipal);

        System.out.println("Cola extra antes:");
        mostrarCola(colaExtra);

        colaPrincipal.agregarCola2(colaExtra);

        System.out.println("Cola principal después de agregar cola extra:");
        mostrarCola(colaPrincipal);

        System.out.println("Cola extra después (debe estar vacía):");
        mostrarCola(colaExtra);
    }

    public static <T> void mostrarCola(ColaConNodos<T> cola) {
        DNode<T> actual = cola.frente;
        while (actual != null) {
            System.out.print("[" + actual.getElemento() + "] ");
            actual = actual.getSiguiente();
        }
        System.out.println();
    }

}
