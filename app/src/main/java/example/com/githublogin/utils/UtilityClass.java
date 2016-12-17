package example.com.githublogin.utils;

import example.com.githublogin.extras.Constants;
import example.com.githublogin.pojos.ParametersForAccessToken;


public class UtilityClass {

    public static String ClientID = "d9e09d2067d7d831bc37";
    public static String client_secret = "a19815acfba283caba8e9091209d474592353095";
    public static String redirect_uri = "https://github.com";
    public static String code = GitLoginApplicationClass.getCommonSharedPreference().getString(Constants.CODE, "");
    public static String state = GitLoginApplicationClass.getCommonSharedPreference().getString(Constants.STATE, "");


    public static ParametersForAccessToken getParametersForAccessTokens() {
        ParametersForAccessToken pfat = new ParametersForAccessToken();
        pfat.setClient_id(ClientID);
        pfat.setClient_secret(client_secret);
        pfat.setRedirect_uri(redirect_uri);
        pfat.setCode(code);
        pfat.setState(state);
        return pfat;

    }


}
