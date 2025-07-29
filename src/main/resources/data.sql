DELETE from book;

INSERT INTO book (id, title, width, height, color, x, y)
VALUES (1001, 'book 1', 50, 120, '#D9722C', 100, 100),
       (1002, 'book 2', 60, 120, '#E84636', 160, 100),
       (1003, 'book 3', 80, 140, '#ffb72b', 230, 80)
ON CONFLICT (id) DO NOTHING;