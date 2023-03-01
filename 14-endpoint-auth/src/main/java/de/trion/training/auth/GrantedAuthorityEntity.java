package de.trion.training.auth;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "TBL_AUTH_AUTHORITIES")
public class GrantedAuthorityEntity implements GrantedAuthority {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   private String authority;

   @ManyToOne(optional = false)
   private UserEntity userEntity;

   public GrantedAuthorityEntity() {
   }

   public GrantedAuthorityEntity(String authority, UserEntity userEntity) {
      this.authority = authority;
      this.userEntity = userEntity;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public UserEntity getUserEntity() {
      return userEntity;
   }

   public void setUserEntity(UserEntity userEntity) {
      this.userEntity = userEntity;
   }

   public void setAuthority(String authority) {
      this.authority = authority;
   }

   @Override
   public String getAuthority() {
      return authority;
   }

   @Override
   public String toString() {
      return authority;
   }
}
