package Assignment;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.regex.*;

public class LogSearcher {
    private static final Pattern LOG_PATTERN = Pattern.compile("\\[(.*?)\\] \\[(.*?)\\] \\[(.*?)\\]- (.*)");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Nhập đường dẫn file input
        System.out.print("Nhập đường dẫn file log input: ");
        String inputFilePath = scanner.nextLine().trim();

        // Nhập đường dẫn file output
        System.out.print("Nhập đường dẫn file output (.txt): ");
        String outputFilePath = scanner.nextLine().trim();

        // Nhập levels (comma-separated, ví dụ: INFO,WARN; nếu rỗng thì all)
        System.out.print("Nhập log levels (comma-separated, ví dụ: INFO,WARN; để trống nếu all): ");
        String levelsInput = scanner.nextLine().trim();
        Set<String> targetLevels = new HashSet<>();
        if (!levelsInput.isEmpty()) {
            targetLevels.addAll(Arrays.asList(levelsInput.toUpperCase().split(",")));
        }

        // Nhập start time (yyyy-MM-dd HH:mm:ss; nếu rỗng thì không lọc)
        System.out.print("Nhập start time (yyyy-MM-dd HH:mm:ss; để trống nếu không lọc): ");
        String startTimeStr = scanner.nextLine().trim();
        LocalDateTime startTime = parseTimestamp(startTimeStr);

        // Nhập end time
        System.out.print("Nhập end time (yyyy-MM-dd HH:mm:ss; để trống nếu không lọc): ");
        String endTimeStr = scanner.nextLine().trim();
        LocalDateTime endTime = parseTimestamp(endTimeStr);

        // Nhập services
        System.out.println("Nhập log services (comma-separated, ví dụ: UserService, OrderService; để trống nếu all): ");
        String servicesInput = scanner.nextLine().trim();
        List<String> targetServices = new ArrayList<>();
        if (!servicesInput.isEmpty()) {
            targetServices.addAll(Arrays.asList(servicesInput.toUpperCase().split(",")));
        }

        // Nhập keyword cho message (nếu rỗng thì không lọc)
        System.out.print("Nhập keyword tìm kiếm trong message (không phân biệt hoa/thường; để trống nếu không lọc): ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        // --- BẮT ĐẦU TÍNH THỜI GIAN ---
        long startMillis = System.currentTimeMillis();

        // Xử lý tìm kiếm
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = LOG_PATTERN.matcher(line);
                if (matcher.matches()) {
                    String timestampStr = matcher.group(1);
                    String level = matcher.group(2).toUpperCase();
                    String service = matcher.group(3).toUpperCase();
                    String message = matcher.group(4).toLowerCase();

                    LocalDateTime logTime = parseTimestamp(timestampStr);
                    if (logTime == null) continue; // Skip nếu parse fail

                    // Lọc level
                    if (!targetLevels.isEmpty() && !targetLevels.contains(level)) continue;

                    // Lọc thời gian
                    if (startTime != null && logTime.isBefore(startTime)) continue;
                    if (endTime != null && logTime.isAfter(endTime)) continue;

                    // Lọc services
                    if(!targetServices.isEmpty() && !targetServices.contains(service)) continue;

                    // Lọc message
                    if (!keyword.isEmpty() && !message.contains(keyword)) continue;

                    // Viết ra output
                    writer.write(line);
                    writer.newLine();
                    count++;
                }
            }
            System.out.println("Tìm thấy " + count + " dòng khớp. Kết quả đã lưu vào " + outputFilePath);

            // --- KẾT THÚC TÍNH THỜI GIAN ---
            long endMillis = System.currentTimeMillis();
            System.out.println("Hoàn tất! Thời gian tìm kiếm: " + (endMillis - startMillis) + " ms");
        } catch (IOException e) {
            System.err.println("Lỗi IO: " + e.getMessage());
        }
    }

    private static LocalDateTime parseTimestamp(String timestampStr) {
        if (timestampStr == null || timestampStr.isEmpty()) return null;
        try {
            return LocalDateTime.parse(timestampStr, TIMESTAMP_FORMAT);
        } catch (DateTimeParseException e) {
            System.err.println("Lỗi parse timestamp: " + timestampStr);
            return null;
        }
    }
}
