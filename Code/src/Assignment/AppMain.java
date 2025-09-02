package Assignment;

import Assignment.repository.LogRepository;
import Assignment.service.LogSearchService;

public class AppMain {
    public static void main(String[] args) throws  Exception {
        LogSearchService service = new LogSearchService(new LogRepository());
        service.searchLogs();
    }
}
