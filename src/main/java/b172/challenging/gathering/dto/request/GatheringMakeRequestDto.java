package b172.challenging.gathering.dto.request;

import b172.challenging.gathering.domain.AppTechPlatform;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record GatheringMakeRequestDto(
        @NotBlank(message = "앱을 선택 해야 합니다.")
        AppTechPlatform appTechPlatform,
        @NotBlank(message = "제목은 공백이 될 수 없습니다.")
        @Size(max = 15, message = "제목은 15자를 초과할 수 없습니다.")
        String title,
        @Size(max = 50, message = "내용은 50자를 초과할 수 없습니다.")
        String description,
        @NotBlank(message = "인원을 설정 해야 합니다.")
        int peopleNum,
        @NotBlank(message = "기간을 설정 해야 합니다.")
        int workingDays,
        @NotBlank(message = "목표 금액을 설정 해야 합니다.")
        Long goalAmount,

        @NotBlank(message = "이미지 Url을 설정 해야 합니다.")
        String gatheringImageUrl,

        @NotBlank(message = "시작 날짜를 설정 해야 합니다.")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
        LocalDateTime startDate,

        @NotBlank(message = "완료 날짜를 설정 해야 합니다.")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
        LocalDateTime endDate



) { }
