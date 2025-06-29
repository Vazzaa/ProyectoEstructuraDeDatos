package ar.edu.uns.cs.ed.proyectos.banco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import ar.edu.uns.cs.ed.proyectos.banco.entities.Persona;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Puesto;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Tramite;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Turno;
import ar.edu.uns.cs.ed.proyectos.banco.util.Par;


/**
 * Tester general del Proyecto.
 * 
 * La mayoría de los tests son unitarios y queda claro su propósito a partir de su nombre 
 * y de los mensajes que puede emitir. Para los métodos que no (por ej. test end to end) se
 * incluye una breve documentación adicional indicando su propósito y alguna orientación acerca 
 * de potenciales problemas si no resultan exitosos.
 * 
 * También se implementan una serie de métodos privados auxiliares que permiten simplificar la
 * lógica de los tests evitando duplicaciones de código e incrementando la legibilidad y
 * mantenibilidad.
 */
public class SucursalBancariaTest {
    
    SucursalBancaria s;
    Puesto[] cajas, puestos;
    Persona[] noCli, cli;

    @Before
    public void inicializarSucursalBancaria() {
        s = new SucursalBancaria();
    }

    /**
     * Método auxiliar que crea y configura una sucursal bancaria con 3 cajas y 4 puestos multifunción
     * Se inicializan una cantidad suficiente de Personas (clientes y no clientes) y se establece la
     * apertura de los diferentes puestos (asociaciones puesto - trámites que atiende).
     */
    private void configurarSucursalBancaria() {
        //Creación de arreglos
        cajas = new Puesto[3];
        puestos = new Puesto[4];
        noCli = new Persona[5];
        cli = new Persona[10];

        //Creación de puestos
        for (int i = 0; i<cajas.length; i++) {
            cajas[i] = new Puesto("Caja "+(i+1));
        }
        for (int i = 0; i<puestos.length; i++) {
            puestos[i] = new Puesto("P"+(i+1));
        }

        //Creación de personas
        for (int i = 0; i<noCli.length; i++) {
            noCli[i] = new Persona("No cliente "+i, false);
        }
        for (int i = 0; i<cli.length; i++) {
            cli[i] = new Persona("Cliente "+i, true);
        }

        //Asociación de puestos y trámites - cajas
        for (int i = 0; i<cajas.length; i++) {
            s.asociarTramiteAPuesto(Tramite.CAJA, cajas[i]);
        }

        //Asociación de puestos y trámites - atención general
        s.asociarTramiteAPuesto(Tramite.CLIENTE, puestos[0]);
        s.asociarTramiteAPuesto(Tramite.TARJETAS, puestos[0]);

        s.asociarTramiteAPuesto(Tramite.COMERCIAL, puestos[1]);
        s.asociarTramiteAPuesto(Tramite.COMEX, puestos[1]);
        s.asociarTramiteAPuesto(Tramite.TARJETAS, puestos[1]);

        s.asociarTramiteAPuesto(Tramite.CLIENTE, puestos[2]);
        s.asociarTramiteAPuesto(Tramite.COMERCIAL, puestos[2]);

        s.asociarTramiteAPuesto(Tramite.CLIENTE, puestos[3]);
        s.asociarTramiteAPuesto(Tramite.COMEX, puestos[3]);
        s.asociarTramiteAPuesto(Tramite.OTROS, puestos[3]);
    }

    private int cantPuestos(Tramite tr) {
        assertNotNull("Invocar cantPuestosParaTramiteEnConfig(...) requiere la sucursal bancaria configurada", s);
        int cont = 0;
        for (int i=0; i<cajas.length; i++) {
            Iterator<Tramite> trIt = s.obtenerTramitesAsociadosAPuesto(cajas[i]).iterator();
            boolean encontreTr = false;
            while (trIt.hasNext() && !encontreTr) {
                if (trIt.next()==tr) { 
                    cont++;
                    encontreTr = true;
                }
            }
        }
        for (int i=0; i<puestos.length; i++) {
            Iterator<Tramite> trIt = s.obtenerTramitesAsociadosAPuesto(puestos[i]).iterator();
            boolean encontreTr = false;
            while (trIt.hasNext() && !encontreTr) {
                if (trIt.next()==tr) { 
                    cont++;
                    encontreTr = true;
                }
            }
        }
        return cont;
    }


