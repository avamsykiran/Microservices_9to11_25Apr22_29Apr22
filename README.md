MicroServices 
=========================================================================================

    Modern App Expectations

        + Interaperability
        + Granular Scalability
        + Scope to implement using enchanced method / techniques.
        + Granular Level Maintanability

    A monolythic application has all the moduels in a single deployment unit. Hence
    it is definitly not possible to scale an independent module.

    Microservices is an eco-system of co-operative isolated apps that function as a
    single Application. As the apps are isolated and independent, each of the app
        1. can be developed in a different environment - interaperable
        2. granular scalability is also possible as the apps are independent.
        3. and so on all modern expectations can be met.

    Challenges while adopting to Microservices

        1. Decomposition
            converting a monolythic design into a microservice design
        2. Integration
            a. inter-service communication
            b. distributed transaction
            c. common url for a client-app to talk tot he entire eco-system of microservices.
            d. auto-scaling
        3. Maintability
        4. Monitoring 
        5. Distributed Tracing

    Microservices Design Patterns

        Decomposition Design Patterns
            Decomposition by Domain
            Decomposition by Sub-domain
        Integration Design Patterns
            Api Gateway Pattern
            Aggregator Pattern
            Client Side Component Pattern
        Database Design Patterns
            Database Per Service
            Shaeed Database            
            Saga Pattern
            CQRS Pattern
        Observability Design Patterns
            Log Aggregator
            Performence Metrics Aggregator
            Distributed Tracing
        Cross Cutting Design Patterns
            Discovery Service 
            Circuite Breaker
            External Configuaration


    Decomposition by Domain
        suggest that each domain (module) of a monolythic ideas turns out to be a microservice.


        e-commerce
            sales service
            orders service
            inventory service
            vendor service
            customer service
        
        health-case system
            pharmacy service
            patients service
            insurance service
            employee service
            labsAndreports service

        budgettracking 
            accountholders service
            txns service
            statement service

    Decomposition by sub-domain
        how do we manage the god-classes that make existence in almost all microservices.
        a sub-domain otherwise called the context is the set of requirements of the
        current microservice. Suggested that the god-class be bounded to that context.
        Hence the term bounded-context.

        budgettracking 
            profiles service
                AccountHolder Entity
                    Long ahId
                    String fullName
                    String mobile
                    String mailId

            txns service
                AccountHolder Entity
                    Long ahId
                    Double currentBalance
                    Set<Txn> txns
                Txn           Entity
                    Long txnId
                    String header
                    Double amount
                    TxnType type
                    LocalDate txnDate
                    AccountHolder holder

            statement service
                AccountHolder Model
                    Long ahId
                    String fullName
                    String mobile
                    String mailId
                    Double currentBalance

                Txn           Model
                    Long txnId
                    String header
                    Double amount
                    TxnType type
                    LocalDate txnDate

                Statement     Model
                    LocalDate start
                    LocalDate end
                    AccountHolder profile
                    Set<Txn> txns
                    totalCredit
                    totalDebit
                    statementBalance
    
    Implementing Budget-tracker
        Step#1  implementing decoposed servce and do inter-service communication
            in.bt:bt-profiles
            in.bt:bt-txns
            in.bt:bt-statement

        Step#2  implementing discovery service and client side load balancing
        Step#3  implementing api-gateway
        Step#4  implementing circuit breaking
        Step#5  implementing distributed tracing
        Step#6  implementing external configuaration

