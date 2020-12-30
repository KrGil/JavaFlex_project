package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import controller.Controller;


public class MemberDao {
	//싱글톤 패턴
	private static MemberDao instance;
	private MemberDao(){}
	public static MemberDao getInstance(){
		if(instance == null){
			instance = new MemberDao();
			
		}
		return instance;
	}
	
	private static JDBCUtil jdbc = JDBCUtil.getInstance();

	public int insertMember(Map<String, Object> param){
		String sql = "INSERT INTO MEMBER VALUES(?,SYSDATE,?,?,1)";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("MEM_ID"));
		p.add(param.get("MEM_PASSWORD"));
		p.add(param.get("CARD_CODE"));
		
		return jdbc.update(sql,p);
		
	}
	
	
	public int insertCard(Map<String, Object> param){
		String sql = "INSERT INTO CARD VALUES(?,?,?,?,?,?)";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("CARD_CODE"));
		p.add(param.get("CARD_COM"));
		p.add(param.get("CARD_NO"));
		p.add(param.get("MEM_ID"));
		p.add(param.get("CARD_CVC"));
		p.add(param.get("CARD_NAME"));
	
		return jdbc.update(sql,p);
	}
	
	public int insertType(Map<String, Object> param){
		String sql = "INSERT INTO WATCH VALUES(?,?,SYSDATE)";
		List<Object> p = new ArrayList<>();
		p.add(param.get("MOVIE_CODE"));
		p.add(param.get("ALIAS_CODE"));
		return jdbc.update(sql,p);
	}
	
	
//	public int insertMembership(Map<String, Object> param){
//	//public  int insertMembership(List<Object> ArrayList){
//		
//		String sql = "INSERT INTO PAY VALUES (?,?,?,SYSDATE)";
//		List<Object> p = new ArrayList<>();
//		p.add(param.get("PAY_CODE"));
//		p.add(param.get("MEM_ID"));
//		p.add(param.get("MEMBERSHIP"));
//		
//		return jdbc.update(sql,p);
//	}

	public int insertMembership(Map<String, Object> param){
		
		String sql = "INSERT INTO PAY VALUES (?,?,?,SYSDATE)";
		List<Object> p = new ArrayList<>();
		p.add(param.get("PAY_CODE"));
		p.add(param.get("MEM_ID"));
		p.add(param.get("MEMBERSHIP"));
		return  jdbc.update(sql,p);
	}
		//맴버쉽 등급을 회원가입할때 넣는거라 회원가입부터해야함 
		
	

	
	public List<Map<String, Object>> selectAlias(){
		Map<String,Object> member= Controller.loginMember;
		String sql = "SELECT * "
					+ " FROM ALIAS"
					+ " WHERE MEM_ID = ? ";
		List<Object> param = new ArrayList<>();
		param.add(member.get("MEM_ID"));
		
		return jdbc.selectList(sql, param);
	}


	//이건로그인화면 헷갈리면 안됨 
	public Map<String, Object> selectLogin(String memId, String password){
		String sql = "SELECT *"
				+ " FROM MEMBER "
				+ " WHERE mem_id = ?"
				+ " AND mem_password = ?";
		
						
		List<Object> param = new ArrayList<>();
		param.add(memId);
		param.add(password);
		
			return jdbc.selectOne(sql, param);
		}
	
	//별명추가
	public int insertAlias(Map<String, Object> param){
		
		String sql = "INSERT INTO alias VALUES(?,?,?)";
		List<Object> p = new ArrayList<>();
		p.add(param.get("ALIAS_CODE"));
		p.add(param.get("MEM_ID"));
		p.add(param.get("ALIAS_NAME"));
		
		return jdbc.update(sql,p);
	}
	
	//별명갯수 구하기
	public  Map<String, Object> selectMembershipNo(String loginId){
		String sql = "SELECT c.alias_no FROM "
				+ " (SELECT a.alias_no, b.pay_date FROM membership a, pay b "
				+ " WHERE a.membership = b.membership AND b.MEM_ID = ? "
				+ " ORDER BY b.PAY_DATE desc) c WHERE ROWNUM = 1";
		List<Object> param = new ArrayList<>();
		param.add(loginId);
		
		return jdbc.selectOne(sql, param);
	}
	
	
	
	}