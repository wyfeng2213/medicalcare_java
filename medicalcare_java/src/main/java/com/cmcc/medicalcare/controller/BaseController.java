package com.cmcc.medicalcare.controller;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.utils.UtilDatePropertyEditor;

/**BaseAction
 * 被继承的action，主要负责模板的跳转，
 * 字符编码的过滤，以及部分公共的action配置参数
 * 项目中其余action全部继承此action
 * @author wanggang
 *
 */
public class BaseController {
	/**
	 * LAYOUT_STANDARD
	 */
	public static final String LAYOUT_STANDARD = "standard";// 标准版页面布局
	/**
	 * LAYOUT_LOGIN
	 */
	public static final String LAYOUT_LOGIN = "login";// 标准版页面布局

	/**
	 * messageSource
	 */
	@Autowired
	protected MessageSource messageSource;
	

	
	
	/**
	 * WEB-INF/tiles-defs.xml配置文件中定义的Tiles模板名<br>
	 * 默认模板名为"default"。使用默认模板时则不需指定模板名。
	 * @author 许亮
	 * @since 2013-4-5 14:08:05
	 */
	public enum TilesTPL {
		//default,
		help
	}
	
	/**
	 * 获取国际化文本
	 * @param i18nKey
	 * @param locale
	 * @return
	 */
	protected String getI18NMessage(String i18nKey, Locale locale){
		return getI18NMessage(i18nKey, null, locale);
	}
	
	/**
	 * 获取国际化文本，支持参数传入
	 * @param i18nKey
	 * @param wildcards 例如："{0}", "{1,date}", "{2,time}"
	 * @param locale
	 * @return
	 */
	protected String getI18NMessage(String i18nKey, Object[] wildcards, Locale locale){
		return messageSource.getMessage(i18nKey, wildcards, i18nKey + " (" + locale + ")", locale);
	}
	
	/**
	 * 填充设置页面的标题
	 * @param model org.springframework.ui.Model
	 * @param pageTitle 页面标题文字
	 */
	protected void setPageTitle(Model model, String pageTitle){
		model.addAttribute("pageTitle", pageTitle);
	}
	
	/**
	 * 返回全局通用默认Tiles模板配置
	 * @param viewName
	 * @return
	 */
	protected String setTilesView(String viewName) {
		return setTilesView(viewName, "default");
	}
	
	/**
	 * 返回用户定制Tiles模板配置
	 * @param viewName
	 * @param tilesTemplate
	 * @return
	 */
	protected String setTilesView(String viewName, String tilesTemplate) {
		viewName = viewName.replaceAll("/", "\\\\");
		return tilesTemplate + "::" + viewName;
	}
	
	/**
	 * 返回用户定制的tiles模版配置
	 * @param viewName 页面名称
	 * @param tilesTemplate 模版
	 * @return
	 */
	protected String setTilesManageView(String viewName,String tilesTemplate){
		viewName = viewName.replaceAll("/", "\\\\");
		return "admin_" + tilesTemplate+ "_" + viewName;
	}
	
	
	/**
	 * 格式化时间
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new UtilDatePropertyEditor());
	}
	/**
	 * getPageSize
	 * @param pageSize
	 * @return
	 */
	protected int getPageSize(Long pageSize) {
		return pageSize==null?Pager.DEFAULT_PAGE_SIZE:pageSize.intValue();
	}
	/**
	 * getPageIndex
	 * @param pageIndex
	 * @return
	 */
	protected int getPageIndex(Long pageIndex) {
		return pageIndex==null?Pager.DEFAULT_PAGE_INDEX:pageIndex.intValue();
	}
}
