import { listOffers, getOffer, createOrder } from '../services/api.js';
import { shell, offerCard, offerRow, icon, art, esc, eur, toast } from '../components/ui.js';

const CATS = ['Sve', 'Pekara', 'Jela', 'Voće i povrće'];
const hour = () => { const h = new Date().getHours(); return h < 11 ? 'Dobro jutro' : h < 18 ? 'Dobar dan' : 'Dobra večer'; };

export async function homePage(root, user) {
  const org = user.role === 'org';
  const all = await listOffers(user.role);
  let cat = 'Sve', q = '';
  const draw = () => {
    const f = all.filter(o => (cat === 'Sve' || o.cat === cat) && (o.name + o.store).toLowerCase().includes(q.toLowerCase()));
    const soon = f.slice(0, 3), near = [...f].sort((a, b) => a.km - b.km);
    const body = `<header class="top"><div class="hi">${hour()}, ${esc(user.name)}</div>
      <h1>${org ? 'Donacije dostupne vašoj organizaciji' : 'Spasi dobru hranu u blizini'}</h1>
      <button class="bell" aria-label="Obavijesti">${icon.bell}</button>
      <label class="search"><span style="color:var(--mute);display:flex">${icon.search}</span>
        <input id="q" value="${esc(q)}" placeholder="${org ? 'Traži donacije, trgovine' : 'Traži kruh, jela, trgovine'}"><span class="chip-km">2 km</span></label></header>
    <div class="content"><div class="chips" role="group" aria-label="Kategorije">${CATS.map(c => `<button class="chip" data-c="${c}" aria-pressed="${c === cat}">${c}</button>`).join('')}</div>
      <div class="sec"><h2>Uskoro završava</h2><a href="#/">Prikaži sve</a></div>
      <div class="hscroll">${soon.map(o => offerCard(o)).join('') || '<p class="empty">Nema ponuda za ovaj filtar.</p>'}</div>
      <div class="sec"><h2>${org ? 'Najbliže vama' : 'U tvojoj blizini'}</h2></div>
      <div class="list">${near.map(offerRow).join('')}</div></div>`;
    root.innerHTML = shell(user, '#/', body);
    root.querySelectorAll('.chip').forEach(b => b.onclick = () => { cat = b.dataset.c; draw(); });
    const i = root.querySelector('#q'); i.oninput = () => { q = i.value; const p = i.selectionStart; draw(); const n = root.querySelector('#q'); n.focus(); n.setSelectionRange(p, p); };
  };
  draw();
}

export async function detailPage(root, user, id) {
  const o = await getOffer(id), org = user.role === 'org';
  if (!o) { root.innerHTML = shell(user, '', '<p class="empty">Ponuda nije pronađena.</p>'); return; }
  let qty = 1; const free = o.sale === null;
  const price = () => free ? 'Besplatno' : eur(o.sale * qty);
  root.innerHTML = shell(user, '#/', `<div class="detail"><div class="hero"><a class="round" style="left:16px" href="#/" aria-label="Natrag">‹</a>
    <button class="round" style="right:16px;border:0" aria-label="Spremi">${icon.heart}</button>
    <span class="badge" style="left:16px;top:auto;bottom:40px">Još ${o.left} dostupno</span>${art(o.kind, 150)}</div>
    <div class="sheet"><div class="head"><div><h1>${esc(o.name)}</h1><p style="color:var(--mute);font-size:13px;margin-top:4px">${esc(o.store)} · ${o.km} km</p></div>
      <div class="price ${free ? 'free' : ''}" style="font-size:24px;text-align:right">${free ? 'Besplatno' : eur(o.sale)}${free ? '' : `<br><s>${eur(o.price)}</s>`}</div></div>
      <div class="tiles"><div class="tile">Preuzimanje<b>Danas ${o.from} – ${o.to}</b></div><div class="tile">Udaljenost<b>${o.km} km · ${Math.round(o.km * 13)} min hoda</b></div></div>
      <h2 style="font-size:16px">${org ? 'O donaciji' : 'Što možeš očekivati'}</h2><p style="font-size:14px;color:var(--mute);margin-top:6px;line-height:1.5">${esc(o.desc)}</p>
      <div class="tags">${o.tags.map(t => `<span class="tag">${esc(t)}</span>`).join('')}</div>
      <div class="store"><h3>${esc(o.store)}</h3><p style="font-size:12px;color:var(--mute);margin-top:2px">${esc(o.addr)}</p><div class="map" role="img" aria-label="Karta lokacije"></div></div></div></div>
    <div class="cta"><div class="qty"><button id="m" aria-label="Manje">−</button><b id="n" style="min-width:18px;text-align:center">1</b><button id="p" aria-label="Više">+</button></div>
      <button class="btn" id="go">${org ? 'Zatraži donaciju' : 'Rezerviraj'} · <span id="pr">${price()}</span></button></div>`);
  const set = d => { qty = Math.min(o.left, Math.max(1, qty + d)); root.querySelector('#n').textContent = qty; root.querySelector('#pr').textContent = price(); };
  root.querySelector('#m').onclick = () => set(-1); root.querySelector('#p').onclick = () => set(1);
  root.querySelector('#go').onclick = async () => {
    const code = await createOrder(o, qty, user.role);
    toast(org ? 'Zahtjev poslan. Prodajno mjesto će ga potvrditi.' : `Rezervirano. Kod za preuzimanje: ${code}`);
    location.hash = '#/orders';
  };
}
