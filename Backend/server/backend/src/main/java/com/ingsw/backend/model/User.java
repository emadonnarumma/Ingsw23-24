package com.ingsw.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ingsw.backend.enumeration.Region;
import com.ingsw.backend.enumeration.Role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("USER")
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  include = JsonTypeInfo.As.PROPERTY,
		  property = "role"
		)
@JsonSubTypes({
		  @JsonSubTypes.Type(value = Seller.class, name = "SELLER"),
		  @JsonSubTypes.Type(value = Buyer.class, name = "BUYER")

		})
@IdClass(UserId.class)
public abstract class User implements UserDetails{

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name="role", insertable = false, updatable = false)
	private Role role;

	@Id
	@Column(length = 330)
	private String email;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Column(length = 500)
	@Length(max = 500)
	private String bio;

	@Enumerated(EnumType.STRING)
	private Region region;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ExternalLink> externalLinks;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Notification> notifications;

	@Override
	@JsonIgnore
	@JsonProperty(value = "authorities")
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	@JsonIgnore
	@JsonProperty(value = "username")
	public String getUsername() {
		return email;
	}

	@Override
	@JsonIgnore
	@JsonProperty(value = "accountNonExpired")
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	@JsonProperty(value = "accountNonLocked")
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	@JsonProperty(value = "credentialsNonExpired")
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	@JsonProperty(value = "enabled")
	public boolean isEnabled() {
		return true;
	}
}
