import { FormEvent } from 'react';
import { EventPayload, User } from '../models/types';
import { categories } from '../utils/format';

export const emptyEvent = (user?: User): EventPayload => ({
  title: '', category: '交流会', tags: '', startAt: '', location: '', capacity: 8,
  imageUrl: 'https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?auto=format&fit=crop&w=900&q=80',
  description: '', ownerId: user?.id ?? 0
});

type Props = { value: EventPayload; onChange: (value: EventPayload) => void; onSubmit: () => void };

export function EventForm({ value, onChange, onSubmit }: Props) {
  const submit = (event: FormEvent) => { event.preventDefault(); onSubmit(); };
  const set = (key: keyof EventPayload, next: string | number) => onChange({ ...value, [key]: next });

  return <form className="panel" onSubmit={submit}>
    <h3>活動を発起する</h3>
    <input placeholder="タイトル" value={value.title} onChange={e => set('title', e.target.value)} />
    <input placeholder="タグ（カンマ区切り）" value={value.tags} onChange={e => set('tags', e.target.value)} />
    <input type="datetime-local" value={value.startAt} onChange={e => set('startAt', e.target.value)} />
    <input placeholder="場所" value={value.location} onChange={e => set('location', e.target.value)} />
    <input placeholder="画像URL" value={value.imageUrl} onChange={e => set('imageUrl', e.target.value)} />
    <select value={value.category} onChange={e => set('category', e.target.value)}>{categories.map(c => <option key={c}>{c}</option>)}</select>
    <input type="number" value={value.capacity} onChange={e => set('capacity', Number(e.target.value))} />
    <textarea placeholder="内容" value={value.description} onChange={e => set('description', e.target.value)} />
    <button>保存</button>
  </form>;
}
