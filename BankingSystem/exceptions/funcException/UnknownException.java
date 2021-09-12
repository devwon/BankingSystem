package exceptions.funcException;

import constant.ErrCode;

public class UnknownException extends Exception{
    public UnknownException(String reason) {
        super(String.format("[%s] %s: %s", ErrCode.E000.getCode(), ErrCode.E000.getErrMsg(), reason));
    }
}
