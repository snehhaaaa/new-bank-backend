package com.axis.tests;

import com.axis.controller.AdminController;
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
import org.springframework.security.authentication.AuthenticationManager;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
    @InjectMocks
    private AdminController adminController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsServiceImpl userService;

    // Other setup and initialization code as needed

    @Test
    public void testAuthenticate_Successful() throws Exception {
        AuthRequest authRequest = new AuthRequest("admin", "password");
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(null);
        Mockito.when(jwtUtil.generateToken("admin")).thenReturn("generated-token");

        String token = adminController.authenticate(authRequest);

        Assert.assertNotNull(token);
        Assert.assertEquals("generated-token", token);
    }

    @Test(expected = Exception.class)
    public void testAuthenticate_Failed() throws Exception {
        AuthRequest authRequest = new AuthRequest("admin", "wrong-password");
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenThrow(new RuntimeException());

        adminController.authenticate(authRequest);
    }

    @Test
    public void testCreateEmployee() {
        Users employee = new Users();
        employee.setUsername("newemployee");
        // Set other properties

        Mockito.when(userService.createEmployee(Mockito.any(Users.class))).thenReturn(employee);

        Users createdEmployee = adminController.create(employee);

        Assert.assertNotNull(createdEmployee);
        Assert.assertEquals("newemployee", createdEmployee.getUsername());
    }

}
