package ir.dotin.softwaresystems.librarymanagement.repository;

import ir.dotin.softwaresystems.librarymanagement.dto.Userdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Repository
public class SessionRepository {
    private final HttpSession session;
    @Autowired
    public SessionRepository(HttpSession session) {
        this.session = session;
    }

    public void createUserSession(Userdto userOnDB) {
        session.setAttribute("userId", userOnDB.getId());
        session.setAttribute("username", userOnDB.getUsername());
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+userOnDB.getRole()));
        Authentication authentication=new UsernamePasswordAuthenticationToken(userOnDB, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public int getUserIdSession(){
        return (Integer) session.getAttribute("userId");
    }
}
