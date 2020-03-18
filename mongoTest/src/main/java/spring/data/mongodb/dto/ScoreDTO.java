package spring.data.mongodb.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// mongodb Collection에서 하나의 document와 매핑될 클래스
// spring data에서 제공하는 특성 - 자동으로 매핑해줌
@Document(collection="score")
public class ScoreDTO {
	// primary key 등록
	@Id
	String _id;
	
	String id;
	String name;
	String dept;
	String addr;
	int java;
	int servlet;
	
	// 기본 생성자는 항상 만들어야 한다(스프링)
	public ScoreDTO() {
		
	}
	
	public ScoreDTO(String id, String name, String dept, String addr, int java, int servlet) {
		super();
		this.id = id;
		this.name = name;
		this.dept = dept;
		this.addr = addr;
		this.java = java;
		this.servlet = servlet;
	}

	@Override
	public String toString() {
		return "ScoreDTO [id=" + id + ", name=" + name + ", dept=" + dept + ", addr=" + addr + ", java=" + java
				+ ", servlet=" + servlet + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getJava() {
		return java;
	}

	public void setJava(int java) {
		this.java = java;
	}

	public int getServlet() {
		return servlet;
	}

	public void setServlet(int servlet) {
		this.servlet = servlet;
	}
	
}
