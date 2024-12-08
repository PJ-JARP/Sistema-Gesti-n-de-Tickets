import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;
import com.google.gson.Gson;

@WebServlet(urlPatterns = {"/TicketServlet"})
public class TicketServlet extends HttpServlet {

    private static final List<String> INGENIEROS = Arrays.asList(
        "ing. Jorge Roque",
        "ing. Diana Mazariegos",
        "ing. Daniel Martinez",
        "ing. Felipe Cavazos",
        "ing. Johan Arath",
        "ing. Garzon Gonzalez",
        "ing. Silvia Elodia"
    );

    private static final String ASIGNACIONES_FILE = "Asignaciones.txt";

    private int folioCounterREQ = 1; // Contador para REQ
    private int folioCounterINC = 1; // Contador para INC

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equalsIgnoreCase(action)) {
            registerTicket(request, response);
        } else if ("assign".equalsIgnoreCase(action)) {
            assignTicket(request, response);
        } else {
            response.getWriter().println("Error: Acción no reconocida.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("generateFolio".equalsIgnoreCase(action)) {
            String tipo = request.getParameter("tipo");
            if (tipo == null || tipo.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Tipo de ticket no especificado.");
                return;
            }

            // Generar folio según el tipo (REQ o INC)
            String folio = generateFolio(tipo);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(folio);

        } else if ("getIngenieros".equalsIgnoreCase(action)) {
            // Devuelve la lista de ingenieros como JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Convertir la lista a JSON usando Gson
            String json = new Gson().toJson(INGENIEROS);

            // Escribir la respuesta
            response.getWriter().write(json);
        }
    }

    private String generateFolio(String tipo) {
        String folio;

        synchronized (this) {
            if ("REQ".equalsIgnoreCase(tipo)) {
                folio = "REQ-" + String.format("%03d", folioCounterREQ++);
            } else if ("INC".equalsIgnoreCase(tipo)) {
                folio = "INC-" + String.format("%03d", folioCounterINC++);
            } else {
                folio = "UNK-000"; // Valor por defecto si el tipo no es válido
            }
        }

        return folio;
    }

    private void registerTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String descripcion = request.getParameter("descripcion");
        String prioridad = request.getParameter("prioridad");
        String estado = request.getParameter("estado");
        String categoria = request.getParameter("categoria");
        String asignadoA = request.getParameter("asignadoA");

        // Validaciones básicas
        if (id == null || descripcion == null || prioridad == null || estado == null || categoria == null || asignadoA == null) {
            response.getWriter().println("Error: Todos los campos son obligatorios.");
            return;
        }

        // Guardar el ticket en el archivo CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tickets.csv", true))) {
            writer.write(String.join(",", id, descripcion, prioridad, estado, categoria, asignadoA));
            writer.newLine();
        }

        response.getWriter().println("Ticket registrado exitosamente.");
    }

    private void assignTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ticketId = request.getParameter("ticketId");
        String ingeniero = request.getParameter("ingeniero");

        // Validaciones básicas
        if (ticketId == null || ticketId.trim().isEmpty()) {
            response.getWriter().println("Error: ID del ticket no puede estar vacío.");
            return;
        }
        if (ingeniero == null || ingeniero.trim().isEmpty()) {
            response.getWriter().println("Error: Debe seleccionar un ingeniero.");
            return;
        }

        // Verificar asignación duplicada
        if (isTicketAlreadyAssigned(ticketId)) {
            response.getWriter().println("Error: El ticket ya está asignado a un ingeniero.");
            return;
        }

        // Guardar asignación
        saveAssignment(ticketId, ingeniero);

        response.getWriter().println("Ticket asignado exitosamente a " + ingeniero);
    }

    private boolean isTicketAlreadyAssigned(String ticketId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ASIGNACIONES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(ticketId)) {
                    return true; // Ticket ya asignado
                }
            }
        } catch (IOException e) {
            // Archivo no encontrado o error de lectura
        }
        return false;
    }

    private void saveAssignment(String ticketId, String ingeniero) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ASIGNACIONES_FILE, true))) {
            writer.write(ticketId + "," + ingeniero);
            writer.newLine();
        }
    }
}
