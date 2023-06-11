package org.example.service;

import org.example.model.Question;
import org.example.repository.dao.QuestionRepository;

import java.util.List;
import java.util.Random;


public class QuestionService {
    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    public boolean save(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.getAll();
    }

    public Question getRandom() {
        List<Question> all = questionRepository.getAll();
        int randomValue = new Random().nextInt(all.size());
        return all.get(randomValue);
    }

    public Question getRandomByTopic(int topicId) {
        List<Question> allBYTopic = questionRepository.getAllByTopic(topicId);
        int randomValue = new Random().nextInt(allBYTopic.size());
        return allBYTopic.get(randomValue);
    }

    public boolean remove(int id) {
        return questionRepository.remove(id);
    }
}
