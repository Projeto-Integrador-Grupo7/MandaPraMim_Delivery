package com.generation.deliverymandapramim.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.deliverymandapramim.model.Usuario;
import com.generation.deliverymandapramim.model.UsuarioLogin;
import com.generation.deliverymandapramim.repository.UsuarioRepository;
import com.generation.deliverymandapramim.security.JwtService;


/**
 * Classe de serviço responsável pelas regras de negócio relacionadas ao usuário.
 * Esta classe gerencia operações como cadastro, atualização e autenticação de usuários.
 */
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository; // Repositório para acessar os dados do usuário

	@Autowired
    private JwtService jwtService; // Serviço para manipulação de tokens JWT


    @Autowired
    private AuthenticationManager authenticationManager; // Gerenciador de autenticação
    
    /**
	 * Cadastra um novo usuário no sistema.
	 * 
	 * @param usuario O objeto Usuario a ser cadastrado.
	 * @return Um Optional contendo o usuário cadastrado ou vazio se o usuário já existir.
	 */
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
		
		// Verifica se o usuário já existe no repositório
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty(); // Retorna vazio se o usuário já existir
		
		// Criptografa a senha do usuário antes de salvar
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		
		// Salva o usuário e retorna o usuário cadastrado
		return Optional.of(usuarioRepository.save(usuario));
	
	}
	
	/**
	 * Atualiza as informações de um usuário existente.
	 * 
	 * @param usuario O objeto Usuario com as informações atualizadas.
	 * @return Um Optional contendo o usuário atualizado ou vazio se o usuário não existir.
	 */
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		// Verifica se o usuário existe no repositório
		if(usuarioRepository.findById(usuario.getId()).isPresent()) {
			// Verifica se já existe outro usuário com o mesmo nome de usuário
			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

			if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null); // Lança exceção se o usuário já existir
			
			// Criptografa a nova senha do usuário
			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
			// Salva o usuário atualizado e retorna
			return Optional.ofNullable(usuarioRepository.save(usuario));
		}

		return Optional.empty(); // Retorna vazio se o usuário não existir
	
	}	
	
	/**
	 * Autentica um usuário com base nas credenciais fornecidas.
	 * 
	 * @param usuarioLogin O objeto UsuarioLogin contendo as credenciais do usuário.
	 * @return Um Optional contendo o objeto UsuarioLogin preenchido com os dados do usuário autenticado ou vazio se a autenticação falhar.
	 */
	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
        
		// Gera o objeto de autenticação com as credenciais do usuário
		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha());
		
        // Autentica o Usuario
		Authentication authentication = authenticationManager.authenticate(credenciais);
        
		// Se a autenticação foi bem-sucedida
		if (authentication.isAuthenticated()) {

			// Preenche o objeto usuarioLogin com os dados do usuário encontrado
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario()); 

            // Se o usuário foi encontrado
			if (usuario.isPresent()) {

				// Preenche o objeto usuarioLogin com os dados do usuário encontrado
				usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
                usuarioLogin.get().setSenha(""); // Limpa a senha antes de retornar
				
                // Retorna o objeto usuarioLogin preenchido
                return usuarioLogin;
			
			}

        } 
            
		return Optional.empty(); // Retorna vazio se a autenticação

    }
	
	/**
	 * Criptografa a senha do usuário usando BCrypt.
	 * 
	 * @param senha A senha a ser criptografada.
	 * @return A senha criptografada.
	 */
	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // Cria uma instância do codificador BCrypt
		
		return encoder.encode(senha); // Retorna a senha criptografada

	}
	
	/**
	 * Gera um token JWT para o usuário autenticado.
	 * 
	 * @param usuario O nome de usuário para o qual o token será gerado.
	 * @return O token JWT gerado, precedido por "Bearer ".
	 */
	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario); // Gera e retorna o token JWT
	}

}