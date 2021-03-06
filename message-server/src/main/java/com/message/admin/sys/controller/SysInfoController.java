package com.message.admin.sys.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.message.admin.sys.pojo.SysInfo;
import com.message.admin.sys.service.SysInfoService;
import com.monitor.api.ApiInfo;
import com.monitor.api.ApiParam;
import com.monitor.api.ApiRes;
import com.system.comm.model.Orderby;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameStringUtil;
import com.system.handle.model.ResponseCode;
import com.system.handle.model.ResponseFrame;

/**
 * sys_info的Controller
 * @author autoCode
 * @date 2017-12-04 16:59:38
 * @version V1.0.0
 */
@RestController
public class SysInfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysInfoController.class);

	@Autowired
	private SysInfoService sysInfoService;

	@RequestMapping(name = "系统信息-新增或修改", value = "/sysInfo/saveOrUpdate")
	@ApiInfo(params = {
			@ApiParam(name="系统编码", code="sysNo", value=""),
			@ApiParam(name="系统名称", code="name", value=""),
	}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value=""),
	})
	public ResponseFrame saveOrUpdate(SysInfo sysInfo) {
		try {
			ResponseFrame frame = sysInfoService.saveOrUpdate(sysInfo);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(name = "系统信息-获取详细信息", value = "/sysInfo/get")
	@ApiInfo(params = {
			@ApiParam(name="系统编码", code="sysNo", value=""),
	}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value=""),
			@ApiRes(name="系统编码", code="sysNo", pCode="body", value=""),
			@ApiRes(name="系统名称", code="name", pCode="body", value=""),
	})
	public ResponseFrame get(String sysNo) {
		try {
			ResponseFrame frame = new ResponseFrame();
			frame.setBody(sysInfoService.get(sysNo));
			frame.setCode(ResponseCode.SUCC.getCode());
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(name = "系统信息-分页查询信息", value = "/sysInfo/pageQuery")
	@ApiInfo(params = {
			@ApiParam(name="页面", code="page", value="1"),
			@ApiParam(name="每页大小", code="size", value="10"),
			@ApiParam(name="排序[{\"property\": \"createTime\", \"type\":\"desc\", \"order\":1}]", code="orderby", value="", required=false),
	}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value=""),
			@ApiRes(name="当前页码", code="page", pCode="body", clazz=Integer.class, value="1"),
			@ApiRes(name="每页大小", code="size", pCode="body", clazz=Integer.class, value="10"),
			@ApiRes(name="总页数", code="totalPage", pCode="body", clazz=Integer.class, value="5"),
			@ApiRes(name="总记录数", code="total", pCode="body", clazz=Integer.class, value="36"),
			@ApiRes(name="数据集合", code="rows", pCode="body", clazz=List.class, value=""),
			@ApiRes(name="系统编码", code="sysNo", pCode="rows", value=""),
	})
	public ResponseFrame pageQuery(SysInfo sysInfo, String orderby) {
		try {
			if(FrameStringUtil.isNotEmpty(orderby)) {
				List<Orderby> orderbys = FrameJsonUtil.toList(orderby, Orderby.class);
				sysInfo.setOrderbys(orderbys);
			}
			ResponseFrame frame = sysInfoService.pageQuery(sysInfo);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}

	@RequestMapping(name = "系统信息-根据主键删除", value = "/sysInfo/delete")
	@ApiInfo(params = {
			@ApiParam(name="sysNo", code="sysNo", value=""),
	}, response = {
			@ApiRes(name="响应码[0成功、-1失败]", code="code", clazz=String.class, value="0"),
			@ApiRes(name="响应消息", code="message", clazz=String.class, value="success"),
			@ApiRes(name="主体内容", code="body", clazz=Object.class, value=""),
	})
	public ResponseFrame delete(String sysNo) {
		try {
			ResponseFrame frame = sysInfoService.delete(sysNo);
			return frame;
		} catch (Exception e) {
			LOGGER.error("处理业务异常: " + e.getMessage(), e);
			return new ResponseFrame(ResponseCode.SERVER_ERROR);
		}
	}
}