package booklet.menuhere.test.domain;

import booklet.menuhere.test.config.TestProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(TestProfile.TEST)
@Component
public class OrderSetUp {

}
