package pl.danielstrielnikow.jobmicroservices.job;

import pl.danielstrielnikow.jobmicroservices.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);

    JobDTO getJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJob(Long id, Job updatedJob);

}
