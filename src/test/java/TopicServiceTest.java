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
            public boolean save(Topic topic) {
                return topics.add(topic);
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
        };
        topicService = new TopicService(topicRepository);
    }

    @Before
    public void setUp() {
        topics = new ArrayList<>();
        topics.add(Topic.builder()
                .id(1)
                .name("Java 7").build());
        topics.add(Topic.builder()
                .id(2)
                .name("Java 8").build());
    }

    @Test
    public void saveTopic() {
        Topic topic = Topic.builder()
                .id(3)
                .name("Exception").build();
        assertTrue("Can't add topic " + topic, topicService.save(topic));
        int actual = topics.size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    public void getTopic() {
        int topicId = 1;
        Topic actual = topicService.get(topicId);
        Topic expected = new Topic(1, "Java 7");
        assertEquals(expected, actual);
    }
}