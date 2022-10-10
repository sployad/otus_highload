package ru.otus.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.socialnetwork.dto.FriendDto;
import ru.otus.socialnetwork.exception.BadRequestException;
import ru.otus.socialnetwork.security.CustomUserDetails;
import ru.otus.socialnetwork.service.FriendService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final DataSource dataSource;

    @Override
    public List<FriendDto> friendList(CustomUserDetails user, Long page, Long size) {

        try {
            String sql = "select u.first_name as firstName, u.last_name as lastName, u.id as userId, f.id as friendId  " +
                    "from rneretin_otus.users u " +
                    "inner join rneretin_otus.friends f on u.id = f.user_from or u.id = f.user_to " +
                    "where u.id != ? " +
                    "and (f.user_to = ? or f.user_from = ?) " +
                    "and f.status = 'APPROVE' " +
                    "limit ? offset ?;";

            long offset = page == 1 ? 0 : page * size;
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            preparedFriendList(user.getId(), size, offset, preparedStatement);
            return mappingFriendsResultSet(preparedStatement.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public List<FriendDto> friendRequestsList(CustomUserDetails user, Long page, Long size) {
        try {
            String sql = "select u.first_name as firstName, u.last_name as lastName, u.id as userId, f.id as friendId  " +
                    "from rneretin_otus.users u " +
                    "inner join rneretin_otus.friends f on u.id = f.user_from " +
                    "where " +
                    "f.status = 'WAIT' and f.user_to = ? " +
                    "limit ? offset ?;";
            long offset = page == 1 ? 0 : page * size;
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, size);
            preparedStatement.setLong(3, offset);
            return mappingFriendsResultSet(preparedStatement.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public void addFriend(CustomUserDetails user, Long friendId) throws BadRequestException {
        try {
            if (hasRecord(user.getId(), friendId)) throw new BadRequestException();
            String sql = "insert into rneretin_otus.friends (user_from, user_to) values (?, ?);";
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, friendId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                throw new BadRequestException();
            }
        }
    }

    @Override
    public void declineFriendRequest(CustomUserDetails user, Long requestId) throws BadRequestException {
        try {
            if (hasNotRecordByRequestId(user.getId(), requestId)) throw new BadRequestException();
            String sql = "delete from rneretin_otus.friends f" +
                    " where f.user_to = ? and f.id = ?;";
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, requestId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
        }
    }

    @Override
    public void approveFriendRequest(CustomUserDetails user, Long requestId) throws BadRequestException {
        try {
            if (hasNotRecordByRequestId(user.getId(), requestId)) throw new BadRequestException();
            String sql = "update  rneretin_otus.friends" +
                    " set status = 'APPROVE'" +
                    " where user_to = ? and id = ?;";
            PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, requestId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
        }
    }

    public boolean hasNotRecordByRequestId(Long userId, Long friendItemId) throws SQLException {
        String sql = "select count(*) as counter" +
                " from rneretin_otus.friends f " +
                " where f.user_to = ? and f.id = ?";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, friendItemId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt(1) != 1;
    }

    public boolean hasRecord(Long userFrom, Long userTo) throws SQLException {
        String sql = "select count(*) as counter" +
                " from rneretin_otus.friends f " +
                " where f.user_from = ? and  f.user_to = ?";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sql);
        preparedStatement.setLong(1, userFrom);
        preparedStatement.setLong(2, userTo);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt(1) == 1;
    }


    private List<FriendDto> mappingFriendsResultSet(ResultSet rs) throws SQLException {
        List<FriendDto> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new FriendDto(
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getLong("userId"),
                    rs.getLong("friendId")
            ));
        }

        return result;
    }

    private void preparedFriendList(Long userId, Long limit, Long offset, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, userId);
        preparedStatement.setLong(3, userId);
        preparedStatement.setLong(4, limit);
        preparedStatement.setLong(5, offset);
    }
}
