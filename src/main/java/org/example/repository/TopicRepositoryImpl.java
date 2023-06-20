package org.example.repository;

import org.example.model.Topic;
import org.example.repository.dao.TopicRepository;

import java.sql.*;


public class TopicRepositoryImpl implements TopicRepository {
    private static final String SELECT_All = "Select * from topic where id = ?";
    private static final String SAVE =
            """
                            INSERT INTO public."Topic"(
                            name)
                            VALUES (?)
                    """;
    private static final String REMOVE =
            """
                            DELETE FROM public."Topic"
                            WHERE id = ?
                                        
                    """;
    private static final String UPDATE =
            """
                            UPDATE public."Topic"
                            SET name=?
                            WHERE  id = ?;
                                                
                    """;
    private Connection connection = ConnectionData.getConnection();
    @Override
    public boolean save(Topic topic) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
            preparedStatement.setString(1, topic.getName());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Can't save data");
        }
    }

    @Override
    public Topic get(int id) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_All + id);
            resultSet.next();
            return Topic.builder()
                    .name(resultSet.getString("name"))
                    .id(resultSet.getInt("id"))
                    .build();
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Can't get topic by id");
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE);
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Can't remove topic by id");
        }
    }

    @Override
    public int update(Topic topic) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, topic.getName());
            preparedStatement.setInt(2,topic.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update data");
        }
    }
}
