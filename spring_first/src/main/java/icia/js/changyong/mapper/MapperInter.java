package icia.js.changyong.mapper;

import org.apache.ibatis.annotations.Select;

public interface MapperInter {
	//InterFace는 public만 사용
	
	@Select("SELECT SYSDATE FROM DUAL")
	public String getDate();
	public String getDate2();
	
}
