package io.billie.organisations.viewmodel

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Table("ORDERS")
data class OrderResponse(
        @JsonProperty("order_id") val orderId: UUID, @JsonProperty("order_state") val orderState: OrderState,
        @JsonProperty("organization_id") val organizationId: UUID,
        @JsonProperty("customer_email") val customerEmail: String,
        @JsonProperty("total_amount") val totalAmount: BigDecimal,
        @JsonProperty("created_at") val createdAt: LocalDateTime,
        @JsonProperty("updated_at") val updatedAt: LocalDateTime
)