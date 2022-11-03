package org.code.blocks.common.exception;

import org.code.blocks.common.errorcode.IErrorCode;

/**
 * @author darwindu
 * @date 2019/4/29
 **/
public class ValidateException extends BaseException {

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ValidateException(String msg) {
        super(msg);
    }

    public ValidateException(Integer code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public ValidateException(Integer code, String msg) {
        super(code, msg);
    }

    public ValidateException(IErrorCode code) {
        super(code);
    }

    public ValidateException(IErrorCode code, Object... paramObjects) {
        super(code, paramObjects);
    }
}
