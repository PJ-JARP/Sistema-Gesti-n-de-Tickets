
public class TicketValidator {
    
    // Validar que la descripción no sea nula ni vacía
    public static boolean validateDescripcion(String descripcion) {
        return descripcion != null && !descripcion.trim().isEmpty();
    }

    // Validar que el ID no sea nulo ni vacío
    public static boolean validateId(String id) {
        return id != null && !id.trim().isEmpty();
    }

    // Validar la prioridad (puedes tener varias prioridades predefinidas)
    public static boolean validatePrioridad(String prioridad) {
        return prioridad != null && (prioridad.equals("Alta") || prioridad.equals("Media") || prioridad.equals("Baja"));
    }
}//finalización de clase.
