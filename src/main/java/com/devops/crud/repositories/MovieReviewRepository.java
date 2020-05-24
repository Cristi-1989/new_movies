package com.devops.crud.repositories;

import com.devops.crud.entities.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {
    MovieReview findByMovieName(String movieName);
}
