package Assignment.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parseTimestamp(String ts) {
        if (ts == null || ts.isEmpty()) return null;
        try {
            return LocalDateTime.parse(ts, TIMESTAMP_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
