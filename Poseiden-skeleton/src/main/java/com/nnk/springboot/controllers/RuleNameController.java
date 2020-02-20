package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameController {

    @Autowired
    private RuleNameRepository repository;
    /**
     * Load all Rule
     * @param model current Model
     * @return itself update
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("rules", repository.findAll());
        return "ruleName/list";
    }
    /**
     * Return add Rule list
     * @return itself update
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }
    /**
     * Use for validate a new RuleList
     * @return redirect to Rule Home if valid
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            repository.save(ruleName);
            logger.info("Add Curve " + ruleName.toString());
            model.addAttribute("rules", repository.findAll());
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }
    /**
     * Use for navigate to the update form with the Rule asked
     * @return redirect to Rule update resource
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id:" + id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }
    /**
     * Use for update a bid and validate it
     * @return redirect to Rule Home if valid
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleName.setId(id);
        repository.save(ruleName);
        logger.info("Update Rule Name " + ruleName.toString());
        return "redirect:/ruleName/list";
    }
    /**
     * Use for delete a bid
     * @return redirect to Rule Home
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id:" + id));
        repository.delete(ruleName);
        logger.info("Delete RuleName " + ruleName.toString());
        return "redirect:/ruleName/list";
    }
}
