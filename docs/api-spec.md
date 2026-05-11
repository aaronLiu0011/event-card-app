# API Spec

## 1. Common

### Base URL

```text
/api
```

### Error Response

エラー時は以下の形式で返す。

```json
{
  "detail": "User Name: can not be empty",
  "instance": "/api/users",
  "status": 400,
  "title": "Bad Request"
}
```

---

## 2. Auth

### 2.1 Simple Login

```http
POST /api/auth/simple-login
```

#### Request

```json
{
  "email": "user@example.com",
  "name": "User Name"
}
```

#### Response

```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "User Name",
  "profileImageUrl": null
}
```

---

## 3. Users

### 3.1 Get User Profile

```http
GET /api/users/{userId}
```

#### Response

```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "User Name",
  "bio": "I am interested in sports and technology.",
  "field": "Engineering",
  "profileImageUrl": "https://example.com/profile.jpg"
}
```

---

### 3.2 Update User Profile

```http
PUT /api/users/{userId}
```

#### Request

```json
{
  "name": "User Name",
  "bio": "I am interested in sports and technology.",
  "field": "Engineering",
  "profileImageUrl": "https://example.com/profile.jpg"
}
```

#### Response

```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "User Name",
  "bio": "I am interested in sports and technology.",
  "field": "Engineering",
  "profileImageUrl": "https://example.com/profile.jpg"
}
```

---

### 3.3 Get Events Created by User

```http
GET /api/users/{userId}/events
```

#### Response

```json
[
  {
    "id": 1,
    "title": "Football Event",
    "eventStartTime": "2026-05-20T10:00:00",
    "eventEndTime": "2026-05-20T12:00:00",
    "capacity": 20,
    "participantCount": 5,
    "location": "Tokyo",
    "eventImageUrl": "https://example.com/event.jpg",
    "eventStatus": "OPEN"
  }
]
```

---

### 3.4 Get Events Joined by User

```http
GET /api/users/{userId}/joined-events
```

#### Response

```json
[
  {
    "id": 2,
    "title": "Study Session",
    "eventStartTime": "2026-05-21T18:00:00",
    "eventEndTime": "2026-05-21T20:00:00",
    "capacity": 10,
    "participantCount": 3,
    "location": "Shibuya",
    "eventImageUrl": "https://example.com/study.jpg",
    "eventStatus": "OPEN",
    "owner": {
      "id": 1,
      "name": "User Name"
    }
  }
]
```

---

## 4. Events

### 4.1 Get Event List

```http
GET /api/events
```

#### Query Parameters

```text
status
page
size
```

#### Response

```json
{
  "items": [
    {
      "id": 1,
      "title": "Football Event",
      "eventStartTime": "2026-05-20T10:00:00",
      "eventEndTime": "2026-05-20T12:00:00",
      "capacity": 20,
      "participantCount": 5,
      "location": "Tokyo",
      "eventImageUrl": "https://example.com/event.jpg",
      "eventStatus": "OPEN",
      "owner": {
        "id": 1,
        "name": "User Name"
      }
    }
  ],
  "page": 0,
  "size": 20,
  "totalItems": 1,
  "totalPages": 1
}
```

---

### 4.2 Get Event Detail

```http
GET /api/events/{eventId}
```

#### Response

```json
{
  "id": 1,
  "title": "Football Event",
  "eventStartTime": "2026-05-20T10:00:00",
  "eventEndTime": "2026-05-20T12:00:00",
  "capacity": 20,
  "participantCount": 5,
  "location": "Tokyo",
  "description": "Let us play football together.",
  "targetUser": "Beginners are welcome.",
  "eventImageUrl": "https://example.com/event.jpg",
  "eventStatus": "OPEN",
  "owner": {
    "id": 1,
    "name": "User Name",
    "profileImageUrl": "https://example.com/profile.jpg"
  },
  "type": {
    "id": 1,
    "name": "ランチ"
  },
  "tags": [
    {
      "id": 1,
      "name": "雑談"
    },
    {
      "id": 2,
      "name": "技術"
    }
  ]
}
```

---

### 4.3 Create Event

```http
POST /api/events
```

#### Request

```json
{
  "title": "Football Event",
  "eventStartTime": "2026-05-20T10:00:00",
  "eventEndTime": "2026-05-20T12:00:00",
  "capacity": 20,
  "location": "Tokyo",
  "description": "Let us play football together.",
  "targetUser": "Beginners are welcome.",
  "eventImageUrl": "https://example.com/event.jpg",
  "eventStatus": "OPEN",
  "ownerId": 1,
  "typeId": 1,
  "tagIds": [1, 2]
}
```

#### Response

