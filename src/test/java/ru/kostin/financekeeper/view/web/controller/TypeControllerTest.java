package ru.kostin.financekeeper.view.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.security.MockSecurityConfiguration;
import ru.kostin.financekeeper.security.SecurityConfiguration;
import ru.kostin.financekeeper.service.TypeService;
import ru.kostin.financekeeper.view.web.form.type.TypeAddForm;
import ru.kostin.financekeeper.view.web.form.type.TypeDeleteForm;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypeController.class)
@WithUserDetails(value = "test@t", userDetailsServiceBeanName = "mockUserDetailsService")
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
public class TypeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TypeService typeService;

    private final List<TypeDTO> types = Stream.of(new TypeDTO(1, "test")).collect(Collectors.toList());
    private final List<String> typesForPrint = types.stream().map(a -> a.getId() + ". " + a.getType()).collect(Collectors.toList());

    @Test
    void getTypeAdd() throws Exception {
        mockMvc.perform(get("/type/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("type-add"));
    }

    @Test
    void postTypeAdd() throws Exception {
        TypeAddForm form = new TypeAddForm();
        form.setType("test");

        mockMvc.perform(post("/type/add")
                        .flashAttr("form", form))
                .andExpect(redirectedUrl("/type/menu"));
        verify(typeService).save("test");
    }

    @Test
    void getTypeDel() throws Exception {
        when(typeService.getAll()).thenReturn(types);

        mockMvc.perform(get("/type/delete"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("options", typesForPrint))
                .andExpect(view().name("type-delete"));

    }

    @Test
    void postTypeDel() throws Exception {
        when(typeService.getAll()).thenReturn(types);
        TypeDeleteForm form = new TypeDeleteForm();
        form.setType(typesForPrint.get(0));

        mockMvc.perform(post("/type/delete")
                        .flashAttr("form", form))
                .andExpect(redirectedUrl("/type/menu"));
        verify(typeService).delete(1);
    }
}