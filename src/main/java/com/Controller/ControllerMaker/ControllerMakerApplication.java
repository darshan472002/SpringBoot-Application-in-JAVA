package com.Controller.ControllerMaker;

import com.Controller.ControllerMaker.Config.AppConstants;
import com.Controller.ControllerMaker.Model.Role;
import com.Controller.ControllerMaker.Repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class ControllerMakerApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ControllerMakerApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


//	BCryptPasswordEncoder perform for custom password in console
	@Override
	public void run(String... args) throws Exception {

		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");

			List<Role> roles = List.of(role, role1);

			List<Role> result = this.roleRepository.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());
			});
		}
		catch (Exception e){

			e.printStackTrace();
		}
	}
}
