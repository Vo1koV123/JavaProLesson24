package org.example.repository.dao;

import org.example.model.Question;

import java.util.List;

public interface QuestionRepository {
    boolean save(Question question);

    Question get(int id);

    boolean remove(int id);

    int update(Question question);
    List<Question> getAll();

    List<Question> getAllByTopic(int topicId);
}
