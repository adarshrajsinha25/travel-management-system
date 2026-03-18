import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import { useAuth } from "../context/AuthContext";
import { NOTIFICATION_TYPES } from "../config";
import {
  getAllNotifications,
  getNotificationsByUser,
  sendNotification
} from "../api/notificationApi";
import { formatDateTime } from "../utils/format";

export default function NotificationsPage() {
  const { token, authState, isAdmin } = useAuth();
  const [notifications, setNotifications] = useState([]);
  const [form, setForm] = useState({
    userId: authState.userId,
    recipientEmail: authState.email,
    subject: "",
    body: "",
    type: "GENERAL"
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const refreshData = async () => {
    setLoading(true);
    setError("");
    try {
      const list = isAdmin
        ? await getAllNotifications(token)
        : await getNotificationsByUser(authState.userId, token);
      setNotifications(list);
    } catch (err) {
      setError(err.message || "Failed to load notifications");
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

    try {
      await sendNotification(
        {
          ...form,
          userId: form.userId ? Number(form.userId) : null
        },
        token
      );
      setSuccess("Notification sent");
      setForm((prev) => ({ ...prev, subject: "", body: "" }));
      refreshData();
    } catch (err) {
      setError(err.message || "Failed to send notification");
    }
  };

  return (
    <Layout title="Notifications">
      <section className="content-grid">
        <form className="panel form-grid" onSubmit={onSubmit}>
          <h3>Send Email Notification</h3>
          <label>
            User ID (optional)
            <input
              type="number"
              value={form.userId}
              onChange={(e) => setForm((prev) => ({ ...prev, userId: e.target.value }))}
            />
          </label>

          <label>
            Recipient email
            <input
              type="email"
              value={form.recipientEmail}
              onChange={(e) => setForm((prev) => ({ ...prev, recipientEmail: e.target.value }))}
              required
            />
          </label>

          <label>
            Subject
            <input
              value={form.subject}
              onChange={(e) => setForm((prev) => ({ ...prev, subject: e.target.value }))}
              required
            />
          </label>

          <label>
            Type
            <select
              value={form.type}
              onChange={(e) => setForm((prev) => ({ ...prev, type: e.target.value }))}
            >
              {NOTIFICATION_TYPES.map((type) => (
                <option key={type} value={type}>
                  {type}
                </option>
              ))}
            </select>
          </label>

          <label>
            Body
            <textarea
              value={form.body}
              onChange={(e) => setForm((prev) => ({ ...prev, body: e.target.value }))}
              required
            />
          </label>

          <button type="submit">Send notification</button>
          {error ? <p className="error-text">{error}</p> : null}
          {success ? <p className="success-text">{success}</p> : null}
        </form>

        <div className="panel table-wrap">
          <div className="stack-row">
            <h3>{isAdmin ? "All Notifications" : "My Notifications"}</h3>
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
                <th>Recipient</th>
                <th>Subject</th>
                <th>Type</th>
                <th>Success</th>
                <th>Sent At</th>
              </tr>
            </thead>
            <tbody>
              {notifications.map((item) => (
                <tr key={item.id}>
                  <td>{item.id}</td>
                  <td>{item.userId || "-"}</td>
                  <td>{item.recipientEmail}</td>
                  <td>{item.subject}</td>
                  <td>{item.type}</td>
                  <td>{String(item.success)}</td>
                  <td>{formatDateTime(item.sentAt)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </Layout>
  );
}
