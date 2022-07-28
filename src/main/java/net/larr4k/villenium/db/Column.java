package net.larr4k.villenium.db;

import lombok.Getter;


public class Column {

    @Getter
    private final String name;

    public Column(String name) {
        this.name = name;
    }

}