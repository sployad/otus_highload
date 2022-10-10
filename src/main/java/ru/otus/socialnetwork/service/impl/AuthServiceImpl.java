package ru.otus.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.socialnetwork.dto.UserRegRequestDto;
import ru.otus.socialnetwork.dto.UserRegResponseDto;
import ru.otus.socialnetwork.exception.BadRequestException;
import ru.otus.socialnetwork.security.CustomUserDetails;
import ru.otus.socialnetwork.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final DataSource dataSource;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserRegResponseDto register(UserRegRequestDto userDto) throws BadRequestException {
        try {
            String sql = "insert into rneretin_otus.users (first_name, last_name, age, interest, city, username, password)" +
                    " values (?, ?, ?, ?, ?, ?, ?);";

            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedDataForRegister(userDto, preparedStatement);
            preparedStatement.executeUpdate();

            return new UserRegResponseDto(getUserId(userDto.getUsername()));
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                throw new BadRequestException();
            }
        }
        return null;
    }

    private Long getUserId(String username) throws SQLException {
        String sql = "select u.id from rneretin_otus.users u where username = ?";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getLong("id");
    }

    private void preparedDataForRegister(UserRegRequestDto userDto, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, userDto.getFirstName());
        preparedStatement.setString(2, userDto.getLastName());
        preparedStatement.setInt(3, userDto.getAge());
        preparedStatement.setString(4, userDto.getInterest());
        preparedStatement.setString(5, userDto.getCity());
        preparedStatement.setString(6, userDto.getUsername().toLowerCase());
        preparedStatement.setString(7, passwordEncoder.encode(userDto.getPassword()));
    }

    public CustomUserDetails currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }
}
