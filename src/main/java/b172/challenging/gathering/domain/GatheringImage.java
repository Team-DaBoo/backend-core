package b172.challenging.gathering.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
