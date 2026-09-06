package com.example.fullstack.HomeFixApplication.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${homefix.cloudinary.name:}")
    private String name;

    @Value("${homefix.cloudinary.key:}")
    private String key;

    @Value("${homefix.cloudinary.secret:}")
    private String secret;

    @Bean
    public Cloudinary cloudinary() {
        // This prints to the console when the app starts so we can verify the keys
        System.out.println("INITIALIZING CLOUDINARY WITH NAME: " + name);

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", name,
                "api_key", key,
                "api_secret", secret
        ));
    }
}