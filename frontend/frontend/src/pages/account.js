import { listOrders, cancelOrder, listCampaigns, logout } from '../services/api.js';
import { shell, esc, eur, icon, toast } from '../components/ui.js';

const topBar = (t, s) => `<header class="top" style="padding-bottom:36px"><div class="hi">${s}</div><h1>${t}</h1></header>`;

export async function ordersPage(root, user) {
  const org = user.role === 'org', all = (await listOrders()).filter(o => (o.role === 'org') === org);
  root.innerHTML = shell(user, '#/orders', `${topBar(org ? 'Zahtjevi za donacije' : 'Tvoje narudžbe', org ? 'Praćenje preuzimanja' : 'Rezervacije')}
    <div class="content" style="padding-top:20px">${all.map(o => `<div class="row" style="align-items:flex-start;flex-wrap:wrap"><div class="t"><h3>${esc(o.name)} × ${o.qty}</h3><p>${esc(o.store)} · preuzmi do ${o.until}</p>
      ${org ? '' : `<div class="code">${esc(o.code)}</div>`}<span class="status">${o.status}</span></div>
      <div class="price">${org ? '' : eur(o.total)}</div><button class="chip" style="background:var(--cream)" data-id="${o.id}">${org ? 'Povuci zahtjev' : 'Otkaži'}</button></div>`).join('')
      || `<p class="empty">${org ? 'Još nema zahtjeva. Pregledaj dostupne donacije.' : 'Još nema rezervacija. Pronađi ponudu u blizini.'}<br><br><a class="link" href="#/">Pregledaj ponude</a></p>`}</div>`);
  root.querySelectorAll('[data-id]').forEach(b => b.onclick = async () => { await cancelOrder(+b.dataset.id); toast('Otkazano.'); ordersPage(root, user); });
}

export async function campaignsPage(root, user) {
  const c = await listCampaigns();
  root.innerHTML = shell(user, '#/campaigns', `${topBar('Akcije prikupljanja', 'Doniraj zajedno')}<div class="content" style="padding-top:20px"><div class="list">
    ${c.map(a => `<div class="row" style="align-items:flex-start"><div class="thumb" style="color:#fff">${icon.gift}</div><div class="t"><h3>${esc(a.name)}</h3><p>${esc(a.place)} · ${a.dates}</p><p>Potrebno: ${esc(a.need)}</p></div></div>`).join('')}</div></div>`);
}

export function mapPage(root, user) {
  root.innerHTML = shell(user, '#/map', `${topBar('Karta ponuda', 'U tvojoj blizini')}<div class="content" style="padding-top:20px"><div class="map" style="height:360px" role="img" aria-label="Karta"></div>
    <p class="empty">Ovdje se uključuje Google Maps s oznakama prodajnih mjesta.</p></div>`);
}

export function profilePage(root, user, rerender) {
  const org = user.role === 'org';
  root.innerHTML = shell(user, '#/profile', `${topBar('Profil', org ? 'Humanitarna organizacija' : 'Kupac')}<div class="content" style="padding-top:20px">
    <div class="profile"><div class="avatar">${esc(user.name[0])}</div><div><h3>${esc(user.name)}</h3><p style="font-size:13px;color:var(--mute)">${esc(user.email)}</p></div></div>
    <div class="stats"><div class="stat"><b>${org ? '12' : '7'}</b><span>${org ? 'preuzetih donacija' : 'spašenih obroka'}</span></div><div class="stat"><b>${org ? '86 kg' : '14 €'}</b><span>${org ? 'hrane primljeno' : 'ušteđeno'}</span></div></div>
    <button class="btn" id="out">Odjava</button></div>`);
  root.querySelector('#out').onclick = () => { logout(); rerender(); };
}
