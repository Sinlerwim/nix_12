package com.command;

import lombok.Getter;

@Getter
public enum Action {

    CREATE("Create vehicle", new Create()),
    UPDATE("Update vehicle", new Update()),
    DELETE("Delete vehicle", new Delete()),
    PRINT("Print all vehicles", new Print()),
    COMPARE("Show comparator example", new Compare()),
    TREE("Show tree example", new ShowTree()),
    STREAM("Show stream example", new ShowStream()),
    FILE_READER("Read data from file", new ReadFromFile()),
    AUTO_BUILDER("Show auto builder example", new ShowAutoBuilder()),
    ANNOTATION("Show annotation example", new Annotation()),
    JDBC("Show JDBC work", new DataBase()),
    EXIT("Exit", null);


    private final String name;
    private final Command command;

    Action(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public Command execute() {
        if (command != null) {
            command.execute();
        }
        return command;
    }
}
