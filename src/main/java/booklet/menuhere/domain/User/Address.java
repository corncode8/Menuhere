package booklet.menuhere.domain.User;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String fullAddress;
    private String zipcode;
}
