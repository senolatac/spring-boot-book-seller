package com.sha.springbootbookseller.controller;

import com.sha.springbootbookseller.model.Role;
import com.sha.springbootbookseller.security.CustomUserDetailsService;
import com.sha.springbootbookseller.security.SecurityConfig;
import com.sha.springbootbookseller.security.jwt.JwtAuthorizationFilter;
import com.sha.springbootbookseller.security.jwt.JwtProvider;
import com.sha.springbootbookseller.service.IPurchaseHistoryService;
import com.sha.springbootbookseller.util.SecurityUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author sa
 * @date 23.07.2022
 * @time 14:36
 */
//@SpringBootTest
//@AutoConfigureMockMvc
//@Import(PurchaseHistoryController.class)
@WithMockUser
@WebMvcTest(controllers = {PurchaseHistoryController.class})
@ContextConfiguration(classes = {PurchaseHistoryController.class, JwtProvider.class, JwtAuthorizationFilter.class})
class PurchaseHistoryControllerTest
{
    private static final String USERNAME = "f@gmail.com";
    private static final Long USER_ID = 1L;

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private IPurchaseHistoryService purchaseHistoryService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void getAllPurchasesOfUser() throws Exception
    {
        this.mockMvc.perform(get("/api/purchase-history")
                        .header("authorization", "Bearer " + jwtHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(purchaseHistoryService).findPurchasedItemsOfUser(USER_ID);
    }

    private String jwtHeader()
    {
        String authorities = Set.of(SecurityUtils.convertToAuthority(Role.USER.name())).stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(USERNAME)
                .claim("roles", authorities)
                .claim("userId", USER_ID)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
}
