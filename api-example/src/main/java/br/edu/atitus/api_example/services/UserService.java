package br.edu.atitus.api_example.services;

import br.edu.atitus.api_example.entities.UserEntity;

public class UserService {
	
	public UserEntity save(UserEntity user) throws Exception {
		if(user == null)
			throw new Exception("Objeto Nulo");
		if(user.getName() == null || user.getName().isEmpty())
			throw new Exception("Nome invalido");
		user.setName(user.getName().trim());
		
		if(user.getEmail() == null || user.getEmail().isEmpty())
			throw new Exception("Email invalido");
		user.setEmail(user.getEmail().trim());
		
		if(user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() < 8)
			throw new Exception("Senha invalido");
		
		
		
		
		//TODO criar hash da senha
		//TODO validar premissao cadastro admins
		//TODO enviar para a camada repository
		return user;
	}

}
