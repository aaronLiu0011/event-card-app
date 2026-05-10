import { EventCard, Profile } from '../models/types';

type EventModalProps = { event: EventCard; canEdit: boolean; onClose: () => void; onEdit: () => void; onDelete: () => void };
export function EventModal({ event, canEdit, onClose, onEdit, onDelete }: EventModalProps) {
  return <div className="modal"><article><button onClick={onClose}>×</button>
    <img src={event.imageUrl} alt="" /><h2>{event.title}</h2><p>{event.description}</p><p>{event.ownerName} / {event.location}</p>
    {canEdit && <><button onClick={onEdit}>編集フォームへ反映</button><button onClick={onDelete}>削除</button></>}
  </article></div>;
}

type ProfileModalProps = { profile: Profile; setProfile: (profile: Profile) => void; onSave: () => void; onClose: () => void };
export function ProfileModal({ profile, setProfile, onSave, onClose }: ProfileModalProps) {
  const set = (key: keyof Profile['user'], value: string) => setProfile({ ...profile, user: { ...profile.user, [key]: value } });
  return <div className="modal"><article><button onClick={onClose}>×</button><h2>個人ページ</h2>
    <input value={profile.user.name} onChange={e => set('name', e.target.value)} />
    <input value={profile.user.department} onChange={e => set('department', e.target.value)} />
    <input value={profile.user.field} onChange={e => set('field', e.target.value)} />
    <textarea value={profile.user.bio} onChange={e => set('bio', e.target.value)} />
    <button onClick={onSave}>プロフィール更新</button>
    <h3>活動履歴</h3>{profile.events.map(event => <p key={event.id}>{event.title}</p>)}
    <h3>memo投稿</h3>{profile.reports.map(report => <p key={report.id}>{report.title}</p>)}
  </article></div>;
}
