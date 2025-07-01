package ar.edu.uns.cs.ed.tdas;

public class Entrada<K,V> implements Entry<K,V>{

    private K clave;
    private V valor;

    public Entrada(K clave_,V valor_){
        clave=clave_;
        valor=valor_;
    }

    public void setKey(K claven){
        clave=claven;
    }

    public void setValue(V valorn){
        valor=valorn;
    }

    @Override
    public K getKey() {
        return clave;
    }

    @Override
    public V getValue() {
        return valor;
    }
    
    public String toString(){
        return "("+ getKey() +","+getValue()+")";
    }

}
