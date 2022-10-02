package com.pool.entity.role;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pool.entity.user.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_ROLE")
public class Role implements Serializable {
	private static final long serialVersionUID = 3035554027976746223L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID")
	private Long roleId;
	
	@Column(name = "ROLE")
	private String role;
	
	@Column(name = "DISPLAY_NAME")
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private List<UserProfile> users;

}
