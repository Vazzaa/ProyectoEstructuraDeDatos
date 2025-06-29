package ar.edu.uns.cs.ed.proyectos.banco;

import ar.edu.uns.cs.ed.proyectos.banco.entities.Persona;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Puesto;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Tramite;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Turno;
import ar.edu.uns.cs.ed.proyectos.banco.util.Par;


public class SucursalBancaria implements SistemaBancario {

    // TODO [Tareas T5, T6 y T7] Declarar las estructuras de datos elegidas


    public SucursalBancaria() {
        
        // TODO [Tareas T5, T6 y T7] Crear e inicializar las estructuras de datos elegidas

    }


    @Override
    public boolean asociarTramiteAPuesto(Tramite t, Puesto p) {

        // TODO [Tarea T5] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        
        return false;
    }

    @Override
    public boolean desasociarTramiteAPuesto(Tramite t, Puesto p) {
        
        // TODO [Tarea T5] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        
        return false;
    }

    @Override
    public Iterable<Tramite> obtenerTramitesAsociadosAPuesto(Puesto p) {
        
        // TODO [Tarea T5] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        
        return null;
    }

    @Override
    public int obtenerCantidadDePuestosAtendiendoElTramite(Tramite t) {
        
        // TODO [Tarea T5] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        
        return 0;
    }



    @Override
    public Turno sacarTurno(Persona p, Tramite t) {
        
        // TODO [Tarea T6] Implementar el método (ver documentación en la interface implementada SistemaBancario)
        
        return null;
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
