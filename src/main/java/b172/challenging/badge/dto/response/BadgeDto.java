package b172.challenging.badge.dto.response;

public record BadgeDto(
        Long id,
        String name,
        String description,
        boolean isAcquired
) {
}
