package org.example.service;

import org.example.model.Topic;
import org.example.repository.dao.TopicRepository;

import java.util.List;

public class TopicService {
    private TopicRepository topicRepository;


    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    public boolean save(Topic topic) {
        return topicRepository.save(topic);
    }

    public Topic get(int id) {
        return topicRepository.get(id);
    }

}
