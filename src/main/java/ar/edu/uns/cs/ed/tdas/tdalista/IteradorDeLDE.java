package ar.edu.uns.cs.ed.tdas.tdalista;
import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;

public class IteradorDeLDE<E> implements Iterator<E>{

    protected PositionList<E> lista;
    protected Position<E> cursor;

    public IteradorDeLDE(PositionList<E> pe){
        lista=pe;
        if(!lista.isEmpty()){
            cursor=lista.first();
        }
        else{
            cursor=null;
        }
    }

    @Override
    public boolean hasNext() {
        return cursor !=null;
    }

    @Override
    public E next() {
        if(cursor == null){
            throw new BoundaryViolationException("le pide el siguiente a un vacio");
        }
        E elemento=cursor.element();
        if(cursor==lista.last()){
            cursor=null;
        }
        else{
            cursor=lista.next(cursor);
        }
        return elemento;
    }
    
}
