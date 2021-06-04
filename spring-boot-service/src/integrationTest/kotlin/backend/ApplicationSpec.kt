package backend

import com.ninjasquad.springmockk.MockkBean
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationSpec {

    @Autowired
    lateinit var helloService: HelloService

    @MockkBean
    lateinit var helloRepository: HelloRepository

    @Test
    fun `can start application`() {
    }

    @Test
    fun `can inject services and mocks`() {
        justRun { helloRepository.store(any()) }

        helloService.sayHelloTo("name")

        verify { helloRepository.store("Hello, name") }
    }
}
