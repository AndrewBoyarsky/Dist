import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class JavaOneClientSocket {
    public static void main(String[] args) {
        byte[] bytes = new byte[1 << 12];
        try (Socket socket = new Socket("127.0.0.1", 5231)) {
            int socCounter = 1_000_000;

            BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream());
            while (socCounter-- > 0) {
                outputStream.write(bytes);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
