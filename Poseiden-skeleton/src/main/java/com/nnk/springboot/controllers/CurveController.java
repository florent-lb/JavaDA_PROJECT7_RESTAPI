package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurveController {

    @Autowired
    private CurvePointRepository repository;
    /**
     * Load all Curve Point
     * @param model current Model
     * @return itself update
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curves",repository.findAll());
        return "curvePoint/list";
    }
    /**
     * Return add Curve Point list
     * @return itself update
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }
    /**
     * Use for validate a new Curve Point
     * @return redirect to Curve Point Home if valid
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if(!result.hasErrors())
        {
            repository.save(curvePoint);
            logger.info("Add Curve " +curvePoint.toString());
            model.addAttribute("bids",repository.findAll());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }
    /**
     * Use for navigate to the update form with the Curve Point asked
     * @return redirect to Curve Point update resource
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Curve Id:" + id));
        model.addAttribute("curve",curvePoint);
        return "curvePoint/update";
    }
    /**
     * Use for update a bid and validate it
     * @return redirect to Curve Point Home if valid
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if(result.hasErrors())
        {
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        repository.save(curvePoint);
        logger.info("Update Curve " +curvePoint.toString());
        return "redirect:/curvePoint/list";
    }
    /**
     * Use for delete a bid
     * @return redirect to Curve Point Home
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));
        repository.delete(curvePoint);
        logger.info("Delete CurvePoint " +curvePoint.toString());
        return "redirect:/curvePoint/list";
    }
}
