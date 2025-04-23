package utez.edu.mx.communitycommitteesystem.exception;

public enum ErrorCatalog {
    UNAUTHORIZED_ACCESS(1005, "UNAUTHORIZED ACCESS"),
    RESOURCE_NOT_FOUND(1005, "ENDPOINT NOT FOUND"),
    PATH_MISSING(1006, "MISSING PARAMETERS"),
    INDEX_OUT_OF_BOUNDS_EXCEPTION(1007, "LIST SEARCH ERROR"),
    METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION(1010, "PARAMETER SENDING ERROR"),
    NULL_POINTER_EXCEPTION(1011, "NULL VALUES"),
    ARITHMETIC_EXCEPTION(1012, "ARITHMETIC ERROR"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(1013, "METHOD NOT SUPPORTED"),
    INTERNAL_SERVER_ERROR(5000, "INTERNAL SERVER ERROR"),
    ILLEGAL_ARGUMENT_EXCEPTION(9000, "INVALID ARGUMENT"),
    USERNAME_NOT_FOUND_EXCEPTION(9001, "INVALID USER"),
    ENTITY_NOT_FOUND_EXCEPTION(9002, "ENTITY NOT FOUND"),
    DATA_INTEGRITY_VIOLATION_EXCEPTION(9003, "DATA ENTRY ERROR");
    private final int code;
    private final String message;

    ErrorCatalog(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}