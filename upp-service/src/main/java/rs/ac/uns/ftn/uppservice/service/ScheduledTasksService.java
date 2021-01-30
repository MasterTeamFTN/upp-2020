package rs.ac.uns.ftn.uppservice.service;

public interface ScheduledTasksService {

    /**
     * Scheduled task that suspends processes that have been active for too long.
     */
    void shutdownCamundaProcesses();
}
