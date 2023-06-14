package org.example;

public enum Commands {
    GET_QUESTION_BY_TOPIC("1"),
    GET_RANDOM_QUESTION("2"),
    ADD_QUESTION("3"),
    REMOVE_QUESTION("4"),
    GET_TOPIC("5"),
    ADD_TOPIC("6");
    private String title;

    Commands(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
