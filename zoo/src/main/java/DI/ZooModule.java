package DI;

import Services.Clinic;
import Services.Zoo;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ZooModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Zoo.class).in(Singleton.class);

        bind(Clinic.class).in(Singleton.class);
    }
}
