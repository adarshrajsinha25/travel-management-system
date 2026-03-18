export default function StatCard({ label, value, tone = "default" }) {
  return (
    <article className={`stat-card stat-${tone}`}>
      <p>{label}</p>
      <h3>{value}</h3>
    </article>
  );
}
