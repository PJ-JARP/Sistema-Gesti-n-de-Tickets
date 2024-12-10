/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *
 * @author jorge
 */
@WebServlet(urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if ("metrics".equalsIgnoreCase(action)) {
            Map<String, Integer> metrics = new HashMap<>();
            metrics.put("open", TicketStorage.countByStatus("Abierto"));
            metrics.put("inProgress", TicketStorage.countByStatus("En Progreso"));
            metrics.put("closed", TicketStorage.countByStatus("Cerrado"));
            response.getWriter().write(new com.google.gson.Gson().toJson(metrics));
            
            // Leer métricas desde el archivo CSV
            /*Map<String, Integer> metrics = TicketStorage.getMetrics();
            response.getWriter().write(new Gson().toJson(metrics));
            
            // Generar métricas
            Map<String, Integer> metrics = new HashMap<>();
            metrics.put("open", TicketStorage.countByStatus("Abierto"));
            metrics.put("inProgress", TicketStorage.countByStatus("En Progreso"));
            metrics.put("closed", TicketStorage.countByStatus("Cerrado"));

            response.getWriter().write(new com.google.gson.Gson().toJson(metrics));*/
            
        } else if ("recent-activity".equalsIgnoreCase(action)) {
            // Leer actividad reciente desde el archivo CSV
             List<String> activity = TicketStorage.getRecentActivity();
            response.getWriter().write(new com.google.gson.Gson().toJson(activity));
            
            /*List<String> recentActivity = TicketStorage.getRecentActivity();
            response.getWriter().write(new Gson().toJson(recentActivity));
            
            // Obtener actividad reciente
            List<String> activity = TicketStorage.getRecentActivity();
            response.getWriter().write(new com.google.gson.Gson().toJson(activity));*/
        } else {
             response.getWriter().write("{\"error\":\"Acción no válida\"}");
        }
    }
}
