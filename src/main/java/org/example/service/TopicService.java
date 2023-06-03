package org.example.service;

import org.example.repository.dao.TopicRepository;

public class TopicService {
    private TopicRepository topicRepository;


    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
}
