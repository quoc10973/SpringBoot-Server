package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Koi;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Orders;
import com.example.demo.model.OrdersDetailRequest;
import com.example.demo.model.OrdersRequest;
import com.example.demo.repository.KoiRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrdersService {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    OrderRepository ordersREpository;

    AuthenticationManager authenticationManager;

    @Autowired
    KoiRepository koiRepository;

    public Orders create(OrdersRequest ordersRequest) {
        Orders orders = new Orders();
        Account customer = authenticationService.getCurrentAccount();
        orders.setDate(new Date(System.currentTimeMillis()));
        orders.setCustomer(customer);
        List<OrderDetail> ordersDetail = new ArrayList<>();
        float total = 0;
        for (OrdersDetailRequest i : ordersRequest.getDetails()) {
            Koi koi = koiRepository.findKoiById(i.getKoiId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(i.getQuantity());
            orderDetail.setPrice(koi.getPrice());
            orderDetail.setKoi(koi);
            orderDetail.setOrder(orders);
            total += orderDetail.getPrice() * orderDetail.getQuantity();
            ordersDetail.add(orderDetail);
        }
        orders.setOrderDetails(ordersDetail);
        orders.setTotal(total);
        return ordersREpository.save(orders);
    }

    public List<Orders> getOrdersByCustomer() {
        Account customer = authenticationService.getCurrentAccount();
        return ordersREpository.findOrdersByCustomer(customer);
    }

    public String createURL(OrdersRequest ordersRequest) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createdDate = LocalDateTime.now();
        String formattedDateTime = createdDate.format(formatter);

        //code thanh toan
        Orders order = create(ordersRequest);
        float money = order.getTotal() * 100;  // Vnp requires the amount * 100 and no decimal
        String amount = String.valueOf((int) money);

        String tmnCode = "FCQGXYEF";
        String secretKey = "5DHWJUAO6ELG2KFHDGET7OCSEK9NF33R";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "https://www.google.com/?orderId=" + order.getId();
        String currCode = "VND";

        Map<String, String> vnp_Params = new TreeMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", tmnCode);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_CurrCode", currCode);
        vnp_Params.put("vnp_TxnRef", order.getId().toString());
        vnp_Params.put("vnp_OrderInfo", "Thanh toan cho ma giao dich: " + order.getId());
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Amount", amount);
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_CreateDate", formattedDateTime);
        vnp_Params.put("vnp_IpAddr", "192.168.101.19");// thanh toan thanh cong

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); //remove last "$"
        String signData = signDataBuilder.toString();

        String singed = generateHMAC(secretKey, signData);

        vnp_Params.put("vnp_SecureHash", singed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); //remove last "$"

        return urlBuilder.toString();
    }

    private String generateHMAC(String secretKey, String data) throws Exception {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(data.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}



