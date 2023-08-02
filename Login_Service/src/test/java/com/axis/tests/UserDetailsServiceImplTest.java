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


    @Test
    public void testLoadUserByUsername() {
        Users user = new Users();
        user.setUsername("testuser");
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        Assert.assertNotNull(userDetails);
        Assert.assertEquals("testuser", userDetails.getUsername());
    }

    @Test
    public void testCreateCustomer() {
        Users user = new Users();
        user.setId(1);

        Mockito.when(userRepository.save(Mockito.any(Users.class))).thenReturn(user);

        Users createdUser = userDetailsService.createCustomer(user);

        Assert.assertNotNull(createdUser);
        Assert.assertEquals(1, createdUser.getId());
    }


    @Test
    public void testGenerateAccountNumber() {
        String accountNumber = UserDetailsServiceImpl.generateAccountNumber();
        Assert.assertNotNull(accountNumber);
        Assert.assertEquals(15, accountNumber.length());
    }


    @Test
    public void testEditUser() {
        Users existingUser = new Users();
        existingUser.setId(1);


        Users updatedUser = new Users();
        updatedUser.setId(1);
        updatedUser.setUsername("newusername");


        Mockito.when(userRepository.findById(1)).thenReturn(existingUser);
        Mockito.when(userRepository.save(Mockito.any(Users.class))).thenReturn(updatedUser);

        Users editedUser = userDetailsService.editUser(updatedUser);

        Assert.assertNotNull(editedUser);
        Assert.assertEquals("newusername", editedUser.getUsername());

    }

}
