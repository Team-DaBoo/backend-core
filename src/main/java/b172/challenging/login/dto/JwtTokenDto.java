package b172.challenging.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokenDto {
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;

    public String toString(){

        return "grantType :" + grantType + " \n" +
                "accessToken :" + accessToken + " \n" +
                "tokenExpiresIn :" + tokenExpiresIn
                ;
    }
}
