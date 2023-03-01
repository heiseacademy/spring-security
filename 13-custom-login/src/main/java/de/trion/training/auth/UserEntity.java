package de.trion.training.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "TBL_AUTH_USERS")
public class UserEntity implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String username;
   private String password;
   private boolean enable;
   private boolean locked;

   @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private Set<GrantedAuthorityEntity> authorities;

   public UserEntity() {
   }

   public UserEntity(String username, String password, Collection<? extends GrantedAuthority> authorities) {
      this.username = username;
      this.password = password;
      this.authorities = authorities.stream().map(GrantedAuthority::getAuthority)
            .map(a -> new GrantedAuthorityEntity(a, this))
            .collect(Collectors.toSet());
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   @Override
   public String getUsername() {
      return username;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return !locked;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return enable;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   @Override
   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public boolean isEnable() {
      return enable;
   }

   public void setEnable(boolean enable) {
      this.enable = enable;
   }

   public boolean isLocked() {
      return locked;
   }

   public void setLocked(boolean locked) {
      this.locked = locked;
   }

   @Override
   public Set<GrantedAuthorityEntity> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<GrantedAuthorityEntity> authorities) {
      this.authorities = authorities;
   }
}
