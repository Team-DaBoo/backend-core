package b172.challenging.auth.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import lombok.Getter;

import b172.challenging.member.domain.Role;

@Getter
public class CustomOauth2User extends DefaultOAuth2User {
	private final Long memberId;
	private final Role role;

	public CustomOauth2User(Collection<? extends GrantedAuthority> authorities,
		Map<String, Object> attributes, String nameAttributeKey,
		Long id, Role role) {
		super(authorities, attributes, nameAttributeKey);
		this.memberId = id;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(this.role.getKey()));
		return authorities;
	}
}
