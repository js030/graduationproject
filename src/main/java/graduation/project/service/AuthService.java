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

}

