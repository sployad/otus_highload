package ru.otus.socialnetwork.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FriendDto implements Serializable {
    private String firstName;
    private String lastName;
    private Long userId;
    private Long friendId;
}
