package com.litecommerce.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;

import com.litecommerce.model.AccountModel;
import com.litecommerce.model.OrderModel;
import com.litecommerce.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	OrderService orderService;

	@GetMapping(value = { "admin-Order", "admin-Order/{action}" })
	public String showOrder(Model model, @RequestParam(required = false) Map<String, String> params,
			@PathVariable(name = "action", required = false) String action,
			@RequestParam(name = "page", defaultValue = "1") Integer currentPage,
			@RequestParam(name = "size", defaultValue = "3") Integer pageSize,
			@RequestParam(name = "sortField", defaultValue = "") String sortField,
			@RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
			HttpSession session) {
		
		//check login
		if(session.getAttribute("account") !=null){
			AccountModel acc = (AccountModel)session.getAttribute("account");
			model.addAttribute("acc", acc);
		}
		else
			return "redirect:/admin";
		
		String status = null;

		if (params != null) {
			status = params.get("status");
		}
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("sortField", sortField);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageSize", pageSize);
		String baseUrl = "/admin-Order";
		model.addAttribute("baseUrl", baseUrl);
		// action accept and cancel order
		if (action != null) {
			if (action.equals("accept")) {
				Integer id = Integer.parseInt(params.get("id"));
				orderService.updateOrder("comfirmed", id);
			} else if (action.equals("cancel")) {
				Integer id = Integer.parseInt(params.get("id"));
				orderService.updateOrder("err", id);
			}
		}
		// list order pagination and sort by status
		Pageable pageable = sortField.length() == 0 ? PageRequest.of(currentPage - 1, pageSize)
				: PageRequest.of(currentPage - 1, pageSize,
						sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		Page<OrderModel> lstOrderPagination = null;
		if (status != null) {
			lstOrderPagination = orderService.getOrdersByStatus(pageable, status);
			model.addAttribute("lstOrder", lstOrderPagination);
			model.addAttribute("status", status);
		}

		// list number page
		int totalPages = lstOrderPagination.getTotalPages();
		model.addAttribute("totalPages", totalPages);
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());		
			if(pageNumbers.size()>5) {
				int begin = currentPage;
				int end =begin + 4;
				if(end >= totalPages) {
					begin = totalPages - 4;
					end = totalPages;
				}
					
				List<Integer> pageNumbersNew = pageNumbers.subList(begin-1, end);
				model.addAttribute("pageNumbers", pageNumbersNew);
			}
			else
				model.addAttribute("pageNumbers", pageNumbers);
			
		}
		return "admin/order";
	}
}
