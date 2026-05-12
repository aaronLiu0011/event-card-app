import { EventCard } from '../models/types';
import { formatDateTime } from '../utils/format';

type Props = { events: EventCard[]; onSelect: (event: EventCard) => void; onJoinToggle: (event: EventCard) => void };

export function EventList({ events, onSelect, onJoinToggle }: Props) {
  return <div className="cards">{events.map(event => <article className="card" key={event.id}>
    <img src={event.imageUrl} alt="" />
    <div>
      <b>{event.category}</b>
      <h3 onClick={() => onSelect(event)}>{event.title}</h3>
      <p>{formatDateTime(event.startAt)} / {event.location}</p>
      <p>{event.description}</p>
      <div>{event.tags.split(',').filter(Boolean).map(tag => <span className="tag" key={tag}>#{tag}</span>)}</div>
      <button onClick={() => onJoinToggle(event)}>{event.participants}/{event.capacity} {event.joined ? '参加取消' : '参加する'}</button>
    </div>
  </article>)}</div>;
}
