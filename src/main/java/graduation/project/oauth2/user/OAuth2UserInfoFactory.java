package graduation.project.oauth2.user;

import graduation.project.model.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuthUserInfo(String registrationId, Map<String, Object> attributes){
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())){
            return new GoogleOAuth2UserInfo(attributes);
        }else if (registrationId.equalsIgnoreCase(AuthProvider.kakao.toString())){
            return new KakaoOAuth2UserInfo(attributes);
        }else if (registrationId.equalsIgnoreCase(AuthProvider.naver.toString())){
            return new NaverOAuth2UserInfo(attributes);
        }
        else{
            throw new IllegalStateException("Sorry! Login with " + registrationId + " is not supported");
        }
    }
}
