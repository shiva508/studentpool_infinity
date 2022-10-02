package com.pool.entity.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import com.pool.entity.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_USER_PROFILE")
public class UserProfile implements Serializable{

	private static final long serialVersionUID = 7823610699711947019L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "AVATHAR_ID")
	private String avatharId;
	
	@Email(message = "Please enter valid Email")
	@NotBlank(message = "Email should not be blank")
	@Column(name = "USER_NAME")
	private String username;
	
	@NotBlank(message = "Email should not be blank")
	@Column(name = "PASSWORD")
	private String password;
	
	@NotBlank(message = "First Name should not be blank")
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@NotBlank(message = "Last Name should not be blank")
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "CREATED_AT")
	private Date createdAt;
	
	@Column(name = "UPDATED_AT")
	private Date updatedAt;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name="TBL_USER_ROLE",
			   joinColumns = {@JoinColumn(name="USER_ID",referencedColumnName ="USER_ID")},
			   inverseJoinColumns = {@JoinColumn(name="ROLE_ID",referencedColumnName = "ROLE_ID")}
			  )
	private List<Role> roles;

}
