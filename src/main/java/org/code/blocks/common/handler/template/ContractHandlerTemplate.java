package org.code.blocks.common.handler.template;

import lombok.extern.slf4j.Slf4j;
import org.code.blocks.common.errorcode.ErrorCode;
import org.code.blocks.common.exception.BaseException;
import org.code.blocks.common.exception.ValidateException;
import org.code.blocks.common.handler.ContractHandler;
import org.code.blocks.common.protocol.response.ResponseData;
import org.code.blocks.common.util.JsonUtils;

/**
 * @author darwindu
 * @date 2019/12/9
 **/
@Slf4j
public class ContractHandlerTemplate {

    public static <T> ResponseData<T> execute(ContractHandler<ResponseData<T>> contractHandler, String methodName, Object... paramObjects) {

        try {
            return contractHandler.execute();
        } catch (ValidateException e) {
            log.error(
                "[{}] RangesException, paramObjects:{}, errorcode:{}",
                methodName,
                JsonUtils.objToJson(paramObjects),
                e.getCode(),
                e
            );
            return new ResponseData<>(null, e.getCode(), e.getMsg());
        } catch (BaseException e) {
            log.error(
                "[{}] BaseException, paramObjects:{}, errorcode:{}",
                methodName,
                JsonUtils.objToJson(paramObjects),
                e.getCode(),
                e
            );
            return new ResponseData<>(null, e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.error(
                "[{}] Exception, paramObjects:{}, errorcode:{}",
                methodName,
                JsonUtils.objToJson(paramObjects),
                ErrorCode.UNKNOW_EXCEPTION.getCode(),
                e
            );
            return new ResponseData<>(null, ErrorCode.UNKNOW_EXCEPTION);
        }
    }
}
