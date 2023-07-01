package ru.kostin.financekeeper.console;

import ru.kostin.financekeeper.dto.AccountDTO;
import ru.kostin.financekeeper.dto.UserDTO;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.service.TransactionService;
import ru.kostin.financekeeper.service.TypeService;
import ru.kostin.financekeeper.utils.ModParam;

import java.util.List;

import static ru.kostin.financekeeper.console.RequestMessage.requestMessage;
import static ru.kostin.financekeeper.console.StringConstants.*;

public class AccountMenu {
    public static void printAccountMenu(UserDTO loggedUser, AccountService accountService, TypeService typeService, TransactionService transactionService) {
        boolean stop = false;
        while (!stop) {
            System.out.println(ACCOUNTS_GREETING);
            System.out.println(ACCOUNTS_OPTIONS);
            switch (requestMessage(SELECT)) {
                case "1": {
                    System.out.println(LIST_SELECT);
                    System.out.println(accountService.getAll(loggedUser.getId()));
                    break;
                }
                case "2": {
                    try {
                        accountService.save(
                                requestMessage(REQUEST + NAME_REQUEST),
                                requestMessage(REQUEST + BALANCE_REQUEST),
                                loggedUser.getId()
                        );
                        System.out.println(SUCCESS);
                    } catch (ItemAlreadyExistsException e) {
                        System.out.println(ALREADY_EXISTS);
                    } catch (IllegalArgumentException e) {
                        System.out.println(ACCOUNT_WRONG_BALANCE);
                    }
                    break;
                }
                case "3": {
                    List<AccountDTO> accounts = accountService.getAll(loggedUser.getId());
                    System.out.println(SELECT);
                    for (int i = 0; i < accounts.size(); i++) {
                        AccountDTO account = accounts.get(i);
                        System.out.println(i + DOT + NAME_REQUEST + account.getName() + COMMA + BALANCE_REQUEST + account.getBalance());
                    }
                    try {
                        int num = Integer.parseInt(requestMessage(SELECT));
                        System.out.println(ACCOUNTS_UPDATE);
                        int option = Integer.parseInt(requestMessage(SELECT));
                        switch (option) {
                            case 1: {
                                accountService.update(accounts.get(num).getId(), ModParam.NAME, requestMessage(REQUEST + NEW + NAME_REQUEST), loggedUser.getId());
                                break;
                            }
                            case 2: {
                                accountService.update(accounts.get(num).getId(), ModParam.BALANCE, requestMessage(REQUEST + NEW + BALANCE_REQUEST), loggedUser.getId());
                                break;
                            }
                            case 3: {
                                break;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(NOT_EXIST);
                    } catch (IllegalArgumentException e) {
                        System.out.println(ACCOUNT_WRONG_BALANCE);
                    } catch (ItemAlreadyExistsException e) {
                        System.out.println(ALREADY_EXISTS);
                    }
                    break;
                }
                case "4": {
                    System.out.println(SELECT);
                    List<AccountDTO> accounts = accountService.getAll(loggedUser.getId());
                    for (int i = 0; i < accounts.size(); i++) {
                        AccountDTO account = accounts.get(i);
                        System.out.println(i + DOT + NAME_REQUEST + account.getName() + COMMA + BALANCE_REQUEST + account.getBalance());
                    }
                    System.out.println(accounts.size() + BACK_OPTION + DOT);
                    try {
                        int num = Integer.parseInt(requestMessage(SELECT));
                        if (num == accounts.size()) {
                            break;
                        }
                        AccountDTO toDelete = accounts.get(num);
                        accountService.delete(toDelete.getId());
                        System.out.println(SUCCESS);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(WRONG_OPTION);
                    } catch (ItemNotExistException e) {
                        System.out.println(NOT_EXIST);
                    }
                    break;
                }
                case "5": {
                    TypeMenu.printTypeMenu(typeService);
                    break;
                }
                case "6": {
                    TransactionMenu.printMenu(transactionService, typeService, accountService, loggedUser);
                    break;
                }
                case "7": {
                    stop = true;
                }
            }
        }
    }
}
