package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
	@Autowired
	BidListController bidListController;
	/**
	 * Home of the application
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String home(Model model)
	{
		return bidListController.home(model);
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
