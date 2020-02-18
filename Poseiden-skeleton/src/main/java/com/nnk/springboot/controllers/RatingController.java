package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class RatingController {

    @Autowired
    RatingRepository repository;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings",repository.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {

        model.asMap().forEach((s, o) -> logger.info("KEY : " +s + "  | Val : " + o.toString()));
        if(!result.hasErrors())
        {
            repository.save(rating);
            logger.info("Add Curve " +rating.toString());
            model.addAttribute("ratings",repository.findAll());
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id));
        model.addAttribute("rating",rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if(result.hasErrors())
        {
            return "rating/update";
        }
        rating.setId(id);
        repository.save(rating);
        logger.info("Update Rating " +rating.toString());
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Rating rating = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id));
        repository.delete(rating);
        logger.info("Delete CurvePoint " +rating.toString());
        return "redirect:/rating/list";
    }
}
