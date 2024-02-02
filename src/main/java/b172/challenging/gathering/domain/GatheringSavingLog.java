package b172.challenging.gathering.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "gathering_saving_log")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GatheringSavingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gathering_saving_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_memeber_id", nullable = false)
    private GatheringMember gatheringMember;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long amount;

    @Column(name = "certificated_at", nullable = false)
    private LocalDateTime certificatedAt;

    public void setAmount(Long amount){
        this.amount = amount;
    }
}
