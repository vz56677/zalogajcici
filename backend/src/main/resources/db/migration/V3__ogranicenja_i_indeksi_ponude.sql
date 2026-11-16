ALTER TABLE ponuda
    ADD CONSTRAINT ponuda_prodajna_cijena_check
        CHECK (prodajna_cijena <= redovna_cijena),
    ADD CONSTRAINT ponuda_proizvod_rok_upotrebe_check
        CHECK (vrsta <> 'PROIZVOD' OR rok_upotrebe IS NOT NULL);

CREATE INDEX ponuda_preuzimanje_do_idx ON ponuda (preuzimanje_do);
CREATE INDEX ponuda_prodajno_mjesto_id_idx ON ponuda (prodajno_mjesto_id);
