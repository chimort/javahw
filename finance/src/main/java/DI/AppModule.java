package DI;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import Database.DbProxy;
import Database.InMemoryDb;
import FinanceFassade.FinanceFassade;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public DbProxy provideDbProxy() {
        return new InMemoryDb();
    }

    @Provides
    @Singleton
    public FinanceFassade provideFinanceFassade(DbProxy dbProxy) {
        return new FinanceFassade(dbProxy);
    }
}
