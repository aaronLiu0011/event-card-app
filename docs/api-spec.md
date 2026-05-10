# Casual Connect API Spec

社内イベント交流アプリ（Casual Connect）の REST API 仕様です。  
Base URL はローカル起動時の標準値として `http://localhost:8080` を想定します。

## 共通仕様

- Content-Type: `application/json`
- 認証: 現時点では簡易ログインのみ。API トークン / Cookie セッションは未実装です。
- 日時形式:
  - Request: `YYYY-MM-DDTHH:mm` 例: `2026-06-05T12:00`
  - Response: Spring Boot の `LocalDateTime` JSON 形式。例: `2026-06-05T12:00:00`
- エラー:
  - 存在しないデータなどは `404 Not Found` を返します。
  - エラーボディ例: `{ "message": "..." }`

## データモデル

### User

```json
{
  "id": 1,
  "name": "田中 愛子",
  "email": "aiko.tanaka@example.com",
  "department": "プロダクト部",
  "field": "UX / リサーチ",
  "bio": "社内の偶発的な出会いを増やしたいです。"
}
```

### Event

```json
{
  "id": 1,
  "title": "ランチ交流：新規事業アイデアを話そう",
  "category": "交流会",
  "tags": "ランチ,新規事業,雑談",
  "startAt": "2026-06-05T12:00:00",
  "location": "本社 12F カフェ",
  "capacity": 12,
  "imageUrl": "https://example.com/image.jpg",
  "description": "部署を越えて最近気になる課題やアイデアをゆるく共有します。",
  "ownerId": 1,
  "ownerName": "田中 愛子",
  "participants": 3,
  "joined": false
}
```

### Report

```json
{
  "id": 1,
  "eventId": 1,
  "authorId": 1,
  "eventTitle": "ランチ交流：新規事業アイデアを話そう",
  "authorName": "田中 愛子",
  "title": "交流会で得た気づき",
  "body": "次回は事前テーマを1つだけ決めると話しやすそうです。",
  "visibility": "社内公開",
  "likes": 5,
  "comments": "\nいいですね！",
  "createdAt": "2026-06-05T13:00:00"
}
```

### ProfileResponse

```json
{
  "user": { "id": 1, "name": "田中 愛子", "email": "aiko.tanaka@example.com", "department": "プロダクト部", "field": "UX / リサーチ", "bio": "..." },
  "events": [],
  "reports": []
}
```

## Auth API

### Login

ログイン情報に一致するユーザーを返します。

```http
POST /api/login
```

Request body:

```json
{
  "email": "aiko.tanaka@example.com",
  "password": "password"
}
```

Response `200 OK`:

```json
{
  "id": 1,
  "name": "田中 愛子",
  "email": "aiko.tanaka@example.com",
  "department": "プロダクト部",
  "field": "UX / リサーチ",
  "bio": "社内の偶発的な出会いを増やしたいです。"
}
```

## Event API

### List events

イベント一覧を返します。`category` または `tag` で絞り込みできます。`userId` を指定すると `joined` が現在ユーザー基準になります。

```http
GET /api/events?category={category}&tag={tag}&userId={userId}
```

Query parameters:

| Name | Required | Description |
| --- | --- | --- |
| `category` | No | 完全一致カテゴリ。未指定または空文字なら全カテゴリ。 |
| `tag` | No | `tags` に対する部分一致。未指定または空文字なら全タグ。 |
| `userId` | No | 参加済み判定用ユーザー ID。未指定なら `0`。 |

Response `200 OK`:

```json
[
  {
    "id": 1,
    "title": "ランチ交流：新規事業アイデアを話そう",
    "category": "交流会",
    "tags": "ランチ,新規事業,雑談",
    "startAt": "2026-06-05T12:00:00",
    "location": "本社 12F カフェ",
    "capacity": 12,
    "imageUrl": "https://example.com/image.jpg",
    "description": "部署を越えて最近気になる課題やアイデアをゆるく共有します。",
    "ownerId": 1,
    "ownerName": "田中 愛子",
    "participants": 0,
    "joined": false
  }
]
```

### Get event detail

```http
GET /api/events/{id}?userId={userId}
```

Response `200 OK`: `Event`

### Create event

