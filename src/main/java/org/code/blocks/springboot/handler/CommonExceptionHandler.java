package org.code.blocks.springboot.handler;

import javax.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;
import org.code.blocks.common.constant.WarnConstant;
import org.code.blocks.common.errorcode.ErrorCode;
import org.code.blocks.common.exception.BaseException;
import org.code.blocks.common.exception.BizException;
import org.code.blocks.common.exception.ComponentException;
import org.code.blocks.common.protocol.response.ResponseData;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author darwindu
 * @date 2020/8/19
 **/
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /**
     * hibernate validate exception(use @Valid).
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseData methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("MethodArgumentNotValidException, code:{}, msg:{}", ErrorCode.ILLEAGAL_INPUT.getCode(), msg, e);
        return ResponseData.fail(ErrorCode.ILLEAGAL_INPUT.getCode(), msg);
    }

    /**
     * hibernate validate exception(use @Validated).
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseData constraintViolationExceptionHandler(ConstraintViolationException e) {
        String msg = e.getMessage();
        log.error("ConstraintViolationException, code:{}, msg:{}", ErrorCode.ILLEAGAL_INPUT.getCode(), msg, e);
        return ResponseData.fail(ErrorCode.ILLEAGAL_INPUT.getCode(), msg);
    }

    /**
     * hibernate validate exception(use @Validated group).
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResponseData BindExceptionHandler(BindException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("BindException, code:{}, msg:{}", ErrorCode.ILLEAGAL_INPUT.getCode(), msg, e);
        return ResponseData.fail(ErrorCode.ILLEAGAL_INPUT.getCode(), msg);
    }


    /**
     * sql uni index repeat(DuplicateKeyException mapping java.sql.sqlIntegrityConstraintViolationException).
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseData duplicateKeyExceptionHandler(DuplicateKeyException e) {
        log.error("{} DuplicateKeyException, code:{}, msg:{}", WarnConstant.WARNING, ErrorCode.SQL_UNI_REPEAT.getCode(), ErrorCode.SQL_UNI_REPEAT.getMsg(), e);
        return ResponseData.fail(ErrorCode.SQL_UNI_REPEAT);
    }

    /**
     * mybatis exception: param bind
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyBatisSystemException.class)
    public ResponseData myBatisSystemExceptionHandler(MyBatisSystemException e) {
        log.error("{} MyBatisSystemException, code:{}, msg:{}", WarnConstant.ERROR, ErrorCode.MYBATIS_BIND_EXCEPTION.getCode(), ErrorCode.MYBATIS_BIND_EXCEPTION.getMsg(), e);
        return ResponseData.fail(ErrorCode.MYBATIS_BIND_EXCEPTION);
    }

    /**
     * mybatis exception: Data truncation: Data too long
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseData dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
        log.error("{} DataIntegrityViolationException, code:{}, msg:{}", WarnConstant.ERROR, ErrorCode.MYBATIS_DATA_EXCEPTION.getCode(), ErrorCode.MYBATIS_DATA_EXCEPTION.getMsg(), e);
        return ResponseData.fail(ErrorCode.MYBATIS_DATA_EXCEPTION);
    }

    /**
     * mybatis exception: bad sql
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public ResponseData badSqlGrammarExceptionHandler(BadSqlGrammarException e) {
        log.error("{} BadSqlGrammarException, code:{}, msg:{}", WarnConstant.ERROR, ErrorCode.MYBATIS_BADSQL_EXCEPTION.getCode(), ErrorCode.MYBATIS_BADSQL_EXCEPTION.getMsg(), e);
        return ResponseData.fail(ErrorCode.MYBATIS_BADSQL_EXCEPTION);
    }

    @ResponseBody
    @ExceptionHandler(value = ComponentException.class)
    public ResponseData componetExceptionHandler(ComponentException e) {
        log.error("ComponentException, code:{}, msg:{}", e.getCode(), e.getMsg(), e);
        return ResponseData.fail(e.getCode(), e.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(value = BizException.class)
    public ResponseData bizExceptionHandler(BizException e) {
        log.warn("{} BizException, code:{}, msg:{}", WarnConstant.Tip, e.getCode(), e.getMsg(), e);
        return ResponseData.fail(e.getCode(), e.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public ResponseData baseExceptionHandler(BaseException e) {
        log.error("BaseException, code:{}, msg:{}", e.getCode(), e.getMsg(), e);
        return ResponseData.fail(e.getCode(), e.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseData exceptionHandler(Exception e) {
        log.error("{} Exception, code:{}, msg:{}", WarnConstant.WARNING, ErrorCode.UNKNOW_EXCEPTION.getCode(), ErrorCode.UNKNOW_EXCEPTION.getMsg(), e);
        return ResponseData.fail(ErrorCode.UNKNOW_EXCEPTION);
    }
}
