package org.code.blocks.common.protocol.response;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.code.blocks.common.errorcode.ErrorCode;
import org.code.blocks.common.errorcode.IErrorCode;

/**
 * The internal base response data class.
 * ResponseData为返回结果集，code=0表示返回成功，否则失败
 *
 * @param <T> the generic type
 * @author darwindu
 * @date 2019/4/11
 */
@Data
@Slf4j
public class ResponseData<T> {

    private T data;
    private Integer code = ErrorCode.SUCCESS.getCode();
    private String msg = ErrorCode.SUCCESS.getMsg();

    /**
     * Instantiates a new response data.
     */
    public ResponseData() {
    }

    /**
     * Instantiates a new response data.
     *
     * @param data the data
     * @param errorCode the return code
     */
    public ResponseData(T data, IErrorCode errorCode) {
        this.data = data;
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    /**
     * Instantiates a new response data.
     *
     * @param data the data
     * @param code the return code
     * @param msg the return message
     */
    public ResponseData(T data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseData<T> fail(Integer code, String msg) {
        return new ResponseData(null, code, msg);
    }

    public static <T> ResponseData<T> fail(IErrorCode errorCode) {
        return new ResponseData(null, errorCode);
    }

    public static <T> ResponseData<T> success(T data, Integer code, String msg) {
        return new ResponseData(data, code, msg);
    }

    public static <T> ResponseData<T> success(T data, IErrorCode errorCode) {
        return new ResponseData(data, errorCode);
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData(data, ErrorCode.SUCCESS);
    }
}
