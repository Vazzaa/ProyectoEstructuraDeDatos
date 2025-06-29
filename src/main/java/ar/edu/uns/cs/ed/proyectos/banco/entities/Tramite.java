package ar.edu.uns.cs.ed.proyectos.banco.entities;

public enum Tramite {
    
    CAJA("Caja","Operaciones en Caja",'C',2),
    CLIENTE("Cliente","Atención al cliente",'A',5),
    COMERCIAL("Comercial","Atención comercial",'B',8),
    TARJETAS("Tarjeta","Operaciones con tarjetas",'T',3),
    COMEX("ComEx","Comercio exterior",'X',5),
    OTROS("Otros","Otros trámites",'O',11);

    private String nombre;
    private String descripcion;
    private char codigo;
    private int duracionEstimadaEnMinutos;

    private Tramite(String nomb, String desc, char cod, int dur) {
        this.nombre = nomb;
        this.descripcion = nomb;
        this.codigo = cod;
        this.duracionEstimadaEnMinutos = dur;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public char getCodigo() {
        return this.codigo;
    }

    public int getDuracionEstimadaEnMinutos() {
        return this.duracionEstimadaEnMinutos;
    }

    public int getDuracionEfectivaEnMinutos() {
        double jitter = Math.random() * 6.0 - 3.0;
        int durTmp = this.duracionEstimadaEnMinutos + (int) jitter;
        return (durTmp < 1)? 1: durTmp;
    }

    @Override
    public String toString() {
        return this.descripcion+" ("+this.codigo+")";
    }
}
