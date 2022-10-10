package ru.otus.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.socialnetwork.security.CustomUserDetails;
import ru.otus.socialnetwork.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final DataSource dataSource;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            String sql = "select u.id as id, u.enabled as enabled, u.password as password from rneretin_otus.users u where username = ?";
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, username);
            return mappingUserDetails(preparedStatement.executeQuery(), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CustomUserDetails mappingUserDetails(ResultSet rs, String username) throws SQLException {
        rs.next();
        return new CustomUserDetails(
                rs.getLong("id"),
                username,
                rs.getBoolean("enabled"),
                rs.getString("password")
        );
    }
}
