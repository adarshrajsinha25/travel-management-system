import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import { useAuth } from "../context/AuthContext";
import { cancelBooking, createBooking, getAllBookings, getBookingsByUser } from "../api/bookingApi";
import { getAvailableTrips } from "../api/tripApi";
import { formatCurrency, formatDateTime } from "../utils/format";

export default function BookingsPage() {
  const { token, authState, isAdmin } = useAuth();
  const [form, setForm] = useState({ tripId: "", numberOfGuests: 1 });
  const [trips, setTrips] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const refreshData = async () => {
    setLoading(true);
    setError("");
    try {
      const [tripList, bookingList] = await Promise.all([
        getAvailableTrips(token),
        isAdmin ? getAllBookings(token) : getBookingsByUser(authState.userId, token)
      ]);
      setTrips(tripList);
      setBookings(bookingList);
      if (!form.tripId && tripList.length > 0) {
        setForm((prev) => ({ ...prev, tripId: String(tripList[0].id) }));
      }
    } catch (err) {
      setError(err.message || "Failed to load booking data");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    refreshData();
  }, [token, authState.userId, isAdmin]);

  const onSubmit = async (event) => {
    event.preventDefault();
    setError("");
    setSuccess("");
    if (!form.tripId) {
      setError("Select a trip");
      return;
    }

    try {
      await createBooking(
        {
          userId: authState.userId,
          tripId: Number(form.tripId),
          numberOfGuests: Number(form.numberOfGuests)
        },
        token
      );
      setSuccess("Booking created");
      refreshData();
    } catch (err) {
      setError(err.message || "Failed to create booking");
    }
  };

  const onCancel = async (bookingId) => {
    setError("");
    setSuccess("");
    try {
      await cancelBooking(bookingId, token);
      setSuccess(`Booking ${bookingId} cancelled`);
      refreshData();
    } catch (err) {
      setError(err.message || "Failed to cancel booking");
    }
  };

  return (
    <Layout title="Bookings">
      <section className="content-grid">
        <form className="panel form-grid" onSubmit={onSubmit}>
          <h3>Create Booking</h3>
          <label>
            Trip
            <select
              value={form.tripId}
              onChange={(e) => setForm((prev) => ({ ...prev, tripId: e.target.value }))}
              required
            >
              <option value="">Select trip</option>
              {trips.map((trip) => (
                <option key={trip.id} value={trip.id}>
                  {trip.name} ({trip.availableSeats} seats)
                </option>
              ))}
            </select>
          </label>
          <label>
            Number of guests
            <input
              type="number"
              min="1"
              value={form.numberOfGuests}
              onChange={(e) => setForm((prev) => ({ ...prev, numberOfGuests: e.target.value }))}
              required
            />
          </label>
          <button type="submit">Create booking</button>
          {error ? <p className="error-text">{error}</p> : null}
          {success ? <p className="success-text">{success}</p> : null}
        </form>

        <div className="panel table-wrap">
          <div className="stack-row">
            <h3>{isAdmin ? "All Bookings" : "My Bookings"}</h3>
            <button type="button" onClick={refreshData}>
              Refresh
            </button>
          </div>

          {loading ? <p>Loading...</p> : null}

          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>User</th>
                <th>Trip</th>
                <th>Guests</th>
                <th>Total</th>
                <th>Status</th>
                <th>Booked At</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {bookings.map((booking) => (
                <tr key={booking.id}>
                  <td>{booking.id}</td>
                  <td>{booking.userId}</td>
                  <td>{booking.tripId}</td>
                  <td>{booking.numberOfGuests}</td>
                  <td>{formatCurrency(booking.totalAmount)}</td>
                  <td>{booking.status}</td>
                  <td>{formatDateTime(booking.bookingDate)}</td>
                  <td>
                    <button
                      type="button"
                      className="small danger"
                      disabled={booking.status === "CANCELLED"}
                      onClick={() => onCancel(booking.id)}
                    >
                      Cancel
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </Layout>
  );
}
