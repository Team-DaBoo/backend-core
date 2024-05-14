package b172.challenging.common.dto;

import java.util.List;

import org.springframework.data.domain.Page;

public record PageResponse<T>(
	List<T> list,
	int number,
	int size,
	long totalElements,
	long totalPages,
	boolean hasPrevious,
	boolean hasNext

) {
	public static <T> PageResponse<T> from(Page<T> page) {
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
