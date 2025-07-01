package ar.edu.uns.cs.ed.tdas;

public class DNode<E> implements Position<E>{

    E elemento;
    DNode<E> siguiente;
    DNode<E> anterior;

    public DNode(E element){
        elemento=element;
        siguiente=null;
        anterior=null;
    }
    
    public DNode(E element,DNode<E> sig,DNode<E> ant){
        elemento=element;
        siguiente=sig;
        anterior=ant;
    }

    public void SetElement(E elem){
        elemento=elem;
    }

    public void SetSiguiente(DNode<E> sig){
        siguiente=sig;
    }

    public void SetAnterior(DNode<E> ant){
        anterior=ant;
    }

    public E getElemento(){
        return elemento;
    }

    public DNode<E> getSiguiente(){
        return siguiente;
    }

    public DNode<E> getAnterior(){
        return anterior;
    }

    @Override
    public E element() {
        return elemento;
    }
    
}
