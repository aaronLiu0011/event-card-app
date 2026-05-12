# Casual Connect（社内イベント交流アプリ）

ぐるっと東京のイベント一覧のような、赤いナビゲーション、検索チップ、カード型一覧を社内向けに簡略化した日文サイトです。

## 目的

- 主目的: 社内メンバーの交流促進
- 副目的: 社内知識共有の促進
- 方針: イベント、repo、profile だけに絞り、活動発起・参加・memo投稿の心理的負担を下げる

## 技術スタック

- Client: React + TypeScript + Vite
- Server: Spring Boot + JDBC + Gradle
- DB: PostgreSQL

## ファイル構成

```text
client/src/
  api/
  components/
  pages/
  models/
  utils/

server/src/main/java/padthai/
  controller/
    event/
    participant/
  dto/
    event/request/
    event/response/
    participant/request/
    participant/response/
  exception/
  model/
    event/
    participant/
  repository/
    event/
    participant/
  service/
    event/
    participant/
```

## ローカル起動

Docker は使いません。PostgreSQL をローカルで起動して、以下の DB / ユーザーを作成してください。

```sql
CREATE DATABASE casual_connect;
CREATE USER casual_user WITH PASSWORD 'casual_pass';
GRANT ALL PRIVILEGES ON DATABASE casual_connect TO casual_user;
```

Server:

```bash
gradle :server:bootRun
```

Client:

```bash
cd client
npm install
npm run dev
```

- Client: http://localhost:5173
- Server: http://localhost:8080

初期ログイン例:

- メール: `aiko.tanaka@example.com`
- パスワード: `password`

## API Spec

API 仕様は [`docs/api-spec.md`](docs/api-spec.md) を参照してください。

## スコープ

イベント、repo、プロフィールのみを扱います。收藏、浏览历史、地图、日历は含めません。
