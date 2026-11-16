import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { createOrder, getOffer, listOffers } from '../services/api.js';
import { useAuth } from '../context/AuthContext.jsx';
import { useToast } from '../components/Toast.jsx';
import Shell from '../components/Shell.jsx';
import { OfferCard, OfferRow } from '../components/OfferItems.jsx';
import { Art, Icon } from '../components/Icons.jsx';
import { eur } from '../utils.js';

const CATS = ['Sve', 'Pekara', 'Jela', 'Voće i povrće'];
const greeting = () => { const h = new Date().getHours(); return h < 11 ? 'Dobro jutro' : h < 18 ? 'Dobar dan' : 'Dobra večer'; };

export function HomePage() {
  const { user } = useAuth();
  const org = user.role === 'org';
  const [all, setAll] = useState(null);
  const [cat, setCat] = useState('Sve');
  const [q, setQ] = useState('');

  useEffect(() => {
    let alive = true;
    listOffers(user.role).then(r => alive && setAll(r));
    return () => { alive = false; };
  }, [user.role]);

  if (!all) return null;

  const f = all.filter(o => (cat === 'Sve' || o.cat === cat) && (o.name + o.store).toLowerCase().includes(q.toLowerCase()));
  const soon = f.slice(0, 3);
  const near = [...f].sort((a, b) => a.km - b.km);

  return (
    <Shell user={user} route="/">
      <header className="top">
        <div className="hi">{greeting()}, {user.name}</div>
        <h1>{org ? 'Donacije dostupne vašoj organizaciji' : 'Spasi dobru hranu u blizini'}</h1>
        <button className="bell" aria-label="Obavijesti"><Icon name="bell" /></button>
        <label className="search">
          <span style={{ color: 'var(--mute)', display: 'flex' }}><Icon name="search" /></span>
          <input value={q} onChange={e => setQ(e.target.value)} placeholder={org ? 'Traži donacije, trgovine' : 'Traži kruh, jela, trgovine'} />
          <span className="chip-km">2 km</span>
        </label>
      </header>
      <div className="content">
        <div className="chips" role="group" aria-label="Kategorije">
          {CATS.map(c => <button key={c} className="chip" aria-pressed={c === cat} onClick={() => setCat(c)}>{c}</button>)}
        </div>
        <div className="sec"><h2>Uskoro završava</h2><Link to="/">Prikaži sve</Link></div>
        <div className="hscroll">
          {soon.length ? soon.map(o => <OfferCard key={o.id} o={o} />) : <p className="empty">Nema ponuda za ovaj filtar.</p>}
        </div>
        <div className="sec"><h2>{org ? 'Najbliže vama' : 'U tvojoj blizini'}</h2></div>
        <div className="list">{near.map(o => <OfferRow key={o.id} o={o} />)}</div>
      </div>
    </Shell>
  );
}

export function DetailPage() {
  const { id } = useParams();
  const { user } = useAuth();
  const toast = useToast();
  const navigate = useNavigate();
  const org = user.role === 'org';
  const [o, setO] = useState(undefined); // undefined = učitava se, null/falsy = nije pronađeno
  const [qty, setQty] = useState(1);

  useEffect(() => {
    let alive = true;
    setO(undefined); setQty(1);
    getOffer(id).then(r => alive && setO(r ?? null));
    return () => { alive = false; };
  }, [id]);

  if (o === undefined) return null;
  if (o === null) return <Shell user={user} route=""><p className="empty">Ponuda nije pronađena.</p></Shell>;

  const free = o.sale === null;
  const price = free ? 'Besplatno' : eur(o.sale * qty);
  const step = d => setQty(v => Math.min(o.left, Math.max(1, v + d)));

  const reserve = async () => {
    const code = await createOrder(o, qty, user.role);
    toast(org ? 'Zahtjev poslan. Prodajno mjesto će ga potvrditi.' : `Rezervirano. Kod za preuzimanje: ${code}`);
    navigate('/orders');
  };

  return (
    <Shell user={user} route="/">
      <div className="detail">
        <div className="hero">
          <Link className="round" style={{ left: 16 }} to="/" aria-label="Natrag">‹</Link>
          <button className="round" style={{ right: 16, border: 0 }} aria-label="Spremi"><Icon name="heart" /></button>
          <span className="badge" style={{ left: 16, top: 'auto', bottom: 40 }}>Još {o.left} dostupno</span>
          <Art kind={o.kind} size={150} />
        </div>
        <div className="sheet">
          <div className="head">
            <div>
              <h1>{o.name}</h1>
              <p style={{ color: 'var(--mute)', fontSize: 13, marginTop: 4 }}>{o.store} · {o.km} km</p>
            </div>
            <div className={`price ${free ? 'free' : ''}`} style={{ fontSize: 24, textAlign: 'right' }}>
              {free ? 'Besplatno' : <>{eur(o.sale)}<br /><s>{eur(o.price)}</s></>}
            </div>
          </div>
          <div className="tiles">
            <div className="tile">Preuzimanje<b>Danas {o.from} – {o.to}</b></div>
            <div className="tile">Udaljenost<b>{o.km} km · {Math.round(o.km * 13)} min hoda</b></div>
          </div>
          <h2 style={{ fontSize: 16 }}>{org ? 'O donaciji' : 'Što možeš očekivati'}</h2>
          <p style={{ fontSize: 14, color: 'var(--mute)', marginTop: 6, lineHeight: 1.5 }}>{o.desc}</p>
          <div className="tags">{o.tags.map(t => <span key={t} className="tag">{t}</span>)}</div>
          <div className="store">
            <h3>{o.store}</h3>
            <p style={{ fontSize: 12, color: 'var(--mute)', marginTop: 2 }}>{o.addr}</p>
            <div className="map" role="img" aria-label="Karta lokacije" />
          </div>
        </div>
      </div>
      <div className="cta">
        <div className="qty">
          <button aria-label="Manje" onClick={() => step(-1)}>−</button>
          <b style={{ minWidth: 18, textAlign: 'center' }}>{qty}</b>
          <button aria-label="Više" onClick={() => step(1)}>+</button>
        </div>
        <button className="btn" onClick={reserve}>{org ? 'Zatraži donaciju' : 'Rezerviraj'} · <span>{price}</span></button>
      </div>
    </Shell>
  );
}
