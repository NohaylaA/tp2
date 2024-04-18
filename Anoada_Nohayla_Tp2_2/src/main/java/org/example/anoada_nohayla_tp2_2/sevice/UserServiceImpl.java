package org.example.anoada_nohayla_tp2_2.sevice;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.anoada_nohayla_tp2_2.entities.Role;
import org.example.anoada_nohayla_tp2_2.entities.User;
import org.example.anoada_nohayla_tp2_2.repositories.RoleRepository;
import org.example.anoada_nohayla_tp2_2.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService
{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Override
    public User addNewUser(User user)
    {
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }
    @Override
    public Role addNewRole(Role role)
    {
        return roleRepository.save(role);
    }
    @Override
    public User findUserByUserName(String userName)
    {
        return userRepository.findUserByUserName(userName);
    }
    @Override
    public Role findRoleByRoleName(String roleName)
    {
        return roleRepository.findByRoleName(roleName);
    }
    @Override
    public void addRoleToUser(String userName, String roleName)
    {
        User user=findUserByUserName(userName);
        Role role=findRoleByRoleName(roleName);
        if(user.getRoles()!=null)
        {
            user.getRoles().add(role);
            role.getUsers().add(user);
        }
        userRepository.save(user);
    }

    @Override
    public User authenticate(String userName, String password)
    {
        User user=userRepository.findUserByUserName(userName);
        if(user==null)
        {
            throw new RuntimeException("Bad credentials");
        }
        if(user.getPassword().equals(password))
        {
            return user;
        } 
        throw new RuntimeException("Bad credentials");
    }
}
