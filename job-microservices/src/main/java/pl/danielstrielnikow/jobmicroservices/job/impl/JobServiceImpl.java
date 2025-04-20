package pl.danielstrielnikow.jobmicroservices.job.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.danielstrielnikow.jobmicroservices.clients.CompanyClient;
import pl.danielstrielnikow.jobmicroservices.clients.ReviewClient;
import pl.danielstrielnikow.jobmicroservices.job.Job;
import pl.danielstrielnikow.jobmicroservices.job.JobRepository;
import pl.danielstrielnikow.jobmicroservices.job.JobService;
import pl.danielstrielnikow.jobmicroservices.job.dto.JobDTO;
import pl.danielstrielnikow.jobmicroservices.job.external.Company;
import pl.danielstrielnikow.jobmicroservices.job.external.Review;
import pl.danielstrielnikow.jobmicroservices.job.mapper.JobMapper;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final RestTemplate restTemplate;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    @Value("${URL.company}")
    private String companyURI;
    @Value("${URL.review}")
    private String reviewURI;

    public JobServiceImpl(JobRepository jobRepository, RestTemplate restTemplate, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.restTemplate = restTemplate;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }

        return false;
    }

    private JobDTO convertToDto(Job job) {
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapToJobWithCompanyDto(job, company, reviews);
    }
}
