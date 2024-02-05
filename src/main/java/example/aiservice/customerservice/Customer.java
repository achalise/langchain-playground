package example.aiservice.customerservice;

public record Customer(String correlationId, String email, String firstName, String lastName, String address, String rebateType, long amount, String status){}
