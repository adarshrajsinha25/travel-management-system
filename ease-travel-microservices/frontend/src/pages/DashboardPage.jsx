import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import StatCard from "../components/StatCard";
import { useAuth } from "../context/AuthContext";
import { getAvailableTrips, getDestinations } from "../api/tripApi";
import { getBookingsByUser } from "../api/bookingApi";
import { getPaymentsByUser } from "../api/paymentApi";
import { getNotificationsByUser } from "../api/notificationApi";

export default function DashboardPage() {
  const { token, authState } = useAuth();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [stats, setStats] = useState({
    availableTrips: 0,
    destinations: 0,
    bookings: 0,
    payments: 0,
    notifications: 0
  });

  useEffect(() => {
    const run = async () => {
      setLoading(true);
      setError("");
      try {
        const [trips, destinations, bookings, payments, notifications] = await Promise.all([
          getAvailableTrips(token),
          getDestinations(token),
          getBookingsByUser(authState.userId, token),
          getPaymentsByUser(authState.userId, token),
          getNotificationsByUser(authState.userId, token)
        ]);
        setStats({
          availableTrips: trips.length,
          destinations: destinations.length,
          bookings: bookings.length,
          payments: payments.length,
          notifications: notifications.length
        });
      } catch (err) {
        setError(err.message || "Failed to load dashboard");
      } finally {
        setLoading(false);
      }
    };

    run();
  }, [token, authState.userId]);

  return (
    <Layout title="Operations Dashboard">
      <section className="hero-panel">
        <h2>Welcome back, {authState.email}</h2>
        <p>
          This frontend is wired to the Spring Cloud Gateway (`/api/v1/*`) and consumes
          user, trip, booking, payment, and notification services.
        </p>
      </section>

      {error ? <p className="error-text">{error}</p> : null}

      <section className="stats-grid">
        <StatCard label="Available Trips" value={loading ? "..." : stats.availableTrips} tone="sea" />
        <StatCard label="Destinations" value={loading ? "..." : stats.destinations} tone="sand" />
        <StatCard label="Your Bookings" value={loading ? "..." : stats.bookings} tone="sun" />
        <StatCard label="Your Payments" value={loading ? "..." : stats.payments} tone="stone" />
        <StatCard
          label="Your Notifications"
          value={loading ? "..." : stats.notifications}
          tone="leaf"
        />
      </section>
    </Layout>
  );
}
