package ru.kostin.financekeeper.console;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kostin.financekeeper.dto.UserDTO;
import ru.kostin.financekeeper.service.AccountService;
import ru.kostin.financekeeper.service.TransactionService;
import ru.kostin.financekeeper.service.TypeService;
import ru.kostin.financekeeper.service.UserService;

import java.util.concurrent.*;

import static ru.kostin.financekeeper.console.RequestMessage.requestMessage;
import static ru.kostin.financekeeper.console.StringConstants.*;

@Service
@RequiredArgsConstructor
public class MainMenu {
    private final static int TIMEOUT = 10;
    private final UserService userService;
    private final AccountService accountService;
    private final TypeService typeService;
    private final TransactionService transactionService;

    public void mainMenu() {
        boolean stop = false;
        UserDTO loggedUser = null;
        ExecutorService service = Executors.newSingleThreadExecutor();
        while (!stop) {
            System.out.println(MAIN_MENU_GREETING);
            System.out.println(MAIN_MENU_OPTIONS);
            String[] command = new String[1];
            try {
                service.submit(
                        () -> command[0] = requestMessage(SELECT)
                ).get(TIMEOUT, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException ignored) {
            } catch (TimeoutException e) {
                return;
            }
            switch (command[0]) {
                case "1": {
                    loggedUser = UserMenu.PrintUserMenu(userService);
                    break;
                }
                case "2": {
                    if (loggedUser == null) {
                        System.out.println(AUTH_REQUIRED);
                        break;
                    }
                    AccountMenu.printAccountMenu(loggedUser, accountService, typeService, transactionService);
                    break;
                }
                case "3": {
                    stop = true;
                    break;
                }
            }
        }
    }
}
