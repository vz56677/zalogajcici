INSERT INTO korisnik (id, ime, email, oauth_provider, oauth_subject, uloga, status_odobrenja)
VALUES
    (9001, 'Zlatno zrno', 'demo9001@example.test', 'test', 'spasizalogaj-seed-prodavac-1', 'PRODAVAC', NULL),
    (9101, 'Ana', 'demo9101@example.test', 'test', 'spasizalogaj-seed-kupac-1', 'KUPAC', NULL),
    (9201, 'Udruga Zlatno zrno', 'demo9201@example.test', 'test', 'spasizalogaj-seed-udruga-1', 'ORGANIZACIJA', 'ODOBRENO'),
    (9002, 'Mrvica', 'demo9002@example.test', 'test', 'spasizalogaj-seed-prodavac-2', 'PRODAVAC', NULL),
    (9102, 'Luka', 'demo9102@example.test', 'test', 'spasizalogaj-seed-kupac-2', 'KUPAC', NULL),
    (9202, 'Udruga Mrvica', 'demo9202@example.test', 'test', 'spasizalogaj-seed-udruga-2', 'ORGANIZACIJA', 'ODOBRENO'),
    (9003, 'Topla korica', 'demo9003@example.test', 'test', 'spasizalogaj-seed-prodavac-3', 'PRODAVAC', NULL),
    (9103, 'Iva', 'demo9103@example.test', 'test', 'spasizalogaj-seed-kupac-3', 'KUPAC', NULL),
    (9203, 'Udruga Topla korica', 'demo9203@example.test', 'test', 'spasizalogaj-seed-udruga-3', 'ORGANIZACIJA', 'ODOBRENO'),
    (9004, 'Mali mlin', 'demo9004@example.test', 'test', 'spasizalogaj-seed-prodavac-4', 'PRODAVAC', NULL),
    (9104, 'Marko', 'demo9104@example.test', 'test', 'spasizalogaj-seed-kupac-4', 'KUPAC', NULL),
    (9204, 'Udruga Mali mlin', 'demo9204@example.test', 'test', 'spasizalogaj-seed-udruga-4', 'ORGANIZACIJA', 'ODOBRENO'),
    (9005, 'Jutarnji kruh', 'demo9005@example.test', 'test', 'spasizalogaj-seed-prodavac-5', 'PRODAVAC', NULL),
    (9105, 'Mia', 'demo9105@example.test', 'test', 'spasizalogaj-seed-kupac-5', 'KUPAC', NULL),
    (9205, 'Udruga Jutarnji kruh', 'demo9205@example.test', 'test', 'spasizalogaj-seed-udruga-5', 'ORGANIZACIJA', 'ODOBRENO'),
    (9006, 'Pšenični klas', 'demo9006@example.test', 'test', 'spasizalogaj-seed-prodavac-6', 'PRODAVAC', NULL),
    (9106, 'Ivan', 'demo9106@example.test', 'test', 'spasizalogaj-seed-kupac-6', 'KUPAC', NULL),
    (9206, 'Udruga Pšenični klas', 'demo9206@example.test', 'test', 'spasizalogaj-seed-udruga-6', 'ORGANIZACIJA', 'ODOBRENO'),
    (9007, 'Miris peciva', 'demo9007@example.test', 'test', 'spasizalogaj-seed-prodavac-7', 'PRODAVAC', NULL),
    (9107, 'Ema', 'demo9107@example.test', 'test', 'spasizalogaj-seed-kupac-7', 'KUPAC', NULL),
    (9207, 'Udruga Miris peciva', 'demo9207@example.test', 'test', 'spasizalogaj-seed-udruga-7', 'ORGANIZACIJA', 'ODOBRENO'),
    (9008, 'Pekarski kutak', 'demo9008@example.test', 'test', 'spasizalogaj-seed-prodavac-8', 'PRODAVAC', NULL),
    (9108, 'Petar', 'demo9108@example.test', 'test', 'spasizalogaj-seed-kupac-8', 'KUPAC', NULL),
    (9208, 'Udruga Pekarski kutak', 'demo9208@example.test', 'test', 'spasizalogaj-seed-udruga-8', 'ORGANIZACIJA', 'ODOBRENO'),
    (9009, 'Dobra štruca', 'demo9009@example.test', 'test', 'spasizalogaj-seed-prodavac-9', 'PRODAVAC', NULL),
    (9109, 'Sara', 'demo9109@example.test', 'test', 'spasizalogaj-seed-kupac-9', 'KUPAC', NULL),
    (9209, 'Udruga Dobra štruca', 'demo9209@example.test', 'test', 'spasizalogaj-seed-udruga-9', 'ORGANIZACIJA', 'ODOBRENO'),
    (9010, 'Pecivo i više', 'demo9010@example.test', 'test', 'spasizalogaj-seed-prodavac-10', 'PRODAVAC', NULL),
    (9110, 'Filip', 'demo9110@example.test', 'test', 'spasizalogaj-seed-kupac-10', 'KUPAC', NULL),
    (9210, 'Udruga Pecivo i više', 'demo9210@example.test', 'test', 'spasizalogaj-seed-udruga-10', 'ORGANIZACIJA', 'ODOBRENO');

