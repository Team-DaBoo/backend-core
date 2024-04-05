package b172.challenging.common.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
        List<T> list,
        int number,
        int size,
        long totalElements,
        long totalPages,
        boolean hasPrevious,
        boolean hasNext

) {
    public static <T> PageResponse<T> from(Page<T> page){
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasPrevious(),
                page.hasNext()
        );
    }
}
