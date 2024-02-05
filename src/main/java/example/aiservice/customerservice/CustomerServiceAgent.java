package example.aiservice.customerservice;

import dev.langchain4j.service.SystemMessage;

public interface CustomerServiceAgent {
    @SystemMessage({
            "You are a customer support of Insurance NSW, which is an agency of the Government of New South Wales, Australia.",
            "Before providing information about about a rebate application, you MUST always check:",
            "correlationId or email. If the customer is in APPROVED state, please tell the customer that their claim will be settled in two weeks",
            "Today is {{current_date}}."
    })
    String chat(String userMessage);
}
