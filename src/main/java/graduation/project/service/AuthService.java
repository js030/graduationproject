package graduation.project.service;

import graduation.project.model.User;
import graduation.project.repository.UserRepository;
import graduation.project.repository.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.PostConstruct;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void initialStart(){
        User user1 = new User(null, "kh","abc@abc", "4234", 24, "남성", UserType.BLIND, null);
        User user2 = new User(null, "sj", "cdb@cdb", "123", 23, "여성", UserType.DRIVER, "15");
        userRepository.save(user1);
        userRepository.save(user2);
    }



}

