import { login, loginGoogle } from '../services/api.js';
import { icon, google, logoMark } from '../components/ui.js';

export function authPage(root, done) {
  let reg = false, role = 'buyer';
  const draw = () => {
    root.innerHTML = `<div class="auth" data-r="${role}">
    <section class="auth-hero">${logoMark}<h1>SpasiZalogaj</h1><small>by zalogajcici</small>
      <p>${reg ? 'Pridruži se i spasi obrok već danas.' : 'Odlična hrana, spašena od bacanja. Preuzmi u blizini po nižoj cijeni.'}</p></section>
    <section class="auth-card"><h2>${reg ? 'Izradi svoj račun' : 'Dobro došli natrag'}</h2>
      <div class="seg" role="group" aria-label="Vrsta računa">
        <button type="button" data-role="buyer" aria-pressed="${role === 'buyer'}">Kupac</button>
        <button type="button" data-role="org" aria-pressed="${role === 'org'}">Organizacija</button></div>
      <form id="f" novalidate>
        ${reg ? `<label for="n">${role === 'org' ? 'Naziv organizacije' : 'Ime'}</label><input class="in" id="n" name="name" autocomplete="name">` : ''}
        <label for="e">E-pošta</label><input class="in" id="e" name="email" type="email" placeholder="ti@primjer.hr" autocomplete="email">
        <label for="p">Lozinka</label><input class="in" id="p" name="password" type="password" placeholder="Tvoja lozinka" autocomplete="${reg ? 'new-password' : 'current-password'}">
        <p class="err" id="m" role="alert"></p>
        <button class="btn">${reg ? 'Registriraj se' : 'Prijava'}</button></form>
      <div class="sep">ili</div>
      <button class="btn alt" id="g" type="button">${google} ${reg ? 'Registriraj se s Googleom' : 'Nastavi s Googleom'}</button>
      <p class="small">${reg ? 'Već imaš račun?' : 'Prvi put ovdje?'} <button class="link" id="t" type="button">${reg ? 'Prijavi se' : 'Registriraj se'}</button></p></section></div>`;
    root.querySelectorAll('[data-role]').forEach(b => b.onclick = () => { role = b.dataset.role; draw(); });
    root.querySelector('#t').onclick = () => { reg = !reg; draw(); };
    root.querySelector('#g').onclick = async () => { await loginGoogle(role); done(); };
    root.querySelector('#f').onsubmit = async e => {
      e.preventDefault();
      try { await login({ ...Object.fromEntries(new FormData(e.target)), role }); done(); }
      catch (x) { root.querySelector('#m').textContent = x.message; }
    };
  };
  draw();
}
