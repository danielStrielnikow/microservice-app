package pl.danielstrielnikow.jobmicroservices.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.danielstrielnikow.jobmicroservices.job.external.Review;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getReviews(@RequestParam("companyId") Long id);
}
