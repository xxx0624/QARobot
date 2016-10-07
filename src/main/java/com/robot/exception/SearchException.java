package com.robot.exception;

/**
 * Created by xing on 2016/9/21.
 */
public class SearchException extends Exception {
    public final static String separator = " # ";
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Enum<ErrorCode> errorCode = null;


    /**
     *
     */
    public SearchException(Enum<ErrorCode> code) {
        // TODO Auto-generated constructor stub
        this.errorCode = code;
    }

    /**
     * @param message
     */
    public SearchException(String message, Enum<ErrorCode> code) {
        super(message);
        this.errorCode = code;
        // TODO Auto-generated constructor stub
    }

    ;

    /**
     * @param cause
     */
    public SearchException(Throwable cause, Enum<ErrorCode> code) {
        super(cause);
        this.errorCode = code;
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public SearchException(String message, Throwable cause,
                           Enum<ErrorCode> code) {
        super(message, cause);
        this.errorCode = code;
        // TODO Auto-generated constructor stub
    }

    public Enum<ErrorCode> getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Enum<ErrorCode> errorCode) {
        this.errorCode = errorCode;
    }

    public static enum ErrorCode {
        LuceneError(401),
        SearchDocError(402),
        UnKnowError(403),
        SynEngineError(405),
        NoSearchResult(406),
        QABeanError(407),
        SearchBeanError(410);

        private int value;

        private ErrorCode(int value) {
            this.value = value;
        }

        public static ErrorCode valueOf(int value) throws IllegalArgumentException { // 手写的从int到enum的转换函数
            for (ErrorCode t : ErrorCode.values()) {
                if (t.value() == value) {
                    return t;
                }
            }
            throw new IllegalArgumentException("Enum ErrorCode Argument is Error!");
        }

        public int value() {
            return value;
        }
    }

}

