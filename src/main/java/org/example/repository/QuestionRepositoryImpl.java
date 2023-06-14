package org.example.repository;

import org.example.exception.*;
import org.example.model.Question;
import org.example.repository.dao.QuestionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository {
    private static final String SELECT_All = "Select * from question where id = ?";
    private static final String SAVE =
            """
                            INSERT INTO public."Question"(
                            text)
                            VALUES (?)
                    """;
    private static final String REMOVE =
            """
                            DELETE FROM public."Question"
                            WHERE id = ?
                                        
                    """;
    private static final String UPDATE =
            """
                            UPDATE public."Question"
                            SET text=?
                            WHERE  id = ?;
                                                
                    """;
    private final static String GET =
            """     
                    SELECT * FROM question
                    WHERE id = ?
            """;
    private final static String GET_ALL =
            """
                    SELECT * FROM "Question"
            """;
    private final static String GET_ALL_BY_TOPIC =
            """
                    SELECT * FROM "Question"
                    WHERE topic_id = ?
            """;
    private final static String GET_ALL_BY_TOPIC_NAME =
            """
                    SELECT question.id, text, topic_id
                    FROM question
                    LEFT OUTER JOIN topic
                    ON topic_id = topic.id
                    WHERE name = ?
            """;
    private final Connection connection = ConnectionData.getConnection();

    @Override
        public Question save(Question question) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SAVE,
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, question.getText());
                preparedStatement.setInt(2, question.getTopicId());
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                question.setId(resultSet.getInt(1));
                return question;
            } catch (SQLException e) {
                throw new SaveDataException("Can't save data " + question, e);
            }
        }

    @Override
    public Question get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return getQuestion(resultSet);
        } catch (SQLException e) {
            throw new GetDataException("Can't get question by id: " + id, e);
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE);
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RemoveDataException("Can't remove question by id: " + id, e);
        }
    }

    @Override
    public boolean update(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setInt(2, question.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new UpdateDataException("Can't update data " + question, e);
        }
    }

    @Override
    public List<Question> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getQuestions(resultSet);
        } catch (SQLException e) {
            throw new GetAllDataException("Can't get all questions: ", e);
        }
    }

    @Override
    public List<Question> getAllByTopic(int topicId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_TOPIC);
            preparedStatement.setInt(1, topicId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getQuestions(resultSet);
        } catch (SQLException e) {
            throw new GetAllDataByIdException("Can't get all questions by topic where topic id: "
                    + topicId, e);
        }
    }

    @Override
    public List<Question> getAllByTopicName(String topicName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_TOPIC_NAME);
            preparedStatement.setString(1, topicName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getQuestions(resultSet);
        } catch (SQLException e) {
            throw new GetAllDataByNameException("Can't get all questions by topic where topic name: "
                    + topicName, e);
        }
    }

    private List<Question> getQuestions(ResultSet resultSet) throws SQLException {
        ArrayList<Question> questions = new ArrayList<>();
        while (resultSet.next()) {
            questions.add(getQuestion(resultSet));
        }
        return questions;
    }
    private Question getQuestion(ResultSet resultSet) throws SQLException {
        return Question.builder()
                .id(resultSet.getInt(1))
                .text(resultSet.getString(2))
                .topicId(resultSet.getInt(3))
                .build();
    }
}