INSERT INTO prodajno_mjesto (id, vlasnik_id, naziv, vrsta, adresa, kontakt, sirina, duzina)
VALUES
    (9001, 9001, 'Zlatno zrno', 'Pekarnica', 'Probna ulica 1, Zagreb', 'demo9001@example.test', 45.811, 15.97),
    (9002, 9002, 'Mrvica', 'Pekarnica', 'Probna ulica 2, Zagreb', 'demo9002@example.test', 45.812000000000005, 15.97),
    (9003, 9003, 'Topla korica', 'Pekarnica', 'Probna ulica 3, Zagreb', 'demo9003@example.test', 45.813, 15.97),
    (9004, 9004, 'Mali mlin', 'Pekarnica', 'Probna ulica 4, Zagreb', 'demo9004@example.test', 45.814, 15.97),
    (9005, 9005, 'Jutarnji kruh', 'Pekarnica', 'Probna ulica 5, Zagreb', 'demo9005@example.test', 45.815000000000005, 15.97),
    (9006, 9006, 'Pšenični klas', 'Pekarnica', 'Probna ulica 6, Zagreb', 'demo9006@example.test', 45.816, 15.97),
    (9007, 9007, 'Miris peciva', 'Pekarnica', 'Probna ulica 7, Zagreb', 'demo9007@example.test', 45.817, 15.97),
    (9008, 9008, 'Pekarski kutak', 'Pekarnica', 'Probna ulica 8, Zagreb', 'demo9008@example.test', 45.818000000000005, 15.97),
    (9009, 9009, 'Dobra štruca', 'Pekarnica', 'Probna ulica 9, Zagreb', 'demo9009@example.test', 45.819, 15.97),
    (9010, 9010, 'Pecivo i više', 'Pekarnica', 'Probna ulica 10, Zagreb', 'demo9010@example.test', 45.82, 15.97);

INSERT INTO pristup_mjestu (prodajno_mjesto_id, korisnik_id)
VALUES
    (9001, 9002),
    (9002, 9003),
    (9003, 9004),
    (9004, 9005),
    (9005, 9006),
    (9006, 9007),
    (9007, 9008),
    (9008, 9009),
    (9009, 9010),
    (9010, 9001);

INSERT INTO radno_vrijeme (prodajno_mjesto_id, datum, vrijeme_od, vrijeme_do)
VALUES
    (9001, (CURRENT_DATE +1), '08:00', '20:00'),
    (9002, (CURRENT_DATE +2), '08:00', '20:00'),
    (9003, (CURRENT_DATE +3), '08:00', '20:00'),
    (9004, (CURRENT_DATE +4), '08:00', '20:00'),
    (9005, (CURRENT_DATE +5), '08:00', '20:00'),
    (9006, (CURRENT_DATE +6), '08:00', '20:00'),
    (9007, (CURRENT_DATE +7), '08:00', '20:00'),
    (9008, (CURRENT_DATE +8), '08:00', '20:00'),
    (9009, (CURRENT_DATE +9), '08:00', '20:00'),
    (9010, (CURRENT_DATE +10), '08:00', '20:00');

