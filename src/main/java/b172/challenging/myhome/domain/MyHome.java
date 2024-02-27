package b172.challenging.myhome.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
