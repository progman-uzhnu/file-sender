package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TestServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                FileOutputStream fileOutputStream = new FileOutputStream("file" + new Random().nextInt(1000) + ".txt");

                int partSize = dataInputStream.readInt();
                for (int i = 0; i < partSize; i++) {
                    int length = dataInputStream.readInt();

                    byte[] buffer = new byte[length];

                    dataInputStream.readFully(buffer);
                    fileOutputStream.write(buffer);
                }

                System.out.println("Received new file from " + socket.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
