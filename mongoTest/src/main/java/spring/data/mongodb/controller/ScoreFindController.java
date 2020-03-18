package spring.data.mongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.data.mongodb.dto.ScoreDTO;
import spring.data.mongodb.service.ScoreMongoService;

@Controller
public class ScoreFindController {
	@Autowired
	ScoreMongoService service;
	
	@RequestMapping(value="/score/search.do", method=RequestMethod.GET)
	public String searchPage() {
		return "search"; //tiles사용 안하는 일반 뷰: jsp파일 이름과 경로
	}
	
	// 1. 조건 두 개를 붙여서 key로 보낸 뒤 나중에 split(,)으로 잘라도 됨 
	// 2. 매개변수 3개로 보내기
	@RequestMapping(value="/score/search.do", method=RequestMethod.POST)
	public ModelAndView search(String key, String opr, String value) {
		List<ScoreDTO> mongolist = service.findCriteria(key+","+opr, value);
		// search결과를 list.jsp에 출력하기(뷰 만들기 싫어서 재활용했으므로 redirect하면 안됨)
		return new ModelAndView("list", "mongolist", mongolist);
	}
	
	// update와 read를 위해 필요한 컨트롤러(action을 이용)
	@RequestMapping(value="/score/detail")
	// mongoDB는 key,value쌍으로 저장되므로(스키마가 없으므로 키가 정해져 있지 않음)
	// 같이 받아야 한다.
	public ModelAndView findById(String key, String value, String action) {
		ScoreDTO doc = service.findById(key, value);
		String view = "";
		if(action.equals("READ")) {
			view = "mongo_detail";
		}else {
			view = "mongo_update";
		}
		return new ModelAndView(view, "document", doc);
	}
}
