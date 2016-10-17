package com.mmk.code.core.service.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.mmk.code.common.CodeSaveTool;
import com.mmk.code.common.PropertyNameTools;
import com.mmk.code.common.TemplateTool;
import com.mmk.code.core.condition.BuildData;
import com.mmk.code.core.model.Field;
import com.mmk.code.core.service.CodeBuilderService;

@Service
public class CodeBuilderServiceImpl implements CodeBuilderService {

	@Override
	public String buildModel(BuildData data) {
		Map<String, Object> importList = modelImport(data.getFieldList());

		TemplateTool tool = new TemplateTool("templates/code/model.html");
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		tool.put("importList", importList);
		return tool.getString();
	}
	
	@Override
	public String buildCondition(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/code/condition.html");
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return tool.getString();
	}

	@Override
	public String buildRepository(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/code/repository.html");
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		String pk ="Long";
		pk = getPKType(data.getFieldList());
		tool.put("PK", pk);
		return tool.getString();
	}

	@Override
	public String buildDao(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/code/dao.html");
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("modelName", PropertyNameTools.firstLetterLowerCase(data.getModel().getModel()));
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return tool.getString();
	}

	@Override
	public String buildDaoImpl(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/code/daoImpl.html");
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		tool.put("modelName", PropertyNameTools.firstLetterLowerCase(data.getModel().getModel()));
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return tool.getString();
	}
	
	@Override
	public String buildService(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/code/service.html");
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		String pk ="Long";
		pk = getPKType(data.getFieldList());
		tool.put("PK", pk);
		return tool.getString();
	}
	
	@Override
	public String buildServiceImpl(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/code/serviceImpl.html");
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		String pk ="Long";
		pk = getPKType(data.getFieldList());
		tool.put("PK", pk);
		return tool.getString();
	}

	/**
	 * 获得主键类型
	 * @param list
	 * @return
	 */
	private String getPKType(List<Field> list) {
		String pk = "Long";
		for(Field field : list){
			if("Y".equals(field.getIsPk())){
				pk = field.getType();
				break;
			}
		}
		return pk;
	}
	
	/**
	 * 获得主键field并首字母大写返回
	 * @param list
	 * @return
	 */
	private String getPKFieldUp(List<Field> list) {
		String fieldUp = "Id";
		for(Field field : list){
			if("Y".equals(field.getIsPk())){
				fieldUp = PropertyNameTools.firstLetterUpperCase(field.getField());
				break;
			}
		}
		return fieldUp;
	}
	
	/**
	 * 获得主键字段
	 * @param fieldList
	 * @return
	 */
	private Object getPKField(List<Field> fieldList) {
		String fieldUp = "id";
		for(Field field : fieldList){
			if("Y".equals(field.getIsPk())){
				fieldUp = field.getField();
				break;
			}
		}
		return fieldUp;
	}

	@Override
	public String buildController(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/code/controller.html");
		tool.put("pkFieldUp", getPKFieldUp(data.getFieldList()));
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return tool.getString();
	}


