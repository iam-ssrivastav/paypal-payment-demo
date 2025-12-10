# PayPal Payment Gateway API Contract & Flow Guide

## 1. Full Business Flow (Order → Payment)

### Step 1: Create Internal Order
First, create an order in your system to track the customer and items.
*   **Endpoint**: `POST /api/orders/create`
*   **Result**: Returns an `Order` object with an `id`.

### Step 2: Create Payment (Linked to Order)
Now create the PayPal payment, linking it to the internal order ID from Step 1.
*   **Endpoint**: `POST /api/payments/create`
*   **Input**: Include `"orderId": <id_from_step_1>` in the request body.
*   **Result**: Returns an `approvalUrl`.

### Step 3: User Approval
Redirect the user to the `approvalUrl`. They will approve the payment on PayPal's website.

### Step 4: Execute Payment
Once redirected back to your site, take the `paymentId` and `PayerID` and finalize the transaction.
*   **Endpoint**: `POST /api/payments/execute`
*   **Result**: Payment is complete, and the Internal Order status is automatically updated to `PROCESSING`.

---

## 2. API Endpoints & Contracts

### A. Create Internal Order
**Endpoint**: `POST /api/orders/create`

**Request Body (`CreateOrderRequest`)**:
```json
{
  "description": "2x T-Shirts",
  "amount": 50.00,
  "customerEmail": "customer@example.com",
  "customerName": "Jane Doe"
}
```

**Response Body (`Order`)**:
```json
{
  "id": 1,
  "orderNumber": "ORD-A1B2C3D4",
  "description": "2x T-Shirts",
  "total": 50.00,
  "status": "PENDING",
  "customerEmail": "customer@example.com",
  "createdAt": "2023-10-27T10:00:00"
}
```

### B. Create Payment
**Endpoint**: `POST /api/payments/create`

**Request Body (`CreatePaymentRequest`)**:
```json
{
  "amount": 50.00,
  "currency": "USD",
  "description": "Payment for Order #1",
  "paymentIntent": "CAPTURE", 
  "orderId": 1, 
  "idempotencyKey": "unique-uuid-123456"
}
```
*   `orderId`: The ID returned from the **Create Internal Order** step.

**Response Body (`PaymentResponse`)**:
```json
{
  "id": 1,
  "paypalPaymentId": "PAYID-MR08107765108343K372793S",
  "status": "CREATED",
  "amount": 50.00,
  "currency": "USD",
  "approvalUrl": "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-5W1960256D480302W",
  "message": "Payment created. Redirect user to approvalUrl to complete payment."
}
```

### C. Execute Payment
**Endpoint**: `POST /api/payments/execute`
*Called after the user approves the payment on PayPal.*

**Request Body (`ExecutePaymentRequest`)**:
```json
{
  "paymentId": "PAYID-MR08107765108343K372793S",
  "payerId": "QYR5EZ8XJ9B2S"
}
```

**Response Body (`PaymentResponse`)**:
```json
{
  "id": 1,
  "paypalPaymentId": "PAYID-MR08107765108343K372793S",
  "status": "CAPTURED",
  "amount": 50.00,
  "message": "Payment completed successfully!",
  "capturedAmount": 50.00
}
```

### D. Capture Payment
**Endpoint**: `POST /api/payments/capture`
*Only used if the original payment intent was `AUTHORIZE`.*

**Request Body (`CapturePaymentRequest`)**:
```json
{
  "authorizationId": "6SK65392E5437831K",
  "amount": 50.00,
  "currency": "USD",
  "isFinalCapture": true
}
```

### E. Refund Payment
**Endpoint**: `POST /api/payments/refund`

**Request Body (`RefundRequest`)**:
```json
{
  "captureId": "8SL65392E5437831M", 
  "amount": 25.00, 
  "currency": "USD",
  "reason": "Customer requested refund"
}
```

### F. Webhook Contract
**Endpoint**: `POST /api/webhooks/paypal`

PayPal sends these notifications automatically. You can simulate them in the PayPal Developer Dashboard.

**Sample Payload (`PAYMENT.CAPTURE.COMPLETED`)**:
```json
{
  "id": "WH-12345-XY",
  "event_type": "PAYMENT.CAPTURE.COMPLETED",
  "resource_type": "capture",
  "resource": {
    "id": "123456789",
    "amount": {
      "total": "50.00",
      "currency": "USD"
    },
    "status": "COMPLETED"
  }
}
```
