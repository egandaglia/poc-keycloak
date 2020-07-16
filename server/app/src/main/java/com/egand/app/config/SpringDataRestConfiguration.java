package com.egand.app.config;

import com.egand.orm.db.entities.Article;
import com.egand.orm.db.entities.Category;
import com.egand.orm.db.entities.User;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class SpringDataRestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        // CORS CONFIGURATION
        config.getCorsRegistry().addMapping("/**")
                .allowedOrigins("http://localhost:4200");
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
        config.useHalAsDefaultJsonMediaType(false);
        config.exposeIdsFor(Category.class);

        // HTTP METHODS EXPOSURE CONFIGURATION
        HttpMethod[] unsupportedActions = {HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.POST};
        Class<?>[] apiClasses = {Article.class, User.class};

        for(Class<?> c : apiClasses) {
            config.getExposureConfiguration()
                    .forDomainType(c)
                    .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)))
                    .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unsupportedActions)));
        }

    }
}
