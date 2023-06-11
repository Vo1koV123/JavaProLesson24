import org.example.model.Question;
import org.example.repository.dao.QuestionRepository;
import org.example.service.QuestionService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionServiceTest {

    private static QuestionService questionService;
    private static List<Question> questions;

    @BeforeClass
    public static void beforeClass() {
        QuestionRepository repository = new QuestionRepository() {
            @Override
            public boolean save(Question question) {
                return questions.add(question);
            }

            @Override
            public Question get(int id) {
                return null;
            }

            @Override
            public boolean remove(int id) {
                for (Question question : questions) {
                    if (question.getId() == id) {
                        return questions.remove(question);
                    }
                }
                return false;
            }

            @Override
            public int update(Question question) {
                return 0;
            }

            @Override
            public List<Question> getAll() {
                return questions;
            }

            @Override
            public List<Question> getAllByTopic(int topicId) {
                return questions.stream()
                        .filter(q -> q.getTopicId() == topicId)
                        .toList();
            }
        };
        questionService = new QuestionService(repository);
    }

    @Before
    public void setUpTest() {
        questions = new ArrayList<>();
        questions.add(Question.builder()
                .id(1)
                .text("What is the difference between JDK and JRE?")
                .topicId(1).build());
        questions.add(Question.builder()
                .id(2)
                .text("How do you create and start a new thread in Java?")
                .topicId(1).build());
    }

    @Test
    public void removeQuestionByQuestionIdTest() {
        int questionId = 2;
        assertTrue("Invalid question id " + questionId,
                questionService.remove(questionId));
        int actual = questions.size();
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    public void saveQuestionTest() {
        Question question = Question.builder()
                .id(3)
                .text("What is Java?")
                .topicId(2).build();
        assertTrue("Can't add question " + question,
                questionService.save(question));
        int actual = questions.size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    public void getRandomByTopicTest() {
        int topicId = 1;
        assertNotNull("Invalid topic id " + topicId,
                questionService.getRandomByTopic(topicId));
        System.out.println(questionService.getRandomByTopic(topicId));
    }

    @Test
    public void getRandomTest() {
        System.out.println(questionService.getRandom());
    }
}