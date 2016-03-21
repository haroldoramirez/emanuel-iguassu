package br.com.emanuelvictor.iguassu.web.service;

import br.com.emanuelvictor.iguassu.web.entity.Perfil;
import br.com.emanuelvictor.iguassu.web.entity.Usuario;
import br.com.emanuelvictor.iguassu.web.repository.DAOUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Calendar;
import java.util.List;

//@Service("serviceUsuario")
@Service
@Transactional
public class ServiceUsuario implements UserDetailsService {

	@Autowired
    DAOUsuario daoUsuario;

//    @Autowired
//    PasswordEncoder passwordEncoder;

	public Usuario save(Usuario usuario, Long id) {
        System.out.println("Senha nova " + usuario.getSenha());
        System.out.println("Senha antiga " + this.daoUsuario.findOne(id).getSenha());
        if (usuario.getSenha()==null||usuario.getSenha().trim()==""||usuario.getSenha()==""){
            System.out.println("Senha esta null ");
            usuario.setSenha(this.find(id).getSenha());
        }else{
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        }
        if (this.getCurrentUser().getPerfil()!= Perfil.GERENTE){
            usuario.setFilial(this.getCurrentUser().getFilial());
            usuario.setPerfil(this.find(id).getPerfil());
        }
        usuario.setDataDeAlteracao(Calendar.getInstance());
        usuario = this.daoUsuario.save(usuario);
        System.out.println("Senha que ficou " + this.daoUsuario.findOne(id).getSenha());
        return usuario;
	}

    public Usuario save(Usuario usuario) {
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        usuario.setDataDeAlteracao(Calendar.getInstance());
        return this.daoUsuario.save(usuario);
    }

	public void delete(Long id) {
		daoUsuario.delete(id);
	}

	public List<Usuario> find() {
		return daoUsuario.findAll();
	}

	public Usuario find(Long id) {
		return daoUsuario.findOne(id);
	}


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        Usuario usuario1 = new Usuario();
//        usuario1.setSenha(new BCryptPasswordEncoder().encode("123456"));
//        usuario1.setNome("Gerente");
//        usuario1.setLogin("Gerente");
//        usuario1.setPerfil(Perfil.GERENTE);
//
//        daoUsuario.save(usuario1);
//
//        Usuario usuario2 = new Usuario();
//        usuario2.setNome("Atendente");
//        usuario2.setSenha(new BCryptPasswordEncoder().encode("123456"));
//        usuario2.setLogin("Atendente");
//        usuario2.setPerfil(Perfil.ATENDENTE);
//
//        daoUsuario.save(usuario2);
//
//        Usuario usuario3 = new Usuario();
//        usuario3.setNome("Administrador");
//        usuario3.setSenha(new BCryptPasswordEncoder().encode("123456"));
//        usuario3.setLogin("Administrador");
//        usuario3.setPerfil(Perfil.ADMINISTRADOR);
//
//        daoUsuario.save(usuario3);
//
//        Usuario usuario4 = new Usuario();
//        usuario4.setNome("Bloqueado");
//        usuario4.setSenha(new BCryptPasswordEncoder().encode("123456"));
//        usuario4.setLogin("Bloqueado");
//        usuario4.setPerfil(Perfil.BLOQUEADO);
//
//        return daoUsuario.save(usuario4);

        Usuario user = daoUsuario.findByLogin(login);
        if (user == null || user.getId() == null || user.getId() == 0){
            throw new UsernameNotFoundException("Tentativa de login sem sucesso, nome de usuário: " + login + " - Nome de usuário não encontrado");
        }
        if (user.getPerfil() == Perfil.BLOQUEADO){
            throw new UsernameNotFoundException("Usuário BLOQUEADO");
        }
        return user;
    }

    public Usuario getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null)
            try{
                Usuario usuario = (Usuario) auth.getPrincipal();
                usuario.setPassword(null);
                return usuario;
            }catch (Exception e){
                return null;
            }
        else
            return null;
    }
}