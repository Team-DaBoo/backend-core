package b172.challenging.myhome.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "home_material")
@NoArgsConstructor
@Schema(description = "집에 필요한 자재")
@JsonIgnoreProperties({
        "materialWallet"
})
public class HomeMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_material_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id", nullable = false, columnDefinition = "bigint default 1")
    private MyHome myHome;


    @Column(nullable = false, length = 30)
    @Schema(description = "재료 이름")
    private String name;

    @Column(nullable = false)
    @Schema(description = "필요한 양")
    private Long needed;
}
