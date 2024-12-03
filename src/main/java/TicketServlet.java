/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

//importación de librerías
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = {"/TicketServlet"})
public class TicketServlet extends HttpServlet {
    private static final String FOLIO_FILE = "folio.txt";
    private static final AtomicInteger folioCounter = new AtomicInteger(loadLastFolio());

    // Cargar el último folio desde un archivo (folio.txt)
    private static int loadLastFolio() {
        File file = new File(FOLIO_FILE);

        // Verificar si el archivo existe
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Crear el archivo y escribir el valor inicial 0
                writer.write("0");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0; // Retornar 0 como valor inicial
        }

        // Leer el valor del archivo existente
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String lastFolio = reader.readLine();
            return lastFolio != null ? Integer.parseInt(lastFolio) : 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0; // Si ocurre un error, retornar 0
    }

    // Guardar el último folio en un archivo (folio.txt)
    private static void saveLastFolio(int folio) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FOLIO_FILE))) {
            writer.write(String.valueOf(folio));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipo = request.getParameter("tipo");
        if (tipo == null || (!tipo.equals("REQ") && !tipo.equals("INC"))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error: Tipo inválido.");
            return;
        }

        // Generar un folio provisional (no se incrementa aún)
        int nextFolio = folioCounter.get(); 
        String folioGenerado = tipo + String.format("%05d", nextFolio);
        response.getWriter().write(folioGenerado);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String descripcion = request.getParameter("descripcion");
        String prioridad = request.getParameter("prioridad");
        String estado = request.getParameter("estado");
        String categoria = request.getParameter("categoria");
        String asignadoA = request.getParameter("asignadoA");

        // Validaciones
        if (TicketValidator.validateId(id) && TicketValidator.validateDescripcion(descripcion)
                && TicketValidator.validatePrioridad(prioridad) && TicketValidator.validateEstado(estado)
                && TicketValidator.validateCategoria(categoria) && TicketValidator.validateAsignadoA(asignadoA)) {

            if (TicketStorage.ticketExists(id)) {
                response.getWriter().println("Error: El ticket con el ID proporcionado ya existe.");
                return;
            }

            // Crear el ticket
            String fechaCreacion = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            Ticket ticket = new Ticket(id, descripcion, fechaCreacion, prioridad, estado, categoria, asignadoA);

            // Guardar el ticket
            TicketStorage.saveTicket(ticket);

            // Incrementar el contador y guardar el nuevo folio
            int nextFolio = folioCounter.incrementAndGet();
            saveLastFolio(nextFolio);

            response.getWriter().println("Ticket registrado exitosamente con ID: " + id);
        } else {
            response.getWriter().println("Error: Validación fallida. Asegúrate de que todos los campos sean correctos.");
        }
    }
}



