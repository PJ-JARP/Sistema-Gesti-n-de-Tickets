//importación de librerías


import java.io.*;
import java.util.*;

public class TicketStorage {
     // Archivo CSV donde se almacenarán los ticket
    private static final String FILE_NAME = "C:/Users/jorge/OneDrive/Escritorio/CSV out/tickets.csv";
    
    // Método para verificar si un ticket ya existe
    public static boolean ticketExists(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(id)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para guardar un ticket en el archivo CSV
    public static void saveTicket(Ticket ticket) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(ticket.toCSV());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
