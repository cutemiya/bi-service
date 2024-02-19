package com.example.newspot.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // forbidden data
        var data = getBody(request.getRequestURL().toString(),
                "Access Denied",
                HttpStatus.FORBIDDEN);
        // response stream
        OutputStream out = response.getOutputStream();
        var token = request.getHeader("Authorization");

        if (Objects.equals(token, "") || Objects.isNull(token)) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, data);
            out.flush();

            return false;
        }

        token = token.split(" ")[1]; // remove bearer

        if (!validateToken(token)) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, data);
            out.flush();

            return false;
        }

        //token payload
        var payload = parseJwt(token.split("\\.")[1]);

        //add
        addContextData(request, payload);

        return true;
    }

    private Map<String, Object> getBody(String url, String message, HttpStatus status) throws Exception {
        // time formatter
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");

        // out data
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", sdf.format(new Date(System.currentTimeMillis())));
        data.put("status", status.value());
        data.put("message", message);
        data.put("path", url);

        return data;
    }

    private boolean validateToken(String token) {
        String key = "wtuNRpAZ3n7iWgWGKPeSEgQUTHGFOOEe-YS4PxHFnir7F881";
        SecretKey skey = new SecretKeySpec(key.getBytes(), "HmacSHA256");

        try {
            Jwts.parserBuilder().
                    setSigningKey(skey).
                    build().
                    parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    private Map<String, Object> parseJwt(String token) throws JSONException {
        Map<String, Object> data = new HashMap<>();

        var payload = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
        JSONObject json = new JSONObject(payload);

        data.put("accountId", json.getString("accountId"));
        data.put("email", json.getString("email"));
        data.put("username", json.getString("username"));
        data.put("role", json.getString("role"));

        return data;
    }

    private void addContextData(HttpServletRequest request, Map<String, Object> data) {
        var ctx = request.getServletContext();
        ctx.setAttribute("accountId", data.get("accountId"));
        ctx.setAttribute("email", data.get("email"));
        ctx.setAttribute("username", data.get("username"));
        ctx.setAttribute("role", data.get("role"));
    }
}

