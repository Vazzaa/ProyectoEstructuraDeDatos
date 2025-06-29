package ar.edu.uns.cs.ed.proyectos.banco.entities;

public class Turno {
    
    private Tramite tramite;
    private int numero;
    private Persona poseedor;

    public Turno(Tramite tramite, int num, Persona poseedor) {
        this.tramite = tramite;
        this.numero = num;
        this.poseedor = poseedor;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public char getCodigo() {
        return this.tramite.getCodigo();
    }

    public int getNumero() {
        return this.numero;
    }

    public Persona getPoseedor() {
        return this.poseedor;
    }

    @Override
    public String toString() {
        return this.getCodigo()+"-"+this.numero;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tramite == null) ? 0 : tramite.hashCode());
        result = prime * result + numero;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Turno other = (Turno) obj;
        if (tramite != other.tramite)
            return false;
        if (numero != other.numero)
            return false;
        return true;
    }
}
