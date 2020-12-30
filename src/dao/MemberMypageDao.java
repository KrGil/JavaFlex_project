package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;
import util.ScanUtil;

public class MemberMypageDao {
	
	//싱글톤
	private static MemberMypageDao instance;
	private MemberMypageDao(){}
	public static MemberMypageDao getInstance(){
		if(instance == null){
			instance = new MemberMypageDao();
		}
		return instance;
	}
	
	//초기화
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	//메서드
//	method selectInfo()
//	method insertPick_chart()
//	method selectPick_chart()
//	method deletePick_chart()
//	method insertWatch()
//	method selectWatch()
	
	//method selectInfo()
	//카드정보
	public Map<String, Object> selectInfoCard(String loginMemberId) {
		String sql = "SELECT CARD_NAME, CARD_COM, CARD_NO "
				+ " FROM CARD A, MEMBER B "
				+ " WHERE A.CARD_CODE = B.CARD_CODE AND B.MEM_ID = ?";
		List<Object> param = new ArrayList();
		param.add(loginMemberId);
		
		return jdbc.selectOne(sql, param);
	}
	//멤버십 정보 
	public Map<String, Object> selectInfoMembership(String loginMemberId){
		String sql = "SELECT * FROM "
				+ " (SELECT B.MEMBERSHIP "
				+ " FROM MEMBER A, PAY B "
				+ " WHERE A.MEM_ID = B.MEM_ID AND B.MEM_ID = ?"
				+ " ORDER BY B.PAY_DATE DESC) WHERE ROWNUM = 1";
		List<Object> param = new ArrayList();
		param.add(loginMemberId);
		return jdbc.selectOne(sql, param);
	}
	
	//봤던영화정보
	public List<Map<String, Object>> selectInfoWatchmovie(String loginMemberAlias){
		String sql = "SELECT * FROM WATCH WHERE ALIAS_CODE = ?";
		List<Object> param = new ArrayList();
		param.add(loginMemberAlias);
		return jdbc.selectList(sql, param);
	}
	
	//장르별통계
	public List<Map<String, Object>> selectInfoMovietype(String loginMemberAlias){
		String sql = "SELECT COUNT(a.movie_code) as TNUM, b.type_lgu as LGU, b.type_name as NAME"
				+ " FROM (SELECT SUBSTR(movie_code,1,3) as MTYPE, movie_code, alias_code"
				+ " FROM watch"
				+ " WHERE alias_code = ?) a, type b"
				+ " WHERE a.MTYPE (+) = b.type_lgu"
				+ " GROUP BY b.type_lgu, b.type_name"
				+ " ORDER BY TNUM DESC";
		List<Object> param = new ArrayList();
		param.add(loginMemberAlias);
		return jdbc.selectList(sql, param);
	}
	
	//별명변경
	public int changeAlias(String loginMemberAlias){
		System.out.println("▶  변경할 별명을 입력해주세요:");
		String alias = ScanUtil.nextLine();
		
		String sql = "UPDATE alias SET alias_name = ?"
				   + " WHERE alias_code = ?";
		List<Object> param = new ArrayList();
		param.add(alias);
		param.add(loginMemberAlias);
		
		Controller.loginAlias.put("ALIAS_NAME", alias);
		
		return jdbc.update(sql, param);
	}
	
	//비밀번호변경
	public int changePW(String loginMemberID){
		while(true){
			System.out.println("▶  변경할 비밀번호를 입력해주세요:");
			String pw = ScanUtil.nextLine();
			System.out.println("▶  한번 더 입력해 주세요:");
			String pwCheck = ScanUtil.nextLine();
			
			if(pw.equals(pwCheck)){
				String sql = "UPDATE member SET mem_password = ?"
						+ " WHERE mem_id = ?";
				List<Object> param = new ArrayList();
				param.add(pw);
				param.add(loginMemberID);
				return jdbc.update(sql, param);
			}else {
				System.out.println("입력한 비밀번호가 서로 다릅니다.");
				System.out.println("다시 변경하시겠습니까?(1.네, 2.아니요)");
				int input = ScanUtil.nextInt();
				switch(input){
				case 1 : break;
				case 2 : return 0;
				}
			}
		}
	}
	
