import java.io.*;
import java.util.*;

public class TicketStorage {
    private static final String FILE_NAME = "C:\\Users\\jorge\\OneDrive\\Documentos\\CSV out\\tickets.csv";

    // Asegurarse de que el archivo exista
    private static void ensureFileExists() {
        File file = new File(FILE_NAME);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Crear directorios si no existen
                file.createNewFile(); // Crear el archivo
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    // Leer todos los tickets del archivo CSV
    public static List<Ticket> readAllTickets() {
        ensureFileExists();
        List<Ticket> tickets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); //se hizo el cambio de tabulación.
                if (values.length >= 7) {
                    Ticket ticket = new Ticket(
                        values[0], // ID
                        values[1], // Descripción
                        values[2], // Fecha de creación
                        values[3], // Prioridad
                        values[4], // Estado
                        values[5], // Categoría
                        values[6]  // Asignado a
                    );
                    tickets.add(ticket);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return tickets;
    }

    // Escribir una lista de tickets en el archivo CSV
    public static void writeAllTickets(List<Ticket> tickets) {
        ensureFileExists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Ticket ticket : tickets) {
                bw.write(ticket.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Guardar un nuevo ticket
    public static void saveTicket(Ticket ticket) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(ticket.toCSV()); // Ahora usa comas
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar el ticket: " + e.getMessage());
            e.printStackTrace();
        }
        
        /*List<Ticket> tickets = readAllTickets();
        tickets.add(ticket); // Agregar el nuevo ticket
        writeAllTickets(tickets); // Guardar toda la lista*/
    }

    // Verificar si un ticket ya existe
    public static boolean ticketExists(String id) {
        return readAllTickets().stream().anyMatch(ticket -> ticket.getId().equals(id));
    }

    // Contar tickets por estado
    public static int countByStatus(String status) {
        return (int) readAllTickets().stream()
            .filter(ticket -> ticket.getEstado().equalsIgnoreCase(status))
            .count();
    }

    // Obtener las últimas 5 actividades
    /*public static List<String> getRecentActivity() {
        List<Ticket> tickets = readAllTickets();
        int start = Math.max(0, tickets.size() - 5);
        List<String> recentActivities = new ArrayList<>();
        for (Ticket ticket : tickets.subList(start, tickets.size())) {
            recentActivities.add(ticket.toCSV());
        }
        return recentActivities;
    }*/

    static boolean isTicketAlreadyAssigned(String ticketId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    static Map<String, Integer> getMetrics() {
        ensureFileExists(); // Asegura que el archivo exista
        Map<String, Integer> metrics = new HashMap<>();
        metrics.put("Abiertos", 0);
        metrics.put("En Progreso", 0);
       // metrics.put("Cerrados", 0);

     try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
        String line;
         while ((line = reader.readLine()) != null) {
             String[] parts = line.split(","); // Asegúrate de que el delimitador sea correcto
             if (parts.length > 4) {
                String estado = parts[4].trim();
                 System.out.println("Estado encontrado: " + estado); // Depuración
                 
                if (estado.equalsIgnoreCase("Abierto")) {
                     
                     metrics.put("Abiertos", metrics.get("Abiertos") + 1);
                     
                 } else if (estado.equalsIgnoreCase("En Progreso")) {
                     
                     metrics.put("En Progreso", metrics.get("En Progreso") + 1);
                     
                 } else if (estado.equalsIgnoreCase("Resuelto") || estado.equalsIgnoreCase("Cerrado")) {
                     
                metrics.put("Cerrados", metrics.get("Cerrados") + 1); // Unificar "Resuelto" y "Cerrados"
                
                } else {
                    System.err.println("Estado no reconocido: " + estado); // Para estados inesperados
                }
             }
         }
     } catch (IOException e) {
         System.err.println("Error al leer el archivo para métricas: " + e.getMessage());
     }

     return metrics;
 }
         /*String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(","); 
            if (parts.length > 4) {
                String estado = parts[4].trim();
                metrics.put(estado, metrics.getOrDefault(estado, 0) + 1);
            }
        }
    } catch (IOException e) {
        System.err.println("Error al leer el archivo para métricas: " + e.getMessage());
    }

    return metrics;
}*/

    public static List<String> getRecentActivity() {
    ensureFileExists(); // Asegura que el archivo exista
    List<String> recentActivities = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
        List<String> allLines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            allLines.add(line);
        }

        // Toma las últimas 5 líneas como actividad reciente
        int start = Math.max(0, allLines.size() - 5);
        recentActivities = allLines.subList(start, allLines.size());
    } catch (IOException e) {
        System.err.println("Error al leer el archivo para actividad reciente: " + e.getMessage());
    }

    return recentActivities;
    }
}
