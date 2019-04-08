import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class JavaOneServerSocket {
    public static void main(String[] args) throws IOException {
        long start = 0;
        AtomicLong size = new AtomicLong();
        try (ServerSocket listener = new ServerSocket(5231)) {
            System.out.println("The socket server is running...");
            Socket socket = listener.accept();
            start = System.currentTimeMillis();
            try {
                BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
                int c;
                byte[] bytes = new byte[8192];
                while ((c = is.read(bytes)) != -1) {
                    size.addAndGet(c);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        long time = (end - start);
        System.out.println(time);
        System.out.println(size.get());
        System.out.println("Read: " + ((double) size.get() / time / 1024 / 1024) + " Mb/ms");
    }
}
