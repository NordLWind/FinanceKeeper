package ru.kostin.financekeeper.view.console;

import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.dto.TransactionDTO;
import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.dto.UserDTO;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.service.TransactionService;
import ru.kostin.financekeeper.service.TypeService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static ru.kostin.financekeeper.view.console.RequestMessage.requestMessage;
import static ru.kostin.financekeeper.view.console.StringConstants.*;
import static ru.kostin.financekeeper.view.console.TypeMenu.printList;

public class TransactionMenu {
    public static void printMenu(TransactionService transactionService, TypeService typeService, AccountService accountService, UserDTO user) {
        System.out.println(TRANSACTION_GREETING);
        while (true) {
            System.out.println(TRANSACTION_OPTIONS);
            switch (requestMessage(SELECT)) {
                case "1": {
                    System.out.println(TRANSACTIONS_SELECT_TYPE);
                    System.out.println(LIST_SELECT);
                    List<TypeDTO> types = typeService.getAll();
                    for (int i = 1; i <= types.size(); i++) {
                        System.out.println(i + DOT + TYPE_REQUEST + types.get(i - 1));
                    }
                    try {
                        int num = Integer.parseInt(requestMessage(SELECT));
                        long typeId;
                        if (num == 0) {
                            typeId = 0;
                        } else {
                            typeId = types.get(num - 1).getId();
                        }
                        String before = requestMessage(BEFORE + DATE_REQUEST);
                        String after = requestMessage(AFTER + DATE_REQUEST);
                        List<TransactionDTO> result = transactionService.getReport(after, before, typeId, user.getId());
                        for (int i = 1; i <= result.size(); i++) {
                            System.out.println(i + DOT + result.get(i - 1).toString());
                        }
                    } catch (ParseException e) {
                        System.out.println(WRONG_DATE);
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.out.println(WRONG_OPTION);
                    }
                    break;
                }
                case "2": {
                    AccountDTO source;
                    AccountDTO target;
                    System.out.println(REQUEST + TRANSACTION_SOURCE);
                    List<AccountDTO> accounts = accountService.getAll(user.getId());
                    for (int i = 0; i <= accounts.size(); i++) {
                        if (i == 0) {
                            System.out.println((i + 1) + DOT + TRANSACTIONS_OUTSIDE);
                        } else {
                            AccountDTO account = accounts.get(i - 1);
                            System.out.println((i + 1) + DOT + NAME_REQUEST + account.getName() + COMMA + BALANCE_REQUEST + account.getBalance());
                        }
                    }
                    try {
                        int num = Integer.parseInt(requestMessage(SELECT));
                        if (num == accounts.size() + 2) {
                            return;
                        }
                        source = accountService.get(accounts.get(num - 2).getId());
                        num = Integer.parseInt(requestMessage(REQUEST + TRANSACTION_TARGET));
                        if (num == accounts.size() + 2) {
                            return;
                        }
                        target = accountService.get(accounts.get(num - 2).getId());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(WRONG_OPTION);
                        break;
                    } catch (ItemNotExistException e) {
                        System.out.println(NOT_EXIST);
                        break;
                    }
                    String amount = requestMessage(REQUEST + BALANCE_REQUEST);
                    String date = requestMessage(REQUEST + DATE_REQUEST);

                    String description = requestMessage(REQUEST + TRANSACTION_DESCR);
                    List<Long> selected = new ArrayList<>();
                    List<TypeDTO> types = printList(typeService);

                    try {
                        int num = Integer.parseInt(requestMessage(SELECT));
                        if (num != types.size() + 1) {
                            selected.add(types.get(num - 1).getId());
                        }
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        System.out.println(WRONG_OPTION);
                    } catch (ItemAlreadyExistsException e) {
                        System.out.println(ALREADY_EXISTS);
                    }

                    try {
                        transactionService.save(source.getId(), target.getId(), amount, description, date, user.getId(), selected);
                    } catch (ParseException e) {
                        System.out.println(WRONG_DATE);
                    }
                }
                case "3": {
                    return;
                }
            }
        }
    }
}
