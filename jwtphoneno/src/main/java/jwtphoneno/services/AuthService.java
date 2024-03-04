package jwtphoneno.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jwtphoneno.entity.AuthRequest;
import jwtphoneno.entity.UserCredential;
import jwtphoneno.repository.UserCredentialRepository;

@Service
public class AuthService {

	@Autowired
    private UserCredentialRepository repository;
	
	 @Autowired
	 private AuthenticationManager authenticationManager;
	 
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

 /*   public String generateToken2(AuthRequest authRequest) {
     
     Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getPhoneno(), authRequest.getPassword()));
    if(authenticate.isAuthenticated())
    {
    	return jwtService.generateToken(authRequest.getPhoneno());
    }else
    {
    	throw new RuntimeException("Credentials invalid!!");
    }
        
    }
*/
    public String generateToken2(AuthRequest authRequest) {
        
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
       if(authenticate.isAuthenticated())
       {
       	return jwtService.generateToken(authRequest.getEmail());
       }else
       {
       	throw new RuntimeException("Credentials invalid!!");
       }
           
       }
    
    public boolean validateToken(String token) {
        jwtService.validateToken(token);
        return true;
    }

	public String deleteAll()
	{
		this.repository.deleteAll();
		return "All Records Deleted!!";
	}
	
	public List<UserCredential> getAll()
	{
		List<UserCredential> users=this.repository.findAll();
		return users;
	}
}
