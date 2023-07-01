package ru.kostin.financekeeper.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
import ru.kostin.financekeeper.web.form.transaction.ReportForm;
import ru.kostin.financekeeper.web.form.transaction.TransactionAddForm;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@WithUserDetails(value = "test@t", userDetailsServiceBeanName = "mockUserDetailsService")
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    @MockBean
    AccountService accountService;

    @MockBean
    TypeService typeService;

    private final List<AccountDTO> accounts = Stream.of(
                    new AccountDTO(1L, "test", new BigDecimal("100.00")),
                    new AccountDTO(2L, "test2", new BigDecimal("1000.00")))
            .collect(Collectors.toList());
    private final List<String> accountsForPrint = accounts.stream().map(a -> a.getId() + ". " + a.getName() + ": " + a.getBalance()).collect(Collectors.toList());
    private final List<TypeDTO> types = Stream.of(
                    new TypeDTO(1, "test"),
                    new TypeDTO(2, "test2"))
            .collect(Collectors.toList());
    private final List<String> typesForPrint = types.stream().map(a -> a.getId() + ". " + a.getType()).collect(Collectors.toList());
    private final List<TransactionDTO> transactions = Stream.of(
                    new TransactionDTO(
                            1L,
                            "test2",
                            "test1",
                            "20.00",
                            "testTransaction2",
                            "20-12-2020"))
            .collect(Collectors.toList());

    @Test
    void getReport() throws Exception {
        mockMvc.perform(get("/report"))
                .andExpect(status().isOk())
                .andExpect(view().name("report"))
                .andExpect(model().attribute("form", new ReportForm()));
    }

    @Test
    void report() throws Exception {
        when(typeService.getAll()).thenReturn(types);
        when(transactionService.getReport(anyString(), anyString(), anyLong(), anyLong()))
                .thenReturn(transactions);

        ReportForm form = new ReportForm();
        form.setAfter("20-12-2020T18:00");
        form.setBefore("20-12-2020T22:00");

        mockMvc.perform(post("/transaction/report")
                        .flashAttr("form", form))
                .andExpect(view().name("list-template"))
                .andExpect(model().attribute("options", transactions));
        verify(transactionService).getReport("20-12-2020 18:00", "20-12-2020 22:00", 0, 1L);
    }

    @Test
    void getTransactionAdd() throws Exception {
        when(accountService.getAll(1L)).thenReturn(accounts);
        when(typeService.getAll()).thenReturn(types);

        mockMvc.perform(get("/transaction/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("accounts", accountsForPrint))
                .andExpect(model().attribute("types", typesForPrint))
                .andExpect(view().name("transaction-add"));
    }

    @Test
    void postTransactionAdd() throws Exception {
        when(accountService.getAll(1L)).thenReturn(accounts);
        when(typeService.getAll()).thenReturn(types);

        TransactionAddForm form = new TransactionAddForm();
        form.setSource(accountsForPrint.get(0));
        form.setTarget(accountsForPrint.get(1));
        form.setAmount("20.00");
        form.setDescription("test");
        form.setType(typesForPrint.get(0));

        mockMvc.perform(post("/transaction/add")
                        .flashAttr("form", form))
                .andExpect(redirectedUrl("/"));
        verify(transactionService).save(
                1L,
                2L,
                "20.00",
                "test",
                "0",
                1L,
                Collections.singletonList(1L)
        );
    }
}