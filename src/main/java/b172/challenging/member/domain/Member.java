package b172.challenging.member.domain;

import b172.challenging.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@Table(name = "member", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"oauth_provider", "oauth_id"})
})
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "oauth_provider", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private OauthProvider oauthProvider;

    @Column(name = "oauth_id", nullable = false, length = 128)
    private String oauthId;

    @Column(nullable = false, length = 30, unique = true)
    private String nickname;

    @Column(length = 30)
    private String jwtCode;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_leaved", nullable = false)
    private boolean isLeaved;

    @Column(name = "birth_year")
    private Long birthYear;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Builder
    public Member(Long id, OauthProvider oauthProvider, String oauthId, String nickname) {
        this.id = id;
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
        this.nickname = nickname;
        this.role = Role.GUEST;
        this.isLeaved = false;
    }

    public Member(Long memberId){
        this.id = memberId;
    }

    public void setJwtCode(String jwtCode) {
        this.jwtCode = jwtCode;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBirthYear(Long birthYear) {
        this.birthYear = birthYear;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isNicknameChanged(String inputNickname) {
        return !nickname.equals(inputNickname);
    }
}
