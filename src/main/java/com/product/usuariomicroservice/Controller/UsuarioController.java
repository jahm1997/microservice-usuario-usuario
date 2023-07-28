package com.product.usuariomicroservice.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.product.usuariomicroservice.Entity.UsuarioEntity;
import com.product.usuariomicroservice.Modelos.Contact;
import com.product.usuariomicroservice.Repository.UsuarioRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UsuarioRepository usuarioRepo;
	

	@GetMapping("/relacion/{id}")
	public Contact[] getContact(@PathVariable("id") String id ){
		Contact[] contactos = restTemplate.getForObject("http://localhost:8080/contact/usuario/" + id, Contact[].class );
		return contactos;
	}

	
	@GetMapping
	public ResponseEntity<List<UsuarioEntity>> getAllusers() {
		List<UsuarioEntity> users = usuarioRepo.findAll();
		ResponseEntity<List<UsuarioEntity>> response = new ResponseEntity<>(users, HttpStatus.OK);
		return response;
	}
	

	@GetMapping("/{id}")
	public UsuarioEntity getOneUser(@PathVariable("id") String id ) {
		return usuarioRepo.findById(id).orElse(null);
	}

	@PostMapping("/iniciar")
    public ResponseEntity<UsuarioEntity> getByEmail(@RequestBody Map<String, String> user) {
        String email = user.get("email");
        String password = user.get("password");
        
        UsuarioEntity usuario = usuarioRepo.findByEmail(email);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        if (!usuario.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(usuario);
	}
	

	@PostMapping
	@ResponseStatus(code = HttpStatus.OK)
	public void postUser(@RequestBody UsuarioEntity usuarioEntity) {
		 usuarioRepo.save(usuarioEntity);
	}
	
	
	
}
