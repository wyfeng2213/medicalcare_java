package com.cmcc.medicalcare.utils;

import java.util.List;

import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.ISensitiveWordService;

/**
 * SensitiveWord
 * 
 * @author Administrator
 *
 */
public class SensitiveWordUtil {

	private volatile static SensitiveWordUtil sensitiveWordUtil;

	private static ISensitiveWordService sensitiveWordService;

	private static List<SensitiveWord> list;

	/**
	 * 构造函数
	 */
	private SensitiveWordUtil() {
	}

	/**
	 * 单例方法
	 * 
	 * @return
	 */
	public static SensitiveWordUtil getInstance() {
		if (sensitiveWordUtil == null) {
			synchronized (SensitiveWordUtil.class) {
				if (sensitiveWordUtil == null) {
					sensitiveWordUtil = new SensitiveWordUtil();
//					System.out.println("selectAllselectAllselectAllselectAllselectAll");
					sensitiveWordService = (ISensitiveWordService) SpringConfigTool.getBean("sensitiveWordService");
					list = sensitiveWordService.getList("selectAll", null);
				}
			}
		}
		return sensitiveWordUtil;
	}

	public SensitiveWord isSensitiveWord(String word) {
		if (word == null || "".equals(word)) return null;
		
		System.out.println("------------" + word);
		for (SensitiveWord sensitiveWord : list) {
//			System.out.println("敏感词：" + sensitiveWord.getText());
			if (word.contains(sensitiveWord.getText())) {
				return sensitiveWord;
			}
		}
		return null;
	}

	public List<SensitiveWord> getAllSensitiveWords() {
		return list;
	}
}
