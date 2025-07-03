package shop.nhnteam04.front.account.user.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shop.nhnteam04.front.account.user.response.AuthType;
import shop.nhnteam04.front.account.user.response.ResponseUser;
import shop.nhnteam04.front.account.user.response.Role;
import lombok.Data;
import shop.nhnteam04.front.account.user.response.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private Status status;
    private AuthType authType;
    private LocalDateTime currentLoginAt;
    private Role role;

    public SecurityUser(Long id, String name, String email, String phoneNumber, LocalDate birthDate, LocalDateTime createdAt, Status status, AuthType authType, LocalDateTime currentLoginAt, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.status = status;
        this.authType = authType;
        this.currentLoginAt = currentLoginAt;
        this.role = role;
    }

    public static SecurityUser from(ResponseUser responseUser) {
        return new SecurityUser(
                responseUser.getId(),
                responseUser.getName(),
                responseUser.getEmail(),
                responseUser.getPhoneNumber(),
                responseUser.getBirthDate(),
                responseUser.getCreatedAt(),
                responseUser.getStatus(),
                responseUser.getAuthType(),
                responseUser.getCurrentLoginAt(),
                responseUser.getRole());
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
