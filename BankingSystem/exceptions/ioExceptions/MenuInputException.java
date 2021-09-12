package exceptions.ioExceptions;

import constant.ErrCode;

public class MenuInputException extends Exception{
    public MenuInputException(String reason) {
        super(String.format("[%s] %s: %s", ErrCode.E201.getCode(), ErrCode.E201.getErrMsg(), reason));
    }
}