INSERT INTO ponuda (id, prodajno_mjesto_id, vrsta, namjena, naziv, kategorija, redovna_cijena, prodajna_cijena, raspolozivo, objavljeno_u, preuzimanje_od, preuzimanje_do)
VALUES
    (9001, 9001, 'PAKET', 'PRODAJA', 'Pekarski paket', 'Pekarski proizvodi', 7, 3.5, 5, (CURRENT_DATE -7 + TIME '08:00'), (CURRENT_DATE -6 + TIME '08:00'), (CURRENT_DATE -6 + TIME '20:00')),
    (9101, 9001, 'PROIZVOD', 'DONACIJA', 'Kruh za donaciju', 'Pekarski proizvodi', 2, 0, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +1 + TIME '08:00'), (CURRENT_DATE +1 + TIME '20:00')),
    (9002, 9002, 'PAKET', 'PRODAJA', 'Pekarski paket', 'Pekarski proizvodi', 7, 3.5, 5, (CURRENT_DATE -7 + TIME '08:00'), (CURRENT_DATE -5 + TIME '08:00'), (CURRENT_DATE -5 + TIME '20:00')),
    (9102, 9002, 'PROIZVOD', 'DONACIJA', 'Kruh za donaciju', 'Pekarski proizvodi', 2, 0, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +2 + TIME '08:00'), (CURRENT_DATE +2 + TIME '20:00')),
    (9003, 9003, 'PAKET', 'PRODAJA', 'Pekarski paket', 'Pekarski proizvodi', 7, 3.5, 5, (CURRENT_DATE -7 + TIME '08:00'), (CURRENT_DATE -4 + TIME '08:00'), (CURRENT_DATE -4 + TIME '20:00')),
    (9103, 9003, 'PROIZVOD', 'DONACIJA', 'Kruh za donaciju', 'Pekarski proizvodi', 2, 0, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +3 + TIME '08:00'), (CURRENT_DATE +3 + TIME '20:00')),
    (9004, 9004, 'PAKET', 'PRODAJA', 'Pekarski paket', 'Pekarski proizvodi', 7, 3.5, 5, (CURRENT_DATE -7 + TIME '08:00'), (CURRENT_DATE -3 + TIME '08:00'), (CURRENT_DATE -3 + TIME '20:00')),
    (9104, 9004, 'PROIZVOD', 'DONACIJA', 'Kruh za donaciju', 'Pekarski proizvodi', 2, 0, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +4 + TIME '08:00'), (CURRENT_DATE +4 + TIME '20:00')),
    (9005, 9005, 'PAKET', 'PRODAJA', 'Pekarski paket', 'Pekarski proizvodi', 7, 3.5, 5, (CURRENT_DATE -7 + TIME '08:00'), (CURRENT_DATE -2 + TIME '08:00'), (CURRENT_DATE -2 + TIME '20:00')),
    (9105, 9005, 'PROIZVOD', 'DONACIJA', 'Kruh za donaciju', 'Pekarski proizvodi', 2, 0, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +5 + TIME '08:00'), (CURRENT_DATE +5 + TIME '20:00')),
    (9006, 9006, 'PAKET', 'PRODAJA', 'Pekarski paket', 'Pekarski proizvodi', 7, 3.5, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +6 + TIME '08:00'), (CURRENT_DATE +6 + TIME '20:00')),
    (9106, 9006, 'PROIZVOD', 'DONACIJA', 'Kruh za donaciju', 'Pekarski proizvodi', 2, 0, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +6 + TIME '08:00'), (CURRENT_DATE +6 + TIME '20:00')),
    (9007, 9007, 'PAKET', 'PRODAJA', 'Pekarski paket', 'Pekarski proizvodi', 7, 3.5, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +7 + TIME '08:00'), (CURRENT_DATE +7 + TIME '20:00')),
    (9107, 9007, 'PROIZVOD', 'DONACIJA', 'Kruh za donaciju', 'Pekarski proizvodi', 2, 0, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +7 + TIME '08:00'), (CURRENT_DATE +7 + TIME '20:00')),
    (9008, 9008, 'PAKET', 'PRODAJA', 'Pekarski paket', 'Pekarski proizvodi', 7, 3.5, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +8 + TIME '08:00'), (CURRENT_DATE +8 + TIME '20:00')),
    (9108, 9008, 'PROIZVOD', 'DONACIJA', 'Kruh za donaciju', 'Pekarski proizvodi', 2, 0, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +8 + TIME '08:00'), (CURRENT_DATE +8 + TIME '20:00')),
    (9009, 9009, 'PAKET', 'PRODAJA', 'Pekarski paket', 'Pekarski proizvodi', 7, 3.5, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +9 + TIME '08:00'), (CURRENT_DATE +9 + TIME '20:00')),
    (9109, 9009, 'PROIZVOD', 'DONACIJA', 'Kruh za donaciju', 'Pekarski proizvodi', 2, 0, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +9 + TIME '08:00'), (CURRENT_DATE +9 + TIME '20:00')),
    (9010, 9010, 'PAKET', 'PRODAJA', 'Pekarski paket', 'Pekarski proizvodi', 7, 3.5, 0, CURRENT_TIMESTAMP, (CURRENT_DATE +10 + TIME '08:00'), (CURRENT_DATE +10 + TIME '20:00')),
    (9110, 9010, 'PROIZVOD', 'DONACIJA', 'Kruh za donaciju', 'Pekarski proizvodi', 2, 0, 5, CURRENT_TIMESTAMP, (CURRENT_DATE +10 + TIME '08:00'), (CURRENT_DATE +10 + TIME '20:00'));

