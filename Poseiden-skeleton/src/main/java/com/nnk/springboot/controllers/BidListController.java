package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
public class BidListController {

    @Autowired
    private BidListRepository repository;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bids",repository.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if(!result.hasErrors())
        {
            repository.save(bid);
            logger.info("Add BidList" +bid.toString());
            model.addAttribute("bids",repository.findAll());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        BidList bidList = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id:" + id));
        model.addAttribute("bidList",bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if(result.hasErrors())
        {
            return "bidList/update";
        }
        bidList.setBidListId(id);
        repository.save(bidList);
        logger.info("Update BidList" +bidList.toString());
        model.addAttribute("bids",repository.findAll());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id:" + id));
        repository.delete(bidList);
        logger.info("Delete BidList" +bidList.toString());
        model.addAttribute("bidList",bidList);
        return "redirect:/bidList/list";
    }
}
