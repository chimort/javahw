package payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        excludeName = {
                "org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration",
                "org.springframework.boot.autoconfigure.r2dbc.R2dbcDataAutoConfiguration"
        }
)
public class PaymentsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentsServiceApplication.class, args);
    }
}
