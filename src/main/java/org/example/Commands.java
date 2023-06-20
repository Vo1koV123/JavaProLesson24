package org.example;

public enum Commands {
    GET_RANDOM_QUESTION("1"),
    ADD_TOPIC("2"),
    REMOVE_QUESTION("3"),
    GET_TOPIC("4"),
    ADD_QUESTION("5"),
    GET_QUESTION_BY_TOPIC("6");
    private String title;

    Commands(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
