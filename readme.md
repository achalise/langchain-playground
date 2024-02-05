# Exploring LangChain4J for AI applications

LangChain is a framework for developing AI powered applications. It provides abstractions and interfaces to
to provide context to the prompts so that responses and reasoning about the actions are relevant to the specific application

The sample application is using Java version of LangChain, [Langchain4j](https://github.com/langchain4j/langchain4j/tree/main).
Spring Framework is used for wiring the components. The application is a customer service agent for an imaginary Insurance Provider. 
The context is provided via system message
```java
public interface CustomerServiceAgent {
    @SystemMessage({
            "You are a customer support of Insurance NSW, which is an agency of the Government of New South Wales, Australia.",
            "Before providing information about about a rebate application, you MUST always check:",
            "correlationId or email. If the customer is in APPROVED state, please tell the customer that their claim will be settled in two weeks"
    })
    String chat(String userMessage);
}
```
as well as the company's policy document, `claims-pdt-guide.txt` which is processed into embeddings and stored in an im memory vector database.

To run the application: 
- `./mvnw spring-boot:run`

This will start the server at port `8080` and expose a REST endpoint  at `localhost:8080/chat` which accepts the user's
input as query parameter.

Response for non-existent customer:

```
GET http://localhost:8080/chat?question=Pleaes cancel my application. My first name is Joe and my correlationId is 111

HTTP/1.1 200
Content-Type: text/plain;charset=UTF-8
Content-Length: 241
Date: Mon, 05 Feb 2024 09:48:20 GMT
Keep-Alive: timeout=60
Connection: keep-alive

I'm sorry, but I couldn't find any application with the correlation ID '111'. Please double-check the correlation ID and make sure it is correct. If you have any further questions or need assistance, please feel free to contact us at 121345.

Response code: 200; Time: 4584ms (4 s 584 ms); Content length: 241 bytes (241 B)
```

Response for existing applications that has already been approved and pyment subnmitted:
```
GET http://localhost:8080/chat?question=My email is 'duran@gmail.com'. Please find my applications.

HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Content-Length: 471
Date: Mon, 05 Feb 2024 09:46:31 GMT
Keep-Alive: timeout=60
Connection: keep-alive

I found one application associated with the email 'duran@gmail.com'. Here are the details:

- Correlation ID: 91137D
- First Name: Leandro
- Last Name: Duran
- Address: 199 George Street, Newtown, 2000
- Rebate Type: HAIL_DAMAGE
- Amount: $300
- Status: PAYMENT_SUBMITTED

Since your application is in the PAYMENT_SUBMITTED state, the funds should be available within 3 days. If you have any further questions or need assistance, please feel free to contact us at 121345.

Response code: 200; Time: 7719ms (7 s 719 ms); Content length: 471 bytes (471 B)

```

