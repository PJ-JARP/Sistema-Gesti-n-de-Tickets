public class TicketValidator {
    public static boolean validateId(String id) {
        // Verifica que el ID no sea nulo, no esté vacío y solo contenga caracteres alfanuméricos
        return id != null && !id.trim().isEmpty() && id.matches("[A-Za-z0-9]+");
    }

    public static boolean validateDescripcion(String descripcion) {
        // Verifica que la descripción no sea nula ni esté vacía
        return descripcion != null && !descripcion.trim().isEmpty();
    }

    public static boolean validatePrioridad(String prioridad) {
        // Verifica que la prioridad sea uno de los valores permitidos
        return prioridad != null && (prioridad.equals("Alta") || prioridad.equals("Media") || prioridad.equals("Baja"));
    }

    public static boolean validateEstado(String estado) {
        // Verifica que el estado sea uno de los valores permitidos
        return estado != null && (estado.equals("Abierto") || estado.equals("En Progreso") || estado.equals("Resuelto"));
    }

    public static boolean validateCategoria(String categoria) {
        // Verifica que la categoría no sea nula ni esté vacía
        return categoria != null && !categoria.trim().isEmpty();
    }

    public static boolean validateAsignadoA(String asignadoA) {
        // Verifica que el campo "Asignado a" no sea nulo ni esté vacío
        return asignadoA != null && !asignadoA.trim().isEmpty();
    }
}

