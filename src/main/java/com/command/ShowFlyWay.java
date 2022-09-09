package com.command;

import com.util.VehicleFactory;
import org.flywaydb.core.Flyway;

public class ShowFlyWay implements Command {
    @Override
    public void execute() {
        System.out.println("DB before FlyWay:");
        VehicleFactory.printAll();
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/NixHibernate", "postgres", "admin")
                .baselineOnMigrate(true)
                .locations("db/migration")
                .load();
        flyway.migrate();
        System.out.println("DB after FlyWay:");
        VehicleFactory.printAll();
    }
}
