package com.axis.tests;

import com.axis.entity.Users;
import com.axis.repository.AccountRepository;
import com.axis.repository.RoleRepository;
import com.axis.repository.UserRepository;
import com.axis.service.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AccountRepository accountRepository;

    // Other setup and initialization code as needed

    @Test
    public void testLoadUserByUsername() {
        Users user = new Users();
        user.setUsername("testuser");
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        Assert.assertNotNull(userDetails);
        Assert.assertEquals("testuser", userDetails.getUsername());
        // Add more assertions as needed
    }

    @Test
    public void testCreateCustomer() {
        Users user = new Users();
        user.setId(1);
        // Set other properties

        Mockito.when(userRepository.save(Mockito.any(Users.class))).thenReturn(user);

        Users createdUser = userDetailsService.createCustomer(user);

        Assert.assertNotNull(createdUser);
        Assert.assertEquals(1, createdUser.getId());
        // Add more assertions as needed
    }

    // Write similar tests for other methods like createEmployee, deleteEmployee, viewProfile, etc.

    @Test
    public void testGenerateAccountNumber() {
        String accountNumber = UserDetailsServiceImpl.generateAccountNumber();
        Assert.assertNotNull(accountNumber);
        Assert.assertEquals(15, accountNumber.length());
    }

    // Write similar tests for other utility methods

    @Test
    public void testEditUser() {
        Users existingUser = new Users();
        existingUser.setId(1);
        // Set other properties

        Users updatedUser = new Users();
        updatedUser.setId(1);
        updatedUser.setUsername("newusername");
        // Set other properties

        Mockito.when(userRepository.findById(1)).thenReturn(existingUser);
        Mockito.when(userRepository.save(Mockito.any(Users.class))).thenReturn(updatedUser);

        Users editedUser = userDetailsService.editUser(updatedUser);

        Assert.assertNotNull(editedUser);
        Assert.assertEquals("newusername", editedUser.getUsername());
        // Add more assertions as needed
    }

    // Write similar tests for other edit methods

    // Add more test cases for the remaining methods
}
