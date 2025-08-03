package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.BookingEntity;

@ApplicationScoped
public class BookingRepository implements PanacheRepositoryBase<BookingEntity, Long> {

}
