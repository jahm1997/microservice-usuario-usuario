package com.product.usuariomicroservice.Repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.product.usuariomicroservice.Entity.UsuarioEntity;

public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String> {

	  UsuarioEntity findByEmail(String email);
	  
}
