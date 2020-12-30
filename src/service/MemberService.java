package service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.MemberDao;

public class MemberService {
	
	//private static final String insertMembership = null;
	//싱글톤 패턴
	private static MemberService instance;
	private MemberService(){}
	public static MemberService getInstance(){
		if(instance == null){
			instance = new MemberService();
		}
		return instance;
	}
	
	//초기화 및 전역변수
	private MemberDao memberDao = MemberDao.getInstance();
	private String vMemberId;
	private String vPassword;
	private String vMembership;
	private String vCardCode;
	private String vCardNum;
	private int vCardcCvc;
	private String vCardName;
	private String vCardCom;
	private String vPayCode;
	private int vLikeTypeNum;
	private String vLikeType;
	private String vAliasName1;
	private String vAliasId1;
	

	
	

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
	//별칭 
	public int aliasList(){
		Map<String, Object> selectMembershipNo = memberDao.selectMembershipNo(Controller.loginMember.get("MEM_ID").toString());
		int MembershipNo = ((BigDecimal)selectMembershipNo.get("ALIAS_NO")).intValue();
		//뷰
		System.out.println("=============================");
		List<Map<String, Object>> alias = memberDao.selectAlias();

		for(int i =0; i<alias.size(); i++){
			System.out.print(i+1+". "+alias.get(i).get("ALIAS_NAME")+"     ");
		}
		System.out.println();
		System.out.println("=============================");
		
		System.out.println("1.별명 선택\t2.별명 추가");
		int input = ScanUtil.nextInt();
		if(input == 1){
			System.out.println("별명을 선택해주세요>");
			input = ScanUtil.nextInt();
			Controller.loginAlias = alias.get(input-1);
			if (Controller.loginAlias == null){
				System.out.println("잘못입력하였습니다.");
				
			}else{
				System.out.println(Controller.loginAlias.get("ALIAS_NAME")+"으로 로그인 합니다.");
				return View.MAIN_PAGE; 
			}
		}else{
			 if(MembershipNo > alias.size()){
				 System.out.println("추가할 별명 이름을 입력해 주세요");
				 String aliasName = ScanUtil.nextLine();
				 String aliasId = Controller.loginMember.get("MEM_ID").toString() + (alias.size() + 1);
				 
				 Map<String,Object> param = new HashMap<String,Object>();
				 param.put("ALIAS_CODE", aliasId);
				 param.put("MEM_ID", Controller.loginMember.get("MEM_ID").toString());
				 param.put("ALIAS_NAME", aliasName);
				 
				 int result = memberDao.insertAlias(param);
				 
				 if(result == 1){
					 System.out.println("추가가 완료되었습니다.");
				 }
				 return View.ALIAS_LIST; 
			 }else{ System.out.println("더이상 별명을 추가할 수 없습니다."); }
			 	return View.ALIAS_LIST; 
			 
		}
		return View.MAIN_PAGE;
	}
	
	
	
	//--------------------------------------------------------------회원가입
	
	//테이블 업데이트
	public int insert() {
		System.out.println("사용할 별명을 입력해주세요.>");
		vAliasName1 = ScanUtil.nextLine();
		vAliasId1 = vMemberId +"1";
		
		
		
		memberIn(); //------------------------------------------- 1
		
		cardIn(); //--------------------------------------------- 2
		
		payIn(); //---------------------------------------------- 3
		
		aliasIn(); //-------------------------------------------- 4
		
		liketypeIn(); //----------------------------------------- 5
		
		System.out.println("회원가입이 완료되었습니다!");
		
		return View.HOME;
	}
	
	
	
	//회원가입화면
	public int join(){
		System.out.println("===========회원가입==========");
		System.out.println("아이디>");
		vMemberId = ScanUtil.nextLine();
		System.out.println("비밀번호>");
		vPassword = ScanUtil.nextLine();
		
		return View.INSERT_MEMBERSHIP_PAGE;
	}
	
	//맴버십
	public int membership(){
		System.out.println("맴버십 페이지입니다. 사용하실 맴버십을 선택해주세요");
		System.out.println("1.BASIC\t2.STANDARD\t3.PREMIUM");
		int input = ScanUtil.nextInt();
		
		switch(input){
		case 1: System.out.println("BASIC 를 선택하셨습니다.");
				vMembership = "BASIC";
				break;
		case 2: System.out.println("STANDARD 를 선택하셨습니다.");
				vMembership = "STANDARD";
				break;
		case 3: System.out.println("PREMIUM 를 선택하셨습니다.");
				vMembership = "PREMIUM";
				break;		
			}
			
	return View.INSERT_CARD_PAGE;
		
	}
	
