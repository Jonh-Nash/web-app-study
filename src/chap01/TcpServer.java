package chap01;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer{
    public static void main(String[] args) throws Exception {
        try (ServerSocket server = new ServerSocket(8001);
            FileOutputStream fos = new FileOutputStream("./chap01/server_recv.txt");
            FileInputStream fis = new FileInputStream("./chap01/server_send.txt")){
                System.out.println("Waiting for connection from client...");
                Socket socket = server.accept();
                System.out.println("Done connection with Client.");

                int ch;
                InputStream input = socket.getInputStream();
                System.out.println("getInputStream");
                while ((ch = input.read()) != 0) {
                    fos.write(ch);
                    System.out.println("write...");
                }

                OutputStream output = socket.getOutputStream();
                System.out.println("getOutputStream");
                while((ch = fis.read()) != -1) {
                    output.write(ch);
                    System.out.println("write...");
                }

                socket.close();;
                System.out.println("Close Connection.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}