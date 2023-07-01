package ru.kostin.financekeeper.web.form.transaction;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TransactionAddForm {

    @NotEmpty
    @NotNull
    private String source;

    @NotEmpty
    @NotNull
    private String target;

    @NotEmpty
    @NotNull
    private String description;

    @NotEmpty
    @NotNull
    private String type;

    @NotEmpty
    @NotNull
    private String amount;
}
