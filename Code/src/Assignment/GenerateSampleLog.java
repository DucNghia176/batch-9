package Assignment;

import javax.swing.text.DateFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class GenerateSampleLog {
    private static final DateTimeFormatter TIMETAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String[] LEVELS = {"ERROR", "WARN", "INFO", "DEBUG", "TRACE"};
    private static final String[] SERVICES = {"UserService", "PaymentService", "AuthService", "OrderService", "NotificationService"};
    private static final String[] MESSAGES = {
            // INFO
            "User login successful",
            "Order processed successfully",
            "Notification sent to user",
            "User profile updated",
            "Password changed successfully",
            "Item added to cart",
            "Checkout completed",
            "Email verification sent",

            // WARN
            "Authentication timeout",
            "User session expired",
            "Too many login attempts",
            "Payment retry due to timeout",
            "Slow database query detected",
            "Disk space running low",
            "High memory usage warning",

            // ERROR
            "Payment failed due to insufficient funds",
            "Database connection error",
            "Service unavailable",
            "Payment gateway timeout",
            "Invalid user input",
            "Null pointer exception encountered",
            "Unable to reach external API",
            "Order creation failed",
            "Message queue overflow",
            "File upload failed",

            // DEBUG
            "Entering authentication method",
            "User object successfully mapped",
            "Request payload validated",
            "Cache miss for user session",
            "Order status updated in memory",
            "Executing SQL query",
            "Response sent to client",

            // TRACE
            "Begin transaction",
            "End transaction",
            "Lock acquired on resource",
            "Lock released on resource",
            "Request received from client",
            "Response headers set",
            "Thread started",
            "Thread finished"
    };


    public static void main(String[] args) {
        String outputFilePath = "log.txt";
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số dòng log cần tạo: ");
        long lineCount = sc.nextInt();
        sc.nextLine();

        System.out.print("Nhập số thread muốn chạy song song: ");
        int numThreads = sc.nextInt();
        sc.nextLine();

        long startTime = System.currentTimeMillis();

        Random random = new Random();
        LocalDateTime startDate = LocalDateTime.of(2024,11, 1, 0,0,0 );
        long secondsRange = Duration.between(startDate, LocalDateTime.of(2025,11,1,0,0,0)).getSeconds();

        long linesPerThread = lineCount / numThreads;
        long remainder = lineCount % numThreads;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            Object lock = new Object();
            Thread[] threads = new Thread[numThreads];

            for (int t = 0; t < numThreads ; t++) {
                long taskSize = linesPerThread + (t == numThreads-1? remainder:0);
                int threadId = t;

                threads[t] = new Thread(() ->{
                    for (long i = 0; i < taskSize; i++) {
                        long randomSeconds = random.nextLong(secondsRange);
                        LocalDateTime randomTime = startDate.plusSeconds(randomSeconds);
                        String timestamp = randomTime.format(TIMETAMP_FORMAT);

                        String level = LEVELS[random.nextInt(LEVELS.length)];
                        String services = SERVICES[random.nextInt(SERVICES.length)];
                        String message = MESSAGES[random.nextInt(MESSAGES.length)];

                        String logLine = String.format("[%s] [%s] [%s]- %s", timestamp, level, services, message);

                        synchronized (lock){
                            try {
                                writer.write(logLine);
                                writer.newLine();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }

//                        if (i % 100000 == 0) {
//                            System.out.println("Thread " + threadId + " đã tạo " + i + " dòng...");                        }
                    }
                    long endTime = System.currentTimeMillis();
                    long durationMillis = endTime - startTime;
                    double durationSeconds = durationMillis / 1000.0;
                    System.out.println("Thread " + threadId + " hoàn tất!");
                    System.out.println("Thời gian: " +  durationSeconds);
                });
                threads[t].start();
            }

            for (Thread t : threads) {
                try {
                    t.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            System.out.println("Hoàn tất! File log đã được tạo tại " + outputFilePath +" với " + lineCount +" dòng");
        } catch (IOException e) {
            System.err.println("Lỗi khi tạo file log: " + e.getMessage());
        }
    }
}
