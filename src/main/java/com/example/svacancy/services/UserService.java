package com.example.svacancy.services;

import com.example.svacancy.Model.*;
import com.example.svacancy.Model.enums.Role;
import com.example.svacancy.exception.RegistrationException.ActivationCodeException;
import com.example.svacancy.exception.RegistrationException.EmailException;
import com.example.svacancy.exception.RegistrationException.UsernameException;
import com.example.svacancy.exception.RegistrationException.VacancyException.RespondVacancyException;
import com.example.svacancy.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final VacancyService vacancyService;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public void createNewUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) throw new UsernameException("This username is already in use!");
        userFromDb = userRepo.findByEmail(user.getEmail());
        if (userFromDb != null) throw new EmailException("This email is already in use!");

        user.setRoles(Collections.singleton(Role.USER));

        if (user.getUsername().equals("admin")) user.setRoles(Collections.singleton(Role.ADMIN));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessageForAcivateUser(user);
    }

    public void passwordRecovery(String email){
        User userFromDb = userRepo.findByEmail(email);

        if(userFromDb == null) throw new EmailException("User with given email does not exist.");

        String code = UUID.randomUUID().toString();

        userFromDb.setActivationCode(code);

        userRepo.save(userFromDb);

        sendMessageForPasswordRecover(userFromDb);

    }

    public void activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) throw new ActivationCodeException("Invalid activation code(link)");

        user.setActivationCode(null);
        user.setActive(true);

        userRepo.save(user);
    }


    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public boolean updateProfile(User user, String password, String email) throws EmailException {
        String userEmail = user.getEmail();

        if(!userEmail.equals(email)){
            if (userRepo.findByEmail(email) != null) throw new EmailException("Email is exist!");
        }

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
            userRepo.save(user);
        }

        if (isEmailChanged) {
            user.setActivationCode(UUID.randomUUID().toString());
            userRepo.save(user);
            user.setEmail(email);
            sendMessageForChangeEmail(user);
        }

        return true;
    }



    public void updateEmail(String code, String email){
        User userFromDb = userRepo.findByActivationCode(code);

        if(userFromDb == null) throw new ActivationCodeException("Invalid activation code(link)");

        userFromDb.setEmail(email);
        userFromDb.setActivationCode(null);

        userRepo.save(userFromDb);
    }


    public void updatePassword(String code, String password){
        User userFromDb = userRepo.findByActivationCode(code);

        if(userFromDb == null) throw new ActivationCodeException("Invalid activation code(link)");

        userFromDb.setPassword(passwordEncoder.encode(password));
        userFromDb.setActivationCode(null);

        userRepo.save(userFromDb);
    }

    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);

        userRepo.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().remove(currentUser);

        userRepo.save(user);
    }

    public void respondVacancy(User user, String currentVacancyId, ChatRoom chatRoom, ChatMessage chatMessage){
        Vacancy vacancy = vacancyService.findById(currentVacancyId);
        if(user.getRespondedVacancies().contains(vacancy)) throw new RespondVacancyException("You have already respond on this vacancy");

        chatRoom.addChatMessage(chatMessage);
        user.addChatRoom(chatRoom);
        user.addRespondedVacancy(vacancy);

        userRepo.save(user);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    private void sendMessageForPasswordRecover(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hi %s.  \n" +
                            "Please, visit next link for change password on your account: http://localhost:8080/change-password/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Change password", message);
        }
    }

    private void sendMessageForAcivateUser(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hi %s. Welcome to my first simple site! \n" +
                            "Please, visit next link for activation your account: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public void sendMessageForChangeEmail(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hi %s. Welcome to my first simple site! \n" +
                            "Please, visit next link for change your email: http://localhost:8080/user/userupdate/%s/%s",
                    user.getUsername(),
                    user.getEmail(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Change email", message);
        }
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }
}
