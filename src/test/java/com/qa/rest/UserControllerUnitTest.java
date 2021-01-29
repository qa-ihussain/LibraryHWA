package com.qa.rest;

	import static org.assertj.core.api.Assertions.assertThat;

	import java.util.ArrayList;
	import java.util.List;
	import java.util.stream.Collectors;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.mockito.Mockito;
	import org.modelmapper.ModelMapper;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.boot.test.mock.mockito.MockBean;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;

	import com.qa.LibraryHobbyProjectApplication;
	import com.qa.persistence.domain.User;
	import com.qa.persistence.dto.UserDTO;
	import com.qa.rest.UserController;
	import com.qa.services.UserService;

	@SpringBootTest(classes = LibraryHobbyProjectApplication.class)
	public class UserControllerUnitTest {

		@Autowired
		private UserController controller;
		
		@MockBean
		private UserService service;
		
		private List<User> userList;
		private UserDTO userDTO;
		private User testUser;
		private Long Id;
		private ModelMapper mapper = new ModelMapper();
		
		private UserDTO mapToDTO(User model) {
			return this.mapper.map(model, UserDTO.class);
		}
		
		@BeforeEach
		void init() {
			this.Id = 4L;
			this.userList = new ArrayList<>();
			this.testUser = new User(Id, "user", "name", 21, "un22", "password", null);
			this.userDTO = new UserDTO(Id, "user", "name", 21, "un22", "password");
			
			this.userList.add(testUser);
			this.userDTO = this.mapToDTO(testUser);
		}
		
		@Test
		public void createTest() {
			Mockito.when(this.service.create(testUser)).thenReturn(userDTO);
			
			assertThat(new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED))
					.usingRecursiveComparison().isEqualTo(controller.create(testUser));
			
			Mockito.verify(this.service, Mockito.times(1)).create(testUser);
		}
		
		@Test
		public void readUnoTest() {
			Mockito.when(this.service.readUno(Id)).thenReturn(mixDTO);
			
			assertThat(ResponseEntity.ok(this.service.readUno(Id)))
					.usingRecursiveComparison().isEqualTo(controller.readMix(Id));
			
			Mockito.verify(this.service, Mockito.times(2)).readUno(Id);
		}
		
		@Test
		public void readAllTest() {
			Mockito.when(this.service.readAll()).thenReturn(mixList.stream().map
					(this::mapToDTO).collect(Collectors.toList()));
			
			assertThat(ResponseEntity.ok(this.service.readAll()))
					.usingRecursiveComparison().isEqualTo(controller.readAll());
			
			Mockito.verify(this.service, Mockito.times(2)).readAll();
		}
		
		@Test
		public void updateTest() {
			Mixes updatedMix = new Mixes(Id, "2020 Round Up", time, null);
			MixesDTO updatedDTO = new MixesDTO(Id, time, "2020 Round Up", null);
			
			Mockito.when(this.service.update(Id, updatedMix)).thenReturn(updatedDTO);
			
			assertThat(new ResponseEntity<>(updatedDTO, HttpStatus.ACCEPTED))
					.usingRecursiveComparison().isEqualTo
					(controller.update(Id, updatedMix));
			
			Mockito.verify(this.service, Mockito.times(1)).update(Id, updatedMix);		
		}
		
		@Test
		public void deleteTest() {
			Mockito.when(this.service.delete(Id)).thenReturn(true);
			
			assertThat(new ResponseEntity<>(HttpStatus.NO_CONTENT))
					.usingRecursiveComparison().isEqualTo(controller.deleteMix(Id));
			
			Mockito.verify(this.service, Mockito.times(1)).delete(Id);
		}
		
		@Test
		public void deleteFailTest() {
			controller.deleteMix(Id);
			
			Mockito.verify(this.service, Mockito.times(1)).delete(Id);	
		}
		
	}
}