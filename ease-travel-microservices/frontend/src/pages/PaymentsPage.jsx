import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import { useAuth } from "../context/AuthContext";
import { PAYMENT_METHODS } from "../config";
import { getBookingsByUser } from "../api/bookingApi";
import { getPaymentsByUser, processPayment } from "../api/paymentApi";
import { formatCurrency, formatDateTime } from "../utils/format";

export default function PaymentsPage() {
  const { token, authState } = useAuth();
  const [bookings, setBookings] = useState([]);
  const [payments, setPayments] = useState([]);
  const [form, setForm] = useState({ bookingId: "", amount: "", method: PAYMENT_METHODS[0] });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const refreshData = async () => {
    setLoading(true);
    setError("");
    try {
      const [bookingList, paymentList] = await Promise.all([
        getBookingsByUser(authState.userId, token),
        getPaymentsByUser(authState.userId, token)
      ]);
      setBookings(bookingList);
      setPayments(paymentList);
      if (!form.bookingId && bookingList.length > 0) {
        setForm((prev) => ({ ...prev, bookingId: String(bookingList[0].id), amount: bookingList[0].totalAmount }));
      }
    } catch (err) {
      setError(err.message || "Failed to load payment data");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    refreshData();
  }, [token, authState.userId]);

  const onSelectBooking = (bookingId) => {
    const selected = bookings.find((item) => String(item.id) === bookingId);
    setForm((prev) => ({ ...prev, bookingId, amount: selected?.totalAmount || "" }));
  };

  const onSubmit = async (event) => {
    event.preventDefault();
    setError("");
    setSuccess("");

    try {
      await processPayment(
        {
          bookingId: Number(form.bookingId),
          userId: authState.userId,
          amount: Number(form.amount),
          method: form.method
        },
        token
      );
      setSuccess("Payment processed successfully");
      refreshData();
    } catch (err) {
      setError(err.message || "Payment failed");
    }
  };

  return (
    <Layout title="Payments">
      <section className="content-grid">
        <form className="panel form-grid" onSubmit={onSubmit}>
          <h3>Process Payment</h3>
          <label>
            Booking
            <select value={form.bookingId} onChange={(e) => onSelectBooking(e.target.value)} required>
              <option value="">Select booking</option>
              {bookings.map((booking) => (
                <option key={booking.id} value={booking.id}>
                  #{booking.id} | Trip {booking.tripId} | {booking.status}
                </option>
              ))}
            </select>
          </label>

          <label>
            Amount
            <input
              type="number"
              min="1"
              step="0.01"
              value={form.amount}
              onChange={(e) => setForm((prev) => ({ ...prev, amount: e.target.value }))}
              required
            />
          </label>

          <label>
            Method
            <select
              value={form.method}
              onChange={(e) => setForm((prev) => ({ ...prev, method: e.target.value }))}
            >
              {PAYMENT_METHODS.map((method) => (
                <option key={method} value={method}>
                  {method}
                </option>
              ))}
            </select>
          </label>

          <button type="submit">Process payment</button>
          {error ? <p className="error-text">{error}</p> : null}
          {success ? <p className="success-text">{success}</p> : null}
        </form>

        <div className="panel table-wrap">
          <div className="stack-row">
            <h3>Payment History</h3>
            <button type="button" onClick={refreshData}>
              Refresh
            </button>
          </div>
          {loading ? <p>Loading...</p> : null}
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Booking</th>
                <th>Amount</th>
                <th>Method</th>
                <th>Status</th>
                <th>Txn ID</th>
                <th>Date</th>
              </tr>
            </thead>
            <tbody>
              {payments.map((payment) => (
                <tr key={payment.id}>
                  <td>{payment.id}</td>
                  <td>{payment.bookingId}</td>
                  <td>{formatCurrency(payment.amount)}</td>
                  <td>{payment.method}</td>
                  <td>{payment.status}</td>
                  <td>{payment.transactionId}</td>
                  <td>{formatDateTime(payment.paymentDate)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </Layout>
  );
}
