import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JavaClientSocket {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executors = Executors.newFixedThreadPool(1000);
        byte[] bytes = new byte[1 << 12];
        int counter = 10_000;

        while (counter-- > 0) {
            executors.execute(() -> {
                try (Socket socket = new Socket("127.0.0.1", 5231)) {
                    int socCounter = 1000;

                    BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
                    while (socCounter-- > 0) {
                        outputStream.write(bytes);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        executors.shutdown();
    }
}
