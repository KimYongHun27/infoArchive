package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.SpecialLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialLogService {

    private final SpecialLogRepository specialLogRepository;

    public SpecialLog saveLog(
            String actionName,
            String status,
            String message,
            String executedBy
    ) {
        SpecialLog log = SpecialLog.builder()
                .actionName(actionName)
                .status(status)
                .message(message)
                .executedBy(executedBy)
                .build();

        return specialLogRepository.save(log);
    }

    public List<SpecialLog> getRecentLogs() {
        return specialLogRepository.findTop20ByOrderByExecutedAtDesc();
    }

    public long getTotalLogCount() {
        return specialLogRepository.count();
    }

    public long getSuccessLogCount() {
        return specialLogRepository.countByStatus("SUCCESS");
    }

    public long getFailLogCount() {
        return specialLogRepository.countByStatus("FAIL");
    }

    public void deleteLog(Long id) {
        specialLogRepository.deleteById(id);
    }

    public void deleteAllLogs() {
        specialLogRepository.deleteAll();
    }

    public List<SpecialLog> getRecent5Logs() {
        return specialLogRepository.findTop5ByOrderByExecutedAtDesc();
    }

    public int getSuccessRate() {
        long total = getTotalLogCount();

        if (total == 0) {
            return 0;
        }

        long success = getSuccessLogCount();

        return (int) Math.round((success * 100.0) / total);
    }
}