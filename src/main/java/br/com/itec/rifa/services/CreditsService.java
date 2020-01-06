package br.com.itec.rifa.services;

import br.com.itec.rifa.models.User;
import br.com.itec.rifa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditsService {

    @Autowired
    private UserRepository userRepository;

    public void rechargCredits(User user, Long value) {
        user.setCredits(user.getCredits() + value);
        userRepository.save(user);
    }
    public Boolean debitCredits(User user, Long value) {
        if (user.getCredits() < value) return false;
        user.setCredits(user.getCredits() - value);
        userRepository.save(user);
        return true;
    }

}
