package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;
import java.util.Date;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2020-12-06
 * Time:23:51
 */

@Getter
@Setter
@ToString
public class User {

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private Date birthday;
    private String phoneNumber;
    private String email;
    private String head;
}
