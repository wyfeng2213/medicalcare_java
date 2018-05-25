package com.cmcc.medicalcare.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.ISensitiveWordService;
import com.cmcc.medicalcare.utils.SpringConfigTool;

/**
 * 敏感词Service
 * @author Administrator
 *
 */
@Service(value="sensitiveWordService")
@Transactional
public class SensitiveWordServiceImpl extends GenericServiceImpl<SensitiveWord, Integer>
		implements ISensitiveWordService {
	/**
	 * 
	 */
//	public boolean isSensitiveWord(String word) {
//		List<SensitiveWord> list= this.getList("selectAll", null);
//		for(SensitiveWord sensitiveWord:list){
//			if(word.contains(sensitiveWord.getText())){
//				return true;
//			}
//		}
//		return false;
//	}

}
