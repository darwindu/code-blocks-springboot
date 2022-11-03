package org.code.blocks.common.exception;

import org.code.blocks.common.errorcode.IErrorCode;

/**
 * @author darwindu
 * @date 2020/11/20
 **/
public class BizException extends BaseException {

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BizException(String msg) {
        super(msg);
    }

    public BizException(Integer code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public BizException(Integer code, String msg) {
        super(code, msg);
    }

    public BizException(IErrorCode code) {
        super(code);
    }

    public BizException(IErrorCode code, Object... paramObjects) {
        super(code, paramObjects);
    }
}
