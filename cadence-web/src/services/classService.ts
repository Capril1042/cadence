import type { ClassSession } from '../types/ClassSession'
import type { Booking } from '../types/Booking'

export async function getUpcomingClasses(): Promise<ClassSession[]> {
    const response = await fetch('http://localhost:8080/api/classes')

    if (!response.ok) {
        throw new Error('Failed to fetch upcoming classes')
    }

    return response.json()
}

export async function bookClass(
    memberId: number,
    classId: number
): Promise<Booking> {
    const response = await fetch(
        `http://localhost:8080/api/bookings?memberId=${memberId}&classSessionId=${classId}`,
        {
            method: 'POST',
        }
    )

    if (!response.ok) {
        const error = await response.json()
        throw new Error(error.error || 'Unable to book this class')
    }

    return response.json()
}

export async function getBookings(): Promise<Booking[]> {
    const response = await fetch('http://localhost:8080/api/bookings')

    if (!response.ok) {
        throw new Error('Failed to fetch bookings')
    }

    const data: Booking[] = await response.json()
    return data
}