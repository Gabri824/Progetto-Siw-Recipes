-- 1. INSERIMENTO UTENTI (Ruolo DEFAULT)
-- Password per tutti: "password"
INSERT INTO utente (id, nome, cognome, data_di_nascita, banned) VALUES (1, 'Mario', 'Rossi', '1980-05-12', false) ON CONFLICT (id) DO NOTHING;
INSERT INTO credentials (id, email, password, role, utente_id) VALUES (1, 'mario@email.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'DEFAULT', 1) ON CONFLICT (id) DO NOTHING;

INSERT INTO utente (id, nome, cognome, data_di_nascita, banned) VALUES (2, 'Luigi', 'Verdi', '1992-11-23', false) ON CONFLICT (id) DO NOTHING;
INSERT INTO credentials (id, email, password, role, utente_id) VALUES (2, 'luigi@email.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'DEFAULT', 2) ON CONFLICT (id) DO NOTHING;

INSERT INTO utente (id, nome, cognome, data_di_nascita, banned) VALUES (3, 'Anna', 'Bianchi', '1985-03-15', false) ON CONFLICT (id) DO NOTHING;
INSERT INTO credentials (id, email, password, role, utente_id) VALUES (3, 'anna@email.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'DEFAULT', 3) ON CONFLICT (id) DO NOTHING;

INSERT INTO utente (id, nome, cognome, data_di_nascita, banned) VALUES (4, 'Giulia', 'Neri', '1995-07-30', false) ON CONFLICT (id) DO NOTHING;
INSERT INTO credentials (id, email, password, role, utente_id) VALUES (4, 'giulia@email.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'DEFAULT', 4) ON CONFLICT (id) DO NOTHING;

INSERT INTO utente (id, nome, cognome, data_di_nascita, banned) VALUES (5, 'Luca', 'Blu', '1988-01-10', false) ON CONFLICT (id) DO NOTHING;
INSERT INTO credentials (id, email, password, role, utente_id) VALUES (5, 'luca@email.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'DEFAULT', 5) ON CONFLICT (id) DO NOTHING;

INSERT INTO utente (id, nome, cognome, data_di_nascita, banned) VALUES (6, 'Gabriele', 'Iorio', '2003-07-08', false) ON CONFLICT (id) DO NOTHING;
INSERT INTO credentials (id, email, password, role, utente_id) VALUES (6, 'gabri.iorio@gmail.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'ADMIN', 6) ON CONFLICT (id) DO NOTHING;

-- 2. INSERIMENTO RICETTE (10 Occorrenze)

-- Ricetta 1: Carbonara (Mario)
INSERT INTO ricetta (id, titolo, descrizione, procedimento, tempo_preparazione, difficoltà, categoria, data_pubblicazione, utente_id) VALUES (1, 'Spaghetti alla Carbonara', 'Un classico della cucina romana.', '1. Portare a ebollizione una pentola di acqua salata e cuocere gli spaghetti al dente.
2. Nel frattempo, tagliare il guanciale a listarelle spesse circa mezzo centimetro e rosolarlo in una padella antiaderente a fuoco medio senza aggiungere olio, fino a quando diventa croccante e dorato.
3. In una ciotola, sbattere i tuorli d''uovo con il pecorino romano grattugiato e una generosa macinata di pepe nero, fino ad ottenere una crema liscia e omogenea.
4. Scolare la pasta tenendo da parte un mestolo di acqua di cottura e versarla nella padella con il guanciale, togliendo dal fuoco.
5. Aggiungere la crema di uova e pecorino mescolando rapidamente per evitare che le uova si rapprendano. Se necessario, aggiungere un po'' di acqua di cottura per ottenere la consistenza desiderata.
6. Impiattare subito e servire con un''ulteriore spolverata di pecorino e pepe nero.', 20, 'Media', 'Primi', '2025-01-10', 1) ON CONFLICT (id) DO NOTHING;

-- Ricetta 2: Tiramisù (Anna)
INSERT INTO ricetta (id, titolo, descrizione, procedimento, tempo_preparazione, difficoltà, categoria, data_pubblicazione, utente_id) VALUES (2, 'Tiramisù Classico', 'Il dolce italiano più amato.', '1. Preparare il caffè espresso e lasciarlo raffreddare completamente in un piatto fondo.
2. Separare i tuorli dagli albumi. Montare i tuorli con lo zucchero usando una frusta elettrica fino a ottenere un composto chiaro e spumoso.
3. Aggiungere il mascarpone ai tuorli montati, mescolando delicatamente dal basso verso l''alto per non smontare il composto.
4. In una ciotola a parte, montare gli albumi a neve ferma con un pizzico di sale, poi incorporarli delicatamente alla crema di mascarpone.
5. Inzuppare velocemente i savoiardi nel caffè freddo, uno alla volta, senza lasciarli troppo a lungo per evitare che si sfaldino.
6. Disporre uno strato di savoiardi sul fondo di una pirofila, coprire con uno strato abbondante di crema al mascarpone. Ripetere con un secondo strato.
7. Spolverare la superficie con cacao amaro in polvere e riporre in frigorifero per almeno 4 ore, meglio se tutta la notte.
8. Servire freddo, spolverando con altro cacao al momento.', 40, 'Facile', 'Dolci', '2025-01-12', 3) ON CONFLICT (id) DO NOTHING;

-- Ricetta 3: Lasagna (Luigi)
INSERT INTO ricetta (id, titolo, descrizione, procedimento, tempo_preparazione, difficoltà, categoria, data_pubblicazione, utente_id) VALUES (3, 'Lasagna alla Bolognese', 'Ricca e gustosa lasagna al forno.', '1. Preparare il ragù: soffriggere sedano, carota e cipolla tritati finemente in olio d''oliva. Aggiungere la carne macinata e rosolarla a fuoco vivo. Sfumare con vino rosso e aggiungere la passata di pomodoro. Cuocere a fuoco basso per almeno 2 ore.
2. Preparare la besciamella: sciogliere il burro in un pentolino, aggiungere la farina e mescolare per un minuto. Versare il latte caldo poco alla volta mescolando continuamente con una frusta per evitare grumi. Aggiungere sale, pepe e noce moscata.
3. Preriscaldare il forno a 180°C.
4. In una teglia imburrata, disporre un primo strato di sfoglia di pasta, coprire con ragù, besciamella e una spolverata di Parmigiano Reggiano grattugiato.
5. Ripetere gli strati per 4-5 volte, terminando con besciamella e Parmigiano in superficie.
6. Infornare per 30-35 minuti fino a ottenere una crosticina dorata in superficie.
7. Lasciar riposare 10 minuti prima di tagliare e servire.', 120, 'Difficile', 'Primi', '2025-01-15', 2) ON CONFLICT (id) DO NOTHING;

-- Ricetta 4: Pollo al Curry (Giulia)
INSERT INTO ricetta (id, titolo, descrizione, procedimento, tempo_preparazione, difficoltà, categoria, data_pubblicazione, utente_id) VALUES (4, 'Pollo al Curry e Riso Basmati', 'Un tocco orientale speziato.', '1. Tagliare il petto di pollo a cubetti di circa 2 cm e condirlo con sale e un cucchiaino di curcuma.
2. In una padella capiente, scaldare l''olio e soffriggere la cipolla tritata finemente fino a doratura. Aggiungere aglio e zenzero fresco grattugiato.
3. Unire i cubetti di pollo e rosolarli a fuoco vivo per 3-4 minuti, girandoli su tutti i lati.
4. Aggiungere il curry in polvere, il garam masala e un pizzico di peperoncino. Mescolare per un minuto per tostare le spezie.
5. Versare il latte di cocco e un po'' di brodo di pollo. Portare a ebollizione, poi abbassare il fuoco e cuocere coperto per 15-20 minuti.
6. Nel frattempo, sciacquare il riso basmati sotto acqua corrente e cuocerlo in acqua salata per 12 minuti. Scolare e tenere coperto.
7. A fine cottura, aggiungere al pollo qualche foglia di coriandolo fresco e una spruzzata di succo di lime.
8. Servire il pollo al curry sul letto di riso basmati.', 35, 'Media', 'Secondi', '2025-01-18', 4) ON CONFLICT (id) DO NOTHING;

-- Ricetta 5: Insalata Caprese (Luca)
INSERT INTO ricetta (id, titolo, descrizione, procedimento, tempo_preparazione, difficoltà, categoria, data_pubblicazione, utente_id) VALUES (5, 'Insalata Caprese', 'Fresca e veloce.', '1. Scegliere pomodori maturi e sodi, preferibilmente cuore di bue, e tagliarli a fette spesse circa mezzo centimetro.
2. Tagliare la mozzarella di bufala fresca a fette dello stesso spessore. Lasciarla a temperatura ambiente per 15 minuti prima di servire.
3. Disporre le fette di pomodoro e mozzarella alternandole su un piatto da portata, leggermente sovrapposte.
4. Distribuire foglie di basilico fresco tra le fette.
5. Condire con un filo generoso di olio extravergine d''oliva di qualità, un pizzico di sale marino e una leggera spolverata di origano.
6. Per un tocco in più, aggiungere una macinata di pepe nero e servire immediatamente.', 10, 'Facile', 'Antipasti', '2025-01-20', 5) ON CONFLICT (id) DO NOTHING;

-- Ricetta 6: Risotto ai Funghi (Mario)
INSERT INTO ricetta (id, titolo, descrizione, procedimento, tempo_preparazione, difficoltà, categoria, data_pubblicazione, utente_id) VALUES (6, 'Risotto ai Funghi Porcini', 'Cremoso e autunnale.', '1. Se si usano funghi porcini secchi, metterli in ammollo in acqua tiepida per 30 minuti, poi scolarli e filtrare il liquido di ammollo.
2. Preparare il brodo vegetale e tenerlo caldo in un pentolino a fuoco basso.
3. In una casseruola, soffriggere cipolla tritata finemente in un filo d''olio e una noce di burro fino a trasparenza.
4. Aggiungere il riso Carnaroli e tostarlo per 2-3 minuti mescolando, fino a quando i chicchi diventano traslucidi.
5. Sfumare con mezzo bicchiere di vino bianco secco e mescolare fino a completo assorbimento.
6. Iniziare ad aggiungere il brodo caldo un mestolo alla volta, mescolando frequentemente e aspettando che venga assorbito prima di aggiungerne altro.
7. A metà cottura, dopo circa 8-9 minuti, aggiungere i funghi porcini tagliati a pezzi e continuare la cottura.
8. Dopo circa 16-18 minuti totali, togliere dal fuoco e mantecare con una noce di burro freddo e Parmigiano Reggiano grattugiato.
9. Coprire e lasciar riposare 2 minuti. Il risotto deve risultare cremoso e all''onda. Servire con una spolverata di prezzemolo fresco.', 45, 'Media', 'Primi', '2025-01-22', 1) ON CONFLICT (id) DO NOTHING;

-- Ricetta 7: Torta di Mele (Anna)
INSERT INTO ricetta (id, titolo, descrizione, procedimento, tempo_preparazione, difficoltà, categoria, data_pubblicazione, utente_id) VALUES (7, 'Torta di Mele della Nonna', 'Soffice e profumata.', '1. Preriscaldare il forno a 180°C e imburrare e infarinare una tortiera da 24 cm di diametro.
2. Sbucciare le mele (preferibilmente Renette o Golden), tagliarle a fettine sottili e irrorarle con succo di limone per evitare che anneriscano.
3. In una ciotola capiente, sbattere le uova con lo zucchero usando una frusta elettrica per 5-6 minuti fino a ottenere un composto chiaro e spumoso.
4. Aggiungere l''olio di semi (o burro fuso) e il latte, mescolando delicatamente.
5. Setacciare la farina con il lievito per dolci e incorporarla al composto poco alla volta, mescolando dal basso verso l''alto.
6. Aggiungere la scorza grattugiata di un limone e una bustina di vanillina per profumare l''impasto.
7. Unire due terzi delle fettine di mele all''impasto e versare nella tortiera. Disporre le fettine rimanenti sulla superficie in modo decorativo.
8. Spolverare con zucchero di canna e infornare per 40-45 minuti, fino a doratura. Verificare la cottura con uno stecchino.
9. Lasciar raffreddare prima di sformare. Spolverare con zucchero a velo prima di servire.', 60, 'Facile', 'Dolci', '2025-01-25', 3) ON CONFLICT (id) DO NOTHING;

-- Ricetta 8: Parmigiana (Luigi)
INSERT INTO ricetta (id, titolo, descrizione, procedimento, tempo_preparazione, difficoltà, categoria, data_pubblicazione, utente_id) VALUES (8, 'Parmigiana di Melanzane', 'Piatto unico sostanzioso.', '1. Lavare le melanzane e tagliarle a fette di circa mezzo centimetro. Cospargerle di sale grosso e lasciarle spurgare in uno scolapasta per almeno 30 minuti.
2. Nel frattempo, preparare il sugo: soffriggere aglio in olio d''oliva, aggiungere la passata di pomodoro, sale, basilico fresco e un pizzico di zucchero. Cuocere a fuoco lento per 20 minuti.
3. Asciugare le melanzane con carta assorbente e friggerle in abbondante olio di semi ben caldo, fino a doratura su entrambi i lati. Scolarle su carta assorbente.
4. Preriscaldare il forno a 180°C.
5. In una teglia da forno, stendere un filo di sugo sul fondo. Disporre uno strato di melanzane fritte, coprire con sugo di pomodoro, fette di mozzarella fiordilatte e una spolverata di Parmigiano Reggiano grattugiato.
6. Ripetere gli strati fino a esaurimento degli ingredienti, terminando con sugo e abbondante Parmigiano.
7. Infornare per 30-35 minuti fino a ottenere una superficie dorata e il formaggio ben fuso.
8. Lasciar riposare almeno 15 minuti prima di tagliare, si gusta meglio tiepida.', 90, 'Media', 'Secondi', '2025-01-28', 2) ON CONFLICT (id) DO NOTHING;

-- Ricetta 9: Bruschetta al Pomodoro (Giulia)
INSERT INTO ricetta (id, titolo, descrizione, procedimento, tempo_preparazione, difficoltà, categoria, data_pubblicazione, utente_id) VALUES (9, 'Bruschetta Classica', 'L''antipasto perfetto.', '1. Tagliare i pomodorini maturi a cubetti piccoli, metterli in una ciotola e condirli con olio extravergine d''oliva, sale, un pizzico di origano e basilico fresco spezzettato a mano. Lasciar marinare per 10 minuti.
2. Tagliare il pane casereccio (ideale il pane pugliese o toscano) a fette spesse circa un centimetro e mezzo.
3. Tostare le fette di pane su una griglia calda o sotto il grill del forno per 2-3 minuti per lato, fino a ottenere una leggera doratura e la superficie croccante.
4. Strofinare immediatamente uno spicchio d''aglio tagliato a metà sulla superficie calda del pane, in modo che il calore ne esalti il profumo.
5. Distribuire generosamente il condimento di pomodorini sulle fette di pane tostato.
6. Completare con un ultimo filo d''olio a crudo e servire immediatamente per mantenere la croccantezza del pane.', 15, 'Facile', 'Antipasti', '2025-01-30', 4) ON CONFLICT (id) DO NOTHING;

-- Ricetta 10: Amatriciana (Luca)
INSERT INTO ricetta (id, titolo, descrizione, procedimento, tempo_preparazione, difficoltà, categoria, data_pubblicazione, utente_id) VALUES (10, 'Bucatini all''Amatriciana', 'Sapore deciso.', '1. Tagliare il guanciale a listarelle spesse circa mezzo centimetro, eliminando la cotenna se presente.
2. In una padella capiente, rosolare il guanciale a fuoco medio-basso senza aggiungere olio, lasciando che il grasso si sciolga lentamente e le listarelle diventino dorate e croccanti.
3. Togliere il guanciale dalla padella e metterlo da parte. Eliminare parte del grasso in eccesso, lasciandone circa due cucchiai nella padella.
4. Nella stessa padella, aggiungere un pizzico di peperoncino e versare i pomodori pelati (o i pelati San Marzano schiacciati a mano). Cuocere a fuoco medio per 15-20 minuti fino a ottenere un sugo denso e saporito.
5. Nel frattempo, cuocere i bucatini in abbondante acqua salata, scolandoli 2 minuti prima del tempo indicato sulla confezione.
6. Rimettere il guanciale nella padella con il sugo e unire i bucatini scolati. Saltare la pasta nel sugo a fuoco vivo per un paio di minuti, aggiungendo un po'' di acqua di cottura se necessario.
7. Togliere dal fuoco e aggiungere una generosa spolverata di pecorino romano grattugiato, mescolando rapidamente.
8. Impiattare e servire con altro pecorino e una macinata di pepe nero.', 30, 'Media', 'Primi', '2025-02-01', 5) ON CONFLICT (id) DO NOTHING;

-- 3. INSERIMENTO INGREDIENTI (Esempi per alcune ricette)

-- Ingredienti Carbonara (ID 1)
INSERT INTO ingrediente (id, nome, quantita, unita_misura, ricetta_id) VALUES (1, 'Spaghetti', 400, 'g', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO ingrediente (id, nome, quantita, unita_misura, ricetta_id) VALUES (2, 'Guanciale', 150, 'g', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO ingrediente (id, nome, quantita, unita_misura, ricetta_id) VALUES (3, 'Uova', 4, 'pezzi', 1) ON CONFLICT (id) DO NOTHING;

-- Ingredienti Tiramisù (ID 2)
INSERT INTO ingrediente (id, nome, quantita, unita_misura, ricetta_id) VALUES (4, 'Mascarpone', 500, 'g', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO ingrediente (id, nome, quantita, unita_misura, ricetta_id) VALUES (5, 'Savoiardi', 300, 'g', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO ingrediente (id, nome, quantita, unita_misura, ricetta_id) VALUES (6, 'Caffè', 200, 'ml', 2) ON CONFLICT (id) DO NOTHING;

-- Ingredienti Pollo Curry (ID 4)
INSERT INTO ingrediente (id, nome, quantita, unita_misura, ricetta_id) VALUES (7, 'Petto di Pollo', 600, 'g', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO ingrediente (id, nome, quantita, unita_misura, ricetta_id) VALUES (8, 'Curry', 2, 'cucchiai', 4) ON CONFLICT (id) DO NOTHING;

-- 4. INSERIMENTO RECENSIONI (Cross-review tra utenti)

-- Luigi recensisce la Carbonara di Mario
INSERT INTO recensione (id, testo, voto, data_pubblicazione, utente_id, ricetta_id) VALUES (1, 'Buonissima, ma preferisco meno pepe.', 4, '2025-02-02', 2, 1) ON CONFLICT (id) DO NOTHING;

-- Mario recensisce il Tiramisù di Anna
INSERT INTO recensione (id, testo, voto, data_pubblicazione, utente_id, ricetta_id) VALUES (2, 'Il miglior tiramisù mai mangiato!', 5, '2025-02-03', 1, 2) ON CONFLICT (id) DO NOTHING;

-- Anna recensisce la Lasagna di Luigi
INSERT INTO recensione (id, testo, voto, data_pubblicazione, utente_id, ricetta_id) VALUES (3, 'Un po'' bruciata ai bordi, ma sapore ottimo.', 3, '2025-02-04', 3, 3) ON CONFLICT (id) DO NOTHING;

-- Giulia recensisce l''Insalata di Luca
INSERT INTO recensione (id, testo, voto, data_pubblicazione, utente_id, ricetta_id) VALUES (4, 'Fresca e leggera, perfetta per l''estate.', 5, '2025-02-05', 4, 5) ON CONFLICT (id) DO NOTHING;

-- Luca recensisce il Risotto di Mario
INSERT INTO recensione (id, testo, voto, data_pubblicazione, utente_id, ricetta_id) VALUES (5, 'Mancava un po'' di sale.', 3, '2025-02-06', 5, 6) ON CONFLICT (id) DO NOTHING;

-- 5. RESET SEQUENCE (Per evitare errori di chiavi duplicate al riavvio)
SELECT setval('utente_id_seq', (SELECT MAX(id) FROM utente));
SELECT setval('credentials_id_seq', (SELECT MAX(id) FROM credentials));
SELECT setval('ricetta_id_seq', (SELECT MAX(id) FROM ricetta));
SELECT setval('ingrediente_id_seq', (SELECT MAX(id) FROM ingrediente));
SELECT setval('recensione_id_seq', (SELECT MAX(id) FROM recensione));

-- 6. REMOVE CONSTRAINT (Per rendere la quantità opzionale)
ALTER TABLE ingrediente ALTER COLUMN quantita DROP NOT NULL;