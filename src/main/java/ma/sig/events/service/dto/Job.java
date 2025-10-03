package ma.sig.events.service.dto;

public class Job {

    private final String jobId;
    private volatile JobStatus status;
    private String result; // optional, store any result

    public Job(String jobId) {
        this.jobId = jobId;
        this.status = JobStatus.PENDING;
    }

    public String getJobId() {
        return jobId;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
