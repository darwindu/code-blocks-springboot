package org.code.blocks.common.exception;

import org.code.blocks.common.errorcode.IErrorCode;

/**
 * @author darwindu
 * @date 2020/11/20
 **/
public class ComponentException extends BaseException {

    public ComponentException(Throwable cause) {
        super(cause);
    }

    public ComponentException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ComponentException(String msg) {
        super(msg);
    }

    public ComponentException(Integer code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public ComponentException(Integer code, String msg) {
        super(code, msg);
    }

    public ComponentException(IErrorCode code) {
        super(code);
    }

    public ComponentException(IErrorCode code, Object... paramObjects) {
        super(code, paramObjects);
    }
}
