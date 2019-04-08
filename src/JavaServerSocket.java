import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class JavaServerSocket {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newCachedThreadPool();
        long start = 0;
        AtomicLong size = new AtomicLong();
        try (ServerSocket listener = new ServerSocket(5231)) {
            System.out.println("The socket server is running...");
            int counter = 10_000;
            while (counter-- > 0) {
                Socket socket = listener.accept();
//                    System.out.println("Accept");
                if (counter == 9_999) {
                    start = System.currentTimeMillis();
                }
                if (counter % 500 == 0) {
                    System.out.println("Requests remaining: " + counter);
                }
                service.execute(() -> {

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
                });
            }
        }
        long end = System.currentTimeMillis();
        long time = (end - start);
        System.out.println(time);
        System.out.println(size.get());
        System.out.println("Read: " + ((double)size.get() / time / 1024 / 1024) + " Mb/ms");
        service.shutdown();
    }
}
