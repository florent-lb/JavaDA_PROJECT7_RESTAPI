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

    /**
     * Load all Bids
     * @param model current Model
     * @return itself update
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bids",repository.findAll());
        return "bidList/list";
    }

    /**
     * Return add Bids list
     * @return itself update
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {

        return "bidList/add";
    }
    /**
     * Use for validate a new BidList
     * @return redirect to Bid Home if valid
     */
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
    /**
     * Use for navigate to the update form with the Bid asked
     * @return redirect to Bid update resource
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        BidList bidList = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id:" + id));
        model.addAttribute("bidList",bidList);
        return "bidList/update";
    }
    /**
     * Use for update a bid and validate it
     * @return redirect to Bid Home if valid
     */
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
    /**
     * Use for delete a bid
     * @return redirect to Bid Home
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid BidList Id:" + id));
        repository.delete(bidList);
        logger.info("Delete BidList" +bidList.toString());
        return "redirect:/bidList/list";
    }
}
