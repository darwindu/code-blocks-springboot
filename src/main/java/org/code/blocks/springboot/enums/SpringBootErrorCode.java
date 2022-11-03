package org.code.blocks.springboot.enums;

import org.code.blocks.common.errorcode.IErrorCode;

/**
 * Define Error Code and the corresponding Error Message.
 * @author darwindu
 * @date 2019/4/29
 **/
public enum SpringBootErrorCode implements IErrorCode {

    /**
     * The success.
     */
    SUCCESS(0, "success"),

    ;

    /**
     * error code.
     */
    private int code;

    /**
     * error message.
     */
    private String msg;

    /**
     * Error Code Constructor.
     *
     * @param code The CommonErrorCode
     * @param msg The CommonErrorCode Description
     */
    SpringBootErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Get the Error Code.
     *
     * @return the CommonErrorCode
     */
    public int getCode() {
        return code;
    }

    /**
     * Set the Error Code.
     *
     * @param code the new CommonErrorCode
     */
    protected void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets the CommonErrorCode Description.
     *
     * @return the CommonErrorCode Description
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the CommonErrorCode Description.
     *
     * @param msg the new CommonErrorCode Description
     */
    protected void setMsg(String msg) {
        this.msg = msg;
    }
}
