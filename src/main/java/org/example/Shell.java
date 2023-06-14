package org.example;

import org.example.model.Question;
import org.example.model.Topic;
import org.example.service.QuestionService;
import org.example.service.TopicService;

import java.util.List;
import java.util.Scanner;

public class Shell {
    private QuestionService questionService;
    private TopicService topicService;
    private Scanner scanner;

    public Shell(TopicService topicService, QuestionService questionService, Scanner scanner) {
        this.questionService = questionService;
        this.topicService = topicService;
        this.scanner = scanner;
    }

    public Runnable getRandomQuestionByTopicName() {
        return () -> {
            System.out.println("Point a topic name from the list bellow");
            List<String> topicNames = questionService.getAll().stream()
                    .map(Question::getTopicId)
                    .filter(id -> id > 0)
                    .distinct()
                    .map(id -> topicService.get(id).getName())
                    .toList();
            topicNames.forEach(System.out::println);
            String name = scanner.nextLine();
            topicNames.stream()
                    .filter(n -> n.equals(name))
                    .findFirst()
                    .ifPresentOrElse(n -> System.out.println(
                                    questionService.getRandomByTopicName(n)),
                            () -> {
                                System.out.println("Incorrect topic name!");
                            });
        };
    }

    public Runnable getRandomQuestion() {
        return () -> {
            System.out.println(questionService.getRandom());
        };
    }

    public Runnable addQuestion() {
        return () -> {
            System.out.println("Enter a question");
            String enteredText = scanner.nextLine();
            System.out.println("Point a topic name from the list bellow");
            topicService.getAll().stream()
                    .map(Topic::getName)
                    .forEach(System.out::println);
            String name = scanner.nextLine();
            topicService.getAll().stream()
                    .filter(topic -> topic.getName().equals(name))
                    .findFirst()
                    .ifPresentOrElse(t -> System.out.println(questionService.save(
                                    Question.builder()
                                            .text(enteredText)
                                            .topicId(t.getId())
                                            .build())),
                            () -> {
                                System.out.println("Incorrect topic!");
                            });
        };
    }

    public Runnable removeQuestion() {
        return () -> {
            System.out.println("Enter a question number to remove from the list bellow");
            List<Question> questions = questionService.getAll();
            questions.forEach(q -> System.out.println(q.getId() + " " + q.getText()));
            int id = scanner.nextInt();
            questions.stream()
                    .filter(q -> q.getId() == id)
                    .findFirst()
                    .ifPresentOrElse(q -> System.out.println(!questionService.remove(q.getId())),
                            () -> {
                                System.out.println("Incorrect question number!");
                            });
        };
    }

    public Runnable getTopic() {
        return () -> {
            System.out.println("Choose the topic name from the list bellow");
            List<Topic> topics = topicService.getAll();
            topics.forEach(t -> System.out.println(t.getName()));
            String name = scanner.nextLine();
            topics.stream()
                    .filter(t -> t.getName().equals(name))
                    .findFirst()
                    .ifPresentOrElse(t -> System.out.println(topicService.get(t.getId())),
                            () -> {
                                System.out.println("Incorrect topic name!");
                            });
        };
    }

    public Runnable addTopic() {
        return () -> {
            System.out.println("Enter topic name");
            String enteredName = scanner.nextLine();
            System.out.println(topicService.save(Topic.builder().name(enteredName).build()));
        };
    }
}
