package com.axis.tests;

import com.axis.controller.EmployeeController;
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
public class EmployeeControllerTest {
    @InjectMocks
    private EmployeeController employeeController;

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
        AuthRequest authRequest = new AuthRequest("employee", "password");
        Users user = new Users();
        user.setUsername("employee");
        Mockito.when(userService.getUserByUsername("employee")).thenReturn(user);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(null);
        Mockito.when(jwtUtil.generateToken("employee")).thenReturn("generated-token");

        ResponseEntity<?> responseEntity = employeeController.authenticate(authRequest);

        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // Add more assertions as needed
    }

    @Test(expected = Exception.class)
    public void testAuthenticate_BlockedAccount() throws Exception {
        AuthRequest authRequest = new AuthRequest("blockedEmployee", "password");
        Users user = new Users();
        user.setUsername("blockedEmployee");
        user.setBlocked(true);
        Mockito.when(userService.getUserByUsername("blockedEmployee")).thenReturn(user);

        employeeController.authenticate(authRequest);
    }
}
