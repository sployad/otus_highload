package ru.otus.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegRequestDto {
    private String firstName;
    private String lastName;
    private Integer age;
    private String interest;
    private String city;
    private String username;
    private String password;
    private Date createAt;
    private Date updateAt;
}
