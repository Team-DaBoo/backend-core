package b172.challenging.myhome.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Getter
@Table(name = "home")
@NoArgsConstructor
@Schema(description = "집 정보")
public class MyHome {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "home_id")
	private Long id;

	@Column(nullable = false, length = 30)
	@Schema(description = "집 종류")
	private String name;

	@Column(nullable = false, unique = true)
	@Schema(description = "집 레벨")
	private Long level;
}
