package io.billie.functional

import com.fasterxml.jackson.databind.ObjectMapper
import io.billie.functional.data.Fixtures
import io.billie.organisations.service.OrderService
import io.billie.organisations.viewmodel.Entity
import io.billie.organisations.viewmodel.OrderState
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrderResourceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var service: OrderService

    @Test
    fun canNotifyOnOrderShipment() {
        val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/orders").contentType(MediaType.APPLICATION_JSON).content(Fixtures.orderRequestJson())
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val id = mapper.readValue(result.response.contentAsString, Entity::class.java).id
        val createdOrder = service.findOrders(id).first()
        assertThat(createdOrder.orderState, equalTo(OrderState.INITIATED))

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/{id}/shipped", id))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val shippedOrder = service.findOrders(id).first()
        assertThat(shippedOrder.orderState, equalTo(OrderState.SHIPPED))
    }

    @Test
    fun cannotShipNonExistingOrder() {
        val nonExistingId = "61f7a5b3-0b48-4bc1-af84-13e1660ed609"
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/{id}/shipped", nonExistingId))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andReturn()
    }
}