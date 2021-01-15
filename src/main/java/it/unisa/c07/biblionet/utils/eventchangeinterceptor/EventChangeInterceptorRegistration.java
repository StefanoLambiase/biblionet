package it.unisa.c07.biblionet.utils.eventchangeinterceptor;

import java.util.Map;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
public class EventChangeInterceptorRegistration
    implements HibernatePropertiesCustomizer {

    @Override
    public void customize(final Map<String, Object> hibernateProperties) {
        EventChangeInterceptor.getInstance().subscribe(new MailConsumer());
        hibernateProperties.put(
            "hibernate.session_factory.interceptor",
            EventChangeInterceptor.getInstance()
        );
    }

}
