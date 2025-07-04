package ar.edu.uns.cs.ed.proyectos.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import ar.edu.uns.cs.ed.proyectos.banco.entities.Persona;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Puesto;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Tramite;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Turno;
import ar.edu.uns.cs.ed.proyectos.banco.util.Par;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.tdacola.ColaConNodos;
import ar.edu.uns.cs.ed.tdas.tdacola.Queue;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.*;
import ar.edu.uns.cs.ed.tdas.tdalista.*;
import ar.edu.uns.cs.ed.tdas.tdamapeo.*;



public class SucursalBancaria implements SistemaBancario {

    // TODO [Tareas T5, T6 y T7] Declarar las estructuras de datos elegidas
    //diccionario para tramite y puesto con hash abierto
    protected Dictionary<Tramite,Puesto> puestotramite;
    protected ListaDoblementeEnlazada<Turno> listaturno;
    protected Map<Character,Integer> codigocantidad;

    public SucursalBancaria() {
        
        // TODO [Tareas T5, T6 y T7] Crear e inicializar las estructuras de datos elegidas

        puestotramite= new DiccionarioConHashAb<Tramite, Puesto>();
        listaturno= new ListaDoblementeEnlazada<Turno>();
        codigocantidad = new MapeoConHash<Character,Integer>();
        codigocantidad.put('C',1);
        codigocantidad.put('A',1);
        codigocantidad.put('B',1);
        codigocantidad.put('T',1);
        codigocantidad.put('X',1);
        codigocantidad.put('O',1);
    }


    @Override
    public boolean asociarTramiteAPuesto(Tramite t, Puesto p) {
        if (t == null || p == null){
            throw new IllegalArgumentException("Parametros Invalido");
        }
        // TODO [Tarea T5] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        Iterator<Entry<Tramite,Puesto>> ite = puestotramite.findAll(t).iterator();
        boolean yaEsta = false;
        while (ite.hasNext() && !yaEsta){
            Entry<Tramite,Puesto> ept = ite.next();
            yaEsta = ept.getKey().equals(t) && ept.getValue().equals(p);
        }
        if (!yaEsta){
            puestotramite.insert(t,p);
        }
        
        return !yaEsta;
    }

    @Override
    public boolean desasociarTramiteAPuesto(Tramite t, Puesto p) {
        
        if (t == null || p == null){
            throw new IllegalArgumentException("Parametros Invalido");
        }
        // TODO [Tarea T5] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        Iterator<Entry<Tramite,Puesto>> ite = puestotramite.findAll(t).iterator();
        boolean yaEsta = false;
        Entry<Tramite,Puesto> encontrada = null;
        while (ite.hasNext() && !yaEsta){
            Entry<Tramite,Puesto> ept = ite.next();
            yaEsta = ept.getValue().equals(p) && ept.getKey().equals(t);
            if (yaEsta){
                encontrada = ept;
            }
        }
        if (yaEsta){//nunca recibe un nulo porque solo se ejecuta si encontro
            puestotramite.remove(encontrada);
        }
        
        return yaEsta;
    }

    @Override
    public Iterable<Tramite> obtenerTramitesAsociadosAPuesto(Puesto p) {
        
        // TODO [Tarea T5] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        if (p==null){
            throw new IllegalArgumentException("Puesto invalido");
        }
        PositionList<Tramite> resultado = new ListaDoblementeEnlazada<Tramite>();
        for (Entry<Tramite,Puesto> e : puestotramite.entries()){
            if (e.getValue().equals(p)){
                resultado.addLast(e.getKey());  
            }
        }
        return resultado;
    }

    @Override
    public int obtenerCantidadDePuestosAtendiendoElTramite(Tramite t) {
        
        // TODO [Tarea T5] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        if (t==null){
            throw new IllegalArgumentException("Tramite invalido");
        }
        int cont = 0;
        for (Entry<Tramite,Puesto> e : puestotramite.findAll(t)){
            cont++;
        }
        return cont;
    }



    @Override
    public Turno sacarTurno(Persona p, Tramite t) {
        
        // TODO [Tarea T6] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        if (p == null || t == null){
            throw new IllegalArgumentException("Persona o Tramite invalidos");
        }
        Turno tu = null;
        if (puestotramite.find(t) != null){
            if((!p.esCliente() && (t.getCodigo()=='B' || t.getCodigo()=='C')) || p.esCliente()){
                tu = new Turno(t, codigocantidad.get(t.getCodigo()), p);
                codigocantidad.put(t.getCodigo(), tu.getNumero()+1);
                listaturno.addLast(tu);
                p.setTurno(tu);
            }
        }
        return tu;
    }

    @Override
    public int obtenerTiempoDeEsperaEstimado(Turno t) {
        
        // TODO [Tarea T6] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        
        return 0;
    }


    @Override
    public Par<Turno, Integer> llamarYAtenderProximoTurno(Puesto p) {
        
        // TODO [Tarea T7] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        
        return null;
    }
    
    @Override
    public Iterable<Par<Turno, Puesto>> obtenerUltimos4Llamados() {
        
        // TODO [Tarea T7] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        
        return null;
    }

}
