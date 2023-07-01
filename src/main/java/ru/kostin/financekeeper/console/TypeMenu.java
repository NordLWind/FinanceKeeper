package ru.kostin.financekeeper.console;

import ru.kostin.financekeeper.dto.TypeDTO;
import ru.kostin.financekeeper.exception.ItemAlreadyExistsException;
import ru.kostin.financekeeper.exception.ItemNotExistException;
import ru.kostin.financekeeper.service.TypeService;

import java.util.List;

import static ru.kostin.financekeeper.console.RequestMessage.requestMessage;
import static ru.kostin.financekeeper.console.StringConstants.*;

public class TypeMenu {

    public static void printTypeMenu(TypeService typeService) {
        System.out.println(TYPES_GREETING);
        while (true) {
            System.out.println(TYPES_OPTIONS);
            switch (requestMessage(SELECT)) {
                case "1": {
                    try {
                        typeService.save(requestMessage(REQUEST + TYPE_REQUEST));
                    } catch (ItemAlreadyExistsException e) {
                        System.out.println(ALREADY_EXISTS);
                    }
                    break;
                }
                case "2": {
                    List<TypeDTO> types = printList(typeService);

                    try {
                        int num = Integer.parseInt(requestMessage(SELECT));
                        if (num == types.size() + 1) {
                            break;
                        }
                        TypeDTO toChange = types.get(num - 1);
                        typeService.update(toChange.getId(), requestMessage(REQUEST + TYPE_REQUEST));
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        System.out.println(WRONG_OPTION);
                    } catch (ItemAlreadyExistsException e) {
                        System.out.println(ALREADY_EXISTS);
                    }
                }
                case "3": {
                    List<TypeDTO> types = printList(typeService);
                    try {
                        int num = Integer.parseInt(requestMessage(SELECT));
                        if (num == types.size() + 1) {
                            break;
                        }
                        TypeDTO toDelete = types.get(num - 1);
                        typeService.delete(toDelete.getId());
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        System.out.println(WRONG_OPTION);
                    } catch (ItemNotExistException e) {
                        System.out.println(NOT_EXIST);
                    }
                    break;
                }
                case "4": {
                    return;
                }
            }
        }
    }

    static List<TypeDTO> printList(TypeService typeService) {
        System.out.println(LIST_SELECT);
        List<TypeDTO> types = typeService.getAll();

        for (int i = 1; i <= types.size(); i++) {
            System.out.println(i + ". " + types.get(i - 1).getType());
        }
        System.out.println((types.size() + 1) + BACK_OPTION);
        return types;
    }
}
