package shop.nhnteam04.front.account.user.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shop.nhnteam04.front.account.user.response.Role;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {
    private final Long id;
    private final String name;
    private final String email;
    private final Role role;

    public SecurityUser(Long id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return name;
    }
}
