package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
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
	public int join1(){
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
	public int login1() {
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
	public int join(){
		System.out.println("===========회원가입==========");
		System.out.println("아이디>");
		String memId = ScanUtil.nextLine();
		System.out.println("비밀번호>");
		String password = ScanUtil.nextLine();
		System.out.println("맴버쉽 등록>");
		String memship = ScanUtil.nextLine();
		System.out.println("이름>");
		String memName = ScanUtil.nextLine();
		System.out.println("카드>");
		String card= ScanUtil.nextLine();
		System.out.println("별명>");
		String alias = ScanUtil.nextLine();
		System.out.println("선호 장르>");
		String likeType = ScanUtil.nextLine();
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("MEM_ID",memId);
		param.put("PASSWORD",password);
		param.put("MEM_NAME",memName);
		param.put("MEM_SHIP", memship);
		param.put("CARD",card);
		param.put("ALIAS",alias);
		param.put("LIKE_TYPE",likeType);
	
		int result = memberDao.insertMember(param);
		if(0 <result){
			System.out.println("회원가입 성공");
		}else{
			System.out.println("회원가입 실패");
		}
		return View.HOME;
	}
	//로그인 화면
	public int login(){
		System.out.println("==========로그인=============");
		System.out.println("아이디>");
		String memberId = ScanUtil.nextLine();
		System.out.println("비밀번호>");
		String password = ScanUtil.nextLine();
		
		Map<String,Object> member = memberDao.selectLogin(memberId, password); //memName, card, alias, likeType);
		
		if(member == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("로그인성공");
			Controller.loginMember = member;
			return View.ALIAS_LIST; //로그인 후에 열리는 페이지 뭐더라 ALIAS_LIST;
		}
		return View.LOGIN;
	}
	public int alias(){
		//List<Map<String,Object>> memberService = MemberDao.
		System.out.println("=============================");
		List<Map<String, Object>> alias = memberDao.selectAlias();
		
		for(int i =0; i<alias.size(); i++){
			System.out.print(i+1+". "+alias.get(i).get("ALIAS_NAME")+"\t");
		}
		System.out.println();
		System.out.println("=============================");
		int input = ScanUtil.nextInt();
		Controller.loginAlias = alias.get(input-1);
		if (Controller.loginAlias == null){
			System.out.println("잘못입력하였습니다.");
		}else{
			System.out.println(Controller.loginAlias.get("ALIAS_NAME")+"으로 로그인 합니다.");
			return View.MAIN_PAGE;
		}
			return View.ALIAS_LIST;
		}
	}
	
	
	
	
	
