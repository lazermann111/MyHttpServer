# MyHttpServer
Basic HTTP server on base of com.sun.net.httpserver.HttpServer with in-mem storage (Hazelcast) and thread-safety.

Check HttpServer.postman_collection for API and loadTest.jmx for load test setup with JMeter.

What can be improved:
1) Spring DI for repositories
2) Adding Spring Data Hazelcast
3) Providing more efficient way to limit TopResults collection