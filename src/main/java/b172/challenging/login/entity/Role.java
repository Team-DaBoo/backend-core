package b172.challenging.login.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_GUEST("ROLE_GUEST","비회원"),
    ROLE_USER("ROLE_USER","사용자"),
    ROLE_ADMIN("ROLE_ADMIN","관리자");

    private final String key;
    private final String value;
}
