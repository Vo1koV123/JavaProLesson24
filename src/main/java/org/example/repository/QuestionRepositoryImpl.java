package org.example.repository;

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
    private final static String GET_ALL =
            """
                    SELECT * FROM "Question"
            """;
    private final static String GET_ALL_BY_TOPIC =
            """
                    SELECT * FROM "Question"
                    WHERE topic_id = ?
            """;
    private final Connection connection = ConnectionData.getConnection();

    @Override
    public boolean save(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
            preparedStatement.setString(1, question.getText());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Can't save data");
        }
    }

    @Override
    public Question get(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_All + id);
            resultSet.next();
            return Question.builder()
                    .text(resultSet.getString("text"))
                    .topicId(resultSet.getInt("id"))
                    .build();

        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Can't get question by id");
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE);
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Can't remove question by id");
        }
    }

    @Override
    public int update(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setInt(2, question.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update data");
        }
    }

    @Override
    public List<Question> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getQuestions(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all questions");
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
            throw new RuntimeException("Can't get all questions by topic where topic id: "  + topicId, e);
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
