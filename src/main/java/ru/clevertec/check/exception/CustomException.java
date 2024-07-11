package main.java.ru.clevertec.check.exception;

public class CustomException extends RuntimeException{
    private String additionalInfo;

    public CustomException(String message, String additionalInfo) {
        super(message);
        this.additionalInfo = additionalInfo;
    }

    public CustomException(String message, String additionalInfo, Throwable cause) {
        super(message, cause);
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
