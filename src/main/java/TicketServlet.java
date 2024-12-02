/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author jorge
 */
@WebServlet(urlPatterns = {"/TicketServlet"})
public class TicketServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
       protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String descripcion = request.getParameter("descripcion");
        String prioridad = request.getParameter("prioridad");
  
          // Validación de los datos
        if (TicketValidator.validateId(id) && TicketValidator.validateDescripcion(descripcion) && TicketValidator.validatePrioridad(prioridad)) {
            
            // Verificar si el ticket ya existe
            if (TicketStorage.ticketExists(id)) {
                response.getWriter().println("Error: El ticket con el ID proporcionado ya existe.");
                return;
            }
   // Crear un nuevo ticket
            String fechaCreacion = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            Ticket ticket = new Ticket(id, descripcion, fechaCreacion, prioridad);

            // Guardar el ticket en el archivo CSV
            TicketStorage.saveTicket(ticket);

            response.getWriter().println("Ticket registrado exitosamente.");
        } else {
            response.getWriter().println("Error: Validación fallida. Asegúrate de que todos los campos sean correctos.");
        }
    }
}


