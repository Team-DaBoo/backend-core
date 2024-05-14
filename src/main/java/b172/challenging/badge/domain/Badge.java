package b172.challenging.badge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

import b172.challenging.common.domain.BaseTimeEntity;

@Entity
@Getter
@Table(name = "badge")
@NoArgsConstructor
@Schema(description = "배지 정보")
public class Badge extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "badge_id")
	private Long id;

	@Column(nullable = false, length = 128)
	@Schema(description = "사용자 화면 배지 이름")
	private String name;

	@Column(nullable = false, length = 255)
	@Schema(description = "배지 획득 방법")
	private String description;

	@Column(name = "image_url", length = 255)
	@Schema(description = "배지 이미지 url")
	private String imageUrl;

}