INSERT INTO rezervacija (id, kupac_id, prodajno_mjesto_id, kod_preuzimanja, status, kreirana_u, rok_preuzimanja, preuzeta_u, preuzimanje_potvrdio_id)
VALUES
    (9001, 9101, 9001, 'SEED-PREUZ-1', 'PREUZETA', (CURRENT_DATE -7 + TIME '09:00'), (CURRENT_DATE -6 + TIME '20:00'), (CURRENT_DATE -6 + TIME '12:00'), 9001),
    (9002, 9102, 9002, 'SEED-PREUZ-2', 'PREUZETA', (CURRENT_DATE -7 + TIME '09:00'), (CURRENT_DATE -5 + TIME '20:00'), (CURRENT_DATE -5 + TIME '12:00'), 9002),
    (9003, 9103, 9003, 'SEED-PREUZ-3', 'PREUZETA', (CURRENT_DATE -7 + TIME '09:00'), (CURRENT_DATE -4 + TIME '20:00'), (CURRENT_DATE -4 + TIME '12:00'), 9003),
    (9004, 9104, 9004, 'SEED-PREUZ-4', 'PREUZETA', (CURRENT_DATE -7 + TIME '09:00'), (CURRENT_DATE -3 + TIME '20:00'), (CURRENT_DATE -3 + TIME '12:00'), 9004),
    (9005, 9105, 9005, 'SEED-PREUZ-5', 'PREUZETA', (CURRENT_DATE -7 + TIME '09:00'), (CURRENT_DATE -2 + TIME '20:00'), (CURRENT_DATE -2 + TIME '12:00'), 9005),
    (9006, 9106, 9006, 'SEED-PREUZ-6', 'AKTIVNA', CURRENT_TIMESTAMP, (CURRENT_DATE +6 + TIME '20:00'), NULL, NULL),
    (9007, 9107, 9007, 'SEED-PREUZ-7', 'AKTIVNA', CURRENT_TIMESTAMP, (CURRENT_DATE +7 + TIME '20:00'), NULL, NULL),
    (9008, 9108, 9008, 'SEED-PREUZ-8', 'AKTIVNA', CURRENT_TIMESTAMP, (CURRENT_DATE +8 + TIME '20:00'), NULL, NULL),
    (9009, 9109, 9009, 'SEED-PREUZ-9', 'AKTIVNA', CURRENT_TIMESTAMP, (CURRENT_DATE +9 + TIME '20:00'), NULL, NULL),
    (9010, 9110, 9010, NULL, 'CEKA_UPLATU', CURRENT_TIMESTAMP, (CURRENT_DATE +10 + TIME '20:00'), NULL, NULL);

INSERT INTO stavka_rezervacije (rezervacija_id, ponuda_id, prodajno_mjesto_id, kolicina, jedinicna_cijena)
VALUES
    (9001, 9001, 9001, 2, 3.5),
    (9002, 9002, 9002, 2, 3.5),
    (9003, 9003, 9003, 2, 3.5),
    (9004, 9004, 9004, 2, 3.5),
    (9005, 9005, 9005, 2, 3.5),
    (9006, 9006, 9006, 2, 3.5),
    (9007, 9007, 9007, 2, 3.5),
    (9008, 9008, 9008, 2, 3.5),
    (9009, 9009, 9009, 2, 3.5),
    (9010, 9010, 9010, 2, 3.5);

