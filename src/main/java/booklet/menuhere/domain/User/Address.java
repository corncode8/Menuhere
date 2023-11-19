package booklet.menuhere.domain.User;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String fullAddress;
    private String zipcode;

    protected Address() {
    }

    public Address(String fullAddress, String zipcode) {
        this.fullAddress = fullAddress;
        this.zipcode = zipcode;
    }
}