活動発起者がイベントを作成します。

```http
POST /api/events
```

Request body:

```json
{
  "title": "チーム横断 LT 会",
  "category": "LT",
  "tags": "技術,LT,交流",
  "startAt": "2026-07-01T18:30",
  "location": "本社 10F / オンライン",
  "capacity": 30,
  "imageUrl": "https://example.com/lt.jpg",
  "description": "5分LTで最近の学びを共有します。",
  "ownerId": 1
}
```

Response `200 OK`: 作成された `Event`

### Update event

作成者本人の `ownerId` が一致する場合にイベントを更新します。

```http
PUT /api/events/{id}
```

Request body: Create event と同じ `EventRequest`

Response `200 OK`: 更新後の `Event`

### Delete event

作成者本人の `ownerId` が一致する場合にイベントを削除します。

```http
DELETE /api/events/{id}?ownerId={ownerId}
```

Response `200 OK`: body なし

### Join event

イベントに参加します。同じユーザーの二重参加は無視されます。

```http
POST /api/events/{id}/join
```

Request body:

```json
{
  "userId": 1
}
```

Response `200 OK`: body なし

### Cancel event participation

イベント参加をキャンセルします。

```http
DELETE /api/events/{id}/join?userId={userId}
```

Response `200 OK`: body なし

## Report API

### List reports

repo 一覧を新しい順で返します。

```http
GET /api/reports
```

Response `200 OK`:

```json
[
  {
    "id": 1,
    "eventId": 1,
    "authorId": 1,
    "eventTitle": "ランチ交流：新規事業アイデアを話そう",
    "authorName": "田中 愛子",
    "title": "交流会で得た気づき",
    "body": "次回は事前テーマを1つだけ決めると話しやすそうです。",
    "visibility": "社内公開",
    "likes": 0,
    "comments": "",
    "createdAt": "2026-06-05T13:00:00"
  }
]
```

### Create report

活動後の参加者 memo / repo を投稿します。公開範囲は `visibility` で指定します。

```http
POST /api/reports
```

Request body:

```json
{
  "eventId": 1,
  "authorId": 1,
  "title": "交流会で得た気づき",
  "body": "話した内容のうち、公開できる学びだけを短く残します。",
  "visibility": "社内公開"
}
```

`visibility` values:

- `社内公開`
- `参加者のみ`
- `自分のみ`

Response `200 OK`: 作成された `Report`

### Like report

repo にいいねします。

```http
POST /api/reports/{id}/like
```

Response `200 OK`: body なし

### Comment report

repo にコメントを追加します。

```http
POST /api/reports/{id}/comments
```

Request body:

```json
{
  "comment": "次回参加したいです！"
}
```

Response `200 OK`: body なし

## Profile API

### Get profile

個人ページ情報を返します。ユーザー基本情報、発起したイベント、投稿した repo を含みます。

```http
GET /api/users/{id}
```

Response `200 OK`: `ProfileResponse`

### Update profile

個人ページを更新します。

```http
PUT /api/users/{id}
```

Request body:

```json
{
  "name": "田中 愛子",
  "department": "プロダクト部",
  "field": "UX / リサーチ",
  "bio": "社内の偶発的な出会いを増やしたいです。"
}
```

Response `200 OK`: 更新後の `ProfileResponse`

## Endpoint Summary

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/api/login` | ログイン |
| `GET` | `/api/events` | イベント一覧 / category・tag filter |
| `GET` | `/api/events/{id}` | イベント詳細 |
| `POST` | `/api/events` | イベント作成 |
| `PUT` | `/api/events/{id}` | イベント更新 |
| `DELETE` | `/api/events/{id}` | イベント削除 |
| `POST` | `/api/events/{id}/join` | イベント参加 |
| `DELETE` | `/api/events/{id}/join` | イベント参加取消 |
| `GET` | `/api/reports` | repo 一覧 |
| `POST` | `/api/reports` | repo 投稿 |
| `POST` | `/api/reports/{id}/like` | repo いいね |
| `POST` | `/api/reports/{id}/comments` | repo コメント |
| `GET` | `/api/users/{id}` | 個人ページ取得 |
| `PUT` | `/api/users/{id}` | 個人ページ更新 |
