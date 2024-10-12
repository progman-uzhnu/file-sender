package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class FileSplitter {

    // TODO: hash SHA-256
    public static List<byte[]> splitFile(File file, int partSize) {
        List<byte[]> fileParts = new ArrayList<>();

        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[partSize];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                if (bytesRead < partSize) {
                    byte[] result = new byte[bytesRead];
                    System.arraycopy(buffer, 0, result, 0, bytesRead);
                } else {
                    fileParts.add(buffer.clone());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileParts;
    }

    public static void sendFile(List<byte[]> file, String address) {
        try {
            Socket socket = new Socket(address, 8080);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeInt(file.size());

            for (byte[] part : file) {
                dataOutputStream.writeInt(part.length);
                dataOutputStream.write(part);
            }

            System.out.println("Sent successfully");
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        File file = new File("key.txt");
        List<byte[]> split = splitFile(file, 1);
        sendFile(split, "localhost");
    }
}

