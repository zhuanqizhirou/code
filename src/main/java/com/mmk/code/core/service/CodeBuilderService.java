package com.mmk.code.core.service;

import com.mmk.code.core.condition.BuildData;
import com.mmk.code.core.condition.ProjectCondition;

public interface CodeBuilderService {

	/**
	 * 根据模型生成model代码
	 * 
	 * @param model
	 * @param fieldList
	 * @return
	 */
	String buildModel(BuildData data);

	/**
	 * 生成查询使用的Condition类
	 * 
	 * @param project
	 * @return
	 */
	String buildCondition(BuildData data);

	/**
	 * 根据模型生成Repository代码
	 * 
	 * @param model
	 * @param fieldList
	 * @return
	 */
	String buildRepository(BuildData data);

	String buildDao(BuildData data);

	String buildDaoImpl(BuildData data);

	/**
	 * 根据模型生成Service代码
	 * 
	 * @param model
	 * @param fieldList
	 * @return
	 */
	String buildService(BuildData data);

	String buildServiceImpl(BuildData data);

	/**
	 * 根据模型生成Controller代码
	 * 
	 * @param model
	 * @param fieldList
	 * @return
	 */
	String buildController(BuildData data);

	/**
	 * 构建所有代码
	 * 
	 * @param project
	 */
	String buildAllCode(BuildData data);

	/**
	 * 构建页面list代码
	 * 
	 * @param project
	 * @return 生成的代码
	 */
	String buildList(BuildData data);

	/**
	 * 构建form页面
	 * 
	 * @param project
	 * @return 生成的代码
	 */
	String buildForm(BuildData data);

	/**
	 * 构建details页面
	 * 
	 * @param project
	 * @return 生成的代码
	 */
	String buildDetails(BuildData data);

	/**
	 * 生成list页面上的js
	 * 
	 * @return
	 */
	String buildListJs(BuildData data);

	/**
	 * 生成form页面上的js
	 * 
	 * @return
	 */
	String buildFormJs(BuildData data);

	/**
	 * 生成details页面上的js
	 * 
	 * @return
	 */
	String buildDetailsJs(BuildData data);

	/**
	 * 给代码加点颜色
	 * 
	 * @param code
	 * @return
	 */
	String addIFrame(String code);

}
