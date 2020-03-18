package spring.data.mongodb.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.data.mongodb.dto.ScoreDTO;
import spring.data.mongodb.service.ScoreMongoService;

@Controller
public class ScoreController {
	@Autowired
	ScoreMongoService service;
	
	@RequestMapping("/score/pagingList.do")
	public ModelAndView showPagingMongolist(String pageNo) {
		ModelAndView mav = new ModelAndView();
		List<ScoreDTO> mongolist = service.findAll(Integer.parseInt(pageNo));
		mav.addObject("mongolist", mongolist);
		// servlet-context.xml에서 name="prefix" value="/WEB-INF/views/"
		// 						 name="suffix" value=".jsp" 
		// 설정했기 때문에 jsp파일 이름만 써도 됨 (tiles 사용 안하므로 tiles뷰 이름x)
		mav.setViewName("list");
		return mav;
	}
	
	@RequestMapping("/score/list.do")
	public ModelAndView showMongolist() {
		ModelAndView mav = new ModelAndView();
		List<ScoreDTO> mongolist = service.findAll();
		mav.addObject("mongolist", mongolist);
		mav.setViewName("list");
		return mav;
	}
	
	// JSON데이터를 만들지 않고 spring에서 사용했던 일반 방법으로 insert
	// 내부에서 JSON으로 자동으로 바꿔줌
	@RequestMapping(value= "/score/insert.do", method=RequestMethod.GET)
	public String insertPage() {
		return "mongo_insert";
	}
	
	@RequestMapping(value= "/score/insert.do", method=RequestMethod.POST)
	public String insert(ScoreDTO document) {
		service.insertDocument(document);
		return "redirect:/score/pagingList.do?pageNo=0";
	}
	
	@RequestMapping("/score/multiInsert.do")
	public String multiInsert() {
		List<ScoreDTO> doclist = new ArrayList<ScoreDTO>();
		ScoreDTO doc = null;
		for(int i=0; i<= 10; ++i) {
			doc = new ScoreDTO();
			doc.setId("multi"+i);
			doc.setDept("multi"+10+i);
			doc.setAddr("부산");
			doc.setJava(100);
			doclist.add(doc);
		}
		service.insertAllDocument(doclist);
		return "redirect:/score/pagingList.do?pageNo=0";
	}
	
	@RequestMapping(value="/score/update.do",method=RequestMethod.POST)
	public String update(ScoreDTO document) {
		service.update(document);
		return "redirect:/score/pagingList.do?pageNo=0";
	}
	
}
