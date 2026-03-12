import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import { useAuth } from "../context/AuthContext";
import { deleteUser, getAllUsers, getUserById, updateUser } from "../api/userApi";
import { formatDateTime } from "../utils/format";

export default function UsersPage() {
  const { token, authState, isAdmin } = useAuth();
  const [profile, setProfile] = useState(null);
  const [users, setUsers] = useState([]);
  const [form, setForm] = useState({ firstName: "", lastName: "" });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const refreshData = async () => {
    setLoading(true);
    setError("");
    try {
      const me = await getUserById(authState.userId, token);
      setProfile(me);
      setForm({ firstName: me.firstName || "", lastName: me.lastName || "" });

      if (isAdmin) {
        const userList = await getAllUsers(token);
        setUsers(userList);
      }
    } catch (err) {
      setError(err.message || "Failed to load users");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    refreshData();
  }, [token, authState.userId, isAdmin]);

  const onUpdateProfile = async (event) => {
    event.preventDefault();
    setError("");
    setSuccess("");
    try {
      await updateUser(authState.userId, form, token);
      setSuccess("Profile updated");
      refreshData();
    } catch (err) {
      setError(err.message || "Failed to update profile");
    }
  };

  const onDeleteUser = async (userId) => {
    if (!window.confirm(`Delete user ${userId}?`)) {
      return;
    }

    setError("");
    setSuccess("");
    try {
      await deleteUser(userId, token);
      setSuccess(`User ${userId} deleted`);
      refreshData();
    } catch (err) {
      setError(err.message || "Failed to delete user");
    }
  };

  return (
    <Layout title="Users & Profile">
      <section className="content-grid">
        <div className="panel">
          <h3>My Profile</h3>
          {loading ? <p>Loading...</p> : null}
          {profile ? (
            <ul className="kv-list">
              <li>
                <strong>ID:</strong> {profile.id}
              </li>
              <li>
                <strong>Email:</strong> {profile.email}
              </li>
              <li>
                <strong>Role:</strong> {profile.role}
              </li>
              <li>
                <strong>Active:</strong> {String(profile.active)}
              </li>
              <li>
                <strong>Created:</strong> {formatDateTime(profile.createdAt)}
              </li>
            </ul>
          ) : null}

          <form className="form-grid" onSubmit={onUpdateProfile}>
            <label>
              First name
              <input
                value={form.firstName}
                onChange={(e) => setForm((prev) => ({ ...prev, firstName: e.target.value }))}
              />
            </label>
            <label>
              Last name
              <input
                value={form.lastName}
                onChange={(e) => setForm((prev) => ({ ...prev, lastName: e.target.value }))}
              />
            </label>
            <button type="submit">Update profile</button>
          </form>

          {error ? <p className="error-text">{error}</p> : null}
          {success ? <p className="success-text">{success}</p> : null}
        </div>

        {isAdmin ? (
          <div className="panel table-wrap">
            <div className="stack-row">
              <h3>All Users (Admin)</h3>
              <button type="button" onClick={refreshData}>
                Refresh
              </button>
            </div>

            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Active</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {users.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>
                      {user.firstName} {user.lastName}
                    </td>
                    <td>{user.email}</td>
                    <td>{user.role}</td>
                    <td>{String(user.active)}</td>
                    <td>
                      <button
                        type="button"
                        className="small danger"
                        disabled={user.id === authState.userId}
                        onClick={() => onDeleteUser(user.id)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <article className="panel">
            <h3>Admin Controls</h3>
            <p>Your account role is not ADMIN, so user listing and deletion are hidden.</p>
          </article>
        )}
      </section>
    </Layout>
  );
}
