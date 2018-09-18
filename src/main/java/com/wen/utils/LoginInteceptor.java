package com.wen.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInteceptor implements HandlerInterceptor{
     public List<String> excutMaperURL;
	
	/*
	 * (non-Javadoc)
	 * 执行视图解析之后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * 执行controller之后执行
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 请求之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		//请求路径
		String url=req.getRequestURI();
		int postfix=url.lastIndexOf(".");//返回指定字符在此字符串中最后一次出现处的索引
		if(postfix>-1){
			//静态资源文件
			String postFixStr=url.substring(postfix);
			if(excutMaperURL.contains(postFixStr)){
				return true;
			}else{
				return false;
			}
		}else{
			//请求
			if(!url.contains("/login")){
				HttpSession session=req.getSession();
				if(session.getAttribute("user")!=null){
					return true;
				}else{
					req.getRequestDispatcher("/views/login.jsp").forward(req, arg1);
					return false;
				}
			}
		}
		return true;
	}

	public List<String> getExcutMaperURL() {
		return excutMaperURL;
	}

	public void setExcutMaperURL(List<String> excutMaperURL) {
		this.excutMaperURL = excutMaperURL;
	}

}