	//카드페이지x 카드 정보만 들고 가기.
	public int card(){
		System.out.println("결제 페이지입니다. 등록하실 카드 12자리를 입력해주세요 (-제외)");
		vCardNum = ScanUtil.nextLine();
		if(vCardNum.length() == 12){
			System.out.println("카드입력에 성공하셨습니다.");
		}else{
			System.out.println("카드번호를 잘못 입력하셨습니다.");
		}

		System.out.println("카드 뒷면의 cvc 번호를 입력해주세요");
		vCardcCvc = ScanUtil.nextInt();
		System.out.println("등록하실 카드에 정할 명칭,별칭을 입력해주세요 ");
		vCardName = ScanUtil.nextLine();
		System.out.println("카드사를 선택해주세요");
		System.out.println("1.삼성\t2.하나 \t3.국민\t4.농협");
		int input = ScanUtil.nextInt();
		
		switch(input){
		case 1:
			vCardCom ="SAMSUNG"; break;
		case 2:
			vCardCom ="HANA"; break;
		case 3:
			vCardCom = "KB"; break;
		case 4:
			vCardCom = "NH"; break;
		}
		
		//카드코드 구하기
		String cardcom = vCardCom.substring(0,2);
		String cardno = vCardNum.substring(0,4);
		String cardcode = cardcom+cardno+vCardcCvc;
		vCardCode = cardcode;

		return View.INSERT_TYPE;
	}

	
	//선호장르
	public int likeType(){
		System.out.println("선호하는 영화 장르를 선택하세요");
		System.out.println("1.로맨스/멜로\t2.코미디\t3.SF/판타지\t4.애니메이션\t5.공포/스릴러\t6.다큐");
		int input = ScanUtil.nextInt();
		
		switch(input){
		case 1:
			System.out.println("로맨스 / 멜로를 선택하셨습니다.");
			vLikeTypeNum = 110;
			vLikeType = "로맨스";
			break;
			
		case 2:
			System.out.println("코미디를 선택하셨습니다.");
			vLikeTypeNum = 120;
			vLikeType = "코미디";
			break;
		
		case 3:
			System.out.println(" SF/판타지를 선택하셨습니다.");
			vLikeTypeNum = 130;
			vLikeType = "SF/판타지";
			break;
		
		case 4:
			System.out.println(" 애니메이션을  선택하셨습니다.");
			vLikeTypeNum = 140;
			vLikeType = "애니메이션";
			break;
		
		case 5:
			System.out.println("공포/스릴러 를 선택하셨습니다.");
			vLikeTypeNum = 150;
			vLikeType = "공포/스릴러";
			break;
			
		case 6:
			System.out.println("다큐를 선택하셨습니다.");
			vLikeTypeNum = 160;
			vLikeType = "다큐";
			break;	
		}
		
		return View.INSERT_DATA;	
	}
	
	//결제내역 업데이트(멤버십)
	public void payIn(){
		vPayCode = vMemberId + "P01";
		Map<String, Object> param= new HashMap<>();
		param.put("PAY_CODE", vPayCode);
		param.put("MEM_ID", vMemberId);
		param.put("MEMBERSHIP", vMembership);
		
		int result = memberDao.insertMembership(param);
	}
	
	//멤버테이블 업데이트
	public void memberIn(){
		Map<String, Object> param= new HashMap<>();
		param.put("MEM_ID", vMemberId);
		param.put("MEM_PASSWORD", vPassword);
		param.put("CARD_CODE", vCardCode);
		
		int result = memberDao.insertMember(param);
	}
	
	//카드테이블 업데이트
	public void cardIn(){
		
		
		Map<String, Object> param = new HashMap<>();
		param.put("CARD_CODE",vCardCode);
		param.put("CARD_COM",vCardCom);
		param.put("CARD_NO",vCardNum);
		param.put("MEM_ID",vMemberId);
		param.put("CARD_CVC",vCardcCvc);
		param.put("CARD_NAME",vCardName);
		
		int result = memberDao.insertCard(param);
	}
	
	//별명테이블 업데이트
	public void aliasIn(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ALIAS_CODE",vAliasId1);
		param.put("MEM_ID",vMemberId);
		param.put("ALIAS_NAME",vAliasName1);
	
		
		int result = memberDao.insertAlias(param);
	}
	
	
	//선호장르테이블 업데이트
	public void liketypeIn(){
		String liketype = vLikeTypeNum + "00";
		vAliasId1 = vMemberId +"1";
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("MOVIE_CODE",liketype);
		param.put("ALIAS_CODE",vAliasId1);
		
		int result = memberDao.insertType(param);
	}
	
	
	
	
	
	
	
	
	
	
	
}