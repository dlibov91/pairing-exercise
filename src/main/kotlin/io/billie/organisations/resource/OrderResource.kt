package io.billie.organisations.resource

import io.billie.organisations.data.OrderNotFound
import io.billie.organisations.data.UnableToFindOrganisation
import io.billie.organisations.service.OrderService
import io.billie.organisations.viewmodel.Entity
import io.billie.organisations.viewmodel.OrderRequest
import io.billie.organisations.viewmodel.OrderResponse
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("orders")
class OrderResource(val service: OrderService) {

    @GetMapping
    fun index(@RequestParam orderId: UUID): List<OrderResponse> = service.findOrders(orderId)

    @PostMapping
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        description = "Accepted the new order",
                        content = [
                            (Content(
                                    mediaType = "application/json",
                                    array = (ArraySchema(schema = Schema(implementation = Entity::class)))
                            ))]
                ),
                ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])]
    )
    fun createOrder(@Valid @RequestBody order: OrderRequest): Entity {
        try {
            val id = service.createOrder(order)
            return Entity(id)
        } catch (e: UnableToFindOrganisation) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PostMapping("/{orderId}/shipped")
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        description = "Order marked as shipped",
                        content = [Content(mediaType = "application/json")]
                ),
                ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
                ApiResponse(responseCode = "404", description = "Order not found", content = [Content()])]
    )
    fun markOrderAsShipped(@PathVariable orderId: UUID): ResponseEntity<Unit> {
        try {
            service.markOrderAsShipped(orderId)
            return ResponseEntity.ok().build()
        } catch (e: OrderNotFound) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}