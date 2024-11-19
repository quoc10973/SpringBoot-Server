package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Student API", version = "1.0", description = "Information"))
@SecurityScheme(name = "api", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	// Resfull API
	// Cach đặt tên API

	// Thêm 1 sinh viên mới
	// /api/student    => POST: tạo 1 thằng student mới
	// /api/student/studentId	   => PUT: update thông tin của student với id gì đấy. ví dụ /api/student/1 -> student với id=1
	// /api/student/studentId     => GET: lấy thông tin của 1 thằng cụ thể  /api/student/1 -> lấy info th có id=1
	// /api/student 			=> GET: lấy tất cả student


	// METHOD:
	/*
	*		POST => create
	* 		PUT => update
	* 		DELETE => delete
	* 		GET => get
	*
	**/


}
