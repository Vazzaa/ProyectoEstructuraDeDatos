package ar.edu.uns.cs.ed.proyectos.banco.entities;

public class Persona {
    private String id;
    private boolean esCliente;
    private Turno turno;

    public Persona(String id, boolean esCliente) {
        this.id = id;
        this.esCliente = esCliente;
    }
    
    public String getId() {
        return this.id;
    }

    public boolean esCliente() {
        return this.esCliente;
    }

    public Turno getTurno() {
        return this.turno;
    }

    public void setTurno(Turno t) {
        Tramite tramTurno = t.getTramite();
        if (!this.esCliente && tramTurno!=Tramite.CAJA && tramTurno!=Tramite.COMERCIAL)
            throw new IllegalStateException("Error: Las personas no clientes sólo pueden hacer operaciones de caja o atención comercial.");
        this.turno = t;
    }

    @Override
    public String toString() {
        return this.id+(this.esCliente?" (Cliente)":" (No cliente)");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Persona other = (Persona) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
