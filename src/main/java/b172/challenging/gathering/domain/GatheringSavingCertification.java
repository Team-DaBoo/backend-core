package b172.challenging.gathering.domain;

import b172.challenging.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "gathering_saving_certification")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GatheringSavingCertification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gathering_saving_certification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_saving_log_id", nullable = false)
    private GatheringSavingLog gatheringSavingLog;

    @Column(name = "image_url")
    private String imageUrl;

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
