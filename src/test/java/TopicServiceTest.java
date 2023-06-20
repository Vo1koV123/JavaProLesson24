import org.example.model.Topic;
import org.example.repository.dao.TopicRepository;
import org.example.service.TopicService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TopicServiceTest {
    private static TopicService topicService;
    private static List<Topic> topics;

    @BeforeClass
    public static void beforeClass() {
        TopicRepository topicRepository = new TopicRepository() {
            @Override
            public Topic save(Topic topic) {
                topics.add(topic);
                return topic;
            }

            @Override
            public Topic get(int id) {
                return topics.stream()
                        .filter(t -> t.getId() == id)
                        .findFirst()
                        .get();
            }

            @Override
            public boolean remove(int id) {
                return false;
            }

            @Override
            public int update(Topic topic) {
                return 0;
            }

            @Override
            public List<Topic> getAll() {
                return topics;
            }
        };
        topicService = new TopicService(topicRepository);
    }

    @Before
    public void setUp() {
        topics = new ArrayList<>();
        topics.add(Topic.builder()
                .id(1)
                .name("Inheritance").build());
        topics.add(Topic.builder()
                .id(2)
                .name("Encapsulation").build());
    }

    @Test
    public void saveTopic() {
        Topic topic = Topic.builder()
                .id(3)
                .name("Exception").build();
        topics.add(topic);
        int actual = topics.size();
        int expected = 3;
        assertEquals(expected, actual);
        assertEquals(topic, topics.get(topics.size() - 1));
        assertEquals(expected, actual);
    }
    @Test
    public void getAll_topics() {
        List<Topic> expected = List.of(
                Topic.builder()
                        .id(1)
                        .name("Inheritance").build(),
                Topic.builder()
                        .id(2)
                        .name("Encapsulation").build()
        );
        List<Topic> actual = topicService.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getTopic() {
        int topicId = 1;
        Topic actual = topicService.get(topicId);
        Topic expected = new Topic(1, "Inheritance");
        assertEquals(expected, actual);
    }
}