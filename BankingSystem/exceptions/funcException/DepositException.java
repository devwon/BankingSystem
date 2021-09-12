package exceptions.funcException;

import constant.ErrCode;

public class DepositException extends Exception{
    public DepositException(String reason) {
        super(String.format("[%s] %s: %s", ErrCode.E102.getCode(), ErrCode.E102.getErrMsg(), reason));
    }
}
