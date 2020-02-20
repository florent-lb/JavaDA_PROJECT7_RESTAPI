package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeController {

    @Autowired
    private TradeRepository repository;
    /**
     * Load all Trade
     * @param model current Model
     * @return itself update
     */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades",repository.findAll());
        return "trade/list";
    }
    /**
     * Return add Trade list
     * @return itself update
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }
    /**
     * Use for validate a new TradeList
     * @return redirect to Trade Home if valid
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if(!result.hasErrors())
        {
            repository.save(trade);
            logger.info("Add Trade " +trade.toString());
            model.addAttribute("trades",repository.findAll());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }
    /**
     * Use for navigate to the update form with the Trade asked
     * @return redirect to Trade update resource
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id:" + id));
        model.addAttribute("trade",trade);
        return "trade/update";
    }
    /**
     * Use for update a bid and validate it
     * @return redirect to Trade Home if valid
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if(result.hasErrors())
        {
            return "trade/update";
        }
        trade.setTradeId(id);
        repository.save(trade);
        logger.info("Update Trade " +trade.toString());
        return "redirect:/trade/list";
    }
    /**
     * Use for delete a bid
     * @return redirect to Trade Home
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade trade = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Trade Id:" + id));
        repository.delete(trade);
        logger.info("Delete Trade " +trade.toString());
        return "redirect:/trade/list";
    }
}
