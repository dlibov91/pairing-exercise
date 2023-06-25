CREATE TABLE IF NOT EXISTS organisations_schema.orders
(
    order_id UUID NOT NULL PRIMARY KEY,
    organization_id UUID NOT NULL,
    order_state VARCHAR(20) NOT NULL,
    customer_email VARCHAR(100) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
    updated_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);


