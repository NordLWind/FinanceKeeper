package ru.kostin.financekeeper.view.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.dto.TransactionDTO;
import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.security.MockSecurityConfiguration;
import ru.kostin.financekeeper.security.SecurityConfiguration;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.service.TransactionService;
import ru.kostin.financekeeper.service.TypeService;
import ru.kostin.financekeeper.view.api.json.CompletionResponse;
import ru.kostin.financekeeper.view.api.json.transaction.TransactionAddRequest;
import ru.kostin.financekeeper.view.api.json.transaction.TransactionReportRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionAPIController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@WithUserDetails(value = "test@t", userDetailsServiceBeanName = "mockUserDetailsService")
class TransactionAPIControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @MockBean
    TransactionService transactionService;

    @MockBean
    AccountService accountService;

    @MockBean
    TypeService typeService;


    @Test
    void add() throws Exception{
        when(accountService.getAll(1L)).thenReturn(Stream.of(
                        new AccountDTO(1L, "test", new BigDecimal("100.00")),
                        new AccountDTO((2L), "test2", new BigDecimal("200.00")))
                .collect(Collectors.toList()));

        when(typeService.getAll()).thenReturn(Stream.of(
                        new TypeDTO(1L, "testType"))
                .collect(Collectors.toList()));

        doNothing().when(transactionService).add(
                1L,
                2L,
                "200.00",
                "descr",
                "0",
                1L,
                Collections.singletonList(1L));

        TransactionAddRequest request = new TransactionAddRequest();
        request.setIdSource(1);
        request.setIdTarget(2);
        request.setType(1);
        request.setAmount("200.00");
        request.setDate("0");
        request.setDescription("descr");


        mockMvc.perform(post("/api/transaction/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(new CompletionResponse(true))));
    }

    @Test
    void getReport() throws Exception{
        TransactionReportRequest request = new TransactionReportRequest();
        request.setDAfter("0");
        request.setDBefore(new Date().toString());
        request.setType(1);

        List<TransactionDTO> transactions = Stream.of(
                new TransactionDTO(
                        1L,
                        "test",
                        "testT",
                        "200.00",
                        "description"
                ),
                new TransactionDTO(
                        2L,
                        "test2",
                        "test2T",
                        "100.00",
                        "description2"
                )
        ).collect(Collectors.toList());

        when(typeService.getAll()).thenReturn(Stream.of(
                        new TypeDTO(1L, "testType"))
                .collect(Collectors.toList()));

        when(transactionService.getReport(
                "0",
                request.getDBefore(),
                1L,
                1L
        )).thenReturn(transactions);

        mockMvc.perform(post("/api/transaction/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(transactionService).getReport("0", request.getDBefore(), 1L, 1L);
    }
}