INSERT INTO zahtjev_donacije (id, organizacija_id, ponuda_id, trazena_kolicina, status, poslan_u)
VALUES
    (9001, 9201, 9101, 4, 'NA_CEKANJU', (CURRENT_TIMESTAMP - INTERVAL '1 minute')),
    (9002, 9202, 9102, 4, 'ODBIJENO', (CURRENT_TIMESTAMP - INTERVAL '1 minute')),
    (9003, 9203, 9103, 4, 'ODOBRENO', (CURRENT_TIMESTAMP - INTERVAL '1 minute')),
    (9004, 9204, 9104, 4, 'NA_CEKANJU', (CURRENT_TIMESTAMP - INTERVAL '1 minute')),
    (9005, 9205, 9105, 4, 'ODBIJENO', (CURRENT_TIMESTAMP - INTERVAL '1 minute')),
    (9006, 9206, 9106, 4, 'ODOBRENO', (CURRENT_TIMESTAMP - INTERVAL '1 minute')),
    (9007, 9207, 9107, 4, 'NA_CEKANJU', (CURRENT_TIMESTAMP - INTERVAL '1 minute')),
    (9008, 9208, 9108, 4, 'ODBIJENO', (CURRENT_TIMESTAMP - INTERVAL '1 minute')),
    (9009, 9209, 9109, 4, 'ODOBRENO', (CURRENT_TIMESTAMP - INTERVAL '1 minute')),
    (9010, 9210, 9110, 4, 'NA_CEKANJU', (CURRENT_TIMESTAMP - INTERVAL '1 minute'));

INSERT INTO akcija (id, organizator_id, naziv, opis, lokacija, pocetak, zavrsetak)
VALUES
    (9001, 9201, 'Prikupljanje hrane 1', 'Prikupljanje trajnih namirnica', 'Zagreb', (CURRENT_DATE +1 + TIME '08:00'), (CURRENT_DATE +1 + TIME '20:00')),
    (9002, 9202, 'Prikupljanje hrane 2', 'Prikupljanje trajnih namirnica', 'Zagreb', (CURRENT_DATE +2 + TIME '08:00'), (CURRENT_DATE +2 + TIME '20:00')),
    (9003, 9203, 'Prikupljanje hrane 3', 'Prikupljanje trajnih namirnica', 'Zagreb', (CURRENT_DATE +3 + TIME '08:00'), (CURRENT_DATE +3 + TIME '20:00')),
    (9004, 9204, 'Prikupljanje hrane 4', 'Prikupljanje trajnih namirnica', 'Zagreb', (CURRENT_DATE +4 + TIME '08:00'), (CURRENT_DATE +4 + TIME '20:00')),
    (9005, 9205, 'Prikupljanje hrane 5', 'Prikupljanje trajnih namirnica', 'Zagreb', (CURRENT_DATE +5 + TIME '08:00'), (CURRENT_DATE +5 + TIME '20:00')),
    (9006, 9206, 'Prikupljanje hrane 6', 'Prikupljanje trajnih namirnica', 'Zagreb', (CURRENT_DATE +6 + TIME '08:00'), (CURRENT_DATE +6 + TIME '20:00')),
    (9007, 9207, 'Prikupljanje hrane 7', 'Prikupljanje trajnih namirnica', 'Zagreb', (CURRENT_DATE +7 + TIME '08:00'), (CURRENT_DATE +7 + TIME '20:00')),
    (9008, 9208, 'Prikupljanje hrane 8', 'Prikupljanje trajnih namirnica', 'Zagreb', (CURRENT_DATE +8 + TIME '08:00'), (CURRENT_DATE +8 + TIME '20:00')),
    (9009, 9209, 'Prikupljanje hrane 9', 'Prikupljanje trajnih namirnica', 'Zagreb', (CURRENT_DATE +9 + TIME '08:00'), (CURRENT_DATE +9 + TIME '20:00')),
    (9010, 9210, 'Prikupljanje hrane 10', 'Prikupljanje trajnih namirnica', 'Zagreb', (CURRENT_DATE +10 + TIME '08:00'), (CURRENT_DATE +10 + TIME '20:00'));

