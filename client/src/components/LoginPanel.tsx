import { FormEvent } from 'react';
import { User } from '../models/types';

type Props = { user: User | null; onLogin: (email: string, password: string) => void };

export function LoginPanel({ user, onLogin }: Props) {
  const submit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    onLogin(String(data.get('email')), String(data.get('password')));
  };

  return <div className="panel">
    <h3>ログイン</h3>
    {user ? <p>{user.department}<br />{user.name}</p> : <form onSubmit={submit}>
      <input name="email" defaultValue="aiko.tanaka@example.com" />
      <input name="password" type="password" defaultValue="password" />
      <button>ログイン</button>
    </form>}
  </div>;
}
