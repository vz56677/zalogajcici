import { useState } from 'react';
import { useAuth } from '../context/AuthContext.jsx';
import { GoogleLogo, LogoMark } from '../components/Icons.jsx';

export default function AuthPage() {
  const { loginGoogle } = useAuth();
  const [role, setRole] = useState('buyer');
  const roles = [
    ['buyer', 'Kupac'],
    ['org', 'Organizacija'],
    ['store', 'Prodajno mjesto'],
  ];
  return (
    <div className="auth" data-r={role}>
      <section className="auth-hero">
        <LogoMark />
        <h1>SpasiZalogaj</h1>
        <small>by zalogajcici</small>
        <p>Odlična hrana, spašena od bacanja. Povezujemo kupce, trgovine i organizacije.</p>
      </section>
      <section className="auth-card">
        <h2>Dobro došli</h2>
        <p className="auth-intro">Odaberite način korištenja platforme</p>
        <div className="seg role-seg" role="group" aria-label="Vrsta računa">
          {roles.map(([value, label]) => <button key={value} type="button" aria-pressed={role === value} onClick={() => setRole(value)}>{label}</button>)}
        </div>
        {role === 'store' && <div className="role-hint">Upravljajte ponudama hrane, rezervacijama i donacijama svoje trgovine.</div>}
        <button className="btn alt" type="button" onClick={() => loginGoogle(role)}>
          <GoogleLogo /> Nastavi s Googleom
        </button>
        <p className="small">Demo prijava – odabir uloge prikazuje odgovarajuće sučelje.</p>
      </section>
    </div>
  );
}
