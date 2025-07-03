package ar.edu.uns.cs.ed.proyectos.banco;

import ar.edu.uns.cs.ed.proyectos.banco.entities.Persona;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Puesto;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Tramite;
import ar.edu.uns.cs.ed.proyectos.banco.entities.Turno;
import ar.edu.uns.cs.ed.proyectos.banco.util.Par;

public interface SistemaBancario {

    /**
     * Añade el trámite t a los trámites que pueden atenderse en el puesto p. Se espera que cada asociación
     * entre t y p se guarde una única vez, retornando false si la asociación ya existía, y true sino.
     * @param t El trámite a asociar
     * @param p El puesto al que se añade el trámite
     * @return true si el trámite fue asociado exitosamente (no existía asociado), false en caso contrario
     * @throws java.lang.IllegalArgumentException si t o p son nulos
     */
    
    public boolean asociarTramiteAPuesto(Tramite t, Puesto p);

    /**
     * Elimina el trámite t de los trámites que pueden atenderse en el puesto p. Se espera que cada asociación
     * entre t y p se guarde una única vez, retornando true si la asociación ya existía y pudo eliminarse, 
     * y false sino.
     * @param t El trámite a desasociar
     * @param p El puesto al que se le elimina el trámite
     * @return true si el trámite fue desasociado exitosamente (existía asociado), false en caso contrario
     * @throws java.lang.IllegalArgumentException si t o p son nulos
     */
    public boolean desasociarTramiteAPuesto(Tramite t, Puesto p);

    /**
     * Devuelve un Iterable con los trámites actualmente asociados a p.
     * Este método es útil para determinar qué trámites pueden ser atendidos en p.
     * @param p El puesto para el que se desean obtener los trámites asociados.
     * @return Un Iterable de trámites asociados a p.
     * @throws java.lang.IllegalArgumentException si p es nulo
     */
    public Iterable<Tramite> obtenerTramitesAsociadosAPuesto(Puesto p);

    /**
     * Devuelve la cantidad de puestos que actualmente están atendiendo el trámite t.
     * Este método es útil para evitar dar turnos para un trámite que no puede ser atendido, y
     * para calcular el tiempo de espera estimado de una persona recién llegada a la sucursal.
     * @param t El trámite para el que se desean obtener la cantidad de puestos de atención
     * @return La cantidad de puestos que actualmente están atendiendo el trámite t.
     * @throws java.lang.IllegalArgumentException si t es nulo
     */
    public int obtenerCantidadDePuestosAtendiendoElTramite(Tramite t);




    /**
     * Modela la acción de una persona p al llegar a la sucursal bancaria y sacar un turno para el trámite t.
     * Se asocia el turno dado a la persona que lo solicitó, y, teniendo en cuenta el tipo de p y t, 
     * se registra la misma en el sistema de espera de la sucursal bancaria.
     * @param p La persona que pide el turno
     * @param t El trámite para el que se pide el turno
     * @return el Turno generado para la persona según su tipo y el trámite solicitado, o null si no
     *         se le puede dar un turno a la persona porque no hay puestos atendiendo el trámite solicitado,
     *         o porque la persona no es cliente del banco e intenta realizar un trámite para clientes.
     * @throws java.lang.IllegalArgumentException si p o t son nulos
     */
    public Turno sacarTurno(Persona p, Tramite t);

    /**
     * Devuelve el tiempo estimado de espera en minutos para el turno asignado t. 
     * Sea t el turno, tr el trámite y p la persona poseedora del mismo, consiste en multiplicar la duración
     * estimada tr por la cantidad de personas que también esperan por tr y que serán atendidas antes que p, 
     * dividido la cantidad de puestos que atienden tr. Si el resultado no es entero, se devuelve el entero
     * inmediato superior al mismo.
     * Ej. Si hay 7 personas esperando para el tr antes que p, el tr tiene una demora de atención estimada 
     * de 5 minutos, y hay 3 puestos atendiendo tr (entre otros trámites), el tiempo de espera es
     * tEsp = 7 * 5 / 3 = 11.6666... resultado cuyo Math.ceil(11.6666...) es 12 minutos.
     * @param t El turno asignado para el que se desea calcular el tiempo de espera estimado
     * @return Un int representando el tiempo de espera en minutos
     * @throws java.lang.IllegalStateException si t no es nulo pero no se encuentra registrado en la sucursal
     *         bancaria (i.e. no fue obtenido mediante el método sacarTurno de la misma instancia)
     * @throws java.lang.IllegalArgumentException si t es nulo
     */
    public int obtenerTiempoDeEsperaEstimado(Turno t);

    


    /**
     * Modela la acción de llamar a la próxima persona en espera para ser atendida en el puesto p.
     * De entre todos los turno dados para las personas que esperan por todos los trámites que se
     * atienden en p, se determina cuál es el próximo turno en ser atendido.
     * De entre todos los turnos antendibles en p, siempre se atiende aquel turno que haya ingresado
     * antes al sistema.
     * Una vez atendido, dicho turno se elimina del sistema de espera para evitar ser llamado por 
     * otros puestos que atiendan el mismo trámite, y se registra como parte de los últimos 4 llamados
     * para ser mostrado con el puesto que realizó la llamada.
     * @param p El puesto que realiza el llamado (y posterior atención)
     * @return Un Par con el Turno llamado/atendido y con un Integer representando la duración 
     *         efectiva de la atención del trámite en minutos, o null si no hay Turnos en espera
     *         para ninguno de los trámites atendidos por el puesto.
     * @throws java.lang.IllegalArgumentException si p es nulo
     */
    public Par<Turno,Integer> llamarYAtenderProximoTurno(Puesto p);

    /**
     * Devuelve un Iterable con los últimos 4 llamados realizados (representa la información necesaria
     * para informar a las personas en las pantallas del banco sobre los llamados a los puestos).
     * El primer llamado realizado devuelto por el Iterable deberá ser el más reciente.
     * @return Un Iterable con a lo sumo 4 Pares, conteniendo c/u el turno llamado 
     *         (a partir de llamarYAtenderProximoTurno(...)) y en qué puesto fue llamado, ordenados
     *         del más reciente al más antiguo.
     */
    public Iterable<Par<Turno,Puesto>> obtenerUltimos4Llamados();

}
