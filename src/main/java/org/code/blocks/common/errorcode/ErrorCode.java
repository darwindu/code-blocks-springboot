/*
 *       Copyright© (2018-2019) WeBank Co., Ltd.
 *
 *       This file is part of weidentity-java-sdk.
 *
 *       weidentity-java-sdk is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       weidentity-java-sdk is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with weidentity-java-sdk.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.code.blocks.common.errorcode;

/**
 * Define ERROR Code and the corresponding ERROR Message.
 *
 * @author darwindu
 * @date 2019/4/10
 */
public enum ErrorCode implements IErrorCode {

    /**
     * The success.
     */
    SUCCESS(0, "success"),

    /**
     * other uncatched exceptions or error.
     */
    UNKNOW_EXCEPTION(160101, "异常，请联系管理员[unknow]"),

    /**
     * illegal input.
     */
    ILLEAGAL_INPUT(160102, "无效参数"),

    /**
     * component class error.
     */
    COMPONENT_EXCEPTION(160103, "调用组件异常，请联系管理员[component]"),

    /**
     * mybatis invalid bound exception.
     */
    MYBATIS_BIND_EXCEPTION(160104, "数据库异常，请联系管理员[binding]"),

    /**
     * mybatis data trancation.
     */
    MYBATIS_DATA_EXCEPTION(160105, "数据库异常，请联系管理员[data]"),

    /**
     * mybatis bad sql.
     */
    MYBATIS_BADSQL_EXCEPTION(160106, "数据库异常，请联系管理员[badsql]"),
    /**
     * mybatis sql.
     */
    MYBATIS_EXCEPTION(160107, "数据库操作异常"),
    /**
     * sql uni index repeat.
     */
    SQL_UNI_REPEAT(160108, "记录已存在[uni]"),
    /**
     * record not exist.
     */
    RECORD_NOT_EXIST(160109, "记录不存在"),



    /**
     * out of ranges error.
     */
    OUT_OF_RANGES_ERROR(160202, "out of ranges error"),
    /**
     * field not empty.
     */
    FIELD_NOT_EMPTY(160203, "field not empty or null"),
    /**
     * field not empty.
     */
    FIELD_NOT_NULL(160204, "field not null"),
    /**
     * list not empty or null.
     */
    LIST_NOT_NULL(160205, "list not empty or null"),
    /**
     * decode output exception.
     */
    DECODE_OUTPUT_ERROR(160206, "decode output exception"),
    /**
     * string to bytesexception.
     */
    STRING_TO_BYTES_ERROR(160207, "string to bytes exception"),
    /**
     * value must be more then zero.
     */
    VALUE_GT_ZERO(160208, "value must be more then zero"),
    /**
     * config file:[transaction.limit] must be more then zero.
     */
    TRANSACTION_LIMIT(160209, "config file:[transaction.limit] must be more then zero"),
    /**
     * String format is only letter and number.
     */
    FORMAT_LETTER_NUMBER(160210, "String format is only letter and number"),
    /**
     * String format is only number.
     */
    FORMAT_NUMBER(160211, "String format is only number"),

    /**
     * value must be more then zero or equal.
     */
    VALUE_GE_ZERO(160212, "value must be more then zero or equal"),



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
     * ERROR Code Constructor.
     *
     * @param code The ErrorCode
     * @param msg The ErrorCode Description
     */
    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Get the ERROR Code.
     *
     * @return the ErrorCode
     */
    public int getCode() {
        return code;
    }

    /**
     * Set the ERROR Code.
     *
     * @param code the new ErrorCode
     */
    protected void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets the ErrorCode Description.
     *
     * @return the ErrorCode Description
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the ErrorCode Description.
     *
     * @param msg the new ErrorCode Description
     */
    protected void setMsg(String msg) {
        this.msg = msg;
    }
}
