package ma.sig.events.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, ma.sig.events.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, ma.sig.events.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, ma.sig.events.domain.User.class.getName());
            createCache(cm, ma.sig.events.domain.Authority.class.getName());
            createCache(cm, ma.sig.events.domain.User.class.getName() + ".authorities");
            createCache(cm, ma.sig.events.domain.Event.class.getName());
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".eventForms");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".eventFields");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".eventControls");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".areas");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".fonctions");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".categories");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".printingModels");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".codes");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".infoSupps");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".attachements");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".organizs");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".photoArchives");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".sites");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".notes");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".operationHistories");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".printingCentres");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".settings");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".printingServers");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".checkAccreditationHistories");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".checkAccreditationReports");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".accreditationTypes");
            createCache(cm, ma.sig.events.domain.Event.class.getName() + ".dayPassInfos");
            createCache(cm, ma.sig.events.domain.PrintingType.class.getName());
            createCache(cm, ma.sig.events.domain.PrintingType.class.getName() + ".printingCentres");
            createCache(cm, ma.sig.events.domain.LogHistory.class.getName());
            createCache(cm, ma.sig.events.domain.Area.class.getName());
            createCache(cm, ma.sig.events.domain.Area.class.getName() + ".fonctions");
            createCache(cm, ma.sig.events.domain.Fonction.class.getName());
            createCache(cm, ma.sig.events.domain.Fonction.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.Fonction.class.getName() + ".areas");
            createCache(cm, ma.sig.events.domain.Category.class.getName());
            createCache(cm, ma.sig.events.domain.Category.class.getName() + ".fonctions");
            createCache(cm, ma.sig.events.domain.Category.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.PrintingModel.class.getName());
            createCache(cm, ma.sig.events.domain.PrintingModel.class.getName() + ".printingCentres");
            createCache(cm, ma.sig.events.domain.PrintingModel.class.getName() + ".accreditationTypes");
            createCache(cm, ma.sig.events.domain.PrintingModel.class.getName() + ".categories");
            createCache(cm, ma.sig.events.domain.CodeType.class.getName());
            createCache(cm, ma.sig.events.domain.CodeType.class.getName() + ".codes");
            createCache(cm, ma.sig.events.domain.Code.class.getName());
            createCache(cm, ma.sig.events.domain.Code.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.AccreditationType.class.getName());
            createCache(cm, ma.sig.events.domain.AccreditationType.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.AuthentificationType.class.getName());
            createCache(cm, ma.sig.events.domain.Status.class.getName());
            createCache(cm, ma.sig.events.domain.Status.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.AttachementType.class.getName());
            createCache(cm, ma.sig.events.domain.AttachementType.class.getName() + ".attachements");
            createCache(cm, ma.sig.events.domain.InfoSuppType.class.getName());
            createCache(cm, ma.sig.events.domain.InfoSuppType.class.getName() + ".infoSupps");
            createCache(cm, ma.sig.events.domain.InfoSupp.class.getName());
            createCache(cm, ma.sig.events.domain.Attachement.class.getName());
            createCache(cm, ma.sig.events.domain.Attachement.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.Organiz.class.getName());
            createCache(cm, ma.sig.events.domain.Organiz.class.getName() + ".printingCentres");
            createCache(cm, ma.sig.events.domain.Organiz.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.Civility.class.getName());
            createCache(cm, ma.sig.events.domain.Civility.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.PhotoArchive.class.getName());
            createCache(cm, ma.sig.events.domain.Nationality.class.getName());
            createCache(cm, ma.sig.events.domain.Nationality.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.Country.class.getName());
            createCache(cm, ma.sig.events.domain.Country.class.getName() + ".printingCentres");
            createCache(cm, ma.sig.events.domain.Country.class.getName() + ".cities");
            createCache(cm, ma.sig.events.domain.Country.class.getName() + ".organizs");
            createCache(cm, ma.sig.events.domain.Country.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.City.class.getName());
            createCache(cm, ma.sig.events.domain.City.class.getName() + ".printingCentres");
            createCache(cm, ma.sig.events.domain.City.class.getName() + ".sites");
            createCache(cm, ma.sig.events.domain.City.class.getName() + ".organizs");
            createCache(cm, ma.sig.events.domain.City.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.Site.class.getName());
            createCache(cm, ma.sig.events.domain.Site.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.Accreditation.class.getName());
            createCache(cm, ma.sig.events.domain.Accreditation.class.getName() + ".photoArchives");
            createCache(cm, ma.sig.events.domain.Accreditation.class.getName() + ".infoSupps");
            createCache(cm, ma.sig.events.domain.Accreditation.class.getName() + ".notes");
            createCache(cm, ma.sig.events.domain.Accreditation.class.getName() + ".checkAccreditationHistories");
            createCache(cm, ma.sig.events.domain.Accreditation.class.getName() + ".sites");
            createCache(cm, ma.sig.events.domain.DayPassInfo.class.getName());
            createCache(cm, ma.sig.events.domain.DayPassInfo.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.OperationType.class.getName());
            createCache(cm, ma.sig.events.domain.OperationType.class.getName() + ".operationHistories");
            createCache(cm, ma.sig.events.domain.Sexe.class.getName());
            createCache(cm, ma.sig.events.domain.Sexe.class.getName() + ".accreditations");
            createCache(cm, ma.sig.events.domain.Note.class.getName());
            createCache(cm, ma.sig.events.domain.OperationHistory.class.getName());
            createCache(cm, ma.sig.events.domain.PrintingCentre.class.getName());
            createCache(cm, ma.sig.events.domain.Language.class.getName());
            createCache(cm, ma.sig.events.domain.Language.class.getName() + ".events");
            createCache(cm, ma.sig.events.domain.Language.class.getName() + ".settings");
            createCache(cm, ma.sig.events.domain.Language.class.getName() + ".printingCentres");
            createCache(cm, ma.sig.events.domain.Setting.class.getName());
            createCache(cm, ma.sig.events.domain.Cloning.class.getName());
            createCache(cm, ma.sig.events.domain.PrintingServer.class.getName());
            createCache(cm, ma.sig.events.domain.PrintingServer.class.getName() + ".printingCentres");
            createCache(cm, ma.sig.events.domain.CheckAccreditationHistory.class.getName());
            createCache(cm, ma.sig.events.domain.CheckAccreditationHistory.class.getName() + ".checkAccreditationReports");
            createCache(cm, ma.sig.events.domain.CheckAccreditationReport.class.getName());
            createCache(cm, ma.sig.events.domain.EventForm.class.getName());
            createCache(cm, ma.sig.events.domain.EventForm.class.getName() + ".eventFields");
            createCache(cm, ma.sig.events.domain.EventField.class.getName());
            createCache(cm, ma.sig.events.domain.EventField.class.getName() + ".eventControls");
            createCache(cm, ma.sig.events.domain.EventControl.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
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