	@Override
	public String buildAllCode(BuildData data) {
		String modelcode =buildModel(data);
		String condition = buildCondition(data);
		String repository = buildRepository(data);
		String dao = buildDao(data);
		String daoImpl = buildDaoImpl(data);
		String service = buildService(data);
		String serviceImpl = buildServiceImpl(data);
		String controller = buildController(data);
		
		//页面部分生成
		
		String list = buildList(data);
		String form = buildForm(data);
		String details = buildDetails(data);
		
		//生成js部分
		String listjs = buildListJs(data);
		String formjs = buildFormJs(data);
		String detailsjs = buildDetailsJs(data);
		
		//保存代码到磁盘
		try {
			CodeSaveTool.saveCode(data.getProject().getPath(), data.getModel().getPackageName()+".model",data.getModel().getModel()+".java", modelcode);
			CodeSaveTool.saveCode(data.getProject().getPath(), data.getModel().getPackageName()+".condition",data.getModel().getModel()+"Condition.java", condition);
			CodeSaveTool.saveCode(data.getProject().getPath(), data.getModel().getPackageName()+".dao",data.getModel().getModel()+"Repository.java", repository);
			CodeSaveTool.saveCode(data.getProject().getPath(), data.getModel().getPackageName()+".dao",data.getModel().getModel()+"Dao.java", dao);
			CodeSaveTool.saveCode(data.getProject().getPath(), data.getModel().getPackageName()+".dao.impl",data.getModel().getModel()+"DaoImpl.java", daoImpl);
			CodeSaveTool.saveCode(data.getProject().getPath(), data.getModel().getPackageName()+".service",data.getModel().getModel()+"Service.java", service);
			CodeSaveTool.saveCode(data.getProject().getPath(), data.getModel().getPackageName()+".service.impl",data.getModel().getModel()+"ServiceImpl.java", serviceImpl);
			CodeSaveTool.saveCode(data.getProject().getPath(), data.getModel().getPackageName()+".web",data.getModel().getModel()+"Controller.java", controller);
			
//			//页面部分
//			CodeSaveTool.saveTemplates(project.getPath(), project.getModel().getModelL(),"list.html", list);
//			CodeSaveTool.saveTemplates(project.getPath(), project.getModel().getModelL(),"form.html", form);
//			CodeSaveTool.saveTemplates(project.getPath(), project.getModel().getModelL(),"details.html", details);
//			//js部分
//			CodeSaveTool.saveJs(project.getPath(), project.getModel().getModelL(),"list.js", listjs);
//			CodeSaveTool.saveJs(project.getPath(), project.getModel().getModelL(),"form.js", formjs);
//			CodeSaveTool.saveJs(project.getPath(), project.getModel().getModelL(),"details.js", detailsjs);
			
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "代码生成成功";
	}

	private Map<String, Object> modelImport(List<Field> fieldList) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		for (Field field : fieldList) {
			if ("String".equals(field.getType()) || "Long".equals(field.getType()) || "Integer".equals(field.getType())
					|| "Float".equals(field.getType()) || "Double".equals(field.getType())) 
			{
				continue;
			}
			if("Date".equals(field.getType())){
				if("Date".equals(field.getType())){
					result.put("Date","import java.util.Date;");
					result.put("Temporal","import javax.persistence.Temporal;");
					result.put("TemporalType","import javax.persistence.TemporalType;");
				}
			}
			
		}
		return result;
	}

	@Override
	public String addIFrame(String code) {
		StringBuilder html = new StringBuilder();
		html.append("<iframe src='/code/preview/"+code+"'></iframe>");
		return html.toString();
	}

	@Override
	public String buildList(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/page/list.html");
		tool.put("pkFieldUp", getPKFieldUp(data.getFieldList()));
		tool.put("pkField", getPKField(data.getFieldList()));
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return tool.getString();
	}

	

	@Override
	public String buildForm(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/page/form.html");
		tool.put("pkFieldUp", getPKFieldUp(data.getFieldList()));
		tool.put("pkField", getPKField(data.getFieldList()));
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return tool.getString();
	}

	@Override
	public String buildDetails(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/page/details.html");
		tool.put("pkFieldUp", getPKFieldUp(data.getFieldList()));
		tool.put("pkField", getPKField(data.getFieldList()));
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return tool.getString();
	}

	@Override
	public String buildListJs(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/js/list.html");
		tool.put("pkFieldUp", getPKFieldUp(data.getFieldList()));
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return tool.getString();
	}
	@Override
	public String buildFormJs(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/js/form.html");
		tool.put("pkFieldUp", getPKFieldUp(data.getFieldList()));
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return tool.getString();
	}
	@Override
	public String buildDetailsJs(BuildData data) {
		TemplateTool tool = new TemplateTool("templates/js/details.html");
		tool.put("pkFieldUp", getPKFieldUp(data.getFieldList()));
		tool.put("project", data.getProject());
		tool.put("model", data.getModel());
		tool.put("fieldList", data.getFieldList());
		tool.put("date",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		return tool.getString();
	}

}
