package b172.challenging.common.exception;

public enum Exceptions {

    INVALID_REQUEST(400, "1001", "올바르지 않은 요청입니다."),
    RESOURCE_NOT_FOUND(400, "1004","해당 정보를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(405,"1005","허용되지 않은 요청입니다."),
    DUPLICATE_RESOURCE(409,"1009","데이터가 이미 존재합니다."),
    SERVER_ERROR(500,"1901","예기치 못한 오류가 발생하였습니다."),


    UNAUTHORIZED(401, "2002", "인증되지 않은 사용자 입니다."),
    ACCESS_DENIED(403, "2003","접근이 거부되었습니다."),
    NOT_FOUND_MEMBER(404, "2004", "요청한 ID에 해당하는 사용자를 찾을 수 없습니다."),


    NOT_FOUND_IMAGE(404, "3404", "저장된 모임 이미지가 없습니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;

    Exceptions(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return this.message;
    }
}