	//마지막 결제 넘버 찾기
	public Map<String, Object> payNum(String loginMemberID){
		String sql = "SELECT pay_code FROM pay WHERE MEM_ID = ?";
		List<Object> param = new ArrayList();
		param.add(loginMemberID);
		return jdbc.selectOne(sql, param);
	}
	
	//멤버십변경
	public int changeMembership(String loginMemberID, String membership){
		String sql = "INSERT INTO pay VALUES (?, ?, ?, sysdate)";
		
		Map<String, Object> payNum = payNum(loginMemberID);
		String payCode = (String)payNum.get("PAY_CODE");
		
		int num = Integer.parseInt(payCode.substring(payCode.indexOf("P")+1));
		num ++;
		if(num < 10){
			payCode = loginMemberID + "P0" + num;
		}else{payCode = loginMemberID + "P" + num;}
		
		List<Object> param = new ArrayList();
		param.add(payCode);
		param.add(loginMemberID);
		param.add(membership);
		
		return jdbc.update(sql, param);
	}
	
	//회원탈퇴
	public int cancleMem(String loginMemberID){
		String sql = "UPDATE member SET mem_end = 0 WHERE mem_id = ?;";
		List<Object> param = new ArrayList();
		param.add(loginMemberID);
		return jdbc.update(sql, param);
	}
	
	//내 찜목록 찾기
	public List<Map<String, Object>> selectPick_chart(String loginMemberAlias){
		String sql = "SELECT ROWNUM, a.*"
					+ " FROM (SELECT * FROM pick_chart WHERE alias_code = ? order by movie_code) a"
					+ " WHERE ROWNUM <=10";
		List<Object> param = new ArrayList();
		param.add(loginMemberAlias);
		
		return jdbc.selectList(sql, param);
	}
	
	//찜목록과 동일한 영화정보 찾기
	public Map<String, Object> getPickChartInfo(String movieCode){
		String sql = "SELECT ROWNUM, a.*, b.type_name FROM (SELECT * FROM movie WHERE movie_code = ?) a, type b "
				   + "WHERE a.type_lgu = b.type_lgu and ROWNUM <=10";
		List<Object> param = new ArrayList();
		param.add(movieCode);
		
		return jdbc.selectOne(sql, param);
	}
	
	//찜목록 삭제
	public int deletePickChartInfo(String movieCode, String loginMemberAlias){
		String sql = "DELETE FROM pick_chart WHERE movie_code = ? AND alias_code = ?";
		List<Object> param = new ArrayList();
		param.add(movieCode);
		param.add(loginMemberAlias);
		return jdbc.update(sql, param);
	}
	
	
	//내가 시청한 영화 목록
	public List<Map<String, Object>> selectWatch(String loginMemberAlias){
		String sql = "SELECT ROWNUM, a.watch_date, b.*, c.type_name"
				+ " FROM(SELECT movie_code, watch_date FROM watch WHERE alias_code = ?) a, movie b, type c"
				+ " WHERE a.movie_code = b.movie_code"
				+ " AND b.type_lgu = c.type_lgu";
		List<Object> param = new ArrayList();
		param.add(loginMemberAlias);
		
		return jdbc.selectList(sql, param);
	}
	
	//봤던목록 삭제
	public int deleteWatchInfo(String movieCode, String loginMemberAlias){
		String sql = "DELETE FROM watch WHERE movie_code = ? AND alias_code = ?";
		List<Object> param = new ArrayList();
		param.add(movieCode);
		param.add(loginMemberAlias);
		return jdbc.update(sql, param);
		}
	
	//영화보기
	public Map<String, Object> whaching_movie (String movieCode){
		String sql = "SELECT * FROM movie WHERE movie_code = ?";
		List<Object> param = new ArrayList();
		param.add(movieCode);
		return jdbc.selectOne(sql, param);
	}
	
	//조회수 업데이트
	public int updateViewCnt(String movie_code){
		
		String sql = "UPDATE movie SET MOVIE_VIEWCNT = movie_viewcnt + 1"
				+ " WHERE movie_code = ?";
		List<Object> param = new ArrayList<>();
		param.add(movie_code);
		
		return jdbc.update(sql, param);
	}
	
}