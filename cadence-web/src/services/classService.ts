import type { ClassSession } from '../types/ClassSession'

export async function getUpcomingClasses(): Promise<ClassSession[]> {
    const response = await fetch('http://localhost:8080/api/classes')

    if (!response.ok) {
        throw new Error('Failed to fetch upcoming classes')
    }

    return response.json()
}

export async function bookClass(classId: number): Promise<ClassSession> {
    const response = await fetch(
        `http://localhost:8080/api/classes/${classId}/book`,
        {
            method: 'POST',
        }
    )

    if (!response.ok) {
        throw new Error('Unable to book this class')
    }

    return response.json()
}