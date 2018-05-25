/**   
* @Title: DoctorsInterrogationController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午11:43:27 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmcc.medicalcare.controller.AppBaseController;
import com.cmcc.medicalcare.controller.model.Results;

/**
 * @ClassName: 问诊表
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午11:43:27
 * 
 */
@Controller
@RequestMapping("/app/doctorsInterrogation")
public class DoctorsInterrogationController extends AppBaseController {

	/*
	 * (非 Javadoc) <p>Title: insert</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#insert(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@Override
	public Results<String> insert(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.insert(request);
	}

	/*
	 * (非 Javadoc) <p>Title: delete</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#delete(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@Override
	public Results<String> delete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.delete(request);
	}

	/*
	 * (非 Javadoc) <p>Title: update</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#update(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@Override
	public Results<String> update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.update(request);
	}

	/*
	 * (非 Javadoc) <p>Title: select</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#select(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@Override
	public Results<String> select(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.select(request);
	}

}