```json
{
  "id": 1,
  "title": "Football Event",
  "eventStartTime": "2026-05-20T10:00:00",
  "eventEndTime": "2026-05-20T12:00:00",
  "capacity": 20,
  "participantCount": 0,
  "location": "Tokyo",
  "description": "Let us play football together.",
  "targetUser": "Beginners are welcome.",
  "eventImageUrl": "https://example.com/event.jpg",
  "eventStatus": "OPEN",
  "owner": {
    "id": 1,
    "name": "User Name"
  },
  "type": {
    "id": 1,
    "name": "ランチ"
  },
  "tags": [
    {
      "id": 1,
      "name": "雑談"
    },
    {
      "id": 2,
      "name": "技術"
    }
  ]
}
```

---

### 4.4 Update Event

```http
PUT /api/events/{eventId}
```

#### Request

```json
{
  "title": "Updated Football Event",
  "eventStartTime": "2026-05-20T10:00:00",
  "eventEndTime": "2026-05-20T12:30:00",
  "capacity": 25,
  "location": "Tokyo",
  "description": "Updated event description.",
  "targetUser": "Anyone interested in sports.",
  "eventImageUrl": "https://example.com/updated-event.jpg",
  "eventStatus": "OPEN",
  "ownerId": 1,
  "typeId": 1,
  "tagIds": [1, 3]
}
```

#### Response

```json
{
  "id": 1,
  "title": "Updated Football Event",
  "eventStartTime": "2026-05-20T10:00:00",
  "eventEndTime": "2026-05-20T12:30:00",
  "capacity": 25,
  "participantCount": 5,
  "location": "Tokyo",
  "description": "Updated event description.",
  "targetUser": "Anyone interested in sports.",
  "eventImageUrl": "https://example.com/updated-event.jpg",
  "eventStatus": "OPEN",
  "owner": {
    "id": 1,
    "name": "User Name"
  },
  "type": {
    "id": 1,
    "name": "ランチ"
  },
  "tags": [
    {
      "id": 1,
      "name": "雑談"
    },
    {
      "id": 3,
      "name": "スポーツ"
    }
  ]
}
```

---

### 4.5 Delete Event

```http
DELETE /api/events/{eventId}
```

#### Request

```json
{
  "ownerId": 1
}
```

#### Response

```text
No Content
```

---

## 5. Event Participants

### 5.1 Join Event

```http
POST /api/events/{eventId}/participants
```

#### Request

```json
{
  "userId": 2
}
```

#### Response

```json
{
  "id": 1,
  "eventId": 1,
  "userId": 2,
  "joinedAt": "2026-05-11T20:30:00"
}
```

---

### 5.2 Cancel Event Participation

```http
DELETE /api/events/{eventId}/participants/{userId}
```

#### Response

```text
No Content
```

---

### 5.3 Get Event Participants

```http
GET /api/events/{eventId}/participants
```

#### Response

```json
[
  {
    "id": 2,
    "name": "User A",
    "email": "user-a@example.com",
    "profileImageUrl": "https://example.com/user-a.jpg",
    "joinedAt": "2026-05-11T20:30:00"
  },
  {
    "id": 3,
    "name": "User B",
    "email": "user-b@example.com",
    "profileImageUrl": null,
    "joinedAt": "2026-05-11T20:35:00"
  }
]
```

---

## 6. Types

### 6.1 Get Type List

```http
GET /api/types
```

#### Response

```json
[
  {
    "id": 1,
    "name": "1on1"
  },
  {
    "id": 2,
    "name": "ランチ"
  },
  {
    "id": 3,
    "name": "交流会"
  }
]
```

---

## 7. Tags

### 7.1 Get Tag List

```http
GET /api/tags
```

#### Response

```json
[
  {
    "id": 1,
    "name": "雑談"
  },
  {
    "id": 2,
    "name": "技術"
  },
  {
    "id": 3,
    "name": "スポーツ"
  }
]
```

---

## 8. MVP Priority

### Must

| Feature | API |
|---|---|
| 簡易ログイン | `POST /api/auth/simple-login` |
| イベント一覧表示 | `GET /api/events` |
| イベント詳細表示 | `GET /api/events/{eventId}` |
| イベント登録 | `POST /api/events` |
| イベント参加 | `POST /api/events/{eventId}/participants` |

### Should

| Feature | API |
|---|---|
| 個人ホームページ表示 | `GET /api/users/{userId}` |
| プロフィール編集 | `PUT /api/users/{userId}` |
| 作成イベント一覧 | `GET /api/users/{userId}/events` |
| 参加イベント一覧 | `GET /api/users/{userId}/joined-events` |
| イベント編集 | `PUT /api/events/{eventId}` |
| イベント削除 | `DELETE /api/events/{eventId}` |
| 参加者一覧表示 | `GET /api/events/{eventId}/participants` |
| 参加キャンセル | `DELETE /api/events/{eventId}/participants/{userId}` |
| イベント形式一覧 | `GET /api/types` |
| タグ一覧 | `GET /api/tags` |
