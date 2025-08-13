DELETE from book;

INSERT INTO book (id, title, width, height, color, x, y)
VALUES (1001, 'A Court of Mist and Fury', 62, 200, '#01a596', 100, 100),
       (1000, 'A Court of Thorns and Roses', 42, 200, '#e83c4e', 160, 100),
       (999, 'A Curse for True Love', 39, 240, '#070101', 160, 100),
       (998, 'The Ballad of Never After', 42, 240, '#252d5e', 160, 100),
       (997, 'Once Upon a Broken Heart', 43, 240, '#070101', 160, 100),
       (996, 'Spectacular', 21, 240, '#c91b22', 160, 100),
       (995, 'Finale', 48, 200, '#b65b3f', 160, 100),
       (994, 'Legendary', 43, 200, '#131341', 160, 100),
       (993, 'Caraval', 41, 200, '#000000', 160, 100),
       (992, 'Murder on the Clifftops', 27, 230, '#08122f', 160, 100),
       (991, 'Two Twisted Crowns', 44, 200, '#7b9e91', 160, 100),
       (990, 'One Dark Window', 39, 200, '#165445', 160, 100),
       (989, 'Surm Ivy House''is', 23, 230, '#002a51', 160, 100),
       (988, 'Vow of Thieves', 50, 200, '#c5201f', 160, 100),
       (987, 'Dance of Thieves', 53, 200, '#102232', 160, 100),
       (986, 'Hackers', 46, 200, '#373737', 160, 100),
       (985, 'Paadimehe tõed', 13, 150, '#fdbb1e', 160, 100),
       (984, 'Onyx Storm', 76, 200, '#0b0d0c', 160, 100),
       (983, 'Iron Flame', 79, 200, '#d38542', 160, 100),
       (982, 'Fourth Wing', 56, 200, '#e2ba64', 160, 100)
ON CONFLICT (id) DO NOTHING;