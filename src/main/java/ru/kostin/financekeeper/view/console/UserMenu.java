package ru.kostin.financekeeper.view.console;

import ru.kostin.financekeeper.dto.UserDTO;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.service.UserService;

import static ru.kostin.financekeeper.view.console.RequestMessage.requestMessage;
import static ru.kostin.financekeeper.view.console.StringConstants.*;

public class UserMenu {
    public static UserDTO PrintUserMenu(UserService userService) {
        System.out.println(USER_MENU_GREETING);
        System.out.println(USERS_OPTIONS);
        switch (requestMessage(SELECT)) {
            case "1": {
                try {
                    String name = requestMessage(REQUEST + NAME_REQUEST);
                    String email = requestMessage(REQUEST + EMAIL_REQUEST);
                    String password = requestMessage(REQUEST + PASSWORD_REQUEST);
                    userService.save(name, email, password);
                    printSuccess();
                    return userService.auth(email, password);
                } catch (ItemAlreadyExistsException e) {
                    System.out.println(ALREADY_EXISTS_EMAIL);
                }
            }
            case "2": {
                try {
                    UserDTO user = userService.auth(
                            requestMessage(REQUEST + EMAIL_REQUEST),
                            requestMessage(REQUEST + PASSWORD_REQUEST)
                    );
                    printSuccess();
                    return user;
                } catch (ItemNotExistException e) {
                    System.out.println(NOT_EXISTS_AUTH);
                }
            }
            case "3": {
                return null;
            }
        }
        return null;
    }

    private static void printSuccess() {
        System.out.println(LOGGED);
    }
}