INSERT INTO potreba_akcije (id, akcija_id, naziv_proizvoda, jedinica_mjere, trazena_kolicina)
VALUES
    (9001, 9001, 'Tjestenina', 'kg', 20),
    (9002, 9002, 'Tjestenina', 'kg', 20),
    (9003, 9003, 'Tjestenina', 'kg', 20),
    (9004, 9004, 'Tjestenina', 'kg', 20),
    (9005, 9005, 'Tjestenina', 'kg', 20),
    (9006, 9006, 'Tjestenina', 'kg', 20),
    (9007, 9007, 'Tjestenina', 'kg', 20),
    (9008, 9008, 'Tjestenina', 'kg', 20),
    (9009, 9009, 'Tjestenina', 'kg', 20),
    (9010, 9010, 'Tjestenina', 'kg', 20);

INSERT INTO donacija_akcije (id, potreba_id, donator_id, najavljena_kolicina)
VALUES
    (9001, 9001, 9101, 3),
    (9002, 9002, 9102, 4),
    (9003, 9003, 9103, 5),
    (9004, 9004, 9104, 6),
    (9005, 9005, 9105, 7),
    (9006, 9006, 9106, 8),
    (9007, 9007, 9107, 9),
    (9008, 9008, 9108, 10),
    (9009, 9009, 9109, 11),
    (9010, 9010, 9110, 12);

INSERT INTO cjenik_clanarine (id, vrsta, vrijedi_od, cijena)
VALUES
    (9001, 'MJESECNA', (CURRENT_DATE -313 + TIME '08:00'), 10),
    (9002, 'MJESECNA', (CURRENT_DATE -282 + TIME '08:00'), 10),
    (9003, 'MJESECNA', (CURRENT_DATE -254 + TIME '08:00'), 10),
    (9004, 'MJESECNA', (CURRENT_DATE -223 + TIME '08:00'), 10),
    (9005, 'MJESECNA', (CURRENT_DATE -193 + TIME '08:00'), 10),
    (9006, 'MJESECNA', (CURRENT_DATE -162 + TIME '08:00'), 10),
    (9007, 'MJESECNA', (CURRENT_DATE -132 + TIME '08:00'), 10),
    (9008, 'MJESECNA', (CURRENT_DATE -101 + TIME '08:00'), 10),
    (9009, 'MJESECNA', (CURRENT_DATE -70 + TIME '08:00'), 10),
    (9010, 'MJESECNA', (CURRENT_DATE -40 + TIME '08:00'), 10);

INSERT INTO clanarina (id, prodavac_id, cjenik_id, vrijedi_od, vrijedi_do, iznos)
VALUES
    (9001, 9001, 9001, (CURRENT_DATE -9 + TIME '08:00'), (CURRENT_DATE +21 + TIME '08:00'), 10),
    (9002, 9002, 9002, (CURRENT_DATE -9 + TIME '08:00'), (CURRENT_DATE +21 + TIME '08:00'), 10),
    (9003, 9003, 9003, (CURRENT_DATE -9 + TIME '08:00'), (CURRENT_DATE +21 + TIME '08:00'), 10),
    (9004, 9004, 9004, (CURRENT_DATE -9 + TIME '08:00'), (CURRENT_DATE +21 + TIME '08:00'), 10),
    (9005, 9005, 9005, (CURRENT_DATE -9 + TIME '08:00'), (CURRENT_DATE +21 + TIME '08:00'), 10),
    (9006, 9006, 9006, (CURRENT_DATE -9 + TIME '08:00'), (CURRENT_DATE +21 + TIME '08:00'), 10),
    (9007, 9007, 9007, (CURRENT_DATE -9 + TIME '08:00'), (CURRENT_DATE +21 + TIME '08:00'), 10),
    (9008, 9008, 9008, (CURRENT_DATE -9 + TIME '08:00'), (CURRENT_DATE +21 + TIME '08:00'), 10),
    (9009, 9009, 9009, (CURRENT_DATE -9 + TIME '08:00'), (CURRENT_DATE +21 + TIME '08:00'), 10),
    (9010, 9010, 9010, (CURRENT_DATE -9 + TIME '08:00'), (CURRENT_DATE +21 + TIME '08:00'), 10);

