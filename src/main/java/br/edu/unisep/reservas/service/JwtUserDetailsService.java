package br.edu.unisep.reservas.service;

import br.edu.unisep.reservas.model.User;
import br.edu.unisep.reservas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Usuario nao encontrado!");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getSenha(),new ArrayList<>());
    }

    public User save(User user){
        User novo = new User();
        novo.setEmail(user.getEmail());
        novo.setSenha(bcryptEncoder.encode(user.getSenha()));
        return repository.save(novo);
    }
}
