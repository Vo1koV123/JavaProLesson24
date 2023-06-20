package org.example;

import org.example.repository.QuestionRepositoryImpl;
import org.example.repository.TopicRepositoryImpl;
import org.example.repository.dao.QuestionRepository;
import org.example.repository.dao.TopicRepository;
import org.example.service.QuestionService;
import org.example.service.TopicService;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the command by number");
        Arrays.stream(Commands.values())
                .forEach(c -> System.out.println(c + " " + c.getTitle()));
        init(scanner)
                .getOrDefault(scanner.nextLine(),
                        () -> System.out.println("Incorrect command"))
                .run();
    }

    private static Map<String, Runnable> init(Scanner scanner) {
        TopicRepository topicRepository = new TopicRepositoryImpl();
        QuestionRepository questionRepository = new QuestionRepositoryImpl();
        Shell shell = new Shell(
                new TopicService(topicRepository),
                new QuestionService(questionRepository), scanner
        );
        return Map.of(
                Commands.GET_QUESTION_BY_TOPIC.getTitle(), shell.getRandomQuestionByTopicName(),
                Commands.ADD_QUESTION.getTitle(), shell.addQuestion(),
                Commands.GET_RANDOM_QUESTION.getTitle(), shell.getRandomQuestion(),
                Commands.REMOVE_QUESTION.getTitle(), shell.removeQuestion(),
                Commands.ADD_TOPIC.getTitle(), shell.addTopic(),
                Commands.GET_TOPIC.getTitle(), shell.getTopic()
        );
    }
}