INSERT INTO uplata (id, rezervacija_id, clanarina_id, vrsta, nacin, status, iznos, evidentirana_u)
VALUES
    (9001, 9001, NULL, 'UPLATA', 'KARTICA', 'USPJESNO', 7, (CURRENT_DATE -7 + TIME '09:00')),
    (9101, NULL, 9001, 'UPLATA', 'KARTICA', 'USPJESNO', 10, (CURRENT_DATE -9 + TIME '08:00')),
    (9002, 9002, NULL, 'UPLATA', 'PAYPAL', 'USPJESNO', 7, (CURRENT_DATE -7 + TIME '09:00')),
    (9102, NULL, 9002, 'UPLATA', 'PAYPAL', 'USPJESNO', 10, (CURRENT_DATE -9 + TIME '08:00')),
    (9003, 9003, NULL, 'UPLATA', 'KARTICA', 'USPJESNO', 7, (CURRENT_DATE -7 + TIME '09:00')),
    (9103, NULL, 9003, 'UPLATA', 'KARTICA', 'USPJESNO', 10, (CURRENT_DATE -9 + TIME '08:00')),
    (9004, 9004, NULL, 'UPLATA', 'PAYPAL', 'USPJESNO', 7, (CURRENT_DATE -7 + TIME '09:00')),
    (9104, NULL, 9004, 'UPLATA', 'PAYPAL', 'USPJESNO', 10, (CURRENT_DATE -9 + TIME '08:00')),
    (9005, 9005, NULL, 'UPLATA', 'KARTICA', 'USPJESNO', 7, (CURRENT_DATE -7 + TIME '09:00')),
    (9105, NULL, 9005, 'UPLATA', 'KARTICA', 'USPJESNO', 10, (CURRENT_DATE -9 + TIME '08:00')),
    (9006, 9006, NULL, 'UPLATA', 'PAYPAL', 'USPJESNO', 7, CURRENT_TIMESTAMP),
    (9106, NULL, 9006, 'UPLATA', 'PAYPAL', 'USPJESNO', 10, (CURRENT_DATE -9 + TIME '08:00')),
    (9007, 9007, NULL, 'UPLATA', 'KARTICA', 'USPJESNO', 7, CURRENT_TIMESTAMP),
    (9107, NULL, 9007, 'UPLATA', 'KARTICA', 'USPJESNO', 10, (CURRENT_DATE -9 + TIME '08:00')),
    (9008, 9008, NULL, 'UPLATA', 'PAYPAL', 'USPJESNO', 7, CURRENT_TIMESTAMP),
    (9108, NULL, 9008, 'UPLATA', 'PAYPAL', 'USPJESNO', 10, (CURRENT_DATE -9 + TIME '08:00')),
    (9009, 9009, NULL, 'UPLATA', 'KARTICA', 'USPJESNO', 7, CURRENT_TIMESTAMP),
    (9109, NULL, 9009, 'UPLATA', 'KARTICA', 'USPJESNO', 10, (CURRENT_DATE -9 + TIME '08:00')),
    (9010, 9010, NULL, 'UPLATA', 'PAYPAL', 'NA_CEKANJU', 7, CURRENT_TIMESTAMP),
    (9110, NULL, 9010, 'UPLATA', 'PAYPAL', 'USPJESNO', 10, (CURRENT_DATE -9 + TIME '08:00'));

INSERT INTO recenzija (rezervacija_id, ocjena, komentar, objavljena_u)
VALUES
    (9001, 5, 'Paket je bio spreman za preuzimanje.', (CURRENT_DATE -6 + TIME '13:00')),
    (9002, 4, 'Paket je bio spreman za preuzimanje.', (CURRENT_DATE -5 + TIME '13:00')),
    (9003, 5, 'Paket je bio spreman za preuzimanje.', (CURRENT_DATE -4 + TIME '13:00')),
    (9004, 4, 'Paket je bio spreman za preuzimanje.', (CURRENT_DATE -3 + TIME '13:00')),
    (9005, 5, 'Paket je bio spreman za preuzimanje.', (CURRENT_DATE -2 + TIME '13:00'));

INSERT INTO pretplata_obavijesti (id, korisnik_id, prodajno_mjesto_id, kategorija)
VALUES
    (9001, 9101, NULL, 'Pekarski proizvodi'),
    (9002, 9102, 9002, NULL),
    (9003, 9103, NULL, 'Pekarski proizvodi'),
    (9004, 9104, 9004, NULL),
    (9005, 9105, NULL, 'Pekarski proizvodi'),
    (9006, 9106, 9006, NULL),
    (9007, 9107, NULL, 'Pekarski proizvodi'),
    (9008, 9108, 9008, NULL),
    (9009, 9109, NULL, 'Pekarski proizvodi'),
    (9010, 9110, 9010, NULL);
