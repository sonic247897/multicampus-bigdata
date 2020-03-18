package spring.data.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import spring.data.mongodb.dto.ScoreDTO;
import spring.data.mongodb.dto.ScoreRepository;

@Repository
public class scoreMongoDAOImpl implements ScoreMongoDAO {
	// 페이징처리를 편하게 하기 위해 추가
	// CLRUD를 위한 기본 기능도 제공
	// (spring-data의 common라이브러리에서 지원하는 기능)
	// import org.springframework.stereotype.Repository;
	@Autowired
	ScoreRepository scoreRepository;
	
	// mongoDB를 연동하기 위한 기능을 제공
	// (spring-data-mongodb라이브러리에서 제공)
	// import org.springframework.[data.mongodb].core.MongoTemplate;
	@Autowired
	MongoTemplate mongoTemplate;
	
	// search메소드
	@Override
	public List<ScoreDTO> findCriteria(String key, String value) {
		System.out.println("조회된 key: "+key);
		String[] data = key.split(",");
		// addCriteria는 query에 Criteria를 추가해서 또 query를 리턴하므로
		// 끝에 addCriteria를 계속 붙일 수 있다.
		// where는 자신의 Criteria에 key정보를 전달하는 메소드
		/*
		 * Query클래스의 addCriteria메소드와
		 * Criteria클래스의 whre메소드의 활용
		 * Query query =new Query();
		 * query.addCriteria(Criteria.where(data[0]).is(value)); */
		Criteria criteria = new Criteria();
		//조건을 계속 추가하면 된다. (입력부분을 DTO로 만들어서 전달하면 더 편하다)
		criteria.andOperator(
				Criteria.where(data[0]).is(value),
				Criteria.where("addr").is("인천"));
		Query query = new Query(criteria);
		List<ScoreDTO> mongolist = mongoTemplate.find(query, 
										ScoreDTO.class, "score");
		return mongolist;
	}
	
	// mongoDB가 json으로 모든 작업을 처리하므로 key, value로 조건을 정의
	// spring-data-mongodb에서 이러한 조건을 처리하는 객체를 만들어서 제공
	// Criteria - 조건에 대한 처리를 하는 클래스(정확하게 일치하는 경우)
	@Override
	public ScoreDTO findById(String key, String value) {
		//1. 조건을 객체로 작성
		Criteria criteria = new Criteria(key);
		//2. 조건에 대한 설정 - value를 셋팅
		criteria.is(value); //정확하게 일치
		//3. Criteria를 이용해서 Query객체를 작성 =>mongoDB의 query문으로 바꿔준다
		// 			(mongodb.core의 객체)
		// 	- mongodb 내부에서 인식할 조건을 정의하는 객체
		Query query = new Query(criteria);
		//4. MongoTemplate클래스의 메소드를 호출하며 Query객체를 매개변수로 전달
		ScoreDTO document = mongoTemplate.findOne(query, 
									ScoreDTO.class, "score");
		return document;
	}

	@Override
	public void insertDocument(ScoreDTO doc) {
		mongoTemplate.insert(doc);
	}

	@Override
	public void insertAllDocument(List<ScoreDTO> docs) {
		mongoTemplate.insertAll(docs);
	}

	@Override
	public void update(ScoreDTO document) {
		// 수정할 document에 조건을 적용 - rdbms의 where절
		Criteria criteria = new Criteria("id"); //매개변수로 받지 않고 직접 입력
		criteria.is(document.getId());
		Query query = new Query(criteria);
		
		// 수정할 데이터를 셋팅 - rdbms의 set절
		// Update객체에 수정할 데이터 셋팅
		Update update = new Update();
		update.set("addr", document.getAddr());
		update.set("dept", document.getDept());
		
		// mongoTemplate의 수정기능 호출
		// updateFirst: 처음 만난 document 수정
		// updateMulti: 조건 만족하는 document 전부 수정
		mongoTemplate.updateMulti(query, update, "score");
	}

	@Override
	public List<ScoreDTO> findAll() {
		// Iterable이 반환되므로 캐스팅만 해주면 된다.
		List<ScoreDTO> mongolist = (List<ScoreDTO>)scoreRepository.findAll();
		System.out.println();
		return mongolist;
	}

	@Override
	public List<ScoreDTO> findAll(int pageNo) {
		// PagingAndSortingRepository에서 제공하는 findAll을 호출하면
		// spring-data내부에서 페이징 처리가 된 객체를 전달받을 수 있다.
		// 페이징 처리를 내부에서 할 수 있도록 필요한 정보를
		// findAll메소드에서 Pageable타입의 매개변수로 전달받는다.
		// Pageable을 구현하고 있는 PageRequest객체를 넘겨준다.
		//					   --------------
		//		현재 페이지 번호(page), size(화면에 표시할 게시글 개수)
		// [리턴]
		// findAll이 처리되면 페이징된 객체를 Page타입으로 리턴한다.
		// PageRequest에 전달한 사이즈와 page번호를 기준으로 조회한 데이터를
		// 매핑해서 넘겨준다.
		Page<ScoreDTO> PageList = scoreRepository.findAll(new PageRequest(pageNo, 10));
		// 프로그램에서 사용하려면 lsit로 변경해서 넘겨줘야 한다.
		// getContent(): Page객체가 갖고 있는 페이징 된 리스트 객체를 반환 
		List<ScoreDTO> mongolist = PageList.getContent();
		return mongolist;
	}

}
