import { Link } from 'react-router-dom';
import { Art } from './Icons.jsx';
import { eur } from '../utils.js';

export function OfferCard({ o }) {
  const free = o.sale === null;
  return (
    <Link className={`card ${o.kind === 'bowl' ? 'g' : ''}`} to={`/p/${o.id}`}>
      <div className="pic"><span className="badge">Još {o.left}</span><Art kind={o.kind} size={80} /></div>
      <div className="body">
        <h3>{o.name}</h3>
        <p>{o.store} · {o.km} km</p>
        <div className={`price ${free ? 'free' : ''}`}>
          {free ? 'Besplatno' : <>{eur(o.sale)}<s>{eur(o.price)}</s></>}
        </div>
        <div className="when">Preuzimanje {o.day} {o.from} – {o.to}</div>
      </div>
    </Link>
  );
}

export function OfferRow({ o }) {
  const free = o.sale === null;
  return (
    <Link className="row" to={`/p/${o.id}`}>
      <div className="thumb"><Art kind={o.kind} size={44} /></div>
      <div className="t">
        <h3>{o.name}</h3>
        <p>{o.store} · {o.km} km</p>
        <p><b>{o.day} {o.from} – {o.to}</b></p>
      </div>
      <div className={`price ${free ? 'free' : ''}`}>
        {free ? 'Besplatno' : <>{eur(o.sale)}<br /><s>{eur(o.price)}</s></>}
      </div>
    </Link>
  );
}
