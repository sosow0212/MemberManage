package com.example.membermanage.service;

import com.example.membermanage.entity.User;
import com.example.membermanage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserPageService {
    private final UserRepository userRepository;

    public User findById(int id) {
        User findUser = userRepository.findById(id);
        return findUser;
    }
}
