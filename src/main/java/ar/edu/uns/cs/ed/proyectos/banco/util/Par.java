package ar.edu.uns.cs.ed.proyectos.banco.util;

public class Par<E,F> {
    private E primero;
    private F segundo;

    public Par(E primero, F segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public E getPrimero() {
        return this.primero;
    }

    public F getSegundo() {
        return this.segundo;
    }

    @Override
    public String toString() {
        return primero+" - "+segundo;
    }   
}
