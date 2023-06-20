package org.example.repository.dao;

import org.example.model.Topic;

import java.util.List;


public interface TopicRepository {
    Topic save(Topic topic);
    Topic get(int id);
    boolean remove(int id);
    int update(Topic topic);
    List<Topic> getAll();

}
