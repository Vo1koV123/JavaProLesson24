package org.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Question {
    private int id;
    private int topic_id;
    private String text;
}
