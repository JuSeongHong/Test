package com.naver.zootopia.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.zootopia.model.peteBean;
import com.naver.zootopia.model.peteEpil;
import com.naver.zootopia.model.peteReservation;
import com.naver.zootopia.model.petepilogue;

@Repository
public class PeteDAOImpl {
	
	@Autowired	
	private SqlSessionTemplate sqlSession;
	//�����ñ�� �� �ۼ�
	public void insertPete(peteBean petebean) throws Exception{
		sqlSession.insert("pete.pete_insert",petebean);
	}
	//�� ����
	public int getListCount() throws Exception{
		int count = 0;
		count = ((Integer) sqlSession.selectOne("pete.pete_count")).intValue();
		return count;
	}
	//�۸��
	public List<peteBean> getPeteList(Map<String, Object> m) throws Exception{
		List<peteBean> list = 
				sqlSession.selectList("pete.pete_list", m);
		return list;
	}
	//�˻� �� ����
	public int getListCount2(Map<String, Object> m) throws Exception{
		int count = 0;
		count = ((Integer) sqlSession.selectOne("pete.petefind_cnt", m)).intValue();
		return count;
	}
	//�˻� �� ���
	public List<peteBean> getPeteList2(Map<String, Object> m) throws Exception{
		List<peteBean> list = new ArrayList<peteBean>();
		list = sqlSession.selectList("pete.find_list", m);
		return list;
	}
	//�� ������
	public peteBean getPeteCont(int num) throws Exception{
		return (peteBean) sqlSession.selectOne("pete.pete_cont", num); 
	}
	//�� ����
	public void editPete(peteBean petebean) {
		sqlSession.update("pete.pete_edit", petebean);
	}
	//�� ����
	public void deletePete(int pete_board_num) throws Exception{
		sqlSession.delete("pete.pete_del", pete_board_num);
	}
	
	
	/////////////////////////////////////////////////////////
	//peteReservation
	//�����ñ�� ����
	public void insertReser(peteReservation pr) {
		sqlSession.insert("pete.pete_reser", pr);
	}
	//�����ñ�� ����Ȯ��
	public List<peteReservation> peteReserList(String join_id) {
		return sqlSession.selectList("pete.pete_reser_list", join_id);
		
	}
	
	/////////////////////////////////////////////////////////
	//petepilogue
	//�� ���� ����
	public void insertPetepil(petepilogue pe) {
		sqlSession.insert("pete.pet_epil_write", pe);
	}
	//�� ���� ����Ʈ
	public List<petepilogue> petepilList(String pet_id) {
		return sqlSession.selectList("pete.pet_epil_list", pet_id);
	}
	/////////////////////////////////////////////////////////
	//����� �ı�
	public void insertPeteEpil(peteEpil peteepil) {
		sqlSession.insert("pete.pete_epil", peteepil);
	}
	//����� ���� �̱�
	public List<peteReservation> peteReserList2(String pete_id) {
		return sqlSession.selectList("pete.pete_reser_list2", pete_id);
	}
	/*//�����ϱ� ��ϰ�(�޷¶����� ����)
	public peteReservation peteReserDate(String pete_id) {
		return sqlSession.selectOne("pete.pete_date", pete_id);
	}*/
	
	//예약 아이디 중복 체크
	public int checkReserId(String id) {
		return sqlSession.selectOne("pete.check_id",id);
	}
	
	// 리스트용 후기가져오기 ㅋ
	public List<Integer> selectListEpil(String pete_id) {
		return sqlSession.selectList("pete.pete_list_epil", pete_id);
	}
	//후기 삭제
	public void deletePeteEpil(Map<String, Object> m) {
		sqlSession.delete("pete.deletePeteEpil", m);
	}
	//후기 갯수
	public int getEpilListCount(String pete_id) {
		/*int count = 0;
		count = ((Integer) sqlSession.selectOne(statement, parameter)).intValue();*/
		return sqlSession.selectOne("pete.getEpilListCount", pete_id);
	}
	//후기 리스트
	public List<peteEpil> selectPeteEpil(Map<String, Object> m) {
		return sqlSession.selectList("pete.pete_select", m);
	}
	public List<peteEpil> selectEpilSum(String pete_id) {
		return sqlSession.selectList("pete.selectEpilSum", pete_id);
	}
	
	//즐겨찾기 추가
	public void insertFavorite(Map<String, Object> m) {
		sqlSession.insert("pete.insertFavorite", m);	
	}
	//즐겨찾기 제거
	public void deleteFavorite(Map<String, Object> m) {
		sqlSession.delete("pete.deleteFavorite", m);
	}
	//즐겨찾기 체크
	public int selectFavorite(Map<String, Object> m) {
		return sqlSession.selectOne("pete.selectFavorite", m);
	}
}