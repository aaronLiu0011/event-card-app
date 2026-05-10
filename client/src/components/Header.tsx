import { User } from '../models/types';

type Props = { user: User | null; onProfile: () => void };

export function Header({ user, onProfile }: Props) {
  return <header className="top">
    <div className="brand">CASUAL CONNECT <small>社内イベント掲示板</small></div>
    <nav><a>EVENT</a><a>REPO</a><a>PROFILE</a></nav>
    {user ? <button onClick={onProfile}>{user.name}</button> : null}
  </header>;
}
