package com.zendesk.viewer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zendesk.viewer.model.Ticket;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
@PropertySource(value="classpath:application.properties")
public class ViewerService {


    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${defaultPageSize}")
    private String defaultPageSize;



    public Page<Ticket> findPaginated(Pageable pageable, List<Ticket> tickets) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Ticket> ticketList;

        if (tickets.size() < startItem) {
            ticketList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, tickets.size());
            ticketList = tickets.subList(startItem, toIndex);
        }

        Page<Ticket> ticketPage
                = new PageImpl<Ticket>(ticketList, PageRequest.of(currentPage, pageSize), tickets.size());

        return ticketPage;
    }



    private  HttpHeaders getHeaders ()
    {
        String adminuserCredentials = username + ":" + password;

        String encodedCredentials =
                new String(Base64.encodeBase64(adminuserCredentials.getBytes()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + encodedCredentials);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    public List<Ticket> getTicketList(int currentPage){


        String url = baseUrl + "?per_page="+defaultPageSize+"&page="+currentPage;
        System.out.println(url);

        List<Ticket> list = new ArrayList<>();


        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = getHeaders();

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> responseEntity;

             responseEntity = restTemplate.exchange(url,
                    HttpMethod.GET, httpEntity, String.class);


            JSONObject jsonObject = new JSONObject(responseEntity.getBody());
            JSONArray ticketList = (JSONArray) jsonObject.get("tickets");

            System.out.println(ticketList.length());


            list = getTicketListFromJsonArray(ticketList);


        return list;
    }

    public Integer getTotalTickets(int currentPage){

        String url = baseUrl + "?per_page="+defaultPageSize+"&page="+currentPage;
        System.out.println(url);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = getHeaders();

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, httpEntity, String.class);


        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        Integer count = Integer.parseInt(jsonObject.get("count").toString());

       return count;
    }

    private List<Ticket> getTicketListFromJsonArray(JSONArray ticketJsonArray){

        List<Ticket> ticketList= new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        for(int i=0; i<ticketJsonArray.length(); i++){


            try {
                ticketList.add( objectMapper.readValue(ticketJsonArray.getJSONObject(i).toString(), Ticket.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ticketList;


    }


}
