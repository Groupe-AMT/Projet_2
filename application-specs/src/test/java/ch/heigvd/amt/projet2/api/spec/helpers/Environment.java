package ch.heigvd.amt.projet2.api.spec.helpers;

import ch.heigvd.amt.projet2.api.DefaultApi;

import java.io.IOException;
import java.util.Properties;
import java.util.TimeZone;

public class Environment {

    private DefaultApi api = new DefaultApi();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heigvd.amt.projet2.fruits.server.url");
        api.getApiClient().setBasePath(url);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public DefaultApi getApi() {
        return api;
    }



}
