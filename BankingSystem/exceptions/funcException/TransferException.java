package exceptions.funcException;

import constant.ErrCode;

public class TransferException extends Exception{
    public TransferException(String reason) {
        super(String.format("[%s] %s: %s", ErrCode.E103.getCode(), ErrCode.E103.getErrMsg(), reason));
    }
}
