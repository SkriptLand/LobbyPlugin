package net.skriptland.lobby.utils;

public enum Files {
    CONFIG("config"),
    CACHE("cache");

    private final String name;

    Files(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}