import { EventCard, EventPayload, Profile, Report, User } from '../models/types';

const API = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';
const headers = { 'Content-Type': 'application/json' };

const request = async <T>(path: string, init?: RequestInit): Promise<T> => {
  const response = await fetch(`${API}${path}`, init);
  if (!response.ok) throw new Error(await response.text());
  if (response.status === 204) return undefined as T;
  return response.json();
};

export const api = {
  login: (email: string, password: string) => request<User>('/api/login', { method: 'POST', headers, body: JSON.stringify({ email, password }) }),
  events: (category: string, tag: string, userId = 0) => request<EventCard[]>(`/api/events?category=${category}&tag=${tag}&userId=${userId}`),
  createEvent: (payload: EventPayload) => request<EventCard>('/api/events', { method: 'POST', headers, body: JSON.stringify(payload) }),
  updateEvent: (id: number, payload: EventPayload) => request<EventCard>(`/api/events/${id}`, { method: 'PUT', headers, body: JSON.stringify(payload) }),
  deleteEvent: (id: number, ownerId: number) => request<void>(`/api/events/${id}?ownerId=${ownerId}`, { method: 'DELETE' }),
  join: (id: number, userId: number) => request<void>(`/api/events/${id}/join`, { method: 'POST', headers, body: JSON.stringify({ userId }) }),
  cancel: (id: number, userId: number) => request<void>(`/api/events/${id}/join?userId=${userId}`, { method: 'DELETE' }),
  reports: () => request<Report[]>('/api/reports'),
  createReport: (payload: { eventId: number; authorId: number; title: string; body: string; visibility: string }) => request<Report>('/api/reports', { method: 'POST', headers, body: JSON.stringify(payload) }),
  like: (id: number) => request<void>(`/api/reports/${id}/like`, { method: 'POST' }),
  comment: (id: number, comment: string) => request<void>(`/api/reports/${id}/comments`, { method: 'POST', headers, body: JSON.stringify({ comment }) }),
  profile: (id: number) => request<Profile>(`/api/users/${id}`),
  updateProfile: (user: User) => request<Profile>(`/api/users/${user.id}`, { method: 'PUT', headers, body: JSON.stringify(user) })
};
