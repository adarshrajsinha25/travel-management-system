import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function RegisterPage() {
  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: ""
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const { register } = useAuth();
  const navigate = useNavigate();

  const onChange = (event) => {
    const { name, value } = event.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const onSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    try {
      await register(form);
      setSuccess("Registration successful. You can now login.");
      setTimeout(() => navigate("/login"), 900);
    } catch (err) {
      setError(err.message || "Registration failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <section className="auth-panel auth-panel-wide">
        <h1>Create account</h1>
        <p>Register to book trips, process payments, and receive notifications.</p>

        <form onSubmit={onSubmit} className="form-grid two-col">
          <label>
            First name
            <input
              name="firstName"
              type="text"
              required
              value={form.firstName}
              onChange={onChange}
            />
          </label>
          <label>
            Last name
            <input
              name="lastName"
              type="text"
              required
              value={form.lastName}
              onChange={onChange}
            />
          </label>
          <label className="full-width">
            Email
            <input name="email" type="email" required value={form.email} onChange={onChange} />
          </label>
          <label className="full-width">
            Password (min 6 chars)
            <input
              name="password"
              type="password"
              required
              minLength={6}
              value={form.password}
              onChange={onChange}
            />
          </label>

          {error ? <p className="error-text full-width">{error}</p> : null}
          {success ? <p className="success-text full-width">{success}</p> : null}

          <button className="full-width" type="submit" disabled={loading}>
            {loading ? "Creating account..." : "Register"}
          </button>
        </form>

        <small>
          Already registered? <Link to="/login">Back to login</Link>
        </small>
      </section>
    </div>
  );
}
