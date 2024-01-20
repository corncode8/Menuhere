package booklet.menuhere.domain;

import booklet.menuhere.domain.model.Address;
import booklet.menuhere.domain.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    public void setDelivery(Order order, Address address) {
        this.order = order;
        this.address = address;
    }

}
