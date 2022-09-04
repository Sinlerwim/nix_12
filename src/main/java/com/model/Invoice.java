package com.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@org.hibernate.annotations.NamedNativeQuery(name = "selectInvoiceExpensiveThat",
        query = "SELECT invoice.id, invoice.created FROM " +
                "(SELECT V.invoice_id, SUM(V.price) as invoice_price " +
                "FROM (SELECT A.invoice_id, A.price " +
                "FROM public.auto as A " +
                "UNION " +
                "SELECT B.invoice_id, B.price " +
                "FROM public.bus as B " +
                "UNION " +
                "SELECT T.invoice_id, T. price " +
                "FROM public.truck as T) as V " +
                "WHERE V.invoice_id is not null " +
                "GROUP BY V.Invoice_id " +
                "HAVING SUM(V.price) > :boundPrice) as S " +
                "INNER JOIN public.invoice " +
                "ON invoice.id = S.invoice_id;",
        resultClass = Invoice.class)

@NamedNativeQuery(name = "invoicesGroupedBySum",
                    query = "SELECT SS.invoice_price, COUNT(SS.id) FROM\n" +
                "(SELECT S.id, S.invoice_price FROM\n" +
                "(SELECT V.id, SUM(V.price) as invoice_price \n" +
                "FROM (SELECT A.id, A.price\n" +
                "FROM public.auto as A\n" +
                "UNION\n" +
                "SELECT B.invoice_id, B.price\n" +
                "FROM public.bus as B\n" +
                "UNION\n" +
                "SELECT T.invoice_id, T. price\n" +
                "FROM public.truck as T) as V\n" +
                "WHERE V.id is not null\n" +
                "GROUP BY V.id\n" +
                "HAVING SUM(V.price) > 0) as S\n" +
                "INNER JOIN public.invoice\n" +
                "ON invoice.id = S.id) as SS\n" +
                "GROUP BY SS.invoice_price;",
resultClass = Invoice.class)

@Getter
@Setter
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private LocalDateTime created;

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", created=" + created +
                '}';
    }
}