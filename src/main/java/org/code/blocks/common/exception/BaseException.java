package org.code.blocks.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.code.blocks.common.constant.CommonConstant;
import org.code.blocks.common.errorcode.IErrorCode;
import org.code.blocks.common.util.JsonUtils;

/**
 * 异常类
 * @author darwindu
 * @date 2019/3/4
 **/
@Slf4j
public class BaseException extends RuntimeException {

    private Integer code;
    private String msg;

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BaseException(String msg) {
        super(msg);
    }


    /**
     * constructor.
     * @param cause exception object
     * @param code exception error code.
     * @param msg exception error messave.
     */
    public BaseException(Integer code, String msg, Throwable cause) {
        super(getMsg(code, msg), cause);
        this.code = code;
        this.msg = msg;
    }


    /**
     * constructor.
     * @param code exception error code
     * @param msg exception error messave
     */
    public BaseException(Integer code, String msg) {
        super(getMsg(code, msg));
        this.code = code;
        this.msg = msg;
    }

    /**
     * constructor.
     * @param code exception enum errorcode
     */
    public BaseException(IErrorCode code) {
        super(getMsg(code));
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public BaseException(IErrorCode code, Object... paramObjects) {
        super(getMsg(code, paramObjects));
        this.code = code.getCode();
        this.msg = code.getMsg();
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private static String getMsg(IErrorCode code) {
        return getMsg(code.getCode(), code.getMsg());
    }

    private static String getMsg(IErrorCode code, Object... paramObjects) {
        log.info("code:{} msg:{} paramObjects:{}", code.getCode(), code.getMsg(), JsonUtils.objToJson(paramObjects));
        return getMsg(code.getCode(), code.getMsg());
    }

    private static String getMsg(Integer errorCode, String errorMessage) {

        return new StringBuffer(errorCode == null ? Strings.EMPTY : errorCode.toString())
            .append(CommonConstant.SYMBOL_VERTICAL)
            .append(errorMessage == null ? Strings.EMPTY : errorMessage).toString();
    }
}
