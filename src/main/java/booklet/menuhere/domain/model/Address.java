package booklet.menuhere.domain.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String zipcode;
    private String defaultAddress;
    private String detailAddress;

    protected Address() {
    }

    public Address(String zipcode, String defaultAddress, String detailAddress) {
        this.zipcode = zipcode;
        this.defaultAddress = defaultAddress;
        this.detailAddress = detailAddress;
    }
}
