package exceptions.funcException;

import constant.ErrCode;

public class WithdrawException extends Exception{
    public WithdrawException(String reason) {
        super(String.format("[%s] %s: %s", ErrCode.E101.getCode(), ErrCode.E101.getErrMsg(), reason));
    }
}
