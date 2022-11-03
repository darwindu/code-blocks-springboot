package org.code.blocks.springboot.protocol.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author darwindu
 * @date 2020/8/19
 **/
@Data
public class FlowIdsRequest {

    @NotEmpty(message = "业务流水号不能为空")
    private String flowId;

    @NotNull(message = "业务流水申请ID不能为空")
    private Long flowApplyId;
}
