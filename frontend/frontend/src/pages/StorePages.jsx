import { useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';
import Shell, { TopBar } from '../components/Shell.jsx';
import { useAuth } from '../context/AuthContext.jsx';
import { useToast } from '../components/Toast.jsx';
import { Icon } from '../components/Icons.jsx';
import { publishOffer } from '../services/api.js';

const KEY = 'sz_store_data';
const seed = {
  profile: { name: 'Mala Trgovina', address: 'Ilica 40, Zagreb', contact: '01 555 0101', hours: 'Pon – Sub, 08:00 – 20:00', description: 'Lokalna trgovina sa svježim i pažljivo odabranim namirnicama.' },
  offers: [
    /*{ id: 1, name: 'Kutija spašenih namirnica', category: 'Voće i povrće', qty: 6, regular: 14, sale: 4.99, expiry: '2026-11-17', pickup: '19:00 – 20:00', purpose: 'prodaja', type: 'Proizvod' },
    { id: 2, name: 'Gajbe voća i povrća', category: 'Voće i povrće', qty: 5, regular: 30, sale: 0, expiry: '2026-11-17', pickup: '17:00 – 19:00', purpose: 'donacija', type: 'Proizvod' },
    { id: 3, name: 'Paket iznenađenja', category: 'Miješano', qty: 4, regular: 18, sale: 5.99, expiry: '2026-11-17', pickup: '19:30 – 20:00', purpose: 'prodaja', type: 'Paket iznenađenja' },
  */
  ],
  requests: [
    { id: 101, org: 'Banka hrane Zagreb', item: 'Gajbe voća i povrća', qty: 2, date: 'Danas, 10:24', status: 'Na čekanju' },
    { id: 102, org: 'Udruga Novi početak', item: 'Kruh i peciva', qty: 10, date: 'Jučer, 16:10', status: 'Na čekanju' },
  ],
  campaigns: [
    { id: 201, name: 'Tjedan bez bacanja', description: 'Prikupljanje trajnih namirnica za lokalnu zajednicu.', location: 'Ilica 40, Zagreb', start: '2026-11-18', end: '2026-11-25', needs: 'Konzerve, tjestenina, ulje' },
  ],
  reviews: [
    { id: 301, name: 'Ana M.', rating: 5, text: 'Odlična ponuda i jednostavno preuzimanje.', reply: '' },
    { id: 302, name: 'Marko K.', rating: 4, text: 'Sve je bilo svježe, hvala!', reply: 'Hvala vam na podršci!' },
  ],
};
const load = () => { try { return { ...seed, ...(JSON.parse(localStorage.getItem(KEY)) || {}) }; } catch { return seed; } };
const save = data => localStorage.setItem(KEY, JSON.stringify(data));
const money = n => `${Number(n || 0).toFixed(2).replace('.', ',')} €`;

function useStoreData() {
  const [data, setData] = useState(load);
  const update = patch => setData(current => { const next = { ...current, ...patch }; save(next); return next; });
  return [data, update];
}

export function StoreDashboard() {
  const { user } = useAuth();
  const [data] = useStoreData();
  const pending = data.requests.filter(r => r.status === 'Na čekanju').length;
  return <Shell user={user} route="/">
    <TopBar title={`Dobro došli, ${data.profile.name}`} sub="Nadzorna ploča prodajnog mjesta" />
    <div className="content store-content">
      <div className="store-welcome"><div><span className="eyebrow">PREGLED POSLOVANJA</span><h2>Spasimo još hrane danas.</h2><p>Upravljajte ponudama i pratite što se događa u vašoj trgovini.</p></div><div className="store-welcome-icon"><Icon name="gift" /></div></div>
      <div className="store-stats">
        <div className="store-stat"><span>Aktivne ponude</span><b>{data.offers.length}</b><small>proizvoda i paketa</small></div>
        <div className="store-stat"><span>Zahtjevi na čekanju</span><b>{pending}</b><small>čekaju vašu potvrdu</small></div>
        <div className="store-stat"><span>Akcije</span><b>{data.campaigns.length}</b><small>aktivne i planirane</small></div>
      </div>
      <div className="sec"><h2>Brze radnje</h2></div>
      <div className="store-quick">
        <Link to="/store/offers"><span className="quick-icon"><Icon name="leaf" /></span><b>Dodaj ponudu</b><small>Objavi višak hrane</small></Link>
        <Link to="/store/requests"><span className="quick-icon"><Icon name="bag" /></span><b>Zahtjevi za donacije</b><small>{pending} zahtjeva čeka</small></Link>
        <Link to="/store/campaigns"><span className="quick-icon"><Icon name="gift" /></span><b>Nova akcija</b><small>Organiziraj prikupljanje</small></Link>
      </div>
      <div className="sec"><h2>Nedavne ponude</h2><Link to="/store/offers">Upravljaj ponudama</Link></div>
      <div className="store-panel">{data.offers.slice(0, 4).map(o => <div className="store-list-row" key={o.id}><div className="store-food-icon"><Icon name="bag" /></div><div className="store-row-main"><b>{o.name}</b><small>{o.category} · {o.qty} kom.</small></div><span className="status">{o.purpose === 'donacija' ? 'Donacija' : money(o.sale)}</span></div>)}</div>
    </div>
  </Shell>;
}

export function StoreOffersPage() {
  const { user } = useAuth(); const toast = useToast(); const [data, update] = useStoreData();
  const blank = { name: '', category: 'Pekara', qty: 1, regular: '', sale: '', expiry: '', pickup: '', purpose: 'prodaja', type: 'Proizvod' };
  const [form, setForm] = useState(blank); const [editing, setEditing] = useState(null); const [show, setShow] = useState(false);
  const set = (k, v) => setForm(f => ({ ...f, [k]: v }));
  /*const submit = e => { e.preventDefault(); if (!form.name.trim() || !form.expiry || !form.pickup) return toast('Popunite naziv, rok i vrijeme preuzimanja.');
    const item = { ...form, id: editing ?? Date.now(), qty: Number(form.qty), regular: Number(form.regular || 0), sale: form.purpose === 'donacija' ? 0 : Number(form.sale || 0) };
    update({ offers: editing ? data.offers.map(o => o.id === editing ? item : o) : [item, ...data.offers] }); setForm(blank); setEditing(null); setShow(false); toast(editing ? 'Ponuda je ažurirana.' : 'Ponuda je objavljena.'); };
  */
  const submit = async e => {
    e.preventDefault();
    if (!form.name.trim() || !form.expiry || !form.pickup) return toast('Popunite naziv, rok i vrijeme preuzimanja.');
    const item = { ...form, id: editing ?? Date.now(), qty: Number(form.qty), regular: Number(form.regular || 0), sale: form.purpose === 'donacija' ? 0 : Number(form.sale || 0) };

    if (!editing) {
      try {
        const saved = await publishOffer(form); // stvarna objava na backend
        item.id = saved.id;
      } catch (err) {
        return toast(err.message); // poruka s backenda, npr. "Snižena cijena mora biti manja od redovne."
      }
    }

    update({ offers: editing ? data.offers.map(o => o.id === editing ? item : o) : [item, ...data.offers] });
    setForm(blank); setEditing(null); setShow(false);
    toast(editing ? 'Ponuda je ažurirana.' : 'Ponuda je objavljena.');
  };
  const edit = o => { setForm({ ...o }); setEditing(o.id); setShow(true); };
  const remove = id => { update({ offers: data.offers.filter(o => o.id !== id) }); toast('Ponuda je uklonjena.'); };
  return <Shell user={user} route="/store/offers"><TopBar title="Ponude hrane" sub="Upravljanje proizvodima i paketima" />
    <div className="content store-content"><div className="store-toolbar"><p>Objavite proizvode na sniženju ili hranu namijenjenu doniranju.</p><button className="btn store-add" onClick={() => { setForm(blank); setEditing(null); setShow(v => !v); }}><Icon name="leaf" /> Nova ponuda</button></div>
      {show && <form className="store-form" onSubmit={submit}><div className="form-heading"><h2>{editing ? 'Uredi ponudu' : 'Nova ponuda'}</h2><button type="button" className="text-button" onClick={() => setShow(false)}>Zatvori ✕</button></div>
        <label>Vrsta ponude<select className="in" value={form.type} onChange={e => set('type', e.target.value)}><option>Proizvod</option><option>Paket iznenađenja</option></select></label>
        <label>Naziv<input className="in" value={form.name} onChange={e => set('name', e.target.value)} placeholder="npr. Paket svježeg povrća" required /></label>
        <div className="form-grid"><label>Kategorija<select className="in" value={form.category} onChange={e => set('category', e.target.value)}>{['Pekara','Jela','Voće i povrće','Mliječni proizvodi','Meso i riba','Miješano','Ostalo'].map(x => <option key={x}>{x}</option>)}</select></label><label>Količina / broj paketa<input className="in" type="number" min="1" value={form.qty} onChange={e => set('qty', e.target.value)} required /></label></div>
        <div className="form-grid"><label>Redovna vrijednost (€)<input className="in" type="number" min="0" step="0.01" value={form.regular} onChange={e => set('regular', e.target.value)} /></label>{form.purpose === 'prodaja' && <label>Snižena cijena (€)<input className="in" type="number" min="0" step="0.01" value={form.sale} onChange={e => set('sale', e.target.value)} /></label>}</div>
        <div className="form-grid"><label>Rok upotrebe<input className="in" type="date" value={form.expiry} onChange={e => set('expiry', e.target.value)} required /></label><label>Vrijeme preuzimanja<input className="in" value={form.pickup} onChange={e => set('pickup', e.target.value)} placeholder="18:00 – 19:00" required /></label></div>
        <label>Namjena<select className="in" value={form.purpose} onChange={e => set('purpose', e.target.value)}><option value="prodaja">Prodaja kupcima</option><option value="donacija">Samo donacija humanitarnim organizacijama</option></select></label>
        <button className="btn" type="submit">{editing ? 'Spremi promjene' : 'Objavi ponudu'}</button></form>}
      <div className="store-panel">{data.offers.length ? data.offers.map(o => <div className="store-list-row offer-admin" key={o.id}><div className="store-food-icon"><Icon name="bag" /></div><div className="store-row-main"><b>{o.name}</b><small>{o.type} · {o.category} · {o.qty} kom. · preuzimanje {o.pickup}</small><small>{o.purpose === 'donacija' ? 'Samo za donaciju' : `${money(o.sale)} (redovno ${money(o.regular)})`}</small></div><div className="row-actions"><button onClick={() => edit(o)}>Uredi</button><button className="danger-text" onClick={() => remove(o.id)}>Obriši</button></div></div>) : <p className="empty">Još nema objavljenih ponuda.</p>}</div>
    </div></Shell>;
}

export function StoreRequestsPage() {
  const { user } = useAuth(); const toast = useToast(); const [data, update] = useStoreData();
  const change = (id, status) => { update({ requests: data.requests.map(r => r.id === id ? { ...r, status } : r) }); toast(status === 'Odobreno' ? 'Zahtjev je odobren.' : 'Zahtjev je odbijen.'); };
  return <Shell user={user} route="/store/requests"><TopBar title="Zahtjevi za donacije" sub="Humanitarne organizacije" /><div className="content store-content"><p className="page-lead">Pregledajte zahtjeve za preuzimanje hrane koja je označena isključivo za donaciju.</p><div className="store-panel">{data.requests.map(r => <div className="request-card" key={r.id}><div className="request-top"><div><span className="eyebrow">{r.date}</span><h3>{r.org}</h3></div><span className={`request-status ${r.status === 'Na čekanju' ? 'pending' : 'done'}`}>{r.status}</span></div><p><b>{r.item}</b> · tražena količina: {r.qty}</p>{r.status === 'Na čekanju' && <div className="request-actions"><button className="btn" onClick={() => change(r.id, 'Odobreno')}>Odobri zahtjev</button><button className="btn outline" onClick={() => change(r.id, 'Odbijeno')}>Odbij</button></div>}</div>)}</div></div></Shell>;
}

export function StoreCampaignsPage() {
  const { user } = useAuth(); const toast = useToast(); const [data, update] = useStoreData();
  const [form, setForm] = useState({ name: '', description: '', location: '', start: '', end: '', needs: '' }); const [show, setShow] = useState(false);
  const set = (k,v) => setForm(f => ({ ...f, [k]: v }));
  const submit = e => { e.preventDefault(); update({ campaigns: [{ ...form, id: Date.now() }, ...data.campaigns] }); setForm({ name: '', description: '', location: '', start: '', end: '', needs: '' }); setShow(false); toast('Akcija je kreirana.'); };
  return <Shell user={user} route="/store/campaigns"><TopBar title="Akcije doniranja" sub="Organizirajte prikupljanje hrane" /><div className="content store-content"><div className="store-toolbar"><p>Kreirajte akcije i navedite koje su vrste proizvoda potrebne.</p><button className="btn store-add" onClick={() => setShow(v => !v)}><Icon name="leaf" /> Nova akcija</button></div>{show && <form className="store-form" onSubmit={submit}><h2>Nova akcija</h2><label>Naziv akcije<input className="in" required value={form.name} onChange={e => set('name',e.target.value)} /></label><label>Opis<textarea className="in" rows="3" value={form.description} onChange={e => set('description',e.target.value)} /></label><label>Lokacija<input className="in" required value={form.location} onChange={e => set('location',e.target.value)} /></label><div className="form-grid"><label>Početak<input className="in" type="date" required value={form.start} onChange={e => set('start',e.target.value)} /></label><label>Završetak<input className="in" type="date" required value={form.end} onChange={e => set('end',e.target.value)} /></label></div><label>Potrebne vrste proizvoda<input className="in" required value={form.needs} onChange={e => set('needs',e.target.value)} placeholder="npr. konzerve, ulje, tjestenina" /></label><button className="btn">Kreiraj akciju</button></form>}<div className="store-panel">{data.campaigns.map(c => <div className="campaign-card" key={c.id}><span className="quick-icon"><Icon name="gift" /></span><div><h3>{c.name}</h3><p>{c.description}</p><small>{c.location} · {c.start} – {c.end}</small><p><b>Potrebno:</b> {c.needs}</p></div></div>)}</div></div></Shell>;
}

export function StoreProfilePage() {
  const { user, logout } = useAuth(); const toast = useToast(); const [data, update] = useStoreData(); const [form, setForm] = useState(data.profile);
  const set = (k,v) => setForm(f => ({ ...f, [k]: v }));
  const submit = e => { e.preventDefault(); update({ profile: form }); toast('Profil prodajnog mjesta je spremljen.'); };
  return <Shell user={user} route="/store/profile"><TopBar title="Profil trgovine" sub="Javni podaci prodajnog mjesta" /><div className="content store-content"><form className="store-form" onSubmit={submit}><div className="profile store-profile"><div className="avatar"><Icon name="home" /></div><div><h3>{form.name}</h3><p>Prodajno mjesto · SpasiZalogaj</p></div></div><label>Naziv prodajnog mjesta<input className="in" value={form.name} onChange={e => set('name',e.target.value)} required /></label><label>Adresa<input className="in" value={form.address} onChange={e => set('address',e.target.value)} required /></label><label>Kontakt telefon / e-pošta<input className="in" value={form.contact} onChange={e => set('contact',e.target.value)} /></label><label>Radno vrijeme<input className="in" value={form.hours} onChange={e => set('hours',e.target.value)} /></label><label>Opis trgovine<textarea className="in" rows="3" value={form.description} onChange={e => set('description',e.target.value)} /></label><button className="btn">Spremi profil</button></form><button className="btn logout-btn" onClick={logout}>Odjava</button></div></Shell>;
}

export function StoreReviewsPage() {
  const { user } = useAuth(); const toast = useToast(); const [data, update] = useStoreData();
  const reply = (id, value) => update({ reviews: data.reviews.map(r => r.id === id ? { ...r, reply: value } : r) });
  return <Shell user={user} route="/store/reviews"><TopBar title="Recenzije kupaca" sub="Povratne informacije" /><div className="content store-content"><div className="store-panel">{data.reviews.map(r => <Review key={r.id} review={r} onReply={value => { reply(r.id,value); toast('Odgovor je spremljen.'); }} />)}</div></div></Shell>;
}
function Review({ review, onReply }) { const [value, setValue] = useState(review.reply); return <div className="review-card"><div className="request-top"><h3>{review.name}</h3><span className="stars">{'★'.repeat(review.rating)}{'☆'.repeat(5-review.rating)}</span></div><p>{review.text}</p>{review.reply ? <div className="merchant-reply"><b>Vaš odgovor</b><p>{review.reply}</p></div> : <div className="reply-form"><input className="in" value={value} onChange={e => setValue(e.target.value)} placeholder="Napišite odgovor kupcu..." /><button className="btn" onClick={() => onReply(value)}>Odgovori</button></div>}</div>; }
