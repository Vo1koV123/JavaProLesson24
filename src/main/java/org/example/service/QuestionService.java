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
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAll() {
        return questionRepository.getAll();
    }

    private Question getRandomQuestion(List<Question> questions) {
        int randomValue = new Random().nextInt(questions.size());
        return questions.get(randomValue);
    }

    public Question getRandom() {
        return getRandomQuestion(questionRepository.getAll());
    }
    public Question getRandomByTopic(int topicId) {
        return getRandomQuestion(questionRepository.getAllByTopic(topicId));
    }

    public Question getRandomByTopicName(String topicName) {
        return getRandomQuestion(questionRepository.getAllByTopicName(topicName));
    }
    public boolean update(Question question) {
        return questionRepository.update(question);
    }
    public boolean remove(int id) {
        return questionRepository.remove(id);
    }
}
