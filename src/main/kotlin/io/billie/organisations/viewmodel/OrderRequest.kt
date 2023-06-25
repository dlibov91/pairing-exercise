package io.billie.organisations.viewmodel

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Table("ORDERS")
data class OrderRequest(
        @field:NotNull @JsonProperty("order_id") val orderId: UUID,
        @field:NotNull @JsonProperty("organization_id") val organizationId: UUID,
        @field:NotBlank @JsonProperty("customer_email") val customerEmail: String,
        @field:NotNull @JsonProperty("total_amount") val totalAmount: BigDecimal,
)
