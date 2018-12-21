package com.naver.zootopia.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.naver.zootopia.DAO.HospitalService;
import com.naver.zootopia.model.HospitalBean;

@Controller
public class HospitalAction {

	@Resource(name = "hospitalService")
	private HospitalService hospitalService;

	

	// 진료기록부 게시판 입력폼
	@RequestMapping(value = "/hospital_write.nhn")
	public String hospital_write() {
		return "member_mypage/hospital_write";
	}
	
	// 진료기록부 게시판 입력완성
	@RequestMapping(value="/hospital_write_ok.nhn", method=RequestMethod.POST)
	public String hospital_write_ok(HospitalBean hospitalbean) throws Exception{
		this.hospitalService.insertHospital(hospitalbean);
		return "redirect:hospital_list.nhn";
	}

	// 진료기록부 게시판 리스트
	@RequestMapping(value = "/hospital_list.nhn")
	public ModelAndView hospital_list(HttpServletRequest request) throws Exception {
		List<HospitalBean> hospitallist = new ArrayList<HospitalBean>();

		int limit = 10;// 목록 보기 초기값
		int listcount = hospitalService.getListCount();// 총 리스트 수를 받아옴
		int page = 1;
		HttpSession session = request.getSession();
		System.out.println("limit :" + request.getParameter("limit"));

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		if (session.getAttribute("limit") != null) {
			limit = (Integer) session.getAttribute("limit");
		}

		// 변경된 limit가 있는지 체크
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
			session.setAttribute("limit", limit);
		}
		System.out.println("limit " + limit);
		System.out.println("page " + page);

		System.out.println("총 게시물 수 : " + hospitalService.getListCount());

		int maxpage = (listcount + limit - 1) / limit;

		int startpage = ((page - 1) / 10) * 10 + 1;

		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		if (endpage < page)
			page = endpage;

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", page);
		m.put("limit", limit);
		hospitallist = hospitalService.getHospitalList(m);

		ModelAndView hospitalListM = new ModelAndView("member_mypage/hospital_list");
		hospitalListM.addObject("page", page);
		hospitalListM.addObject("limit", limit);
		hospitalListM.addObject("maxpage", maxpage);
		hospitalListM.addObject("startpage", startpage);
		hospitalListM.addObject("endpage", endpage);
		hospitalListM.addObject("listcount", listcount);
		hospitalListM.addObject("hospitallist", hospitallist);

		return hospitalListM;
	}

	// 진료기록부 게시판 내용보기, 삭제폼, 수정폼, 답변달기폼
	@RequestMapping(value = "/hospital_cont.nhn")
	public ModelAndView hospital_cont(@RequestParam("hospital_num") int hospital_num,
			@RequestParam("page") int page, @RequestParam("state") String state) throws Exception {
		HospitalBean hospital = hospitalService.getHospitalCont(hospital_num);

		ModelAndView contM = new ModelAndView();
		contM.addObject("hospital", hospital);
		contM.addObject("page", page);

		if (state.equals("cont")) {// 내용보기일때
			contM.setViewName("member_mypage/hospital_cont");// 내용보기 페이지 설정
			
		} else if (state.equals("edit")) {// 수정폼
			contM.setViewName("member_mypage/hospital_edit");
		} else if (state.equals("del")) {// 삭제폼
			contM.setViewName("member_mypage/hospital_del");
		}

		return contM;
	}
	
	//진료기록부 게시판 수정
	@RequestMapping(value="/hospital_edit_ok.nhn",method=RequestMethod.POST)
	public ModelAndView hospital_edit_ok(HospitalBean hospitalbean,
			@RequestParam int page,
			HttpServletResponse response) throws Exception{
		System.out.println(page);
		
		response.setContentType("text/html;charset=UTF-8");
		int hospital_num = hospitalbean.getHospital_board_num();
		System.out.println("게시글 번호 : "+hospital_num);
		
			response.sendRedirect("hospital_cont.nhn?state=cont&page="+page+"&hospital_num="+hospital_num);
		return null;
	}
	
	
	//진료기록부 게시판 삭제
	@RequestMapping(value="/hospital_del_ok.nhn", method=RequestMethod.POST)
	public ModelAndView hospital_delete(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("hospital_num") int hospital_num,
			@RequestParam("page") int page) throws Exception{
		
		System.out.println(page);
		
		response.setContentType("text/html;charset=UTF-8");
		hospitalService.deleteHospital(hospital_num);
		response.sendRedirect("hospital_list.nhn?page="+page);	
		
		return null;
	}
	
	//진료기록부 검색 목록
	@RequestMapping(value="/hospital_find_ok.nhn",method=RequestMethod.GET)
	public ModelAndView hospital_find_ok(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="page",defaultValue="1") int page,
			@RequestParam("find_name") String find_name,
			@RequestParam("find_field") String find_field) throws Exception{
		
				int limit = 10;
				
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("page", page);
				m.put("find_field", find_field);
				m.put("find_name", "%"+find_name+"%");
				int listcount = this.hospitalService.getListCount3(m);
				
				//총 페이지 수
				int maxpage = (listcount + limit - 1) / limit;

				int startpage = ((page - 1) / 10) * 10 + 1;

				int endpage = startpage + 10 - 1;

				if (endpage > maxpage)
					endpage = maxpage;

				if (endpage < page)
					page = endpage;

				List<HospitalBean> hospitallist = hospitalService.getHospitalList3(m);
				ModelAndView model = new ModelAndView("member_mypage/hospital_find");
				model.addObject("find_name", find_name);
				model.addObject("find_field", find_field);
				model.addObject("page", page);
				model.addObject("maxpage", maxpage);
				model.addObject("startpage", startpage);
				model.addObject("endpage", endpage);
				model.addObject("listcount", listcount);
				model.addObject("hospitallist", hospitallist);

				return model; 
				
			}
	

}
