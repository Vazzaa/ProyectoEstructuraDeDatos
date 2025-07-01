package ar.edu.uns.cs.ed.tdas.tdalista;

import java.util.Iterator;
import ar.edu.uns.cs.ed.tdas.DNode;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyListException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;

public class ListaDoblementeEnlazada<E> implements PositionList<E>{

    DNode<E> lista;
    DNode<E> ultimo;
    int cant;

    public ListaDoblementeEnlazada(){
        lista=new DNode<E>(null);
        ultimo=new DNode<E>(null);
        cant=0;
        lista.SetSiguiente(ultimo);
        ultimo.SetAnterior(lista);
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
    public Position<E> first() {
        if(isEmpty()){
            throw new EmptyListException("Lista vacia");
        }
        return lista.getSiguiente();
    }

    @Override
    public Position<E> last() {
        if(isEmpty()){
            throw new EmptyListException("Lista vacia");
        }
        return ultimo.getAnterior();
    }

    @Override
    public Position<E> next(Position<E> p) {
        DNode<E> pele=checkPosition(p);
        if(isEmpty()){
            throw new InvalidPositionException("Esta mal");
        }
        if(pele.getSiguiente() == ultimo){
            throw new BoundaryViolationException("Es el ultimo papucho");
        }
        return pele.getSiguiente();
        
    }

    @Override
    public Position<E> prev(Position<E> p) {
        if(isEmpty()){
            throw new InvalidPositionException("Esta mal");
        }
        DNode<E> pele=checkPosition(p);
        if(pele.getAnterior()==lista){
            throw new BoundaryViolationException("Es el primero pa");
        }
        return pele.getAnterior();
    }

    @Override
    public void addFirst(E element) {
        PonerEnElMedio(lista,lista.getSiguiente(),element);
    }

    @Override
    public void addLast(E element) {
        PonerEnElMedio(ultimo.getAnterior(), ultimo, element);
    }

    @Override
    public void addAfter(Position<E> p, E element) {
        if(isEmpty() || p == null){
            throw new InvalidPositionException("no se puede");
        }
        DNode<E> pele = checkPosition(p);
        PonerEnElMedio(pele,pele.getSiguiente() , element);
    }

    @Override
    public void addBefore(Position<E> p, E element) {
        if(isEmpty() || p == null){
            throw new InvalidPositionException("no se puede");
        }
        DNode<E> messi = checkPosition(p);
        PonerEnElMedio(messi.getAnterior(), messi, element);
    }

    @Override
    public E remove(Position<E> p) {
        DNode<E> remuevo = checkPosition(p);
        if(isEmpty()){
            throw new InvalidPositionException("Esta vacio");
        }
        E devolver= remuevo.getElemento();
        remuevo.getAnterior().SetSiguiente(remuevo.getSiguiente());  
        remuevo.getSiguiente().SetAnterior(remuevo.getAnterior());
        cant--;
        return devolver;
    }

    @Override
    public E set(Position<E> p, E element) {
        if(isEmpty()){
            throw new InvalidPositionException("Vacio");
        }
        DNode<E> cambio=checkPosition(p);
        E devolver=cambio.getElemento();
        cambio.SetElement(element);
        return devolver;
    }

    @Override
    public Iterator<E> iterator() {
        return new IteradorDeLDE<E>(this);
    }

    @Override
    public Iterable<Position<E>> positions() {
        PositionList<Position<E>> nuevaLista= new ListaDoblementeEnlazada<>();
        if(!isEmpty()){
        Position<E> n = first();
        while(n != last()){
            nuevaLista.addLast(n);
            n=next(n);
        }
        if(n == last() && n !=null){
            nuevaLista.addLast(n);
        }
        }
        return nuevaLista;
    }

    private void PonerEnElMedio(DNode<E> anter, DNode<E> sigui,E elem){
        DNode<E> nuevoelemento=new DNode<E>(elem);
        /*anter.SetSiguiente(nuevoelemento);
        nuevoelemento.SetAnterior(anter);
        sigui.SetAnterior(nuevoelemento);
        nuevoelemento.SetSiguiente(sigui);
        cant++;*/
        nuevoelemento.SetSiguiente(sigui);
        sigui.SetAnterior(nuevoelemento);
        anter.SetSiguiente(nuevoelemento);
        nuevoelemento.SetAnterior(anter);
        cant++;
    }

    private DNode<E> checkPosition(Position<E> p)throws InvalidPositionException{
        DNode<E> n;
        if(isEmpty() || p==null){
            throw new InvalidPositionException("Esta mal xd");
        }
        try{
            n=(DNode<E>) p;
        }
        catch(ClassCastException e){
            throw new InvalidPositionException("Posicion invalida");
        }
        return n;
    }

}
