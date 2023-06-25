package io.billie.organisations.data

import io.billie.organisations.viewmodel.OrderRequest
import io.billie.organisations.viewmodel.OrderResponse
import io.billie.organisations.viewmodel.OrderState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Repository
class OrderRepository {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Transactional(readOnly = true)
    fun findOrders(orderId: UUID): List<OrderResponse> {
        val query = "SELECT * FROM organisations_schema.orders WHERE order_id = ?"
        return jdbcTemplate.query(query, orderMapper(), orderId)
    }

    @Transactional
    fun create(order: OrderRequest, state: OrderState): UUID {
        if (!valuesValid(order)) {
            throw UnableToFindOrganisation(order.organizationId.toString())
        }
        return createOrder(order, state)
    }

    @Transactional
    fun updateOrderState(orderId: UUID, state: OrderState) {
        val sql = "UPDATE organisations_schema.orders SET order_state = ?, updated_at = current_timestamp WHERE order_id = ?"
        val affectedRows = jdbcTemplate.update(sql, state.toString(), orderId)
        if (affectedRows == 0) {
            throw OrderNotFound(orderId.toString())
        }
    }

    private fun valuesValid(order: OrderRequest): Boolean {
        val reply: Int? = jdbcTemplate.query(
                "select count(id) from organisations_schema.organisations o WHERE o.id = ?",
                ResultSetExtractor {
                    it.next()
                    it.getInt(1)
                },
                order.organizationId
        )
        return (reply != null) && (reply > 0)
    }

    private fun createOrder(order: OrderRequest, state: OrderState): UUID {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        val sql = """
        INSERT INTO organisations_schema.orders (
            order_id, 
            organization_id, 
            order_state, 
            customer_email,
            total_amount, 
            created_at, 
            updated_at
        ) VALUES (?, ?, ?, ?, ?, ?, ?)
    """.trimIndent()

        val creationTimestamp = Timestamp.valueOf(LocalDateTime.now())
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(sql, arrayOf("order_id"))
            val orderId = UUID.randomUUID()

            ps.setObject(1, orderId)
            ps.setObject(2, order.organizationId)
            ps.setString(3, state.toString())
            ps.setString(4, order.customerEmail)
            ps.setBigDecimal(5, order.totalAmount)
            ps.setTimestamp(6, creationTimestamp)
            ps.setTimestamp(7, creationTimestamp)
            ps
        }, keyHolder)

        return keyHolder.getKeyAs(UUID::class.java)!!
    }

    private fun orderMapper() = RowMapper<OrderResponse> { rs: ResultSet, _: Int ->
        OrderResponse(
                orderId = rs.getObject("order_id", UUID::class.java),
                orderState = OrderState.valueOf(rs.getString("order_state")),
                organizationId = rs.getObject("organization_id", UUID::class.java),
                customerEmail = rs.getString("customer_email"),
                totalAmount = rs.getBigDecimal("total_amount"),
                createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
                updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
        )
    }
}