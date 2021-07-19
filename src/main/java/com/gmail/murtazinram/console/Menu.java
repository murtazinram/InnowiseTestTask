package com.gmail.murtazinram.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final List<MenuEntry> entries = new ArrayList<>();
    private final BufferedReader reader;
    private boolean isExit = false;

    public Menu(BufferedReader reader) {
        this.reader = reader;
        entries.add(new MenuEntry("exit") {
            @Override
            public void run() {
                isExit = true;
            }
        });
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void run() {
        while (!isExit) {
            printMenu();
            try {
                int choice = Integer.parseInt(reader.readLine());
                MenuEntry entry = entries.get(choice);
                entry.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printMenu() {
        int item = 0;
        System.out.println("menu");
        for (MenuEntry entry : entries) {
            System.out.println(item++ + "_" + entry.getTitle());
        }
    }

    public void addEntry(MenuEntry entry) {
        entries.add(entry);
    }
}
