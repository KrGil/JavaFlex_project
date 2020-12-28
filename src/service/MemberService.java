package service;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.MemberDao;

public class MemberService {
	//싱글톤 패턴으로 만듦.
	private static MemberService instance;
	private MemberService(){} 
	public static MemberService getInstance(){
		if(instance == null){
			instance = new MemberService();
		}
		return instance;
	}
	
	private MemberDao memberDao = MemberDao.getInstance();

	//회원가입
	public int join(){
		System.out.println("================ 회원가입 ================");
		System.out.println("아이디 > ");
		String userId = ScanUtil.nextLine();
		System.out.println("비밀번호 > ");
		String password = ScanUtil.nextLine();
		System.out.println("이름 > ");
		String userName = ScanUtil.nextLine();
		//아이디 중복 확인 생략
		//비밀번호 확인 생략
		//정규표현식(유효성 검사) 생략
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("USER_ID", userId);
		param.put("PASSWORD", password);
		param.put("USER_NAME", userName);
		
		int result = memberDao.insertUser(param);
		if(0 < result){
			System.out.println("회원가입 성공");
		}else{
			System.out.println("회원가입 실패");
		}
		
		return View.HOME;
	}
	public int login() {
		System.out.println("================ 로그인 ================");
		System.out.println("아이디 > ");
		String userId = ScanUtil.nextLine();
		System.out.println("비밀번호 > ");
		String password = ScanUtil.nextLine();
		
		Map<String, Object> user = memberDao.selectUser(userId, password);
		
		if(user == null){//user.size() == 0;
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하였습니다.");
		}else{
			System.out.println("로그인 성공");
			Controller.loginMember = user;
			
			return View.MAIN_PAGE;
		}
		return View.LOGIN;
	}
}
