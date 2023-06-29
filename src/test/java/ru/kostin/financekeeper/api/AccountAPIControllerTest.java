package ru.kostin.financekeeper.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kostin.financekeeper.api.controller.AccountAPIController;
import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.security.MockSecurityConfiguration;
import ru.kostin.financekeeper.security.SecurityConfiguration;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.utils.ModParam;
import ru.kostin.financekeeper.api.json.CompletionResponse;
import ru.kostin.financekeeper.api.json.account.AccountAddRequest;
import ru.kostin.financekeeper.api.json.account.AccountDeleteRequest;
import ru.kostin.financekeeper.api.json.account.AccountUpdateRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountAPIController.class)
@WithUserDetails(value = "test@t", userDetailsServiceBeanName = "mockUserDetailsService")
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
class AccountAPIControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @MockBean
    AccountService accountService;

    @Test
    void list() throws Exception {
        List<AccountDTO> accounts = Stream.of(
                new AccountDTO(1L, "test", new BigDecimal("100.00")),
                new AccountDTO(2L, "test2", new BigDecimal("200.00"))
        ).collect(Collectors.toList());

        when(accountService.getAll(1L)).thenReturn(accounts);

        mockMvc.perform(get("/api/account/list"))
                .andExpect(status().isOk());
        verify(accountService).getAll(1L);
    }

    @Test
    void add() throws Exception {
        AccountAddRequest request = new AccountAddRequest();
        request.setName("test");
        request.setBalance("100.00");

        doNothing().when(accountService).save("test", "100.00", 1L);

        mockMvc.perform(post("/api/account/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(new CompletionResponse(true))));
    }

    @Test
    void delete() throws Exception {
        doNothing().when(accountService).delete(1L);
        when(accountService.getAll(1L))
                .thenReturn(
                        Stream.of(new AccountDTO().setId(1L))
                        .collect(Collectors.toList()));
        AccountDeleteRequest request = new AccountDeleteRequest();
        request.setId(1);

        mockMvc.perform(post("/api/account/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(new CompletionResponse(true))));
    }

    @Test
    void update() throws Exception {
        doNothing().when(accountService).update(1L, ModParam.NAME, "newName", 1L);
        when(accountService.getAll(1L))
                .thenReturn(
                        Stream.of(new AccountDTO().setId(1L))
                                .collect(Collectors.toList()));
        AccountUpdateRequest request = new AccountUpdateRequest();
        request.setId(1);
        request.setParam(ModParam.NAME);
        request.setVal("newName");

        mockMvc.perform(post("/api/account/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(new CompletionResponse(true))));
        verify(accountService).update(1L, ModParam.NAME, "newName", 1L);
    }
}