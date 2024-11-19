package com.example.demo.config;

import com.example.demo.entity.Account;
import com.example.demo.exception.AuthenticateException;
import com.example.demo.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

//extend de filter
@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;//xử lý exception trong các yêu cầu http

    private final List<String> AUTH_PERMISSION = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/login",
            "/api/register",
            "/api/forgot-password",
            "/api/major"
    );

    public boolean isPublicAPI(String uri){
        //VD: uri: /api/register
        //nếu gặp các api ở trên cho truy cập luôn
        AntPathMatcher pathMatcher = new AntPathMatcher();
        //không thì check token => false
        return AUTH_PERMISSION.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri)); //check trong List có ít nhất 1 thì true, ngược lại false
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //check xem api mà người dùng yêu cầu có phải là 1 public api hay không?(ai cũng dùng được)

        boolean isPublicAPI = isPublicAPI(request.getRequestURI());
        if(isPublicAPI){
            filterChain.doFilter(request,response);
        }
        else{
            String token = getToken(request);
            if(token == null){
                //không đươc phép truy cập
                resolver.resolveException(request, response, null, new AuthenticateException("Empty token"));
                return;
            }

            // => có token
            //check token có đúng không, lấy thông tin account từ token
            Account account;
            try{
                account = tokenService.getAccountByToken(token);
            }catch (ExpiredJwtException e){
                //response token hết hạn
                resolver.resolveException(request, response, null, new AuthenticateException("Expired token"));
                return;
            } catch (MalformedJwtException e) {
                resolver.resolveException(request, response, null, new AuthenticateException("Invalid token"));
                // response token sai
                return;
            }

            //token chuẩn
            //cho phép truy cập
            //lưu lại thông tin account
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    account,
                    token,
                    account.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); //lưu trữ đối tượng xác thực vào SecurityContext, trong suốt quá trình của phiên làm việc, mọi yêu cầu tiếp theo sẽ được nhận diện là từ người dùng này.
            //cho token đó vào
            filterChain.doFilter(request,response);
        }
    }

    public String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");  //token nằm trong header của Authorization
        if(authHeader == null){
            return null;
        }
        return authHeader.substring(7); //bỏ Bearer
    }
}
