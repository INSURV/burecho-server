/**
 * This file is property of INSURV (c) 2014
 * BURECHO is a program property of INSURV (c) 2014
 *
 * This is the BURECHO server. It implements the ECHO protocol and is used for surveillance tasks
 * in conjunction with other INSURV programs.
 *
 * Usage: java BurechoServer PORT
 */
import java.io.*;
import java.net.*;
import java.util.UUID;

public class BurechoServer implements Runnable {
    Socket csocket;
    BurechoServer (Socket csocket) {
        this.csocket = csocket;
    }

    public void run()  {
        PrintWriter out;
        BufferedReader in;
        String input;
        try {
            out = new PrintWriter(csocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
            // Generate reference for data
            UUID id = UUID.randomUUID();
            String fname = "db/K2TINB14" + id.toString();
            System.out.println(">> Starting data transfer...");
            System.out.println(">> REF: " + fname);
            PrintWriter data = new PrintWriter(fname, "UTF-8");
            while ((input = in.readLine()) != null) {
                if (!input.trim().equals("STOP")) {
                    out.println(">> ECHO: " + input);
                    data.println(input);
                } else {
                    out.println(">> BYE");
                    data.println("DATA TRANSMISSION STOPPED");
                    break;
                }

            }
            System.out.println(">> Stopped data transmission. Closing referenced file.");
            data.close();
        } catch (Exception e) {
            System.err.println(">> Error while processing incoming connection.");
        }
    }
    public static void main(String[] args) throws IOException {
        // Syntax validation
        if (args.length != 1) {
            System.err.println("Usage: java BurechoServer [port]");
            System.exit(1);
        }
        // Variable declarations
        int port = Integer.parseInt(args[0]);
        ServerSocket burecho_server;
        Socket burecho_client;


        // Start the server up
        try {
            System.out.printf(">> Starting BURECHO server on port %d...\n", port);
            burecho_server = new ServerSocket(port);
            System.out.println(">> Server started correctly.");
            while (true) {
                burecho_client = burecho_server.accept();
                System.out.println(">> Connection accepted.");
                new Thread(new BurechoServer(burecho_client)).start();
            }

        } catch (IOException e) {
            System.out.println("BURECHO caught an exception while trying to listen for a connection on " +
                    "port " + port);
            System.out.println(e.getMessage());
        }
    }

}