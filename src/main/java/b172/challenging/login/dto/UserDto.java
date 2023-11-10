package b172.challenging.login.dto;


import b172.challenging.login.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
public class UserDto implements Serializable {

    UserDto() {}

    public UserDto(User user){
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

    private String nickname;

    private String email;
    private String name;

    private String picture;

    private String gender;

    private String age;
}
