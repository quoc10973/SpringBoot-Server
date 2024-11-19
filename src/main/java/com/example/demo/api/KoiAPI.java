package com.example.demo.api;

import com.example.demo.entity.Koi;
import com.example.demo.service.KoiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*") //cho phép tất cả code truy cập
@SecurityRequirement(name = "api") //bắt buộc có, nên nhớ
public class KoiAPI {

    @Autowired
    KoiService koiService;

    @GetMapping("/api/koi")
    public ResponseEntity getAllKoi(){
        List<Koi> koi = koiService.getAll();
        return ResponseEntity.ok(koi);
    }

    @PostMapping("/api/koi")
    public ResponseEntity createKoi(@RequestBody Koi koi){
        Koi newKoi = koiService.create(koi);
        return ResponseEntity.ok(newKoi);
    }
}

// 1. API lấy danh sách lên => phân trang ở BE
// 2. Tạo v lưu đơn hàng
// 3. Lịch sử đơn hàng
