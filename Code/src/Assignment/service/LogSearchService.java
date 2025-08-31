package Assignment.service;

import Assignment.repository.LogRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

public class LogSearchService {
    private final LogRepository logRepository;

    public LogSearchService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    public void searchLogs(String inputFile, String outputFile,
                           Set<String> levels, Set<String> services,
                           LocalDateTime startTime, LocalDateTime endTime,
                           String keyword) throws Exception {
        long start = System.currentTimeMillis();
        logRepository.processLogs(inputFile, outputFile, levels, services, startTime, endTime, keyword);
        long end = System.currentTimeMillis();
        System.out.println("Hoàn tất! Thời gian tìm kiếm: " + (end - start) + " ms");
    }
}
