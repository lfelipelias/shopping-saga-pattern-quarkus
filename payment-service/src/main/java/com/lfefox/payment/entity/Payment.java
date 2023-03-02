package com.lfefox.payment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
/**
 * Felipe.Elias
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment extends PanacheEntityBase {
}
