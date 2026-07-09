package com.vinayagam.cctv.controller;

import com.vinayagam.cctv.model.CustomerAccount;
import com.vinayagam.cctv.repository.CustomerAccountRepository;
import com.vinayagam.cctv.repository.QuoteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteRepository quoteRepository;

    @MockBean
    private CustomerAccountRepository customerAccountRepository;

    @Test
    void signupShouldSaveCustomerAndRedirectToSignin() throws Exception {
        when(customerAccountRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(post("/signup")
                        .param("fullName", "John")
                        .param("email", "john@example.com")
                        .param("phone", "9876543210")
                        .param("password", "secret123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signin"));

        ArgumentCaptor<CustomerAccount> customerCaptor = ArgumentCaptor.forClass(CustomerAccount.class);
        verify(customerAccountRepository).save(customerCaptor.capture());

        CustomerAccount savedAccount = customerCaptor.getValue();
        assertEquals("John", savedAccount.getFullName());
        assertEquals("john@example.com", savedAccount.getEmail());
    }
}
