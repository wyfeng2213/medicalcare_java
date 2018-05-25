package com.cmcc.medicalcare.controller;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmcc.medicalcare.utils.MakePicture;

public class ImageServlet extends HttpServlet 
{   
	@Override
	protected void service(HttpServletRequest req,HttpServletResponse res)
			throws ServletException, IOException {
		MakePicture mp=new MakePicture() ;
		String str=mp.drawPicture(60, 20,req,res ) ;
		res.getOutputStream().print(str) ;
	}
}
