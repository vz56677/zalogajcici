import { Link } from 'react-router-dom';
import { Icon, LogoMark } from './Icons.jsx';

// Navigacija: donja traka (mobitel) + bočna traka (desktop) iz istih podataka
export const NAV = {
  buyer: [['/', 'Istraži', 'home'], ['/map', 'Karta', 'pin'], ['/orders', 'Narudžbe', 'bag'], ['/profile', 'Profil', 'user']],
  org: [['/', 'Donacije', 'home'], ['/campaigns', 'Akcije', 'gift'], ['/orders', 'Zahtjevi', 'bag'], ['/profile', 'Profil', 'user']],
  store: [['/', 'Pregled', 'home'], ['/store/offers', 'Ponude', 'bag'], ['/store/requests', 'Donacije', 'gift'], ['/store/campaigns', 'Akcije', 'leaf'], ['/store/reviews', 'Recenzije', 'user'], ['/store/profile', 'Profil', 'user']],
};

export default function Shell({ user, route, children }) {
  const items = NAV[user.role] || NAV.buyer;
  const links = items.map(([to, label, icon]) => (
    <Link key={to} to={to} aria-current={to === route ? 'page' : undefined}>
      <Icon name={icon} /><span>{label}</span>
    </Link>
  ));
  return (
    <div className="shell">
      <aside className="side">
        <div className="brand"><LogoMark /><span>SpasiZalogaj</span></div>
        {links}
        <div className="who">{user.name}<br />{user.role === 'org' ? 'Organizacija' : user.role === 'store' ? 'Prodajno mjesto' : 'Kupac'}</div>
      </aside>
      <main className="main">{children}</main>
      <nav className="nav" aria-label="Glavna navigacija">{links}</nav>
    </div>
  );
}

export const TopBar = ({ title, sub }) => (
  <header className="top" style={{ paddingBottom: 36 }}>
    <div className="hi">{sub}</div>
    <h1>{title}</h1>
  </header>
);
