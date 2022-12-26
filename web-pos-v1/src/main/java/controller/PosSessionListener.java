package controller;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

import beans.GroupBean;
import services.auth.Auth;

//@WebListener
public class PosSessionListener implements HttpSessionListener, HttpSessionIdListener, HttpSessionBindingListener, HttpSessionAttributeListener {

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		// 세션 생성시 호출
		System.out.println("[ SESSION ] Created Session Id : " + sessionEvent.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		// 세션 소멸시 호출
		System.out.println("[ SESSION ] Destroyed Session_id : " + sessionEvent.getSession().getId());
		System.out.println(sessionEvent.getSession().getAttribute("AccessInfo"));
	}

	@Override
	public void sessionIdChanged(HttpSessionEvent sessionEvent, String oldSessionId) {
		// 세션ID 변경시 호출
		System.out.println("[ SESSION ] Changed Old_Session_Id : " + oldSessionId + " / New Session Id : " + sessionEvent.getSession().getId());
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent sessionBindingEvent) {
		// 프라퍼티 추가시 호출
		System.out.println("[ SESSION ] Add Attribute Name : " + sessionBindingEvent.getName() + ", Value : " + sessionBindingEvent.getValue());
	}
	

	@Override
	public void attributeRemoved(HttpSessionBindingEvent sessionBindingEvent) {
		// 프라퍼티 삭제시 호출
		System.out.println("[ SESSION ] Remove Attribute Name : " + sessionBindingEvent.getName());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent sessionBindingEvent) {
		// 프라퍼티 값 변경시 호출
		System.out.println("[ SESSION ] Replace Attribute Name : " + sessionBindingEvent.getName() + ", value : " + sessionBindingEvent.getValue() + " --> " +  sessionBindingEvent.getSession().getAttribute(sessionBindingEvent.getName()));
	}

}
