import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class JavaPipes {
    private static PipedOutputStream pipedOutputStream = new PipedOutputStream();
    private static PipedInputStream pipedInputStream;

    static {
        try {
            pipedInputStream = new PipedInputStream(pipedOutputStream, 1024* 1024 * 8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        byte[] bytes = new byte[8192];
        Random random = new Random();
        random.nextBytes(bytes);
        Thread thread = new Thread(() -> {
            int counter = 10000000;
            long writeSizr = 0;
            long start = System.currentTimeMillis();
            while (counter-- > 0) {
                try {
                    pipedOutputStream.write(bytes);
                    writeSizr += bytes.length;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            long time = (System.currentTimeMillis() - start) / 1000;
            System.out.println(time);
            System.out.println("Write speed: " + (writeSizr / time / 1024 / 1024) + " mb");
        });
        Thread thread2 = new Thread(() -> {
            int counter = 10000000;
            long readSizr = 0;
            long start = System.currentTimeMillis();
            while (counter-- > 0) {
                try {
                    int read = pipedInputStream.read(bytes);
                    if (read != bytes.length) {
                        System.out.println("Skip: " + read);
                    }
                    readSizr += read;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            long time = (System.currentTimeMillis() - start) / 1000;
            System.out.println(time);
            System.out.println("Read speed: " + (readSizr / time / 1024 / 1024) + " mb");
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread2.start();
    }


}
