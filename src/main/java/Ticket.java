public class Ticket {
    private String id;
    private String descripcion;
    private String fechaCreacion;
    private String prioridad;  // Opcional, puedes agregar otros campos como estado, asignado, etc.

    // Constructor
    public Ticket(String id, String descripcion, String fechaCreacion, String prioridad) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.prioridad = prioridad;
    }
    
    //comit1
    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    
    // Método para convertir el ticket a formato CSV
    public String toCSV() {
        return id + "," + descripcion + "," + fechaCreacion + "," + prioridad;
    }
}
