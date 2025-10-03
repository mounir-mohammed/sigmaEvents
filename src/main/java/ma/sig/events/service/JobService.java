package ma.sig.events.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import ma.sig.events.service.dto.Job;
import ma.sig.events.service.dto.JobStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final Map<String, Job> jobs = new ConcurrentHashMap<>();

    @Async
    public void processJobAsync(Job job, Runnable task) {
        try {
            job.setStatus(JobStatus.RUNNING);
            task.run();
            job.setStatus(JobStatus.COMPLETED);
        } catch (Exception e) {
            job.setStatus(JobStatus.FAILED);
        }
    }

    public String submit(Runnable task) {
        String jobId = UUID.randomUUID().toString();
        Job job = new Job(jobId);
        jobs.put(jobId, job);

        processJobAsync(job, task); // <-- must be async

        return jobId; // immediately return
    }

    public Job getJob(String jobId) {
        return jobs.get(jobId);
    }
}
