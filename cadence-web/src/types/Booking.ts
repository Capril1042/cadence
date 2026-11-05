import type { ClassSession } from './ClassSession'
import type { Member } from './Member'

export interface Booking {
    id: number
    member: Member
    classSession: ClassSession
    bookingDate: string
    status: string
}