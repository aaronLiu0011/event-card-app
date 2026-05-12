export type User = { id: number; name: string; email: string; department: string; field: string; bio: string };

export type EventCard = { id: number; title: string; category: string; tags: string; startAt: string; location: string; capacity: number; imageUrl: string; description: string; ownerId: number; ownerName: string; participants: number; joined: boolean };
export type Report = { id: number; eventId: number; authorId: number; eventTitle: string; authorName: string; title: string; body: string; visibility: string; likes: number; comments: string; createdAt: string };
export type Profile = { user: User; events: EventCard[]; reports: Report[] };
export type EventPayload = Omit<EventCard, 'id' | 'ownerName' | 'participants' | 'joined'>;
