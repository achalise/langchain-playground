package example.aiservice.customerservice;

import dev.langchain4j.agent.tool.Tool;
import opennlp.tools.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
public class CustomerService {
    private final Collection<Customer> customers = new ArrayList<>();
    public CustomerService() {
        customers.add(new Customer("18666A", "curtis@gmail.com", "Brock", "Curtis", "76 Chalmers Ave, Redfern 2119", "FLOODS", 1000, "SUBMITTED"));
        customers.add(new Customer("19966A", "curtis@gmail.com", "Brock", "Curtis", "76 Chalmers Ave, Redfern 2119", "BUSH_FIRE", 550, "SUBMITTED"));
        customers.add(new Customer("21966A", "curtis@gmail.com", "Brock", "Curtis", "76 Chalmers Ave, Redfern 2119", "HAIL_DAMAGE", 250, "SUBMITTED"));
        customers.add(new Customer("36559B","blogg@gmail.com", "Joe", "Blogg", "40 Bridge Road, Sydney, 2000", "BUSH_FIRE", 300, "APPROVED"));
        customers.add(new Customer("91137D", "duran@gmail.com","Leandro", "Duran", "199 George Street, Newtown, 2000", "HAIL_DAMAGE", 300, "PAYMENT_SUBMITTED"));

    }

    @Tool
    public List<Customer> findCustomer(String email, String correlationId) {
        if (email != null && !StringUtil.isEmpty(email)) {
            return getCustomerApplications(email);
        }
        return getCustomer(correlationId);
    }

    private List<Customer> getCustomerApplications(String email) {
        System.out.println("Finding customer with correlationId " + email);
        var custList = customers.stream().filter(customer -> customer.email().equals(email)).toList();
        if (custList.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }
        return custList;
    }

    private List<Customer> getCustomer(String correlationId) {
        System.out.println("Finding customer with correlationId " + correlationId);
        var custList = customers.stream().filter(customer -> customer.correlationId().equals(correlationId)).toList();
        if (custList.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }
        return custList.stream().filter(c -> c.correlationId().equals(correlationId)).toList();
    }
}
