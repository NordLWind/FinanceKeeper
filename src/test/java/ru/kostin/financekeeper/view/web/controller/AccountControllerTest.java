package ru.kostin.financekeeper.view.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.security.MockSecurityConfiguration;
import ru.kostin.financekeeper.security.SecurityConfiguration;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.view.web.form.account.AccountAddForm;
import ru.kostin.financekeeper.view.web.form.account.AccountDeleteForm;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@WithUserDetails(value = "test@t", userDetailsServiceBeanName = "mockUserDetailsService")
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    private final List<AccountDTO> accounts = Stream.of(new AccountDTO(1L, "test", new BigDecimal("100.00"))).collect(Collectors.toList());
    private final List<String> accountsPrinted = accounts.stream().map(a -> a.getId() + ". " + a.getName() + ": " + a.getBalance()).collect(Collectors.toList());

    @Test
    public void getAccountAdd() throws Exception {
        mockMvc.perform(get("/account/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-add"))
                .andExpect(model().attributeExists("form"));
    }

    @Test
    public void addAccount() throws Exception {
        AccountAddForm addAccountForm = new AccountAddForm();
        addAccountForm.setName("test");
        addAccountForm.setBalance("100.00");

        mockMvc.perform(post("/account/add")
                        .flashAttr("form", addAccountForm))
                .andExpect(redirectedUrl("/account/menu"));

        verify(accountService, times(1)).save("test", "100.00", 1L);
    }

    @Test
    public void list() throws Exception {
        when(accountService.getAll(1L)).thenReturn(accounts);

        mockMvc.perform(get("/account/list"))
                .andExpect(view().name("list-template"))
                .andExpect(model().attributeExists("options"))
                .andExpect(model().attribute("options", accountsPrinted));
    }

    @Test
    public void getDeleteAccountForm() throws Exception {
        when(accountService.getAll(1L)).thenReturn(accounts);
        mockMvc.perform(get("/account/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-delete"))
                .andExpect(model().attribute("accounts", accountsPrinted));
    }

    @Test
    public void deleteAccount() throws Exception {
        when(accountService.getAll(1L)).thenReturn(accounts);
        AccountDeleteForm form = new AccountDeleteForm();
        form.setAccount(accountsPrinted.get(0));

        mockMvc.perform(post("/account/delete")
                        .flashAttr("form", form))
                .andExpect(redirectedUrl("/account/menu"));
        verify(accountService).delete(1L);
    }
}