    @Test
    public void testRetornoAsociarTramiteAPuestoExitoso() {
        Puesto c1 = new Puesto("Caja 1");
        Puesto p1 = new Puesto("P1");
        assertTrue("La asociación de "+Tramite.CAJA+" a "+c1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CAJA, c1));
        assertTrue("La asociación de "+Tramite.CLIENTE+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CLIENTE, p1));
        assertTrue("La asociación de "+Tramite.COMERCIAL+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.COMERCIAL, p1));
        assertTrue("La asociación de "+Tramite.TARJETAS+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.TARJETAS, p1));
        assertTrue("La asociación de "+Tramite.CAJA+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CAJA, p1));
    }

    @Test
    public void testRetornoAsociarTramiteAPuestoFallido() {
        Puesto p1 = new Puesto("P1");
        assertTrue("La asociación de "+Tramite.CLIENTE+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CLIENTE, p1));
        assertTrue("La asociación de "+Tramite.COMERCIAL+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.COMERCIAL, p1));
        assertFalse("La asociación de "+Tramite.CLIENTE+" a "+p1+" NO debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CLIENTE, p1));
        assertTrue("La asociación de "+Tramite.TARJETAS+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.TARJETAS, p1));
        assertFalse("La asociación de "+Tramite.COMERCIAL+" a "+p1+" NO debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.COMERCIAL, p1));
    }

    @Test
    public void testAsociarTramiteAPuestoConPNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.asociarTramiteAPuesto(Tramite.CLIENTE, null),
                    "El método asociarTramiteAPuesto(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }

    @Test
    public void testAsociarTramiteAPuestoConTNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.asociarTramiteAPuesto(null, new Puesto("P1")),
                    "El método asociarTramiteAPuesto(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }

    @Test
    public void testAsociarTramiteAPuestoConPTNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.asociarTramiteAPuesto(null, null),
                    "El método asociarTramiteAPuesto(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }
    

    @Test
    public void testCantidadDePuestosAtendiendoElTramite() {
        Puesto c1 = new Puesto("Caja 1");
        Puesto p1 = new Puesto("P1");
        assertTrue("La asociación de "+Tramite.CAJA+" a "+c1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CAJA, c1));
        assertTrue("La asociación de "+Tramite.CLIENTE+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CLIENTE, p1));
        assertTrue("La asociación de "+Tramite.COMERCIAL+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.COMERCIAL, p1));
        assertTrue("La asociación de "+Tramite.TARJETAS+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.TARJETAS, p1));
        assertTrue("La asociación de "+Tramite.CAJA+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CAJA, p1));

        assertEquals(Tramite.CAJA+" debería tener 2 puestos atendiendo.", 2, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CAJA));
        assertEquals(Tramite.CLIENTE+" debería tener 1 puesto atendiendo.", 1, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CLIENTE));
        assertEquals(Tramite.COMERCIAL+" debería tener 1 puesto atendiendo.", 1, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.COMERCIAL));
        assertEquals(Tramite.TARJETAS+" debería tener 1 puesto atendiendo.", 1, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.TARJETAS));
        assertEquals(Tramite.COMEX+" debería tener 0 puestos atendiendo.", 0, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.COMEX));
        assertEquals(Tramite.OTROS+" debería tener 0 puestos atendiendo.", 0, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.OTROS));
    }

    @Test
    public void testObtenerCantidadDePuestosAtendiendoElTramiteConTNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.obtenerCantidadDePuestosAtendiendoElTramite( null ),
                    "El método obtenerCantidadDePuestosAtendiendoElTramite(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }

    @Test
    public void testObtenerTramitesAsociadosAPuesto() {
        Puesto c1 = new Puesto("Caja 1");
        Puesto p1 = new Puesto("P1");
        Puesto p2 = new Puesto("P2");
        assertTrue("La asociación de "+Tramite.CAJA+" a "+c1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CAJA, c1));
        assertTrue("La asociación de "+Tramite.CLIENTE+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CLIENTE, p1));
        assertTrue("La asociación de "+Tramite.COMERCIAL+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.COMERCIAL, p1));
        assertTrue("La asociación de "+Tramite.TARJETAS+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.TARJETAS, p1));
        assertTrue("La asociación de "+Tramite.CAJA+" a "+p1+" debería haber sido exitosa.", s.asociarTramiteAPuesto(Tramite.CAJA, p1));

        int cont = 0;
        for(Tramite t:s.obtenerTramitesAsociadosAPuesto(c1)) {
            assertEquals(c1+" debería tener asociado el trámite "+Tramite.CAJA, Tramite.CAJA, t);
            cont++;
        }
        assertEquals(c1+" debería tener asociado exactamente un único trámite.", 1, cont);

        cont = 0;
        Set<Tramite> setTramitesC1 = Stream.of(Tramite.CAJA, Tramite.CLIENTE, Tramite.COMERCIAL, Tramite.TARJETAS).collect(Collectors.toSet());
        for(Tramite t:s.obtenerTramitesAsociadosAPuesto(p1)) {
            assertTrue(t+" debería estar asociado a "+p1, setTramitesC1.remove(t));
            cont++;
        }
        assertEquals(c1+" debería tener asociado exactamente 4 trámites.", 4, cont);
        assertTrue("Se encontraron trámites que deberían estar asociados a "+p1+" y no lo están: "+
                    setTramitesC1.stream().map((a)->(a.toString())).reduce("", (a,b)->(a + ", " + b)), setTramitesC1.isEmpty());

        assertFalse(p2+" no debería tener asociado ningún trámite.", s.obtenerTramitesAsociadosAPuesto(p2).iterator().hasNext());            
    }

    @Test
    public void testObtenerTramitesAsociadosAPuestoConPNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.obtenerTramitesAsociadosAPuesto( null ),
                    "El método obtenerTramitesAsociadosAPuesto(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }


    @Test
    public void testRetornoDesasociarTramiteAPuestoExitoso() {
        Puesto c1 = new Puesto("Caja 1");
        Puesto p1 = new Puesto("P1");
        s.asociarTramiteAPuesto(Tramite.CAJA, c1);
        s.asociarTramiteAPuesto(Tramite.CLIENTE, p1);
        s.asociarTramiteAPuesto(Tramite.COMERCIAL, p1);
        s.asociarTramiteAPuesto(Tramite.TARJETAS, p1);
        s.asociarTramiteAPuesto(Tramite.CAJA, p1);


        s.desasociarTramiteAPuesto(Tramite.CAJA, p1);
        assertEquals(Tramite.CAJA+" debería tener 1 puesto atendiendo.", 1, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CAJA));
        assertTrue("La desasociación de "+Tramite.CAJA+" a "+c1+" debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.CAJA, c1));
        assertTrue("La desasociación de "+Tramite.TARJETAS+" a "+p1+" debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.TARJETAS, p1));
        assertTrue("La desasociación de "+Tramite.CLIENTE+" a "+p1+" debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.CLIENTE, p1));
        assertTrue("La desasociación de "+Tramite.COMERCIAL+" a "+p1+" debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.COMERCIAL, p1));
    }

    @Test
    public void testRetornoDesasociarTramiteAPuestoFallido() {
        Puesto c1 = new Puesto("Caja 1");
        Puesto p1 = new Puesto("P1");
        s.asociarTramiteAPuesto(Tramite.CAJA, c1);
        s.asociarTramiteAPuesto(Tramite.CLIENTE, p1);
        s.asociarTramiteAPuesto(Tramite.COMERCIAL, p1);
        s.asociarTramiteAPuesto(Tramite.TARJETAS, p1);
        s.asociarTramiteAPuesto(Tramite.CAJA, p1);

        assertTrue("La desasociación de "+Tramite.CAJA+" a "+p1+" debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.CAJA, p1));
        assertFalse("La desasociación de "+Tramite.CAJA+" a "+p1+" NO debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.CAJA, p1));
        assertFalse("La desasociación de "+Tramite.COMEX+" a "+p1+" NO debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.COMEX, p1));
        assertTrue("La desasociación de "+Tramite.CAJA+" a "+c1+" debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.CAJA, c1));
        assertTrue("La desasociación de "+Tramite.TARJETAS+" a "+p1+" debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.TARJETAS, p1));
        assertTrue("La desasociación de "+Tramite.COMERCIAL+" a "+p1+" debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.COMERCIAL, p1));
        assertFalse("La desasociación de "+Tramite.CAJA+" a "+c1+" NO debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.CAJA, c1));
        assertFalse("La desasociación de "+Tramite.TARJETAS+" a "+c1+" NO debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.TARJETAS, c1));
        assertFalse("La desasociación de "+Tramite.TARJETAS+" a "+p1+" NO debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.TARJETAS, p1));
        assertFalse("La desasociación de "+Tramite.COMERCIAL+" a "+p1+" NO debería haber sido exitosa.", s.desasociarTramiteAPuesto(Tramite.COMERCIAL, p1));
    }


    @Test
    public void testCantidadDePuestosAtendiendoElTramiteDespuesDeDesasociar() {
        Puesto c1 = new Puesto("Caja 1");
        Puesto p1 = new Puesto("P1");
        s.asociarTramiteAPuesto(Tramite.CAJA, c1);
        s.asociarTramiteAPuesto(Tramite.CLIENTE, p1);
        s.asociarTramiteAPuesto(Tramite.COMERCIAL, p1);
        s.asociarTramiteAPuesto(Tramite.TARJETAS, p1);
        s.asociarTramiteAPuesto(Tramite.CAJA, p1);

        assertEquals(Tramite.CAJA+" debería tener 2 puestos atendiendo.", 2, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CAJA));
        s.desasociarTramiteAPuesto(Tramite.CAJA, p1);
        assertEquals(Tramite.CAJA+" debería tener 1 puesto atendiendo.", 1, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CAJA));
        s.desasociarTramiteAPuesto(Tramite.CAJA, p1);
        assertEquals(Tramite.CAJA+" debería tener 1 puesto atendiendo.", 1, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CAJA));
        s.desasociarTramiteAPuesto(Tramite.CAJA, c1);
        assertEquals(Tramite.CAJA+" debería tener 0 puestos atendiendo.", 0, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CAJA));
        s.desasociarTramiteAPuesto(Tramite.CAJA, c1);
        s.desasociarTramiteAPuesto(Tramite.CAJA, p1);
        assertEquals(Tramite.CAJA+" debería tener 0 puestos atendiendo.", 0, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CAJA));

        assertEquals(Tramite.TARJETAS+" debería tener 1 puesto atendiendo.", 1, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.TARJETAS));
        s.desasociarTramiteAPuesto(Tramite.TARJETAS, p1);
        assertEquals(Tramite.TARJETAS+" debería tener 0 puestos atendiendo.", 0, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.TARJETAS));

        assertEquals(Tramite.CLIENTE+" debería tener 1 puesto atendiendo.", 1, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CLIENTE));
        s.desasociarTramiteAPuesto(Tramite.CLIENTE, p1);
        assertEquals(Tramite.CLIENTE+" debería tener 0 puesto atendiendo.", 0, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CLIENTE));
        s.desasociarTramiteAPuesto(Tramite.CLIENTE, p1);
        assertEquals(Tramite.CLIENTE+" debería tener 0 puesto atendiendo.", 0, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.CLIENTE));

        assertEquals(Tramite.COMERCIAL+" debería tener 1 puesto atendiendo.", 1, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.COMERCIAL));
        assertEquals(Tramite.COMEX+" debería tener 0 puestos atendiendo.", 0, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.COMEX));
        assertEquals(Tramite.OTROS+" debería tener 0 puestos atendiendo.", 0, s.obtenerCantidadDePuestosAtendiendoElTramite(Tramite.OTROS));
    }

    @Test
    public void testDesasociarTramiteAPuestoConPNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.desasociarTramiteAPuesto(Tramite.CLIENTE, null),
                    "El método desasociarTramiteAPuesto(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }

    @Test
    public void testDesasociarTramiteAPuestoConTNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.desasociarTramiteAPuesto(null, new Puesto("P1")),
                    "El método desasociarTramiteAPuesto(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }

    @Test
    public void testDesasociarTramiteAPuestoConPTNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.desasociarTramiteAPuesto(null, null),
                    "El método dessociarTramiteAPuesto(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }


    private void sacarYVerificarTurno(Persona p, Tramite tr, int numEsperado) {
        Turno t = s.sacarTurno(p, tr);
        assertEquals("El turno obtenido "+t+" debería tener el número "+numEsperado+".", numEsperado, t.getNumero());
        assertEquals("El turno obtenido "+t+" debería tener "+tr+" como trámite asociado.", tr, t.getTramite());
        assertEquals("El turno obtenido "+t+" debería tener "+p+" como poseedor.", p, t.getPoseedor());
        assertEquals("El turno obtenido "+t+" debería tener "+tr.getCodigo()+" como código.", tr.getCodigo(), t.getCodigo());
        assertEquals("La persona "+p+" debería poseer el turno "+t+".", t, p.getTurno());
    }

    @Test
    public void testInfoYPoseedorSacarTurno() {

        this.configurarSucursalBancaria();

        sacarYVerificarTurno(noCli[0], Tramite.CAJA, 1);
        sacarYVerificarTurno(noCli[1], Tramite.CAJA, 2);
        sacarYVerificarTurno(noCli[2], Tramite.COMERCIAL, 1);
        sacarYVerificarTurno(cli[0], Tramite.CAJA, 3);
    }
    

    private void verificarTurnoNullPorNoAtencionDeTramite(Persona p, Tramite tr) {
        assertNull("sacarTurno(...) debería haber retornado null debido a trámite no disponible.", s.sacarTurno(p, tr));
        assertNull("sacarTurno(...) no debería haber asignado un turno a "+p+" debido a trámite no disponible.", p.getTurno());
    }

    @Test
    public void testSacarTurnoNullPorNoAtencionDeTramite() {

        verificarTurnoNullPorNoAtencionDeTramite(new Persona("Cli0", true), Tramite.CAJA);
        verificarTurnoNullPorNoAtencionDeTramite(new Persona("Cli1", true), Tramite.CLIENTE);
        verificarTurnoNullPorNoAtencionDeTramite(new Persona("NoCli2", false), Tramite.COMERCIAL);
        verificarTurnoNullPorNoAtencionDeTramite(new Persona("Cli3", true), Tramite.COMEX);
        verificarTurnoNullPorNoAtencionDeTramite(new Persona("Cli4", true), Tramite.OTROS);
        verificarTurnoNullPorNoAtencionDeTramite(new Persona("Cli5", true), Tramite.TARJETAS);
    }

    private void verificarTurnoNullPorTramiteNoPermitidoANoClientes(Persona p, Tramite tr) {
        assertFalse("Se espera una persona no cliente en lugar de "+p, p.esCliente());
        assertNull("sacarTurno(...) debería haber retornado null ya que un no cliente sacó turno para "+tr+" y debería poder.", s.sacarTurno(p, tr));
        assertNull("sacarTurno(...) no debería haber asignado un turno al no cliente "+p+" por haber sacado turno (no permitido) para "+tr, p.getTurno());
    }

    @Test
    public void testSacarTurnoNullPorTramiteNoPermitidoANoClientes() {

        this.configurarSucursalBancaria();
        verificarTurnoNullPorTramiteNoPermitidoANoClientes(this.noCli[0], Tramite.CLIENTE);
        verificarTurnoNullPorTramiteNoPermitidoANoClientes(this.noCli[1], Tramite.COMEX);
        verificarTurnoNullPorTramiteNoPermitidoANoClientes(this.noCli[0], Tramite.OTROS);
        verificarTurnoNullPorTramiteNoPermitidoANoClientes(this.noCli[1], Tramite.TARJETAS);
    }


    private void sacarTurnoYVerificarTiempoDeEsperaEstimado(Persona p, Tramite tr, int cantPersonasPorDelanteEsperadas) {
        int tEstimEsperado = (int) Math.ceil(cantPersonasPorDelanteEsperadas*tr.getDuracionEstimadaEnMinutos()/(double) cantPuestos(tr));
        Turno t = s.sacarTurno(p, tr);
        assertEquals("El turno obtenido "+t+" debería tener un tiempo de espera estimado de "+tEstimEsperado+" minutos.", tEstimEsperado, s.obtenerTiempoDeEsperaEstimado(t));
    }

    @Test
    public void testSacarTurnoConPNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.sacarTurno(null, Tramite.CLIENTE),
                    "El método sacarTurno(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }

    @Test
    public void testSacarTurnoConTNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.sacarTurno(new Persona("Pers", true), null ),
                    "El método sacarTurno(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }

    @Test
    public void testSacarTurnoConPTNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.sacarTurno(null, null),
                    "El método sacarTurno(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }

    @Test
    public void testObtenerTiempoDeEsperaEstimado() {

        this.configurarSucursalBancaria();

        sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[0], Tramite.CAJA, 0);        
        sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[1], Tramite.CAJA, 1);        
        sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[2], Tramite.COMERCIAL, 0);
        sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[0], Tramite.CAJA, 2);
        sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[1], Tramite.COMERCIAL, 1);
        sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[2], Tramite.COMERCIAL, 2);
        sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[3], Tramite.OTROS, 0);
        sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[4], Tramite.OTROS, 1);
    }


    @Test
    public void testObtenerTiempoDeEsperaEstimadoConTNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.obtenerTiempoDeEsperaEstimado( null ),
                    "El método obtenerTiempoDeEsperaEstimado(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }


    @Test
    public void testObtenerTiempoDeEsperaEstimadoConTurnoInvalido() {

        assertThrows(java.lang.IllegalStateException.class,
                    () -> s.obtenerTiempoDeEsperaEstimado(new Turno(Tramite.CAJA, 3, new Persona("cli0", true))),
                    "El método obtenerTiempoDeEsperaEstimado(...) debería lanzar java.lang.IllegalStateException si el turno no fue dado con sacarTurno(...) para el mismo sistema bancario.");
    }

    @Test
    public void testObtenerTiempoDeEsperaEstimadoConTurnoInvalidoYSucursalConfigurada() {

        this.configurarSucursalBancaria();
        assertThrows(java.lang.IllegalStateException.class,
                    () -> s.obtenerTiempoDeEsperaEstimado(new Turno(Tramite.CAJA, 3, cli[0])),
                    "El método obtenerTiempoDeEsperaEstimado(...) debería lanzar java.lang.IllegalStateException si el turno no fue dado con sacarTurno(...) para el mismo sistema bancario.");
    }


    @Test
    public void testLlamadosNulosSiNoHayTurnos() {

        this.configurarSucursalBancaria();
        for (int i=0; i<cajas.length; i++)
            assertNull("llamarYAtenderProximoTurno(...) debe devolver null si no hay turnos para ninguno de los trámites asignados al puesto", s.llamarYAtenderProximoTurno(cajas[i]));

        for (int i=0; i<puestos.length; i++)
            assertNull("llamarYAtenderProximoTurno(...) debe devolver null si no hay turnos para ninguno de los trámites asignados al puesto", s.llamarYAtenderProximoTurno(puestos[i]));
    }


    @Test
    public void testLlamarYAtenderProximoTurnoConPNulo() {

        assertThrows(java.lang.IllegalArgumentException.class,
                    () -> s.llamarYAtenderProximoTurno( null ),
                    "El método llamarYAtenderProximoTurno(...) debería lanzar java.lang.IllegalArgumentException si se le pasan argumentos nulos.");
    }


    @Test
    public void testObtenerUltimos4LlamadosVacioSinLlamadosRealizados() {

        this.configurarSucursalBancaria();

        assertFalse("llamarYAtenderProximoTurno(...) debe devolver un iterable vacío si no hubo llamados/atenciones de turnos", s.obtenerUltimos4Llamados().iterator().hasNext());

        for (int i=0; i<cajas.length; i++)
            assertNull("llamarYAtenderProximoTurno(...) debe devolver null si no hay turnos para ninguno de los trámites asignados al puesto", s.llamarYAtenderProximoTurno(cajas[i]));

        for (int i=0; i<puestos.length; i++)
            assertNull("llamarYAtenderProximoTurno(...) debe devolver null si no hay turnos para ninguno de los trámites asignados al puesto", s.llamarYAtenderProximoTurno(puestos[i]));

        assertFalse("llamarYAtenderProximoTurno(...) debe devolver un iterable vacío los llamados/atenciones de turnos fueron nulos", s.obtenerUltimos4Llamados().iterator().hasNext());
    }


    private void llamarAtenderYVerificarRes(Puesto p, Turno tEsp) {
        int cotaInfTiempoAt = tEsp.getTramite().getDuracionEstimadaEnMinutos()-3;
        int cotaSupTiempoAt = tEsp.getTramite().getDuracionEstimadaEnMinutos()+3;
        
        Par<Turno,Integer> resultadoAtencion = s.llamarYAtenderProximoTurno(p);
        assertEquals("Se debería haber llamado al turno "+tEsp+" de "+tEsp.getPoseedor()+" pero en su lugar se llamó al turno "+resultadoAtencion.getPrimero(), tEsp, resultadoAtencion.getPrimero());
    	assertTrue("Error en la duración de la atención del llamado", cotaInfTiempoAt<=resultadoAtencion.getSegundo() && resultadoAtencion.getSegundo()<=cotaSupTiempoAt);
    }


    private void verificarUltimos4Llamados(Par<Turno,Puesto> primero, Par<Turno,Puesto> segundo, Par<Turno,Puesto> tercero, Par<Turno,Puesto> cuarto) {

        Vector<Par<Turno,Puesto>> llamadosEsperados = new Vector<>();
        if (primero != null) {
            llamadosEsperados.add(primero);
            if (segundo != null) {
                llamadosEsperados.add(segundo);
                if (tercero!=null) {
                    llamadosEsperados.add(tercero);
                    if (cuarto!=null) {
                        llamadosEsperados.add(cuarto);
                    }
                }
            }
        }

        Iterable<Par<Turno,Puesto>> pantallaTurnos = s.obtenerUltimos4Llamados();
        assertNotNull("obtenerUltimos4Llamados(...) siempre debe devolver un iterable (aunque sea vacío).", pantallaTurnos);
        
        Iterator<Par<Turno,Puesto>> pantallaTurnosIt = pantallaTurnos.iterator();
        int i=0;
        System.out.println("------------------------ Pantalla Llamados -----------------------"); //muestra pantalla por consola
        for (Par<Turno,Puesto> llamadoEsp: llamadosEsperados) {
            i++;
            assertTrue("Debería haber "+llamadosEsperados.size()+" llamado(s) en el registro de los últimos 4 llamados realizados.", pantallaTurnosIt.hasNext());
            Par<Turno,Puesto> llamadoActual = pantallaTurnosIt.next();
            assertNotNull("Debería haber "+llamadosEsperados.size()+" llamado(s) en el registro de los últimos 4 llamados realizados.", llamadoActual);
            assertEquals("El turno "+i+" registrado en los últimos 4 llamados realizados no es correcto.", llamadoEsp.getPrimero(), llamadoActual.getPrimero());
            assertEquals("El puesto "+i+" registrado en los últimos 4 llamados realizados no es correcto.", llamadoEsp.getSegundo(), llamadoActual.getSegundo());

            System.out.println(i+": "+llamadoActual.getPrimero()+" - "+llamadoActual.getSegundo()); //muestra pantalla por consola
        }
        assertFalse("Debería haber "+llamadosEsperados.size()+" llamado(s) en el registro de los últimos 4 llamados realizados.", pantallaTurnosIt.hasNext());
        System.out.println("------------------------------------------------------------------"); //muestra pantalla por consola
    }

    /**
     * Un test end to end (se prueban todas las funciones del sistema) simple en 
     * el sentido que testea un flujo de turnos y llamdos desde puestos que no
     * contiene casos específicos.
     * Este es el primer test end to end que deberían hacer funcionar, antes de 
     * a los siguientes. Para abordar los tests end to end, todos los testeos
     * anteriores ya deberían ser exitosos.
     */
    @Test
    public void testAtencionBancariaEndToEndSimple() {

        this.configurarSucursalBancaria();

        this.verificarUltimos4Llamados(null, null, null, null);
        
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[0], Tramite.CAJA, 0);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[1], Tramite.CAJA, 1);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[0], Tramite.CAJA, 2);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[2], Tramite.COMERCIAL, 0);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[1], Tramite.CLIENTE, 0);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[2], Tramite.CLIENTE, 1);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[3], Tramite.CAJA,3);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[4], Tramite.COMERCIAL,1);
        
        this.llamarAtenderYVerificarRes(cajas[1], noCli[0].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]), 
                                       null, null, null);

        this.llamarAtenderYVerificarRes(puestos[1], noCli[2].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[1]), 
                                       new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]),
                                       null, null);

        this.llamarAtenderYVerificarRes(puestos[0], cli[1].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(cli[1].getTurno(),puestos[0]), 
                                       new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[1]),
                                       new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]),
                                       null);

        this.llamarAtenderYVerificarRes(puestos[3], cli[2].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(cli[2].getTurno(),puestos[3]), 
                                       new Par<Turno, Puesto>(cli[1].getTurno(),puestos[0]),
                                       new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[1]),
                                       new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]));

        this.llamarAtenderYVerificarRes(cajas[0], noCli[1].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(noCli[1].getTurno(),cajas[0]),
                                       new Par<Turno, Puesto>(cli[2].getTurno(),puestos[3]), 
                                       new Par<Turno, Puesto>(cli[1].getTurno(),puestos[0]),
                                       new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[1]));
    }


    /**
     * Similar al test testAtencionBancariaEndToEndSimple, sólo que incorpora llamados en puestos 
     * donde no hay nadie esperando por dichos trámites para añadir esta situación.
     */
    @Test
    public void testAtencionBancariaEndToEndConLlamadosNulos() {

        this.configurarSucursalBancaria();

        this.verificarUltimos4Llamados(null, null, null, null);
        
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[0], Tramite.CAJA, 0);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[1], Tramite.CAJA, 1);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[0], Tramite.CAJA, 2);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[2], Tramite.COMERCIAL, 0);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[1], Tramite.CLIENTE, 0);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[2], Tramite.CLIENTE, 1);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[3], Tramite.CAJA,3);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[4], Tramite.COMERCIAL,1);
        
        this.llamarAtenderYVerificarRes(cajas[1], noCli[0].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]), 
                                       null, null, null);

        this.llamarAtenderYVerificarRes(puestos[1], noCli[2].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[1]), 
                                       new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]),
                                       null, null);

        this.llamarAtenderYVerificarRes(puestos[0], cli[1].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(cli[1].getTurno(),puestos[0]), 
                                       new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[1]),
                                       new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]),
                                       null);

        this.llamarAtenderYVerificarRes(puestos[3], cli[2].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(cli[2].getTurno(),puestos[3]), 
                                       new Par<Turno, Puesto>(cli[1].getTurno(),puestos[0]),
                                       new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[1]),
                                       new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]));

        this.llamarAtenderYVerificarRes(cajas[0], noCli[1].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(noCli[1].getTurno(),cajas[0]),
                                       new Par<Turno, Puesto>(cli[2].getTurno(),puestos[3]), 
                                       new Par<Turno, Puesto>(cli[1].getTurno(),puestos[0]),
                                       new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[1]));

        assertNull("llamarYAtenderProximoTurno(...) debe devolver null si no hay turnos para ninguno de los trámites asignados al puesto", 
                   s.llamarYAtenderProximoTurno(puestos[0]));

        //Si no hay llamados exitosos, la pantalla no debería cambiar...
        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(noCli[1].getTurno(),cajas[0]),
                                       new Par<Turno, Puesto>(cli[2].getTurno(),puestos[3]), 
                                       new Par<Turno, Puesto>(cli[1].getTurno(),puestos[0]),
                                       new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[1]));

        this.llamarAtenderYVerificarRes(cajas[1], cli[0].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(cli[0].getTurno(),cajas[1]),
                                       new Par<Turno, Puesto>(noCli[1].getTurno(),cajas[0]),
                                       new Par<Turno, Puesto>(cli[2].getTurno(),puestos[3]), 
                                       new Par<Turno, Puesto>(cli[1].getTurno(),puestos[0]));

        assertNull("llamarYAtenderProximoTurno(...) debe devolver null si no hay turnos para ninguno de los trámites asignados al puesto", 
                   s.llamarYAtenderProximoTurno(puestos[3]));

        //Si no hay llamados exitosos, la pantalla no debería cambiar...
        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(cli[0].getTurno(),cajas[1]),
                                       new Par<Turno, Puesto>(noCli[1].getTurno(),cajas[0]),
                                       new Par<Turno, Puesto>(cli[2].getTurno(),puestos[3]), 
                                       new Par<Turno, Puesto>(cli[1].getTurno(),puestos[0]));

    }

    /**
     * Testea llamados desde un puesto que atiende varios trámites con personas a 
     * la espera por ellos.
     * 
     * Verifica que se respete el orden de atención de acuerdo al de llegada, 
     * independientemente de que las personas esperen por diferentes trámites.
     * 
     * Si los demás tests funcionan, pero este falla, muy posiblemente se deba a 
     * que se están llamando a las personas en cualquier orden, o se está llamando 
     * primero a todas las personas de un trámite por sobre las personas que 
     * esperan por el otro, cosas que no son correctas.
     */
    @Test
    public void testAtencionBancariaEndToEndConPuestoMultitramite() {

        this.configurarSucursalBancaria();

        this.verificarUltimos4Llamados(null, null, null, null);
        
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[0], Tramite.CAJA, 0);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[1], Tramite.CAJA, 1);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[0], Tramite.CAJA, 2);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(noCli[2], Tramite.COMERCIAL, 0);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[1], Tramite.CLIENTE, 0);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[2], Tramite.CLIENTE, 1);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[3], Tramite.CAJA,3);
        this.sacarTurnoYVerificarTiempoDeEsperaEstimado(cli[4], Tramite.COMERCIAL,1);
        
        this.llamarAtenderYVerificarRes(cajas[1], noCli[0].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]), 
                                       null, null, null);

        this.llamarAtenderYVerificarRes(puestos[2], noCli[2].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[2]), 
                                       new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]),
                                       null, null);

        this.llamarAtenderYVerificarRes(puestos[2], cli[1].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(cli[1].getTurno(),puestos[2]), 
                                       new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[2]),
                                       new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]),
                                       null);

        this.llamarAtenderYVerificarRes(cajas[0], noCli[1].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(noCli[1].getTurno(),cajas[0]),
                                       new Par<Turno, Puesto>(cli[1].getTurno(),puestos[2]), 
                                       new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[2]),
                                       new Par<Turno, Puesto>(noCli[0].getTurno(),cajas[1]));

        this.llamarAtenderYVerificarRes(puestos[2], cli[2].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(cli[2].getTurno(),puestos[2]), 
                                       new Par<Turno, Puesto>(noCli[1].getTurno(),cajas[0]),
                                       new Par<Turno, Puesto>(cli[1].getTurno(),puestos[2]), 
                                       new Par<Turno, Puesto>(noCli[2].getTurno(),puestos[2]));

        this.llamarAtenderYVerificarRes(puestos[2], cli[4].getTurno());

        this.verificarUltimos4Llamados(new Par<Turno, Puesto>(cli[4].getTurno(),puestos[2]),
                                       new Par<Turno, Puesto>(cli[2].getTurno(),puestos[2]), 
                                       new Par<Turno, Puesto>(noCli[1].getTurno(),cajas[0]),
                                       new Par<Turno, Puesto>(cli[1].getTurno(),puestos[2]));
    }
}
