package ar.edu.uns.cs.ed.tdas.tdadiccionario;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Entrada;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEntryException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class DiccionarioConHashAb<K,V> implements Dictionary<K,V>{

    private int cubetas;
    private int cantidad;
    private PositionList<Entry<K,V>>[] arreglo;
        protected static final double FactordeCarga=0.75;

    public DiccionarioConHashAb(){
        cubetas=19;
        cantidad=0;
        arreglo = new ListaDoblementeEnlazada[cubetas];
        for(int i=0; i<cubetas;i++){
            arreglo[i] = new ListaDoblementeEnlazada<Entry<K,V>>();
        }
    }

    @Override
    public int size() {
        return cantidad;
    }

    @Override
    public boolean isEmpty() {
        return cantidad==0;
    }

    @Override
    public Entry<K, V> find(K key) {
        if(key==null){
            throw new InvalidKeyException("Clave nula");
        }
        else{
            int cubeta= hash(key);
            Entry<K,V> resultado = null;
            Iterator<Entry<K,V>> ite = arreglo[cubeta].iterator();
            boolean encontre=false;
            while(ite.hasNext() && !encontre){
                Entry<K,V> entrada=ite.next();
                if(entrada.getKey().equals(key)){
                    encontre=true;
                    resultado=entrada;
                }
            }
            return resultado;
        }
    }

    @Override
    public Iterable<Entry<K, V>> findAll(K key) {
       if(key==null){
            throw new InvalidKeyException("Clave nula");
        }
        else{
            int cubeta=hash(key);
            PositionList<Entry<K,V>> devolver= new ListaDoblementeEnlazada<Entry<K,V>>();
            for(Entry<K,V> ele:arreglo[cubeta]){
                if(ele.getKey().equals(key)){
                    devolver.addLast(ele);
                }
            }
            return devolver;
        }
    }

    @Override
    public Entry<K, V> insert(K key, V value) {
        if(key==null){
            throw new InvalidKeyException("Clave nula");
        }
        if((cantidad+1 / cubetas)>FactordeCarga ){
            rehash();
        }
        int cubeta = hash(key);
        boolean encontre=false;
        Iterator<Entry<K,V>> ite= arreglo[cubeta].iterator();
        Entry<K,V> entrada=null;
        while(ite.hasNext() && !encontre){
            entrada=ite.next();
            if(entrada.getKey().equals(key) && entrada.getValue().equals(value)){
                encontre=true;
            }
        }
        if(!encontre){
            entrada=new Entrada<K,V>(key, value);
            arreglo[cubeta].addLast(entrada);
            cantidad++;
        }
        return entrada;
    }

    @Override
    public Entry<K, V> remove(Entry<K, V> e) {
        if(e==null){
            throw new InvalidEntryException("Entrada vacia");
        }
        else{
            int cubeta=hash(e.getKey());
            boolean encontre=false;
            Entry<K,V> devolver=null;
            Iterator<Position<Entry<K,V>>> ite=arreglo[cubeta].positions().iterator();
            while(ite.hasNext() && !encontre){
                Position<Entry<K,V>> posdelel=ite.next();
                Entry<K,V> el=posdelel.element();
                if(el.getKey().equals(e.getKey()) && el.getValue().equals(e.getValue())){
                    encontre=true;
                    devolver=el;
                    arreglo[cubeta].remove(posdelel);
                    cantidad--;
                }
            }
            if(!encontre){
                throw new InvalidEntryException("No esta la entrada");
            }
            return devolver;
        }
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        PositionList<Entry<K,V>> resultado= new ListaDoblementeEnlazada<Entry<K,V>>();
        for(int i=0;i<cubetas;i++){
            for(Entry<K,V> entrada:arreglo[i]){
                resultado.addLast(entrada);
            }
        }
        return resultado;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % cubetas);
    }
    

    private void rehash(){
        int cub=siguientePrimo(cubetas);
        PositionList<Entry<K,V>> [] arregs= new ListaDoblementeEnlazada[cub];
        for(int i=0; i<cub; i++){
            arregs[i] = new ListaDoblementeEnlazada<Entry<K,V>>();
        }
        for(Entry<K,V> e: this.entries()){
            int cubo=hash(e.getKey());
            arregs[cubo].addLast((Entrada<K,V>)e);
        }
        arreglo=arregs;
    }

    private int siguientePrimo(int input){
        input++;
    //now find if the number is prime or not

    for(int i=2;i<input;i++) {
        if(input % i ==0  ) {
            input++;
            i=2;
        }
        else{
            continue;
        }
    }
    return input;
    }

}
