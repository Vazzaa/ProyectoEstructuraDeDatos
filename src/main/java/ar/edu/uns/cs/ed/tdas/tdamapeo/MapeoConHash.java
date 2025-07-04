package ar.edu.uns.cs.ed.tdas.tdamapeo;


import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.DNode;
import ar.edu.uns.cs.ed.tdas.Entrada;
import ar.edu.uns.cs.ed.tdas.Entry;

public class MapeoConHash<K, V> implements Map<K, V> {
    protected int cantidad;
    protected int cubetas;
    protected PositionList<Entry<K, V>> [] arreglo;

    public MapeoConHash() {
        cantidad = 0;
        cubetas = 11;
        arreglo = new ListaDoblementeEnlazada[cubetas];
        for (int i = 0 ; i < cubetas ; i++){
            arreglo[i] = new ListaDoblementeEnlazada<Entry<K,V>>();
        }
    }    

    @Override
    public int size() {
        return cantidad;//c1
        //O(1)
    }

    @Override
    public boolean isEmpty() {
        return cantidad == 0; //c1
        // O(1)
    }

    @Override
    public V get(K key) {
        if (key == null){
            throw new InvalidKeyException("La clave que se utilizo es invalida");
        }
        else {
            int cubeta = hash(key); //c1
            Iterator<Entry<K,V>> ite1 = arreglo[cubeta].iterator();//c2
            boolean encontre = false;// c3
            V valor = null;//c4
            while (!encontre && ite1.hasNext()){//Cuerpo c5+c6+c7+c10 = c9
                Entry<K,V> e = ite1.next();//c5
                encontre = e.getKey() == key;//c6
                if (encontre){//c10
                    valor = e.getValue();//c7
                }
            }
            return valor;//c8
            //sea n la cantidad de elementos en arreglo[cubeta]
            //O(c1)+O(c2)+O(c3)+O(c4)+O(n*c9)+O(c9) = O(c*n) = O(n)
        }
    }

    public int hash(K key){
        return key.hashCode() % cubetas;//c1
        //O(1)
    }

    @Override
    public V put(K key, V value) {
        if (key == null){//c1
            throw new InvalidKeyException("La clave pasada por parametro es invalida");
        }
        else {
            int cubeta = hash(key);//c2
            boolean encontre = false;//c3
            Iterator<Entry<K, V>> ite = arreglo[cubeta].iterator();//c4
            V valor = null;//c5
            while(ite.hasNext() && !encontre){//cuerpo: c
                Entry<K,V> e = ite.next();//c
                K clave = e.getKey();//c
                encontre = clave == key;//c
                if (encontre){//c
                    valor = e.getValue();//c
                    ((Entrada)e).setValue(value);//c
                }
            }
            if (!encontre){//c , cuerpo: c
                Entry<K,V> ent = new Entrada<K,V>(key, value);//c
                arreglo[cubeta].addLast(ent);//c
                this.cantidad++;//c
            }
            return valor;//c + n*c + c = c + n*c
            //Sea n la cantidad de elementos de arreglo[cubeta]
            //T(f()) = c + n*c
            //t(f()) es de orden O(n)
        }
    }

    @Override
    public V remove(K key) {
        if (key == null){
            throw new InvalidKeyException("La key pasada como parametro es invalida");
        }
        else {
            int cubeta = hash(key);
            boolean encontre = false;
            V valor = null;
            Iterator<Position<Entry<K,V>>> ite = arreglo[cubeta].positions().iterator();
            while (!encontre && ite.hasNext()){
                Position<Entry<K,V>> e = ite.next();
                encontre = e.element().getKey() == key;
                if (encontre){
                    valor = e.element().getValue();
                    arreglo[cubeta].remove(e);
                    cantidad--;
                }
            }
            return valor;
        }
    }

    @Override
    public Iterable<K> keys() {
        PositionList<K> res = new ListaDoblementeEnlazada<K>();
        for (int i = 0 ; i < cubetas ; i++){
            for (Entry<K,V> e : arreglo[i]){
                res.addLast(e.getKey());
            }
        }
        return res;
        //sea n la cantidad de cubetas y sea m la cantidad de elementos de arreglo[i]
        // t(f()) es de O(n*m )
    }

    @Override
    public Iterable<V> values() {
        PositionList<V> res = new ListaDoblementeEnlazada<V>();
        for (int i = 0 ; i < cubetas ; i++){
            for (Entry<K,V> e : arreglo[i]){
                res.addLast(e.getValue());
            }
        }
        return res;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        PositionList<Entry<K,V>> res = new ListaDoblementeEnlazada<Entry<K,V>>();
        for (int i = 0 ; i<cubetas ; i++){
            for (Entry<K,V> e : arreglo[i]){
                res.addLast(e);
            }
        }
        return res;
    }
    public static PositionList<Entry<Integer, Integer>> mismoAlumno(Map<Integer, Integer> m1, Map<Integer, Integer> m2){
        PositionList<Entry<Integer, Integer>> resultado = new ListaDoblementeEnlazada<>();
        for (Entry<Integer, Integer> ent : m1.entries()){
            for (Entry<Integer, Integer> et : m2.entries()){
                if (ent.getKey() == et.getKey() && ent.getValue() != et.getValue()){
                    resultado.addLast(ent);
                    resultado.addLast(et);
                }
            }
        }
        return resultado;
    }
    public boolean m1ContenidoM2(Map<K,V> m1, Map<K,V> m2){
        boolean encontre = true;
        K sig1;
        Iterator<K> ite1 = m1.keys().iterator();
        while(ite1.hasNext() && encontre){
            sig1 = ite1.next();
            encontre = false;
            Iterator<K> ite2 = m2.keys().iterator();
            K sig2;
            while (ite2.hasNext() && !encontre){
                sig2 = ite2.next();
                encontre = sig1 == sig2;
            }
        }
        return encontre;
    }
    public Iterable<Entry<K,V>> mismoHashCode(K c, V v) throws InvalidKeyException{
        int cubeta = hash(c);
        PositionList<Entry<K,V>> resu = new ListaDoblementeEnlazada<>();
        for (Entry<K,V> e : arreglo[cubeta]){
            if (e.getValue().equals(v)){
                resu.addLast(e);
            }
        }
        return resu;
    }
}
