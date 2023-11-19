package booklet.menuhere.domain;

import javax.persistence.Embeddable;

@Embeddable
public enum orderStatus {
    ORDER, DELIVERING, COMPLETE , CANCLE
//    주문,   배달중,    배달완료,    주문취소
}
