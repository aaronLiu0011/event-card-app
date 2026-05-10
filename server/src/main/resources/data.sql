INSERT INTO users (name, email, password, department, field, bio) VALUES
('田中 愛子', 'aiko.tanaka@example.com', 'password', 'プロダクト部', 'UX / リサーチ', '社内の偶発的な出会いを増やしたいです。'),
('佐藤 健', 'ken.sato@example.com', 'password', '開発部', 'Backend / PostgreSQL', '小さな勉強会と輪読会が好きです。')
ON CONFLICT (email) DO NOTHING;

INSERT INTO events (title, category, tags, start_at, location, capacity, image_url, description, owner_id)
SELECT 'ランチ交流：新規事業アイデアを話そう', '交流会', 'ランチ,新規事業,雑談', '2026-06-05 12:00:00', '本社 12F カフェ', 12, 'https://images.unsplash.com/photo-1556761175-b413da4baf72?auto=format&fit=crop&w=900&q=80', '部署を越えて最近気になる課題やアイデアをゆるく共有します。準備不要です。', id FROM users WHERE email='aiko.tanaka@example.com'
ON CONFLICT (title) DO NOTHING;

INSERT INTO events (title, category, tags, start_at, location, capacity, image_url, description, owner_id)
SELECT 'PostgreSQL JDBC ミニハンズオン', '勉強会', '技術,JDBC,PostgreSQL', '2026-06-12 18:30:00', 'オンライン', 20, 'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=900&q=80', 'Spring Boot から JDBC で小さな CRUD を作る会です。初心者歓迎。', id FROM users WHERE email='ken.sato@example.com'
ON CONFLICT (title) DO NOTHING;
