package com.intuite.shopped.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.intuite.shopped.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.intuite.shopped.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.intuite.shopped.domain.User.class.getName());
            createCache(cm, com.intuite.shopped.domain.Authority.class.getName());
            createCache(cm, com.intuite.shopped.domain.User.class.getName() + ".authorities");
            createCache(cm, com.intuite.shopped.domain.PersistentToken.class.getName());
            createCache(cm, com.intuite.shopped.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, com.intuite.shopped.domain.UserProfile.class.getName());
            createCache(cm, com.intuite.shopped.domain.Cookies.class.getName());
            createCache(cm, com.intuite.shopped.domain.Follower.class.getName());
            createCache(cm, com.intuite.shopped.domain.Transaction.class.getName());
            createCache(cm, com.intuite.shopped.domain.NotificationType.class.getName());
            createCache(cm, com.intuite.shopped.domain.Notification.class.getName());
            createCache(cm, com.intuite.shopped.domain.Commendation.class.getName());
            createCache(cm, com.intuite.shopped.domain.LogType.class.getName());
            createCache(cm, com.intuite.shopped.domain.Log.class.getName());
            createCache(cm, com.intuite.shopped.domain.Cart.class.getName());
            createCache(cm, com.intuite.shopped.domain.CartHasRecipe.class.getName());
            createCache(cm, com.intuite.shopped.domain.Comment.class.getName());
            createCache(cm, com.intuite.shopped.domain.ReportType.class.getName());
            createCache(cm, com.intuite.shopped.domain.ReportComment.class.getName());
            createCache(cm, com.intuite.shopped.domain.ReportPost.class.getName());
            createCache(cm, com.intuite.shopped.domain.Recipe.class.getName());
            createCache(cm, com.intuite.shopped.domain.RecipeShared.class.getName());
            createCache(cm, com.intuite.shopped.domain.Collection.class.getName());
            createCache(cm, com.intuite.shopped.domain.CollectionHasRecipe.class.getName());
            createCache(cm, com.intuite.shopped.domain.Ingredient.class.getName());
            createCache(cm, com.intuite.shopped.domain.RecipeHasIngredient.class.getName());
            createCache(cm, com.intuite.shopped.domain.CartHasIngredient.class.getName());
            createCache(cm, com.intuite.shopped.domain.TagType.class.getName());
            createCache(cm, com.intuite.shopped.domain.RecipeTag.class.getName());
            createCache(cm, com.intuite.shopped.domain.RecipeHasRecipeTag.class.getName());
            createCache(cm, com.intuite.shopped.domain.IngredientTag.class.getName());
            createCache(cm, com.intuite.shopped.domain.IngredientHasIngredientTag.class.getName());
            createCache(cm, com.intuite.shopped.domain.Award.class.getName());
            createCache(cm, com.intuite.shopped.domain.Post.class.getName());
            createCache(cm, com.intuite.shopped.domain.Bite.class.getName());
            createCache(cm, com.intuite.shopped.domain.Message.class.getName());
            createCache(cm, com.intuite.shopped.domain.Catalogue.class.getName());
            createCache(cm, com.intuite.shopped.domain.Unit.class.getName());
            createCache(cm, com.intuite.shopped.domain.Bundle.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
