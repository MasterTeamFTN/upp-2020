package rs.ac.uns.ftn.uppservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uppservice.service.ScheduledTasksService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTasksServiceImpl implements ScheduledTasksService {

    private final HistoryService historyService;
    private final RuntimeService runtimeService;

    // Execute this method once a day
    @Scheduled(fixedDelay = 60000 * 60 * 24)
    @Override
    public void shutdownCamundaProcesses() {
        List<HistoricProcessInstance> activeProcesses = historyService.createHistoricProcessInstanceQuery()
                .active()
                .orderByProcessInstanceStartTime()
                .asc()
                .list();

        for (var process : activeProcesses) {
            ZonedDateTime startTime = ZonedDateTime.ofInstant(process.getStartTime().toInstant(), ZoneId.systemDefault());
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime process30daysActive = startTime.plusDays(30);

            if (process30daysActive.isBefore(now)) {
                runtimeService.suspendProcessInstanceById(process.getId());
            }
        }
    }
}
