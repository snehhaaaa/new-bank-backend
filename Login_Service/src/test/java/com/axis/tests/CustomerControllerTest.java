package com.axis.tests;

import com.axis.controller.CustomerController;
import com.axis.entity.AuthRequest;
import com.axis.entity.Users;
import com.axis.service.UserDetailsServiceImpl;
import com.axis.util.JwtUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsServiceImpl userService;

    @Mock
    private JavaMailSender javaMailSender;



    @Test
    public void testAuthenticate_Successful() throws Exception {
        AuthRequest authRequest = new AuthRequest("user", "password");
        Users user = new Users();
        user.setUsername("user");
        Mockito.when(userService.getUserByUsername("user")).thenReturn(user);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(null);
        Mockito.when(jwtUtil.generateToken("user")).thenReturn("generated-token");

        ResponseEntity<?> responseEntity = customerController.authenticate(authRequest);

        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test(expected = Exception.class)
    public void testAuthenticate_BlockedAccount() throws Exception {
        AuthRequest authRequest = new AuthRequest("blockedUser", "password");
        Users user = new Users();
        user.setUsername("blockedUser");
        user.setBlocked(true);
        Mockito.when(userService.getUserByUsername("blockedUser")).thenReturn(user);

        customerController.authenticate(authRequest);
    }
}
