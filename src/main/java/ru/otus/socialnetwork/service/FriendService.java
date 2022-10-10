package ru.otus.socialnetwork.service;

import ru.otus.socialnetwork.dto.FriendDto;
import ru.otus.socialnetwork.exception.BadRequestException;
import ru.otus.socialnetwork.security.CustomUserDetails;

import java.util.List;

public interface FriendService {
    List<FriendDto> friendList(CustomUserDetails user, Long page, Long size);
    List<FriendDto> friendRequestsList(CustomUserDetails user, Long page, Long size);
    void addFriend(CustomUserDetails user, Long friendId) throws BadRequestException;
    void declineFriendRequest(CustomUserDetails user, Long requestId) throws BadRequestException;
    void approveFriendRequest(CustomUserDetails user, Long requestId) throws BadRequestException;
}
