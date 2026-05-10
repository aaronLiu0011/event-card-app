import { useEffect, useMemo, useState } from 'react';
import { api } from '../api/client';
import { EventForm, emptyEvent } from '../components/EventForm';
import { EventList } from '../components/EventList';
import { Header } from '../components/Header';
import { LoginPanel } from '../components/LoginPanel';
import { EventModal, ProfileModal } from '../components/Modals';
import { ReportList } from '../components/ReportList';
import { EventCard, EventPayload, Profile, Report, User } from '../models/types';
import { categories, popularTags } from '../utils/format';

export function HomePage() {
  const [user, setUser] = useState<User | null>(null);
  const [events, setEvents] = useState<EventCard[]>([]);
  const [reports, setReports] = useState<Report[]>([]);
  const [selected, setSelected] = useState<EventCard | null>(null);
  const [profile, setProfile] = useState<Profile | null>(null);
  const [category, setCategory] = useState('');
  const [tag, setTag] = useState('');
  const [editingId, setEditingId] = useState<number | null>(null);
  const [eventForm, setEventForm] = useState<EventPayload>(emptyEvent());
  const userId = useMemo(() => user?.id ?? 0, [user]);

  const loadEvents = () => api.events(category, tag, userId).then(setEvents);
  const loadReports = () => api.reports().then(setReports);

  useEffect(() => { loadEvents(); }, [category, tag, userId]);
  useEffect(() => { loadReports(); }, []);
  useEffect(() => { setEventForm(emptyEvent(user ?? undefined)); }, [user]);

  const saveEvent = () => {
    if (!user) return alert('ログインしてください');
    const payload = { ...eventForm, ownerId: user.id };
    const action = editingId ? api.updateEvent(editingId, payload) : api.createEvent(payload);
    action.then(() => { setEditingId(null); setSelected(null); setEventForm(emptyEvent(user)); loadEvents(); });
  };

  const editEvent = (event: EventCard) => {
    setEditingId(event.id);
    setEventForm({ title: event.title, category: event.category, tags: event.tags, startAt: event.startAt.substring(0, 16), location: event.location, capacity: event.capacity, imageUrl: event.imageUrl, description: event.description, ownerId: event.ownerId });
  };

  const joinToggle = (event: EventCard) => {
    if (!user) return alert('ログインしてください');
    (event.joined ? api.cancel(event.id, user.id) : api.join(event.id, user.id)).then(loadEvents);
  };

  const openProfile = () => user && api.profile(user.id).then(setProfile);

  return <>
    <Header user={user} onProfile={openProfile} />
    <section className="hero"><p>EVENT</p><h1>部署を越えて、話す・学ぶ・残す。</h1><span>小さな活動募集と気軽なmemo投稿で、社内交流と知識共有を続けやすくします。</span></section>
    <main className="layout">
      <aside className="side">
        <LoginPanel user={user} onLogin={(email, password) => api.login(email, password).then(setUser)} />
        <div className="panel"><h3>詳しく探す</h3><button onClick={() => setCategory('')}>全て</button>{categories.map(c => <button key={c} onClick={() => setCategory(c)}>{c}</button>)}<div className="chips">{popularTags.map(t => <span onClick={() => setTag(t)} key={t}>#{t}</span>)}</div></div>
        <EventForm value={eventForm} onChange={setEventForm} onSubmit={saveEvent} />
      </aside>
      <section>
        <div className="tabs"><button>本日開催</button><button>今週開催</button><button>今月開催</button><button className="post">イベントの投稿</button></div>
        <h2>イベント一覧</h2>
        <EventList events={events} onSelect={setSelected} onJoinToggle={joinToggle} />
        <h2>repo 一覧</h2>
        <ReportList events={events} reports={reports} user={user} onCreate={payload => api.createReport(payload).then(loadReports)} onLike={id => api.like(id).then(loadReports)} onComment={(id, comment) => api.comment(id, comment).then(loadReports)} />
      </section>
    </main>
    {selected && <EventModal event={selected} canEdit={user?.id === selected.ownerId} onClose={() => setSelected(null)} onEdit={() => editEvent(selected)} onDelete={() => user && api.deleteEvent(selected.id, user.id).then(() => { setSelected(null); loadEvents(); })} />}
    {profile && <ProfileModal profile={profile} setProfile={setProfile} onSave={() => api.updateProfile(profile.user).then(setProfile)} onClose={() => setProfile(null)} />}
  </>;
}
