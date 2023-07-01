package ru.kostin.financekeeper.console;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final MainMenu menu;

    @Override
    public void run(String... args) {
        menu.mainMenu();
    }
}
