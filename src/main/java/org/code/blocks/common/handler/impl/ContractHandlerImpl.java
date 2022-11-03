package org.code.blocks.common.handler.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.code.blocks.common.handler.ContractHandler;

/**
 * 合约执行接口实现
 * @author darwindu
 * @date 2019/3/4
 */
public abstract class ContractHandlerImpl<T> implements ContractHandler {

	@Override
	public abstract T execute() throws InterruptedException, ExecutionException, TimeoutException;

}
