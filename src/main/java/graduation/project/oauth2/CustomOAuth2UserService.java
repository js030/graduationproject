package graduation.project.oauth2;

import graduation.project.model.AuthProvider;
import graduation.project.model.User;
import graduation.project.oauth2.user.OAuth2UserInfo;
import graduation.project.oauth2.user.OAuth2UserInfoFactory;
import graduation.project.repository.UserRepository;
import graduation.project.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try{
             return processOAuth2User(oAuth2UserRequest, oAuth2User);
        }catch (OAuth2AuthenticationException ex){
            throw ex;
        }catch (Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuthUserInfo = OAuth2UserInfoFactory.getOAuthUserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuthUserInfo.getEmail())) {
            throw new OAuth2AuthenticationException("Email not found from OAuth Provider");
        }

        //email은 있음

        //email과 매치되는 user가 있는지 탐색

        Optional<User> userOptional = userRepository.findByEmail(oAuthUserInfo.getEmail());
        User user;
        if (userOptional.isPresent()){
            user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))){
                throw new OAuth2AuthenticationException("Please use diffrent platform you registered");
            }

            user = updateExistingUser(user, oAuthUserInfo);
        }else{
            user = registerNewUser(oAuth2UserRequest, oAuthUserInfo);
        }
        return CustomUserDetail.create(user, oAuth2User.getAttributes());
    }


    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo){
        User user = new User();

        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setUsername(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo){
        existingUser.setUsername(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}
