DELETE from book;

INSERT INTO book (id, title, width, height, color, x, y)
VALUES (1002, 'A Court of Wings and Ruin', 73, 200, '#c24d96', 10, 750),
       (1001, 'A Court of Mist and Fury', 62, 200, '#01a596', 10, 750),
       (1000, 'A Court of Thorns and Roses', 42, 200, '#e83c4e', 10, 750),
       (999, 'A Curse for True Love', 39, 240, '#070101', 10, 750),
       (998, 'The Ballad of Never After', 42, 240, '#252d5e', 10, 750),
       (997, 'Once Upon a Broken Heart', 43, 240, '#070101', 10, 750),
       (996, 'Spectacular', 21, 240, '#c91b22', 10, 750),
       (995, 'Finale', 48, 200, '#b65b3f', 10, 750),
       (994, 'Legendary', 43, 200, '#131341', 10, 750),
       (993, 'Caraval', 41, 200, '#000000', 10, 750),
       (992, 'Murder on the Clifftops', 27, 230, '#08122f', 10, 750),
       (991, 'Two Twisted Crowns', 44, 200, '#7b9e91', 10, 750),
       (990, 'One Dark Window', 39, 200, '#165445', 10, 750),
       (989, 'Surm Ivy House''is', 23, 230, '#002a51', 10, 750),
       (988, 'Vow of Thieves', 50, 200, '#c5201f', 10, 750),
       (987, 'Dance of Thieves', 53, 200, '#102232', 10, 750),
       (986, 'Hackers', 46, 200, '#373737', 10, 750),
       (985, 'Paadimehe tõed', 13, 150, '#fdbb1e', 10, 750),
       (984, 'Onyx Storm', 76, 200, '#0b0d0c', 10, 750),
       (983, 'Iron Flame', 79, 200, '#d38542', 10, 750),
       (982, 'Fourth Wing', 56, 200, '#e2ba64', 10, 750)
ON CONFLICT (id) DO NOTHING;