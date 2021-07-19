package com.gmail.murtazinram.console;

public abstract class MenuEntry {
    private final String title;

    protected MenuEntry(String title) {
        this.title = title;
    }

    public abstract void run();

    public String getTitle() {
        return title;
    }
}
