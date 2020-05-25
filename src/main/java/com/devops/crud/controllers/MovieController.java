package com.devops.crud.controllers;

import com.devops.crud.entities.MovieReview;
import com.devops.crud.repositories.MovieReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;


@Controller
public class MovieController {

    private final MovieReviewRepository movieReviewRepository;


    @Autowired
    public MovieController(MovieReviewRepository movieReviewRepository) {
        this.movieReviewRepository = movieReviewRepository;
    }

    /**
     * return root page with reviews list.
     *
     * @param model model
     * @return page view
     */
    @GetMapping("/")
    public String getReviews(Model model) {
        model.addAttribute("reviews", movieReviewRepository.findAll());
        return "index.html";
    }

    /**
     * return page with new review form.
     *
     * @param model model
     * @return page view
     */
    @GetMapping("/addreview")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movieReview", new MovieReview("", ""));
        return "add-review";
    }

    /**
     * Add the review with the form data.
     *
     * @param movieReview movieReview
     * @param bindingResult bindingResult
     * @param attributes attributes
     * @return redirect to root page with reviews table.
     */
    @PostMapping("/addreview")
    public Object addReview(@Valid MovieReview movieReview, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("add-review", "movieReview", movieReview);
        }

        movieReviewRepository.save(movieReview);
        attributes.addAttribute("reviews", movieReviewRepository.findAll());
        return "redirect:/";
    }

    /**
     * return page to edit a specific review.
     *
     * @param id review id
     * @param model model
     * @return page view.
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        MovieReview movieReview = movieReviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid movieReview Id:" + id));
        model.addAttribute("movieReview", movieReview);
        return "update-review";
    }

    /**
     * Update existing review with form data.
     *
     * @param movieReview movieReview
     * @param bindingResult bindingResult
     * @param attributes attributes
     * @return redirect to root page with reviews table.
     */
    @PostMapping("/update")
    public Object updateReview(@Valid MovieReview movieReview, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("update-review", "movieReview", movieReview);
        }

        movieReviewRepository.save(movieReview);
        attributes.addAttribute("reviews", movieReviewRepository.findAll());
        return "redirect:/";
    }

    /**
     * Remove movie review entry by id.
     *
     * @param id id
     * @param attributes attributes
     * @return redirect to root page with reviews table.
     */
    @GetMapping("/delete/{id}")
    public String deleteMovieReview(@PathVariable("id") long id, RedirectAttributes attributes)
    {
        MovieReview movieReview = movieReviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid movie Review Id:" + id));
        movieReviewRepository.delete(movieReview);
        attributes.addAttribute("reviews", movieReviewRepository.findAll());
        return "redirect:/";
    }
}
