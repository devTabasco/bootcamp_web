package icia.js.changyong.services.registration;

/* Registration Class
 * - 대표자등록
 * - 상점등록
 * - 직원등록
 * - 분류등록 
 * */
public interface Registration {
	
	//ActionBean
	public void backController();
	
	/* 회원가입 첫 페이지 이동 */
	//ActionBean
	public void moveGroup();
	
	/* 그룹네임 중복 체크 >> 임시 저장 */
	//ActionBean
	public void groupNameDuplicateCheckCtl();
	
	/* 그룹네임 삭제 >> 임시 저장 */
	//ActionBean
	public void delGroupNameCtl();
	
	/* 회원가입 :: 그룹코드 생성 */
	//ActionBean
	public void regGroupCtl();
	
	/* 회원가입2 :: 매장코드 생성 */
	//ActionBean
	public void regStoreCtl();
	
	//String
	public void makeHTMLSelectBox();
	
	//String
	public void makeMessage();
	
	/* 회원가입4 :: 직원코드 생성 */
	//ActionBean
	public void regEmpCtl();
	
	/* 회원가입3 :: 분류코드 생성 */
	//ActionBean
	public void regCategoriesCtl();
	
	/* Convert to Boolean */
	//boolean
	public boolean convertToBoolean();
	
	/* Referer Page 추출 */
	//String
	public String getRefererPage();
	
}
