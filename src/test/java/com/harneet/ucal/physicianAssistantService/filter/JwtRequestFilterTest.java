package com.harneet.ucal.physicianAssistantService.filter;

import com.harneet.ucal.physicianAssistantService.service.UserDetailsServiceImpl;
import com.harneet.ucal.physicianAssistantService.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;


class JwtRequestFilterTest {

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @Mock
    private UsernamePasswordAuthenticationToken authToken;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jwtRequestFilter).build();
    }

    @Test
    void testDoFilterInternal_NoAuthorizationHeader_ShouldNotSetAuthentication() throws Exception {
        // Mock the request to return no Authorization header
        when(request.getHeader("Authorization")).thenReturn(null);

        // Call the method under test
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filter chain continues without setting authentication
        verify(filterChain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

    @Test
    void testDoFilterInternal_InvalidAuthorizationHeaderFormat_ShouldNotSetAuthentication() throws Exception {
        // Mock the request to return an invalid Authorization header
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");

        // Call the method under test
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        // Verify that the filter chain continues without setting authentication
        verify(filterChain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

}
