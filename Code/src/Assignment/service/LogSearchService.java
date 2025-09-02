package Assignment.service;

import Assignment.repository.LogRepository;
import Assignment.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class LogSearchService {
    private final LogRepository logRepository;

    public LogSearchService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    public void searchLogs() throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Input / Output
        System.out.print("Nhập đường dẫn file log input: ");
        String inputFile = scanner.nextLine().trim();
        System.out.print("Nhập đường dẫn file output (.txt): ");
        String outputFile = scanner.nextLine().trim();

        // Lọc theo level
        System.out.print("Nhập levels (comma-separated, ví dụ: INFO,WARN; để trống nếu all): ");
        String levelsInput = scanner.nextLine().trim();
        Set<String> levels = parseSet(levelsInput);

        // Lọc theo service
        System.out.print("Nhập services (comma-separated, ví dụ: UserService,PaymentService; để trống nếu all): ");
        String servicesInput = scanner.nextLine().trim();
        Set<String> services = parseSet(servicesInput);

        // Thời gian
        System.out.print("Nhập start time (yyyy-MM-dd HH:mm:ss; để trống nếu không lọc): ");
        LocalDateTime startTime = DateTimeUtil.parseTimestamp(scanner.nextLine().trim());
        System.out.print("Nhập end time (yyyy-MM-dd HH:mm:ss; để trống nếu không lọc): ");
        LocalDateTime endTime = DateTimeUtil.parseTimestamp(scanner.nextLine().trim());

        // Keyword
        System.out.print("Nhập keyword trong message (để trống nếu không lọc): ");
        String keyword = scanner.nextLine().trim().toLowerCase();
        if (keyword.isEmpty()) keyword = null;

        System.out.print("\nĐang lọc xin đợi .....\n");

        long start = System.currentTimeMillis();
        logRepository.processLogs(inputFile, outputFile, levels, services, startTime, endTime, keyword);
        long end = System.currentTimeMillis();
        System.out.println("Hoàn tất! Thời gian tìm kiếm: " + (end - start) + " ms");
    }

    private Set<String> parseSet(String input) {
        Set<String> result = new HashSet<>();
        if (!input.isEmpty()) {
            for (String s : input.split(",")) {
                result.add(s.trim());
            }
        }
        return result.isEmpty() ? null : result;
    }
}
