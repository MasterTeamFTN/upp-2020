package rs.ac.uns.ftn.uppservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.uppservice.dto.request.LoginDTO;
import rs.ac.uns.ftn.uppservice.dto.request.PasswordChangerDTO;
import rs.ac.uns.ftn.uppservice.dto.response.UserDTO;
import rs.ac.uns.ftn.uppservice.dto.response.UserTokenDTO;
import rs.ac.uns.ftn.uppservice.security.TokenUtils;
import rs.ac.uns.ftn.uppservice.service.impl.CustomUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {

    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid LoginDTO authenticationRequest) {
        return new ResponseEntity<UserDTO>(userDetailsService.login(authenticationRequest), HttpStatus.OK);
    }


    @PostMapping("/refresh")
    public ResponseEntity<UserTokenDTO> refreshAuthenticationToken(HttpServletRequest request) {
        return new ResponseEntity("", HttpStatus.OK);
//        return new ResponseEntity<>(userDetailsService.refreshAuthenticationToken(request), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody @Valid PasswordChangerDTO passwordChanger) {
        userDetailsService.changePassword(passwordChanger.getOldPassword(), passwordChanger.getNewPassword());
        return ResponseEntity.ok().build();
    }

}
