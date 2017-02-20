package groupgetpost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Group5
 */
public class HTTPClient {

    static String userText = null;
    String optionEntry = null;
    int option = 0;
    
    public HTTPClient() {
        System.out.println("HTTP Client Started");
        try {
            InetAddress serverInetAddress = 
               InetAddress.getByName("127.0.0.1");
            Socket connection = new Socket(serverInetAddress, 80);

            try (OutputStream out = connection.getOutputStream();
                 BufferedReader in = 
                     new BufferedReader(new 
                         InputStreamReader(
                             connection.getInputStream()))) {
                
                Scanner reader = new Scanner(System.in);
                System.out.println("Please select an option.\n1 to write a new passage to your diary or 2 to display the contents of your diary.");
                optionEntry = reader.nextLine();
                option = Integer.parseInt(optionEntry);
                
                    if (option == 1){
                        System.out.println("Entry:");
                        userText = reader.nextLine();
                        sendPost(out);
                        System.out.println(getResponse(in));}

                    else if (option == 2){
                        sendGet(out);
                        System.out.println("Diary Entries:");
                        System.out.println(getResponse(in));}

                    else {
                        System.out.println("That selection is invalid. Please selection option 1 or option 2.");
                        option = reader.nextInt();}
                    
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendGet(OutputStream out) {
        try {
            out.write("GET /default\r\n".getBytes());
            out.write("User-Agent: Mozilla/5.0\r\n".getBytes());
            out.write("\n".getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void sendPost(OutputStream out) {
        try {
            out.write("POST /default\r\n".getBytes());
            out.write("User-Agent: Mozilla/5.0\r\n".getBytes());
            out.write((userText + "\n").getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
   
    
    private String getResponse(BufferedReader in) {
        try {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            return response.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        new HTTPClient();
    }
}
