INSERT INTO posts (title, content, created_at, updated_at, views)
SELECT 'Sample Post 1', 'Sample post content 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE no = 1);

INSERT INTO posts (title, content, created_at, updated_at, views)
SELECT 'Sample Post 2', 'Sample post content 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 10
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE no = 2);

INSERT INTO posts (title, content, created_at, updated_at, views)
SELECT 'Sample Post 3', 'Sample post content 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 15
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE no = 3);

INSERT INTO posts (title, content, created_at, updated_at, views)
SELECT 'Sample Post 4', 'Sample post content 4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 20
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE no = 4);

INSERT INTO posts (title, content, created_at, updated_at, views)
SELECT 'Sample Post 5', 'Sample post content 5', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 25
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE no = 5);

INSERT INTO posts (title, content, created_at, updated_at, views)
SELECT 'Sample Post 6', 'Sample post content 6', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE no = 6);

INSERT INTO posts (title, content, created_at, updated_at, views)
SELECT 'Sample Post 7', 'Sample post content 7', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 35
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE no = 7);

INSERT INTO posts (title, content, created_at, updated_at, views)
SELECT 'Sample Post 8', 'Sample post content 8', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 40
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE no = 8);

INSERT INTO posts (title, content, created_at, updated_at, views)
SELECT 'Sample Post 9', 'Sample post content 9', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 45
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE no = 9);

INSERT INTO posts (title, content, created_at, updated_at, views)
SELECT 'Sample Post 10', 'Sample post content 10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 50
WHERE NOT EXISTS (SELECT 1 FROM posts WHERE no = 10);
