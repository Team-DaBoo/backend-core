package b172.challenging.gathering.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Getter
@Table(name = "gathering_image")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "모임 이미지")
public class GatheringImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gathering_image_id")
	private Long id;

	@Schema(description = "기본 이미지 url")
	private String url;
}
