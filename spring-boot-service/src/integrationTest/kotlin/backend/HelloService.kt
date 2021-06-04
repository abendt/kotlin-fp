package backend

import domain.sayHello
import org.springframework.stereotype.Service

@Service
class HelloService(private val repository: HelloRepository) {
    fun sayHelloTo(name: String) {
        repository.store(sayHello(name))
    }
}
