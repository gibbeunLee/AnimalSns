package com.animalSNS.animalSNS.auth.handler;

import com.animalSNS.animalSNS.auth.jwt.JwtTokenizer;
import com.animalSNS.animalSNS.auth.utils.CustomAuthorityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class MemberAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

}
