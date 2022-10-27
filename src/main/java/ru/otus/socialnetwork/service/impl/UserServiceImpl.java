package ru.otus.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.socialnetwork.dto.UserDto;
import ru.otus.socialnetwork.service.UserService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service()
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final DataSource dataSource;

    @Override
    public List<UserDto> findByFirstNameOrLastName(String firstName, String lastName) {
        String sql = "select u.first_name, u.last_name, u.id from rneretin_otus.users u where u.first_name LIKE ? and u.last_name LIKE ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(true);
            preparedStatement.closeOnCompletion();
            preparedStatement.setString(1, firstName + "%");
            preparedStatement.setString(2, lastName + "%");
            List<UserDto> result = new ArrayList<>();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(
                            new UserDto(
                                    resultSet.getString(1),
                                    resultSet.getString(2),
                                    resultSet.getLong(3)
                            )
                    );
                }
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
