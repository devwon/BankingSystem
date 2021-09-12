package constant;

public enum ErrCode {
    /**
     * 에러코드 정의
     */
    // Unexpected Error
    E000("E000", "예상치 못한 오류 발생"),

    // Custom Error
    E101("E101", "출금 처리 중 에러 발생"),
    E102("E102", "입금 처리 중 에러 발생"),
    E103("E103", "송금 처리 중 에러 발생"),
    E104("E104", "적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다."),

    // IO Error
    E201("E201", "입력값 오류 발생");

    public final String code;
    public final String errMsg;

    ErrCode(String code, String errMsg){
        this.code = code;
        this.errMsg = errMsg;
    }

    public String getCode(){
        return code;
    }

    public String getErrMsg(){
        return errMsg;
    }
}
