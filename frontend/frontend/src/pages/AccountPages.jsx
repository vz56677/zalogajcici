import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { cancelOrder, listCampaigns, listOrders } from '../services/api.js';
import { useAuth } from '../context/AuthContext.jsx';
import { useToast } from '../components/Toast.jsx';
import Shell, { TopBar } from '../components/Shell.jsx';
import { Icon } from '../components/Icons.jsx';
import { eur } from '../utils.js';

export function OrdersPage() {
  const { user } = useAuth();
  const toast = useToast();
  const org = user.role === 'org';
  const [orders, setOrders] = useState(null);

  const load = useCallback(() => listOrders().then(r => setOrders(r.filter(o => (o.role === 'org') === org))), [org]);
  useEffect(() => { load(); }, [load]);

  const cancel = async id => {
      try {
          await cancelOrder(id);
          toast(org ? 'Zahtjev je povučen.' : 'Rezervacija je otkazana, količina je vraćena u ponudu.');
      } catch (err) {
          toast(err.message);
      }
      load();
  };

  if (!orders) return null;
  return (
    <Shell user={user} route="/orders">
      <TopBar title={org ? 'Zahtjevi za donacije' : 'Tvoje narudžbe'} sub={org ? 'Praćenje preuzimanja' : 'Rezervacije'} />
      <div className="content" style={{ paddingTop: 20 }}>
        {orders.length ? orders.map(o => (
          <div key={o.id} className="row" style={{ alignItems: 'flex-start', flexWrap: 'wrap' }}>
            <div className="t">
              <h3>{o.name} × {o.qty}</h3>
              <p>{o.store} · preuzmi do {o.until}</p>
              {!org && <div className="code">{o.code}</div>}
              <span className="status">{o.status}</span>
            </div>
            <div className="price">{org ? '' : eur(o.total)}</div>
              {o.status !== 'Otkazano' && (
                  <button className="chip" style={{ background: 'var(--cream)' }} onClick={() => cancel(o.id)}>
                  {org ? 'Povuci zahtjev' : 'Otkaži'}
                  </button>
              )}
          </div>
        )) : (
          <p className="empty">
            {org ? 'Još nema zahtjeva. Pregledaj dostupne donacije.' : 'Još nema rezervacija. Pronađi ponudu u blizini.'}
            <br /><br /><Link className="link" to="/">Pregledaj ponude</Link>
          </p>
        )}
      </div>
    </Shell>
  );
}

export function CampaignsPage() {
  const { user } = useAuth();
  const [campaigns, setCampaigns] = useState(null);
  useEffect(() => { listCampaigns().then(setCampaigns); }, []);

  if (!campaigns) return null;
  return (
    <Shell user={user} route="/campaigns">
      <TopBar title="Akcije prikupljanja" sub="Doniraj zajedno" />
      <div className="content" style={{ paddingTop: 20 }}>
        <div className="list">
          {campaigns.map(a => (
            <div key={a.id} className="row" style={{ alignItems: 'flex-start' }}>
              <div className="thumb" style={{ color: '#fff' }}><Icon name="gift" /></div>
              <div className="t">
                <h3>{a.name}</h3>
                <p>{a.place} · {a.dates}</p>
                <p>Potrebno: {a.need}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </Shell>
  );
}

export function MapPage() {
  const { user } = useAuth();
  return (
    <Shell user={user} route="/map">
      <TopBar title="Karta ponuda" sub="U tvojoj blizini" />
      <div className="content" style={{ paddingTop: 20 }}>
        <div className="map" style={{ height: 360 }} role="img" aria-label="Karta" />
        <p className="empty">Ovdje se uključuje Google Maps s oznakama prodajnih mjesta.</p>
      </div>
    </Shell>
  );
}

export function ProfilePage() {
  const { user, logout } = useAuth();
  const org = user.role === 'org';
  return (
    <Shell user={user} route="/profile">
      <TopBar title="Profil" sub={org ? 'Humanitarna organizacija' : 'Kupac'} />
      <div className="content" style={{ paddingTop: 20 }}>
        <div className="profile">
          <div className="avatar">{user.name[0]}</div>
          <div>
            <h3>{user.name}</h3>
            <p style={{ fontSize: 13, color: 'var(--mute)' }}>{user.email}</p>
          </div>
        </div>
        <div className="stats">
          <div className="stat"><b>{org ? '12' : '7'}</b><span>{org ? 'preuzetih donacija' : 'spašenih obroka'}</span></div>
          <div className="stat"><b>{org ? '86 kg' : '14 €'}</b><span>{org ? 'hrane primljeno' : 'ušteđeno'}</span></div>
        </div>
        <button className="btn" onClick={logout}>Odjava</button>
      </div>
    </Shell>
  );
}
