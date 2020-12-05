package rs.ac.uns.ftn.uppservice.common;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeProvider {

    private static final long serialVersionUID = -3301695478208950415L;

    public Date now() {
        return new Date();
    }
}
