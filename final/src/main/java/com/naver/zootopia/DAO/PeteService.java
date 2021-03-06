package com.naver.zootopia.DAO;

import java.util.List;
import java.util.Map;

import com.naver.zootopia.model.peteBean;
import com.naver.zootopia.model.peteEpil;
import com.naver.zootopia.model.peteReservation;
import com.naver.zootopia.model.petepilogue;

public interface PeteService {

	//���� �ñ�� �� �ۼ�
	public void insertPete(peteBean pt) throws Exception;

	//���� �ñ�� �� ����Ʈ
	public List<peteBean> getPeteList(Map<String, Object> m) throws Exception;

	//���� �ñ�� �� ����
	public int getListCount() throws Exception;

	//���� �ñ�� �˻� ��� �Խù� ��
	public int getListCount2(Map<String, Object> m) throws Exception;

	//���� �ñ�� �˻� ��� ����Ʈ
	public List<peteBean> getPeteList2(Map<String, Object> m) throws Exception;

	//��ȣ�� �������� �ڷ�� ���� ��������
	public peteBean getPeteCont(int num) throws Exception;

	//�� �����ϱ�
	public void editPete(peteBean petebean) throws Exception;

	//�� ����
	public void deletePete(int pete_board_num) throws Exception;

	

	/////////////////////////////////////////////////////////
	//peteReservation
	
	//�����ñ�� �����ϱ�
	public void peteReservation(peteReservation pr) throws Exception;
	//���� �ñ�� ����Ʈ
	public List<peteReservation> peteReserList(String join_id) throws Exception;

	/////////////////////////////////////////////////////////
	//petepilogue
	//�� ���� �ֱ�
	public void insertPetepil(petepilogue pe) throws Exception;
	//�� ���� ����Ʈ
	public List<petepilogue> petepilList(String pete_id) throws Exception;

	/////////////////////////////////////////////////////////
	//����� �ı�
	public void insertPeteEpil(peteEpil peteepil) throws Exception;

	//����� �ı� ����Ʈ
	public List<peteEpil> selectPeteEpil(Map<String, Object> m) throws Exception;

	//����� ���� �̱�
	public List<peteReservation> peteReserList2(String pete_id) throws Exception;
	//����� �ı� ����Ʈ

	//예약 아이디 중복 체크
	public int checkReserId(String id) throws Exception;

	public List<Integer> selectListEpil(String pete_id);

	public void deletePeteEpil(Map<String, Object> m);

	public int getEpilListCount(String pete_id);

	public List<peteEpil> selectEpilSum(String pete_id);

	public void insertFavorite(Map<String, Object> m);

	public void deleteFavorite(Map<String, Object> m);

	public int selectFavorite(Map<String, Object> m);

 }
