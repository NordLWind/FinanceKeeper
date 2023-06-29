package ru.kostin.financekeeper.view.console;

import java.util.Scanner;

public class RequestMessage {
    public static String requestMessage(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        return scanner.nextLine();
    }
}
