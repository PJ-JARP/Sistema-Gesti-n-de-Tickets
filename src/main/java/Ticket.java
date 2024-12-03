
public class Ticket {
   private String id;
    private String descripcion;
    private String fechaCreacion;
    private String prioridad;  // Opcional, puedes agregar otros campos como estado, asignado, etc.
    private String estado;     // Estado del ticket: Abierto, En progreso, Resuelto, etc.
    private String categoria;  // Categoría o tipo de solicitud: Soporte técnico, Consulta general, etc.
    private String asignadoA;  // Persona responsable de gestionar el ticket


     // Constructor actualizado con todos los campos
    public Ticket(String id, String descripcion, String fechaCreacion, String prioridad, String estado, String categoria, String asignadoA) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.categoria = categoria;
        this.asignadoA = asignadoA;
    }
    
   
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }

     // Método para convertir el ticket a formato CSV
    public String toCSV() {
        return id + "," + descripcion + "," + fechaCreacion + "," + prioridad + "," + estado + "," + categoria + "," + asignadoA;
    }
}