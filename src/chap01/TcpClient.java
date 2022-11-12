package chap01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpClient {
    public static void main(String[] args) throws Exception{
        try (Socket socket = new Socket("host.docker.internal", 80);
        FileInputStream fis = new FileInputStream("./chap01/client_send.txt");
        FileOutputStream fos = new FileOutputStream("./chap01/client_recv.txt")){

            System.out.println("Socket start.");

            int ch;
            OutputStream output = socket.getOutputStream();
            while ((ch = fis.read()) != -1) {
                output.write(ch);
            }

            InputStream input = socket.getInputStream();
            while ((ch = input.read()) != -1) {
                fos.write(ch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
