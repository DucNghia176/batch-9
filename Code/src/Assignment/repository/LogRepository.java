package Assignment.repository;

import Assignment.util.DateTimeUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogRepository {
    private static final Pattern LOG_PATTERN = Pattern.compile("\\[(.*?)\\] \\[(.*?)\\] \\[(.*?)\\]- (.*)");
    private static final String POISON_PILL = "__END__";

    public void processLogs(String inputFilePath, String outputFilePath,
                            Set<String> targetLevels, Set<String> targetServices,
                            LocalDateTime startTime, LocalDateTime endTime,
                            String keyword) throws Exception {

        BlockingQueue<List<String>> inputQueue = new ArrayBlockingQueue<>(500);
        BlockingQueue<String> outputQueue = new ArrayBlockingQueue<>(5000);

        int numWorkers = Runtime.getRuntime().availableProcessors();
        ExecutorService workers = Executors.newFixedThreadPool(numWorkers);

        Thread reader = createReader(inputFilePath, inputQueue, numWorkers);
        createWorkers(workers, inputQueue, outputQueue,
                targetLevels, targetServices, startTime, endTime, keyword);
        Thread writer = createWriter(outputFilePath, outputQueue, workers);

        reader.start();
        writer.start();
        reader.join();
        workers.shutdown();
        workers.awaitTermination(1, TimeUnit.HOURS);
        writer.join();
    }

    private Thread createReader(String inputFilePath,
                                BlockingQueue<List<String>> inputQueue,
                                int numWorkers) {
        return new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
                List<String> batch = new ArrayList<>(1000);
                String line;
                while ((line = br.readLine()) != null) {
                    batch.add(line);
                    if (batch.size() == 1000) {
                        inputQueue.put(new ArrayList<>(batch));
                        batch.clear();
                    }
                }
                if (!batch.isEmpty()) inputQueue.put(batch);
                for (int i = 0; i < numWorkers; i++) {
                    inputQueue.put(Collections.singletonList(POISON_PILL));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createWorkers(ExecutorService workers,
                               BlockingQueue<List<String>> inputQueue,
                               BlockingQueue<String> outputQueue,
                               Set<String> targetLevels, Set<String> targetServices,
                               LocalDateTime startTime, LocalDateTime endTime,
                               String keyword) {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            workers.submit(() -> {
                try {
                    while (true) {
                        List<String> batch = inputQueue.take();
                        if (batch.size() == 1 && batch.get(0).equals(POISON_PILL)) break;

                        for (String line : batch) {
                            Matcher matcher = LOG_PATTERN.matcher(line);
                            if (!matcher.matches()) continue;

                            String timestampStr = matcher.group(1);
                            String level = matcher.group(2).toUpperCase();
                            String service = matcher.group(3).trim();
                            String message = matcher.group(4).toLowerCase();

                            LocalDateTime logTime = DateTimeUtil.parseTimestamp(timestampStr);
                            if (logTime == null) continue;

                            if (!targetLevels.isEmpty() && !targetLevels.contains(level)) continue;
                            if (!targetServices.isEmpty() && !targetServices.contains(service)) continue;
                            if (startTime != null && logTime.isBefore(startTime)) continue;
                            if (endTime != null && logTime.isAfter(endTime)) continue;
                            if (keyword != null && !keyword.isEmpty() && !message.contains(keyword)) continue;

                            outputQueue.put(line);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private Thread createWriter(String outputFilePath,
                                BlockingQueue<String> outputQueue,
                                ExecutorService workers) {
        return new Thread(() -> {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
                int count = 0;
                while (true) {
                    String line = outputQueue.poll(5, TimeUnit.SECONDS);
                    if (line == null && workers.isTerminated()) break;
                    if (line != null) {
                        bw.write(line);
                        bw.newLine();
                        count++;
                    }
                }
                System.out.println("Đã ghi xong " + count + " dòng vào " + outputFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
