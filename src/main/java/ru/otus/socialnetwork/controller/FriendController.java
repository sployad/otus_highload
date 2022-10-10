package ru.otus.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import ru.otus.socialnetwork.dto.FriendDto;
import ru.otus.socialnetwork.dto.IdDto;
import ru.otus.socialnetwork.dto.SuccessResponseDto;
import ru.otus.socialnetwork.exception.BadRequestException;
import ru.otus.socialnetwork.service.AuthService;
import ru.otus.socialnetwork.service.FriendService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final AuthService authService;

    @GetMapping(params = {"page", "size"})
    public List<FriendDto> getMyFriends(@RequestParam("page") Long page, @RequestParam("size") Long size) {
        return friendService.friendList(authService.currentUser(), page, size);
    }

    @GetMapping(path = "/requests", params = {"page", "size"})
    public List<FriendDto> getFriendRequests(@RequestParam("page") Long page, @RequestParam("size") Long size){
        return friendService.friendRequestsList(authService.currentUser(), page, size);
    }

    @PostMapping("")
    public SuccessResponseDto addFriend(@RequestBody IdDto idDto) throws BadRequestException {
        friendService.addFriend(authService.currentUser(), idDto.getId());
        return new SuccessResponseDto();
    }

    @DeleteMapping("/{id}/decline")
    public SuccessResponseDto declineRequest(@PathVariable Long id) throws BadRequestException {
        friendService.declineFriendRequest(authService.currentUser(), id);
        return new SuccessResponseDto();
    }

    @PutMapping("{id}/approve")
    public SuccessResponseDto approve(@PathVariable Long id) throws BadRequestException {
        friendService.approveFriendRequest(authService.currentUser(), id);
        return new SuccessResponseDto();
    }
}
