package chap01;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class ServerThread implements Runnable{
    private static final String DOCUMENT_ROOT = "/workspace/src/chap01/docs";
    private Socket socket;

    private static String readLine(InputStream input) throws Exception {
        int ch;
        String ret = "";
        while ((ch = input.read()) != -1) {
            if (ch == '\r') {
                // Do Nothing
            } else if (ch == '\n') {
                break;
            } else {
                ret += (char)ch;
            }
        }
        if (ch == -1) {
            return null;
        } else {
            return ret;
        }
    }

    private static void writeLine(OutputStream output, String str) throws Exception { 
        for (char ch : str.toCharArray()) {
            output.write((int)ch);
        }
        output.write((int)'\r');
        output.write((int)'\n');
    }

    private static String getDateStringUtc() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);

        df.setTimeZone(cal.getTimeZone());
        return df.format(cal.getTime()) + " GMT";
    }

    private static final HashMap<String, String> contentTypeMap =
        new HashMap<String, String>() {{
            put("html", "text/html");
            put("htm", "text/html");
            put("css", "text/css");
            put("png", "image/png");
            put("png", "image/png");
            put("jpg", "image/jpg");
            put("jpeg", "image/jpeg");
            put("gif", "image/gif");

        }};

    private static String getContentType(String ext) {
        String ret = contentTypeMap.get(ext.toLowerCase());
        if (ret==null) {
            return "application/octet-stream";
        }
        return ret;
    }

    @Override
    public void run() {
        OutputStream output;
        try {
            InputStream input = socket.getInputStream();

            String line;
            String path = null;
            String ext = null;
            while ((line = readLine(input)) != null) {
                if (line == "") {
                    break;
                }
                if (line.startsWith("GET")) {
                    path = line.split(" ")[1];
                    String[] tmp = path.split("\\.");
                    ext = tmp[tmp.length -1];
                }
            }
            output = socket.getOutputStream();

            writeLine(output, "HTTP/1.1 200 OK");
            writeLine(output, "Date: " + getDateStringUtc());
            writeLine(output, "Server: Modoki/0.1");
            writeLine(output, "Connection: close");
            writeLine(output, "Content-type: " + getContentType(ext));
            writeLine(output, "");

            try (FileInputStream fis = new FileInputStream(DOCUMENT_ROOT + path);){
                int ch;
                while ((ch = fis.read()) != -1) {
                    output.write(ch);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ServerThread(Socket socket) {
        this.socket = socket;
    }
}
