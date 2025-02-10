

export interface Subject {
    id: number;
    name: string;
    hours: number;
    sessionsPerWeek: number;
    sessionsScheduled: number;
    sessionDurations: number[];
  
    // Method signatures
    getHours(): number;
    canScheduleMoreSessions(): boolean;
    incrementSessionsScheduled(): void;
    toString(): string;
  }
  