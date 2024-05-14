package b172.challenging.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSample {

	@NotNull(message = "Email cannot be null")
	@Size(min = 2, message = "Email not be less than two characters")
	@Email
	@Schema(description = "사용자 이메일", example = "k12@gmail.com")
	@Id
	@GeneratedValue
	private String email;

	@NotNull(message = "Name cannot be null")
	@Size(min = 2, message = "Name not be less than two characters")
	@Schema(description = "사용자 이름", example = "김재")
	private String name;

	@NotNull(message = "Password cannot be null")
	@Size(min = 8, message = "Password not be less than two characters")
	@Schema(description = "사용자 비밀번호", example = "pwd")
	private String password;

	@NotNull(message = "PhoneNumber cannot be null")
	@Schema(description = "사용자 핸드폰 번호", nullable = true, example = "010-12-31")
	private String phoneNumber;

}
