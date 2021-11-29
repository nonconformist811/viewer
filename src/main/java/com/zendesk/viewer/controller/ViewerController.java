package com.zendesk.viewer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zendesk.viewer.model.Ticket;
import com.zendesk.viewer.service.ViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class ViewerController {

    @Autowired
    private ViewerService viewerService;

    private List<Ticket> ticketList ;

    public static final String restServiceUrl = "https://zccandrew3370.zendesk.com/api/v2/tickets.json";



    @GetMapping("/ticketViewer")
    public String listAllTickets(Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size) throws JsonProcessingException {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(25);

        ticketList = viewerService.getTicketList(currentPage);
        int totalCount  = viewerService.getTotalTickets(currentPage);
        int totalPages = totalCount/25 + (totalCount%25==0?0 :1);

        Page<Ticket> ticketPage = viewerService.getPaginatedTicketList(PageRequest.of(0, pageSize), ticketList);

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("ticketPage", ticketPage);
        model.addAttribute("size",25);
        model.addAttribute("totalPages",totalPages);

        return "listTickets.html";
    }




    @GetMapping("/ticketViewer/tickets/{ticketId}")
    public String getIndividualTicket(Model model, @PathVariable Integer ticketId) {


        Ticket ticket = null;

        for(int i=0;i<ticketList.size();i++){
            if(ticketList.get(i).getId() == ticketId){
                ticket = ticketList.get(i);
                break;
            }
        }
        model.addAttribute("ticket", ticket);

        return "displayIndividualTicket.html";
    }


}
