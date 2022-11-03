package org.code.blocks.common.handler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 合约执行接口
 * @author darwindu
 * @date 2019/3/4
 */
public interface ContractHandler<T> {

	T execute() throws InterruptedException, ExecutionException, TimeoutException;
}
