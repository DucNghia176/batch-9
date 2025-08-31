package Assignment;

import Assignment.repository.LogRepository;
import Assignment.service.LogSearchService;
import Assignment.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AppMain {
    public static void main(String[] args) throws  Exception {
        Scanner scanner = new Scanner(System.in);

        // Input / Output
        System.out.print("Nhập đường dẫn file log input: ");
        String inputFilePath = scanner.nextLine().trim();
        System.out.print("Nhập đường dẫn file output (.txt): ");
        String outputFilePath = scanner.nextLine().trim();

        // Lọc theo level
        System.out.print("Nhập levels (comma-separated, ví dụ: INFO,WARN; để trống nếu all): ");
        String levelsInput = scanner.nextLine().trim();
        Set<String> targetLevels = new HashSet<>();
        if (!levelsInput.isEmpty()) {
            targetLevels.addAll(Arrays.asList(levelsInput.toUpperCase().split(",")));
        }

        // Lọc theo service
        System.out.print("Nhập services (comma-separated, ví dụ: UserService,PaymentService; để trống nếu all): ");
        String servicesInput = scanner.nextLine().trim();
        Set<String> targetServices = new HashSet<>();
        if (!servicesInput.isEmpty()) {
            targetServices.addAll(Arrays.asList(servicesInput.split(",")));
        }

        // Thời gian
        System.out.print("Nhập start time (yyyy-MM-dd HH:mm:ss; để trống nếu không lọc): ");
        LocalDateTime startTime = DateTimeUtil.parseTimestamp(scanner.nextLine().trim());
        System.out.print("Nhập end time (yyyy-MM-dd HH:mm:ss; để trống nếu không lọc): ");
        LocalDateTime endTime = DateTimeUtil.parseTimestamp(scanner.nextLine().trim());

        // Keyword
        System.out.print("Nhập keyword trong message (để trống nếu không lọc): ");
        String keyword = scanner.nextLine().trim().toLowerCase();
        LogSearchService service = new LogSearchService(new LogRepository());
        service.searchLogs(inputFilePath, outputFilePath, targetLevels, targetServices, startTime, endTime, keyword);
    }
}
