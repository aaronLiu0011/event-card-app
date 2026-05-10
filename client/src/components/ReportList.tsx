import { FormEvent } from 'react';
import { EventCard, Report, User } from '../models/types';

type Props = { events: EventCard[]; reports: Report[]; user: User | null; onCreate: (payload: { eventId: number; authorId: number; title: string; body: string; visibility: string }) => void; onLike: (id: number) => void; onComment: (id: number, comment: string) => void };

export function ReportList({ events, reports, user, onCreate, onLike, onComment }: Props) {
  const submit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!user) return alert('ログインしてください');
    const data = new FormData(event.currentTarget);
    onCreate({ eventId: Number(data.get('eventId')), authorId: user.id, title: String(data.get('title')), body: String(data.get('body')), visibility: String(data.get('visibility')) });
    event.currentTarget.reset();
  };

  return <>
    <form className="repoForm" onSubmit={submit}>
      <select name="eventId">{events.map(event => <option value={event.id} key={event.id}>{event.title}</option>)}</select>
      <input name="title" placeholder="repoタイトル" />
      <select name="visibility"><option>社内公開</option><option>参加者のみ</option><option>自分のみ</option></select>
      <textarea name="body" placeholder="学び・気づき・次に話したいことだけでOK" />
      <button>repo投稿</button>
    </form>
    <div className="reports">{reports.map(report => <article className="report" key={report.id}>
      <span>{report.visibility}</span><h3>{report.title}</h3><p>{report.body}</p>
      <small>{report.eventTitle} / {report.authorName}</small>
      <div><button onClick={() => onLike(report.id)}>♡ {report.likes}</button><button onClick={() => { const c = prompt('コメント'); if (c) onComment(report.id, c); }}>コメント</button></div>
      <pre>{report.comments}</pre>
    </article>)}</div>
  </>;
}
