package kr.multi.bigdataShop.product.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductCommentController {
	@Autowired
	ProductCommentService service;
	
	//댓글 등록
	@RequestMapping(value = "/comment/write.do", method=RequestMethod.POST)
	public String commentinsert(ProductCommentDTO comment) {
		// commentDTO 자동으로 대입
		service.insert(comment);
		return "redirect:/product/read.do?prd_no="+comment.getPrd_no();
	}
	
	/*@RequestMapping(value = "/comment/analyze.do")
	public String analyze()*/
}


